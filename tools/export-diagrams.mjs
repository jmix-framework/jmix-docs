/*
 * Exports the pages of a draw.io file to SVG, one file per page, with the draw.io desktop command line.
 *
 * Each page is exported to <images-dir>/<prefix><page name>.svg with a transparent background and a
 * 16 px border, so the image macro's role=light-background draws a rounded white background with
 * padding in the dark theme. Fonts are not embedded: with font embedding on, draw.io turns text
 * labels into PNG images. The diagram source is not embedded either; the .drawio file is the source.
 *
 * Usage: node tools/export-diagrams.mjs <file.drawio> <images-dir> [--prefix <prefix>] [--page <name>]...
 *
 * Without --page, every page is exported. Repeat --page to export several pages.
 *
 * The diagrams of the documentation, from the repository root:
 *   node tools/export-diagrams.mjs content/modules/concepts/diagrams/architecture.drawio content/modules/concepts/images --prefix architecture-
 *   node tools/export-diagrams.mjs content/modules/concepts/diagrams/features.drawio content/modules/concepts/images
 *   node tools/export-diagrams.mjs content/modules/features/diagrams_src/jmix-cluster.drawio content/modules/features/images --prefix jmix-cluster-
 *   node tools/export-diagrams.mjs content/modules/flow-ui/diagrams_src/standard-view-lifecycle.drawio content/modules/flow-ui/images/views
 *   node tools/export-diagrams.mjs content/modules/flow-ui/diagrams_src/open-detail-view-all.drawio content/modules/flow-ui/images/views
 *   node tools/export-diagrams.mjs content/modules/flow-ui/diagrams_src/detail-view-close-with-save.drawio content/modules/flow-ui/images/views
 *   node tools/export-diagrams.mjs content/modules/modularity/images_src/modularity.drawio content/modules/modularity/images
 *   node tools/export-diagrams.mjs content/modules/reports/image-src/reports-2.drawio content/modules/reports/images/create --page bands-1 --page create-runtime-1 --page create-design-time-1
 *   node tools/export-diagrams.mjs content/modules/reports/image-src/reports-2.drawio content/modules/reports/images/run --page run-1
 *   node tools/export-diagrams.mjs content/modules/security/images_src/jmix-security.drawio content/modules/security/images --prefix jmix-security-
 *   node tools/export-diagrams.mjs content/modules/studio/diagrams/openapi-client.drawio content/modules/studio/images/openapi-client
 *
 * To restyle a new or changed diagram before the export, run tools/restyle-drawio.mjs on its source.
 *
 * The draw.io binary is $DRAWIO, else the macOS app if it is installed, else `drawio` on the PATH.
 * The .drawio file must be saved uncompressed (the default of current draw.io versions).
 */

import { spawnSync } from 'node:child_process';
import { existsSync, readFileSync, realpathSync } from 'node:fs';
import { join } from 'node:path';
import { pathToFileURL } from 'node:url';

const MACOS_DRAWIO = '/Applications/draw.io.app/Contents/MacOS/draw.io';

/** Returns the page names of a .drawio file in file order. */
export function listPages(xml) {
    const pages = [];
    for (const match of xml.matchAll(/<diagram\b([^>]*)>([\s\S]*?)<\/diagram>/g)) {
        const name = match[1].match(/\bname="([^"]*)"/)?.[1] ?? '';
        if (!/<mxGraphModel\b/.test(match[2])) {
            throw new Error(`Page "${name}" is compressed; save the file uncompressed in draw.io (File > Properties)`);
        }
        pages.push(name);
    }
    return pages;
}

/** Returns the draw.io command line arguments that export one page (1-based index) to an SVG file. */
export function exportArgs(source, pageIndex, out) {
    return ['-x', '-f', 'svg', '-p', String(pageIndex), '--theme', 'light', '-t', '-b', '16',
        '--embed-svg-fonts', 'false', '-o', out, source];
}

function drawioBinary() {
    if (process.env.DRAWIO) return process.env.DRAWIO;
    return existsSync(MACOS_DRAWIO) ? MACOS_DRAWIO : 'drawio';
}

const USAGE = 'Usage: node tools/export-diagrams.mjs <file.drawio> <images-dir> [--prefix <prefix>] [--page <name>]...';

/** Parses the command line arguments (without node and the script path). */
export function parseArgs(argv) {
    const result = { prefix: '', pages: [] };
    const positional = [];
    for (let i = 0; i < argv.length; i++) {
        const arg = argv[i];
        if (arg === '--prefix' || arg === '--page') {
            const value = argv[++i];
            if (value === undefined || value.startsWith('--')) throw new Error(`${arg} needs a value. ${USAGE}`);
            if (arg === '--prefix') result.prefix = value;
            else result.pages.push(value);
        } else if (arg.startsWith('--')) {
            throw new Error(`Unknown option ${arg}. ${USAGE}`);
        } else {
            positional.push(arg);
        }
    }
    if (positional.length < 2) throw new Error(USAGE);
    if (positional.length > 2) throw new Error(`Unexpected argument ${positional[2]}. ${USAGE}`);
    const [source, imagesDir] = positional;
    return { source, imagesDir, ...result };
}

function main() {
    let args;
    try {
        args = parseArgs(process.argv.slice(2));
    } catch (e) {
        console.error(e.message);
        process.exit(2);
    }
    const { source, imagesDir, prefix } = args;
    const pages = listPages(readFileSync(source, 'utf8'));
    if (pages.length === 0) {
        console.error(`No pages in ${source}`);
        process.exit(1);
    }
    const missing = args.pages.filter((name) => !pages.includes(name));
    if (missing.length > 0) {
        console.error(`No page "${missing.join('", "')}" in ${source}; pages: ${pages.join(', ')}`);
        process.exit(1);
    }
    const binary = drawioBinary();
    pages.forEach((name, i) => {
        if (args.pages.length > 0 && !args.pages.includes(name)) return;
        const out = join(imagesDir, `${prefix}${name}.svg`);
        const result = spawnSync(binary, exportArgs(source, i + 1, out), { encoding: 'utf8' });
        if (result.status !== 0) {
            console.error(`draw.io failed on page "${name}": ${result.error?.message ?? result.stderr}`);
            process.exit(1);
        }
        console.log(out);
    });
}

const invokedAs = process.argv[1] && realpathSync(process.argv[1]);
if (invokedAs && import.meta.url === pathToFileURL(invokedAs).href) main();
