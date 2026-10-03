/*
 * Checks the docs UI stylesheets in content/supplemental/css.
 *
 *  1. Colors come from design tokens: outside tokens.css a color is written only as var(--…).
 *     Keywords such as transparent and currentColor, and the CSS system colors used in forced
 *     colors mode (CanvasText, Highlight and so on), are allowed because they are not literals.
 *  2. Every custom property read with var() without a fallback is declared in one of the files.
 *  3. The dark block of tokens.css (:root[data-theme="dark"]) sets every tier 2 token of the light :root block
 *     (--color-* and --shadow-*), and nothing the light block does not declare.
 *
 * Usage: node tools/check-css.mjs [dir]    (default: content/supplemental/css; exit code 1 on findings)
 */

import { readdirSync, readFileSync, realpathSync } from 'node:fs';
import { join, resolve } from 'node:path';
import { pathToFileURL } from 'node:url';

const TOKENS_FILE = 'tokens.css';

// CSS named colors (CSS Color Module Level 4); system colors are deliberately not listed
const NAMED_COLORS = new Set(('aliceblue antiquewhite aqua aquamarine azure beige bisque black blanchedalmond blue '
    + 'blueviolet brown burlywood cadetblue chartreuse chocolate coral cornflowerblue cornsilk crimson cyan darkblue '
    + 'darkcyan darkgoldenrod darkgray darkgreen darkgrey darkkhaki darkmagenta darkolivegreen darkorange darkorchid '
    + 'darkred darksalmon darkseagreen darkslateblue darkslategray darkslategrey darkturquoise darkviolet deeppink '
    + 'deepskyblue dimgray dimgrey dodgerblue firebrick floralwhite forestgreen fuchsia gainsboro ghostwhite gold '
    + 'goldenrod gray green greenyellow grey honeydew hotpink indianred indigo ivory khaki lavender lavenderblush '
    + 'lawngreen lemonchiffon lightblue lightcoral lightcyan lightgoldenrodyellow lightgray lightgreen lightgrey '
    + 'lightpink lightsalmon lightseagreen lightskyblue lightslategray lightslategrey lightsteelblue lightyellow lime '
    + 'limegreen linen magenta maroon mediumaquamarine mediumblue mediumorchid mediumpurple mediumseagreen '
    + 'mediumslateblue mediumspringgreen mediumturquoise mediumvioletred midnightblue mintcream mistyrose moccasin '
    + 'navajowhite navy oldlace olive olivedrab orange orangered orchid palegoldenrod palegreen paleturquoise '
    + 'palevioletred papayawhip peachpuff peru pink plum powderblue purple rebeccapurple red rosybrown royalblue '
    + 'saddlebrown salmon sandybrown seagreen seashell sienna silver skyblue slateblue slategray slategrey snow '
    + 'springgreen steelblue tan teal thistle tomato turquoise violet wheat white whitesmoke yellow yellowgreen').split(' '));

