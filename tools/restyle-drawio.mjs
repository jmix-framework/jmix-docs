/*
 * Restyles draw.io diagrams to the docs style: brand-tinted fills, rounded boxes, gray connectors,
 * Jmix "space" text color and the font stack Roboto, Helvetica, Arial.
 *
 * It rewrites the style string and the HTML label of every cell of an uncompressed .drawio file.
 * Running it again on a restyled file changes nothing.
 *
 * Usage: node tools/restyle-drawio.mjs <file.drawio>...    (rewrites the files in place)
 */

import { readFileSync, realpathSync, writeFileSync } from 'node:fs';
import { pathToFileURL } from 'node:url';

const FONT_FAMILY = 'Roboto, Helvetica, Arial';
const TEXT_COLOR = '#17124b';
const SECONDARY_TEXT_COLOR = '#5c606b';
const EDGE_COLOR = '#5c606b';
const PLAIN_BORDER_COLOR = '#8a8f99';
const LINE_WIDTH = '1.25';
const ARC_SIZE = '16'; // with absoluteArcSize=1, a corner radius of 8 px

// draw.io default fill -> new fill and border
const FILLS = {
    '#ffe6cc': ['#f0eeff', '#a99bff'],
    '#dae8fc': ['#e2f7fa', '#7fd9e6'],
    '#d5e8d4': ['#e2f8ee', '#83e2b6'],
    '#fff2cc': ['#fff4dd', '#f9c766'],
    '#f8cecc': ['#ffe4ee', '#fb8db4'],
    '#e1d5e7': ['#e9e8f1', '#9c99b8'],
    '#f5f5f5': ['#f6f7f9', '#cfd3da'],
    // other draw.io palette colors
    '#ffcc99': ['#f0eeff', '#a99bff'],
    '#cce5ff': ['#e2f7fa', '#7fd9e6'],
    '#cdeb8b': ['#e2f8ee', '#83e2b6'],
    '#ffcccc': ['#ffe4ee', '#fb8db4'],
    '#f9f7ed': ['#f6f7f9', '#cfd3da'],
    // C4 shapes: person, container (essential), container, component
    '#083f75': ['#f0eeff', '#a99bff'],
    '#1061b0': ['#f0eeff', '#a99bff'],
    '#23a2d9': ['#e2f7fa', '#7fd9e6'],
    '#63bef2': ['#e2f7fa', '#7fd9e6'],
};

// draw.io and C4 default text colors that become the text color
const DEFAULT_TEXT_COLORS = ['#333333', '#404040'];
// light gray text that C4 shapes use for descriptions on dark fills
const LIGHT_TEXT_COLORS = ['#cccccc', '#e6e6e6'];

function parseStyle(style) {
    return style.split(';').filter(Boolean).map((token) => {
        const i = token.indexOf('=');
        return i < 0 ? { bare: token } : { key: token.slice(0, i), value: token.slice(i + 1) };
    });
}

/** Returns the new draw.io style string of a cell; kind is 'vertex' or 'edge'. */
export function restyleStyle(style, kind) {
    const tokens = parseStyle(style);
    const get = (key) => tokens.find((t) => t.key === key)?.value;
    const set = (key, value) => {
        const token = tokens.find((t) => t.key === key);
        if (token) token.value = value;
        else tokens.push({ key, value });
    };
    const bare = new Set(tokens.filter((t) => t.bare).map((t) => t.bare));

    set('fontFamily', FONT_FAMILY);
    const fontColor = get('fontColor')?.toLowerCase();
    const fill = get('fillColor')?.toLowerCase();
    if (fontColor === undefined || DEFAULT_TEXT_COLORS.includes(fontColor)
        || (FILLS[fill] && fontColor === '#ffffff')) {
        set('fontColor', TEXT_COLOR);
    }

    const stroke = get('strokeColor')?.toLowerCase();
    if (kind === 'edge') {
        set('strokeColor', EDGE_COLOR);
        set('strokeWidth', LINE_WIDTH);
    } else if (bare.has('group')) {
        // a group draws nothing itself; a border would add a frame around its children
    } else if (bare.has('text') || bare.has('edgeLabel')) {
        // a text cell with its own fill, such as a row of a UML class, takes the new fill and border
        if (FILLS[fill]) {
            set('fillColor', FILLS[fill][0]);
            if (stroke !== undefined && stroke !== 'none') set('strokeColor', FILLS[fill][1]);
        }
    } else {
        if (FILLS[fill]) {
            set('fillColor', FILLS[fill][0]);
            if (stroke !== 'none') set('strokeColor', FILLS[fill][1]);
        } else if ((fill === undefined || fill === 'none' || fill === '#ffffff')
            && (stroke === undefined || stroke === '#000000')) {
            set('strokeColor', PLAIN_BORDER_COLOR);
        }
        if (get('shape') === 'umlActor' || get('participant') === 'umlActor') set('strokeColor', TEXT_COLOR);
        if (get('strokeColor') !== 'none') set('strokeWidth', LINE_WIDTH);
        if (get('shape') === undefined && bare.size === 0) {
            set('rounded', '1');
            set('absoluteArcSize', '1');
            set('arcSize', ARC_SIZE);
        }
    }

    return tokens.map((t) => (t.bare ?? `${t.key}=${t.value}`)).join(';') + ';';
}

