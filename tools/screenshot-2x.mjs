/*
 * Take a documentation screenshot at 2x (Retina) resolution.
 *
 * Screenshots in this repository are captured at double resolution and declared
 * at half their pixel width (`image::foo.png[width="413"]` for an 826px-wide
 * file), so they stay sharp on high-DPI displays. This script exists because
 * that cannot be done with the Playwright MCP tool an agent normally drives:
 * its resize action takes a width and a height only, with no deviceScaleFactor,
 * so every capture it makes is 1x. Here the browser context is created directly
 * with `deviceScaleFactor: 2`.
 *
 * It captures ONE element, not the page, so the image is tight to the component
 * with no surrounding page chrome.
 *
 * Usage:
 *   node tools/screenshot-2x.mjs \
 *     --url http://localhost:8080/code-block \
 *     --selector 'jmix-ai-code-block#basicsBlock' \
 *     --out content/modules/ai-chat/images/aichat-code-block-overview.png \
 *     [--click 'vaadin-button:has-text("Log in")'] \
 *     [--eval "document.querySelector('#x').value = 'hello'"] \
 *     [--wait 2500] [--width 1280] [--height 900] [--scale 2] \
 *     [--round-corners auto]
 *
 *   --selector  the element to capture; repeatable: with several selectors the
 *            capture is the box that covers all of them, for a panel that is
 *            drawn by several elements (e.g. a header and a content area)
 *   --click  a selector to click before capturing; repeatable (e.g. a login button)
 *   --eval   JavaScript evaluated in the page before capturing, to put the
 *            component into the state you want to photograph
 *   --wait   milliseconds to settle before capturing (default 2000); raise it
 *            when the component highlights or lays out asynchronously
 *   --round-corners  make the pixels outside the element's rounded corners
 *            transparent, so no page background shows at the corners. `auto`
 *            reads the radii from the element's computed style (with several
 *            selectors, the top corners from the topmost element and the bottom
 *            corners from the bottommost one); four numbers
 *            in CSS pixels (`15,15,0,0`: top-left, top-right, bottom-right,
 *            bottom-left) set them explicitly. Select the element that draws
 *            the rounded border, e.g. 'vaadin-dialog [part="overlay"]' for a
 *            dialog. See lib/round-corners.mjs, which custom Playwright scripts
 *            can also import.
 *
 * Afterwards, read the real pixel size out of the file rather than assuming it:
 *   node -e "const b=require('fs').readFileSync(P);console.log(b.readUInt32BE(16),b.readUInt32BE(20))"
 * and declare the image at half that width.
 *
 * Playwright is not a dependency of this repository; the script resolves it
 * from wherever it is already installed (an npx cache is normal) and uses the
 * browser Playwright has already downloaded.
 */

import { readFileSync, mkdirSync } from 'node:fs';
import { dirname, resolve } from 'node:path';
import { loadPlaywright, findInstalledChromium } from './lib/playwright.mjs';
import { captureRounded, readRadii } from './lib/round-corners.mjs';

function parseArgs(argv) {
    const opts = { click: [], selector: [], wait: 2000, width: 1280, height: 900, scale: 2 };
    for (let i = 0; i < argv.length; i += 2) {
        const key = argv[i].replace(/^--/, '');
        const value = argv[i + 1];
        if (key === 'click' || key === 'selector') opts[key].push(value);
        else if (['wait', 'width', 'height', 'scale'].includes(key)) opts[key] = Number(value);
        else opts[key] = value;
    }
    return opts;
}

const opts = parseArgs(process.argv.slice(2));
for (const required of ['url', 'selector', 'out']) {
    if (!opts[required] || opts[required].length === 0) {
        console.error(`missing --${required}\n\nSee the comment at the top of this file for usage.`);
        process.exit(1);
    }
}

const { chromium } = loadPlaywright();
const executablePath = findInstalledChromium();

const browser = await chromium.launch(executablePath ? { executablePath } : {});
const context = await browser.newContext({
    viewport: { width: opts.width, height: opts.height },
    deviceScaleFactor: opts.scale,
    colorScheme: 'light',
});
const page = await context.newPage();

await page.goto(opts.url);
for (const selector of opts.click) {
    await page.locator(selector).click();
    await page.waitForTimeout(500);
}
if (page.url() !== opts.url) {
    await page.goto(opts.url); // a login redirect landed us elsewhere
}
for (const selector of opts.selector) {
    await page.waitForSelector(selector);
}
if (opts.eval) {
    await page.evaluate(opts.eval);
}
await page.waitForTimeout(opts.wait);

const outPath = resolve(opts.out);
mkdirSync(dirname(outPath), { recursive: true });
const targets = opts.selector.map(selector => page.locator(selector));
const boxes = await Promise.all(targets.map(target => target.boundingBox()));
const left = Math.min(...boxes.map(b => b.x));
const top = Math.min(...boxes.map(b => b.y));
const right = Math.max(...boxes.map(b => b.x + b.width));
const bottom = Math.max(...boxes.map(b => b.y + b.height));
const clip = { x: left, y: top, width: right - left, height: bottom - top };
if (opts['round-corners'] === 'auto') {
    const topmost = boxes.findIndex(b => b.y === top);
    const bottommost = boxes.findIndex(b => b.y + b.height === bottom);
    const [topLeft, topRight] = await readRadii(targets[topmost]);
    const [, , bottomRight, bottomLeft] = await readRadii(targets[bottommost]);
    await captureRounded(page, clip, [topLeft, topRight, bottomRight, bottomLeft], outPath);
} else if (opts['round-corners']) {
    await captureRounded(page, clip, opts['round-corners'].split(',').map(Number), outPath);
} else if (targets.length === 1) {
    await targets[0].screenshot({ path: outPath, type: 'png' });
} else {
    await page.screenshot({ clip, path: outPath, type: 'png' });
}
await browser.close();

const png = readFileSync(outPath);
const width = png.readUInt32BE(16);
const height = png.readUInt32BE(20);
console.log(`${opts.out}  ${width}x${height} px  ${(png.length / 1024).toFixed(1)} KB  -> declare width="${width / 2}"`);