const HEX_RX = /#[0-9a-f]{3,8}\b/i;
const COLOR_FUNCTION_RX = /\b(?:rgba?|hsla?|hwb|lab|lch|oklab|oklch|color)\(/i;
const WORD_RX = /(?<![\w-])[a-z]+(?![\w-])/gi;
// a declaration starts after "{" or ";" and ends before ";" or "}"; selectors end with "{" and never match
const DECLARATION_RX = /(?:^|[;{])\s*(--[\w-]+|[a-z-]+)\s*:\s*([^;{}]*)(?=[;}])/gi;
const VAR_RX = /var\(\s*(--[\w-]+)\s*(,)?/g;
const DECLARED_RX = /(?:^|[;{\s])(--[\w-]+)\s*:/g;
const TIER2_RX = /^--(?:color|shadow)-/;
const LIGHT_BLOCK_RX = /(?:^|[\s}]):root\s*\{/;
const DARK_BLOCK_RX = /:root\[data-theme="dark"\]\s*\{/;

// Blank out comments, url(...) and strings, keeping offsets so that line numbers stay right.
function mask(text) {
    return text
        .replace(/\/\*[\s\S]*?\*\//g, (m) => m.replace(/[^\n]/g, ' '))
        .replace(/url\((?:[^()"']|"[^"]*"|'[^']*')*\)/gi, (m) => 'url(' + ' '.repeat(m.length - 5) + ')')
        .replace(/"[^"\n]*"|'[^'\n]*'/g, (m) => m[0] + ' '.repeat(m.length - 2) + m[0]);
}

function lineOf(text, index) {
    return text.slice(0, index).split('\n').length;
}

// Custom properties declared in the block whose "{" is at index `open` of the masked text (blocks hold no braces once masked).
function declaredInBlock(masked, open) {
    const close = masked.indexOf('}', open);
    const block = masked.slice(open + 1, close === -1 ? masked.length : close);
    return new Set([...block.matchAll(DECLARED_RX)].map((m) => m[1]));
}

export function checkDarkTokens(text) {
    const masked = mask(text);
    const light = masked.match(LIGHT_BLOCK_RX);
    if (!light) return [];
    const lightNames = declaredInBlock(masked, light.index + light[0].length - 1);
    const tier2 = [...lightNames].filter((name) => TIER2_RX.test(name));
    // the selector's quotes are blanked in the masked text, so the dark block is found in the original
    const dark = text.match(DARK_BLOCK_RX);
    if (!dark) {
        // the match starts at the character before ":root"
        const line = lineOf(masked, light.index + light[0].indexOf(':root'));
        return tier2.length ? [{ line, message: 'no dark block :root[data-theme="dark"] for the tier 2 tokens' }] : [];
    }
    const darkNames = declaredInBlock(masked, dark.index + dark[0].length - 1);
    const line = lineOf(masked, dark.index);
    return [
        ...tier2.filter((name) => !darkNames.has(name)).map((name) => ({ line, message: `the dark block does not set ${name}` })),
        ...[...darkNames].filter((name) => !lightNames.has(name)).map((name) => ({ line, message: `the dark block sets ${name}, which the light :root block does not declare` })),
    ];
}

function colorLiteral(value) {
    const hex = value.match(HEX_RX);
    if (hex) return hex[0];
    const fn = value.match(COLOR_FUNCTION_RX);
    if (fn) return fn[0] + '…)';
    for (const word of value.matchAll(WORD_RX)) {
        if (NAMED_COLORS.has(word[0].toLowerCase())) return word[0];
    }
    return null;
}

export function checkStylesheets(sheets) {
    const findings = [];
    const declared = new Set();
    const used = [];
    for (const { name, text } of sheets) {
        const masked = mask(text);
        for (const m of masked.matchAll(DECLARED_RX)) declared.add(m[1]);
        for (const m of masked.matchAll(VAR_RX)) {
            if (!m[2]) used.push({ name, line: lineOf(masked, m.index), property: m[1] });
        }
        if (name === TOKENS_FILE) {
            findings.push(...checkDarkTokens(text).map((f) => ({ name, ...f })));
            continue;
        }
        for (const m of masked.matchAll(DECLARATION_RX)) {
            const [whole, property, value] = m;
            const literal = colorLiteral(value);
            if (literal) {
                findings.push({
                    name,
                    line: lineOf(masked, m.index + whole.indexOf(property)),
                    message: `color literal "${literal}" in ${property}; use a token from ${TOKENS_FILE}`,
                });
            }
        }
    }
    for (const use of used) {
        if (!declared.has(use.property)) {
            findings.push({ name: use.name, line: use.line, message: `${use.property} is used but never declared` });
        }
    }
    return findings;
}

function main() {
    const dir = resolve(process.argv[2] || 'content/supplemental/css');
    const sheets = readdirSync(dir)
        .filter((file) => file.endsWith('.css'))
        .sort()
        .map((name) => ({ name, text: readFileSync(join(dir, name), 'utf8') }));
    const findings = checkStylesheets(sheets);
    for (const f of findings) {
        console.log(`${join(dir, f.name)}:${f.line}: ${f.message}`);
    }
    if (findings.length) {
        console.log(`\n${findings.length} problem(s) in ${sheets.length} stylesheets.`);
        process.exit(1);
    }
    console.log(`${sheets.length} stylesheets checked, no problems.`);
}

// run main() only when this file is the entry point; Node resolves symlinks in import.meta.url but not in argv[1]
const invokedAs = process.argv[1] && realpathSync(process.argv[1]);
if (invokedAs && import.meta.url === pathToFileURL(invokedAs).href) main();