/** Returns the new HTML of a decoded cell label. */
export function restyleLabel(html) {
    const lightened = html.replace(/(<font\b[^>]*\bcolor=")(#[0-9a-f]{6})"/gi,
        (m, start, color) => (LIGHT_TEXT_COLORS.includes(color.toLowerCase()) ? `${start}${SECONDARY_TEXT_COLOR}"` : m));
    const colored = lightened.replace(/<font\b([^>]*)>(?!<b>)/g, (tag, attrs) => {
        // a font element that starts with <b> holds a title, which keeps the text color
        const styleMatch = attrs.match(/\bstyle="([^"]*)"/);
        if (/\bcolor=/.test(attrs.replace(/\bstyle="[^"]*"/, '')) || !styleMatch) return tag;
        const style = styleMatch[1];
        if (/(^|;)\s*color\s*:/.test(style)) return tag;
        const newStyle = `${style.trim().replace(/;?$/, ';')} color: ${SECONDARY_TEXT_COLOR};`;
        return tag.replace(styleMatch[0], `style="${newStyle}"`);
    });
    return colored.replace(/^([^<]+)(<br\s*\/?>)(<font\b[^>]*>\[[^<]*\]<\/font>)$/,
        '<span style="font-weight: 500;">$1</span>$2$3');
}

const ENTITIES = { quot: '"', lt: '<', gt: '>', amp: '&', apos: "'" };
const decode = (s) => s.replace(/&(#x[0-9a-f]+|#\d+|quot|lt|gt|amp|apos);/gi, (m, ref) => {
    if (ref[0] !== '#') return ENTITIES[ref.toLowerCase()];
    return String.fromCodePoint(ref[1].toLowerCase() === 'x' ? parseInt(ref.slice(2), 16) : parseInt(ref.slice(1), 10));
});
const encode = (s) => s.replace(/&/g, '&amp;').replace(/"/g, '&quot;').replace(/</g, '&lt;')
    .replace(/>/g, '&gt;').replace(/\n/g, '&#xa;');

function restyleLabelAttribute(tag, name) {
    return tag.replace(new RegExp(`(\\s${name}=")([^"]*)"`), (m, start, value) => {
        const html = decode(value);
        const restyled = restyleLabel(html);
        return restyled === html ? m : `${start}${encode(restyled)}"`;
    });
}

function restyleTag(tag) {
    // the page setting that draws a shadow under every shape
    if (tag.startsWith('<mxGraphModel')) return tag.replace(/\sshadow="1"/, ' shadow="0"');
    // an object or UserObject element keeps the label of the cell inside it
    if (!tag.startsWith('<mxCell')) return restyleLabelAttribute(tag, 'label');
    const kind = /\bedge="1"/.test(tag) ? 'edge' : /\bvertex="1"/.test(tag) ? 'vertex' : null;
    if (!kind) return tag;
    const result = /\sstyle="/.test(tag)
        ? tag.replace(/(\sstyle=")([^"]*)"/, (m, start, style) => `${start}${restyleStyle(style, kind)}"`)
        : tag.replace(/^<mxCell\b/, `<mxCell style="${restyleStyle('', kind)}"`);
    return restyleLabelAttribute(result, 'value');
}

const TAGS = /<(?:mxCell|object|UserObject|mxGraphModel)\b(?:[^>"]|"[^"]*")*>/g;

/** Restyles every cell of an uncompressed .drawio file; onChange is called for each changed element. */
export function restyleDrawio(xml, onChange = () => {}) {
    return xml.replace(TAGS, (tag) => {
        const restyled = restyleTag(tag);
        if (restyled !== tag) onChange();
        return restyled;
    });
}

function main() {
    const files = process.argv.slice(2);
    if (files.length === 0) {
        console.error('Usage: node tools/restyle-drawio.mjs <file.drawio>...');
        process.exit(2);
    }
    for (const file of files) {
        const xml = readFileSync(file, 'utf8');
        let changed = 0;
        const result = restyleDrawio(xml, () => changed++);
        if (result !== xml) writeFileSync(file, result);
        console.log(`${file}: ${changed} elements changed`);
    }
}

const invokedAs = process.argv[1] && realpathSync(process.argv[1]);
if (invokedAs && import.meta.url === pathToFileURL(invokedAs).href) main();
