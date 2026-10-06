/*
 * Element screenshots with transparent corners.
 *
 * An element with rounded corners (a dialog, a card, a panel) is captured as a rectangle, so the
 * pixels outside its corners show the page behind it, and they look like grey bits on a docs page
 * with a different background or in the dark theme. captureRounded() takes an ordinary screenshot
 * and makes only the pixels outside the rounded corners transparent; everything inside stays as
 * the app draws it.
 *
 * Making the page background transparent instead does not work: Aura draws dialogs and some
 * panels with a translucent background, so the docs page would show through the element itself.
 *
 * Capture the element that draws the rounded border. Playwright selectors pierce shadow DOM, so a
 * part can be targeted directly, e.g. 'vaadin-dialog [part="overlay"]' for a dialog. A descendant
 * selector also matches parts of nested components; when it matches more than one element, use the
 * child combinator, e.g. 'vaadin-app-layout > [part="content"]'.
 */

import { writeFileSync } from 'node:fs';

/**
 * Reads the computed corner radii of the element in CSS pixels:
 * [topLeft, topRight, bottomRight, bottomLeft].
 */
export function readRadii(locator) {
    return locator.evaluate(e => {
        const s = getComputedStyle(e);
        return [s.borderTopLeftRadius, s.borderTopRightRadius, s.borderBottomRightRadius, s.borderBottomLeftRadius]
            .map(parseFloat);
    });
}

/**
 * Captures `clip` (a box in CSS pixels, e.g. from locator.boundingBox()) and writes a PNG to `path`
 * with the pixels outside the rounded corners made transparent. `radii` are in CSS pixels:
 * [topLeft, topRight, bottomRight, bottomLeft]. Pass 0 for a corner that is cut through, e.g. the
 * bottom corners of a crop that ends in the middle of a panel.
 */
export async function captureRounded(page, clip, radii, path) {
    const png = await page.screenshot({ clip, type: 'png' });
    const dataUrl = await page.evaluate(async ({ src, cssWidth, radii }) => {
        const img = new Image();
        img.src = src;
        await img.decode();
        const scale = img.width / cssWidth;
        const canvas = document.createElement('canvas');
        canvas.width = img.width;
        canvas.height = img.height;
        const ctx = canvas.getContext('2d');
        ctx.beginPath();
        ctx.roundRect(0, 0, img.width, img.height, radii.map(r => r * scale));
        ctx.clip();
        ctx.drawImage(img, 0, 0);
        return canvas.toDataURL('image/png');
    }, { src: 'data:image/png;base64,' + png.toString('base64'), cssWidth: clip.width, radii });
    writeFileSync(path, Buffer.from(dataUrl.split(',')[1], 'base64'));
}
