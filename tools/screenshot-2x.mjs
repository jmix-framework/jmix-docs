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
 *     [--wait 2500] [--width 1280] [--height 900] [--scale 2]
 *
 *   --click  a selector to click before capturing; repeatable (e.g. a login button)
 *   --eval   JavaScript evaluated in the page before capturing, to put the
 *            component into the state you want to photograph
 *   --wait   milliseconds to settle before capturing (default 2000); raise it
 *            when the component highlights or lays out asynchronously
 *
 * Afterwards, read the real pixel size out of the file rather than assuming it:
 *   node -e "const b=require('fs').readFileSync(P);console.log(b.readUInt32BE(16),b.readUInt32BE(20))"
 * and declare the image at half that width.
 *
 * Playwright is not a dependency of this repository; the script resolves it
 * from wherever it is already installed (an npx cache is normal) and uses the
 * browser Playwright has already downloaded.
 */

import { createRequire } from 'node:module';
import { execSync } from 'node:child_process';
import { readFileSync, mkdirSync } from 'node:fs';
import { dirname, resolve } from 'node:path';

function parseArgs(argv) {
    const opts = { click: [], wait: 2000, width: 1280, height: 900, scale: 2 };
    for (let i = 0; i < argv.length; i += 2) {
        const key = argv[i].replace(/^--/, '');
        const value = argv[i + 1];
        if (key === 'click') opts.click.push(value);
        else if (['wait', 'width', 'height', 'scale'].includes(key)) opts[key] = Number(value);
        else opts[key] = value;
    }
    return opts;
}

function loadPlaywright() {
    const require = createRequire(import.meta.url);
    try {
        return require('playwright');
    } catch {
        // not installed here: fall back to any copy npx has already cached
        const found = execSync(
            'find ~/.npm/_npx -maxdepth 4 -type d -name playwright 2>/dev/null | head -1',
            { encoding: 'utf8', shell: '/bin/bash' },
        ).trim();
        if (!found) {
            throw new Error('playwright not found; run: npx playwright@latest install chromium');
        }
        return require(found);
    }
}

function findInstalledChromium() {
    // Use the browser Playwright already downloaded, so the script does not
    // depend on the launcher's own revision pinning matching this machine.
    const out = execSync(
        'ls -d ~/Library/Caches/ms-playwright/chromium-*/chrome-mac*/ 2>/dev/null | tail -1',
        { encoding: 'utf8', shell: '/bin/bash' },
    ).trim();
    if (!out) return undefined;
    const candidates = execSync(
        `find "${out}" -maxdepth 4 -type f \\( -name 'Google Chrome for Testing' -o -name 'Chromium' \\) 2>/dev/null | head -1`,
        { encoding: 'utf8', shell: '/bin/bash' },
    ).trim();
    return candidates || undefined;
}

const opts = parseArgs(process.argv.slice(2));
for (const required of ['url', 'selector', 'out']) {
    if (!opts[required]) {
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
await page.waitForSelector(opts.selector);
if (opts.eval) {
    await page.evaluate(opts.eval);
}
await page.waitForTimeout(opts.wait);

const outPath = resolve(opts.out);
mkdirSync(dirname(outPath), { recursive: true });
await page.locator(opts.selector).screenshot({ path: outPath, type: 'png' });
await browser.close();

const png = readFileSync(outPath);
const width = png.readUInt32BE(16);
const height = png.readUInt32BE(20);
console.log(`${opts.out}  ${width}x${height} px  ${(png.length / 1024).toFixed(1)} KB  -> declare width="${width / 2}"`);
