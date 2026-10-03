/*
 * Audits the built docs site in Chromium: keyboard focus, the skip link, accessible names,
 * keyboard access to search results, text contrast, expected computed styles, fonts, forced
 * colors mode, and screenshot comparison with a saved snapshot.
 *
 * Build the site first (npx antora antora-playbook.yml), then:
 *   node tools/ui-audit.mjs                          run every check
 *   node tools/ui-audit.mjs --only focus,contrast    run some checks
 *   node tools/ui-audit.mjs --snapshot <dir>         save reference screenshots
 *   node tools/ui-audit.mjs --compare <dir>          compare with the screenshots in <dir>
 *
 * Options: --site <dir> (default build/site), --out <dir> (default build/ui-audit), --mask <selector> (with
 * --snapshot and --compare: hide these elements in both runs, for example the header when it has changed on purpose).
 * Forced colors screenshots are written to --out for review. Exit code 1 when a check fails.
 * Requests to hosts other than the local server, cdnjs.cloudflare.com (highlight.js) and kroki.io
 * (diagrams) are blocked, so the audit never sends analytics.
 */

import { createServer } from 'node:http';
import { mkdir, readFile } from 'node:fs/promises';
import { readdirSync } from 'node:fs';
import { extname, join, normalize, resolve } from 'node:path';
import { launchChromium } from './lib/playwright.mjs';

const PAGES = {
    manager: 'jmix/data-access/data-manager.html',
    events: 'jmix/bpm/bpmn/bpmn-events.html',
    geomap: 'jmix/flow-ui/vc/components/geoMap.html',
    caution: 'jmix/studio/quick-cloud-deployment.html',
    important: 'jmix/studio/ai-assistant.html',
    features: 'jmix/studio/studio-features.html',
    intro: 'jmix/intro.html',
    bpm: 'jmix/bpm/index.html',
    saml: 'jmix/saml/keycloak-saml.html',
    install: 'jmix/studio/install.html',
    gdg: 'jmix/flow-ui/vc/components/groupDataGrid.html',
};

const ALLOWED_HOSTS = ['cdnjs.cloudflare.com', 'kroki.io'];

// Text that must reach 4.5:1 against its background: [page, selector]
const TEXT_CONTRAST = [
    ['manager', 'article.doc .paragraph p'],
    ['manager', '.nav-menu .nav-link'],
    ['manager', '.toc-menu a'],
    ['manager', '.breadcrumbs a'],
    ['manager', '.edit-this-page a'],
    ['manager', 'article.doc pre .hljs-keyword'],
    ['manager', 'article.doc pre .hljs-comment'],
    ['manager', 'article.doc pre .hljs-meta'],
    ['manager', 'article.doc pre .hljs-title'],
    ['manager', 'article.doc .source-toolbox .source-lang'],
    ['events', 'article.doc pre .hljs-string'],
    ['events', '.admonitionblock.note td.icon i'],
    ['events', '.admonitionblock.warning td.icon i'],
    ['geomap', '.admonitionblock.tip td.icon i'],
    ['geomap', '.admonitionblock.addon-component td.icon i'],
    ['caution', '.admonitionblock.caution td.icon i'],
    ['important', '.admonitionblock.important td.icon i'],
];

// Computed styles the spec fixes: [page, selector (may end with ::before or ::after), property, expected].
// Colors can be written as #rrggbb. Tasks 7 to 11 add entries.
const STYLE_EXPECTATIONS = [
    // Task 6: header and search
    ['manager', 'nav.navbar', 'background-color', '#ffffff'],
    ['manager', 'nav.navbar', 'border-bottom-color', '#e4e6eb'],
    ['manager', '.navbar-logo-center', 'fill', '#17124b'],
    ['manager', '.version-dropdown-toggle', 'background-color', '#f0eeff'],
    ['manager', '.version-dropdown-toggle', 'color', '#342a98'],
    ['manager', '#search-input', 'width', '324px'],
    ['manager', '#search-input', 'border-top-color', '#8a8f99'],
    ['manager', '.search-kbd', 'font-weight', '400'],
    ['manager', '.header-icon-link.ai-assistant-link', 'background-color', 'rgba(0, 0, 0, 0)'],
    ['manager', '.header-icon-link.ai-assistant-link', 'color', '#17124b'],
    ['manager', '.header-icon-link.git-link', 'color', '#17124b'],
    // Task 8: navigation, explore panel, toolbar, TOC
    ['manager', '.nav', 'border-right-width', '1px'],
    ['manager', '.is-current-page > .nav-link', 'background-color', '#f0eeff'],
    ['manager', '.is-current-page > .nav-link', 'color', '#342a98'],
    ['manager', '.nav-item-toggle', 'width', '24px'],
    ['manager', '.nav-menu-toggle', 'visibility', 'visible'],
    ['manager', '.nav-menu-toggle', 'width', '24px'],
    ['manager', '.nav-panel-explore .context', 'height', '49.5px'],
    ['manager', '.toolbar', 'height', '49.5px'],
    ['manager', '.toolbar', 'border-bottom-width', '1px'],
    ['manager', '.edit-this-page a', 'color', '#5c606b'],
    ['manager', 'aside.toc.sidebar', 'flex-basis', '252px'],
    ['manager', '.toc .toc-menu a', 'border-left-width', '1px'],
    ['manager', 'html', 'scroll-padding-top', '130.5px'],
    // Task 9: article, code, admonitions
    ['manager', 'article.doc > h1.page', 'font-weight', '700'],
    ['manager', 'article.doc > h1.page', 'color', '#17124b'],
    ['manager', 'article.doc h2', 'border-bottom-width', '0px'],
    ['manager', 'article.doc pre.highlight > code', 'background-color', '#f7f8fa'],
    ['manager', 'article.doc pre.highlight > code', 'border-top-left-radius', '8px'],
    ['manager', 'article.doc .hljs-keyword', 'color', '#0033b3'],
    ['manager', 'article.doc pre.highlight > code', 'font-variant-ligatures', 'none'],
    ['manager', 'article.doc .source-toolbox', 'visibility', 'visible'],
    ['manager', 'article.doc .conum[data-value]', 'background-color', '#17124b'],
    ['events', '.admonitionblock.note > table', 'border-left-color', '#25cde3'],
    ['events', '.admonitionblock.note td.icon i', 'background-color', 'rgba(0, 0, 0, 0)'],
    ['events', '.admonitionblock.note td.icon i', 'color', '#0a6874'],
    ['geomap', '.admonitionblock.addon-component > table', 'border-left-color', '#17124b'],
    ['geomap', '.admonitionblock.addon-component td.icon i::after', 'content', '"Add-on component"'],
    // Task 9 rulings: plain blocks have the code size (also in an admonition, where upstream sets 15px), and a block
    // whose first line would run under the toolbox starts its code below it (code-toolbox.js, 2.3rem at 18px)
    ['bpm', 'article.doc .listingblock pre:not(.highlight)', 'font-size', '14.04px'],
    ['saml', 'article.doc .admonitionblock .listingblock pre:not(.highlight)', 'font-size', '14.04px'],
    ['saml', 'article.doc pre.highlight.first-line-under-toolbox > code', 'padding-top', '41.4px'],
    // Task 10: tables, blocks, end of page
    ['events', 'table.tableblock > thead > tr > th', 'background-color', '#f6f7f9'],
    ['events', 'table.tableblock > thead > tr > th', 'color', '#17124b'],
    ['manager', 'nav.pagination a', 'border-top-width', '1px'],
    ['manager', 'nav.pagination a', 'text-decoration-line', 'none'],
    ['manager', 'nav.pagination .next a::before', 'content', '"Next"'],
    ['manager', '.feedback-form', 'border-top-left-radius', '8px'],
    ['manager', '.feedback-form__btn', 'background-color', '#ffffff'],
    ['manager', 'footer.footer', 'background-color', '#ffffff'],
    ['manager', 'footer.footer', 'border-top-width', '1px'],
    // guard from the Task 9 review: the embedded TOC is hidden from 1024px up, and must not space the first section there
    ['install', 'article.doc > .sect1', 'margin-top', '0px'],
    // final review guard: the Since badge keeps its own size inside an admonition (page: a Since badge in a TIP)
    ['gdg', '.admonitionblock .paragraph.since p', 'font-size', '13.5px'],
];

const SNAPSHOT_PAGES = ['manager', 'events', 'geomap', 'features', 'intro'];

// the Tab walk ends when focus returns to the skip link; this only stops a keyboard trap
const MAX_TAB_PRESSES = 1000;

// Runs before the page's scripts: stores a preference (unless null) and records data-theme at the moment the first
// stylesheet link enters the document. The parser delivers these mutation records before it runs the next script,
// so a stylesheet placed before the theme script is caught.
const THEME_PROBE = (stored) => {
    if (stored !== null) localStorage.setItem('jmix-docs-theme', stored);
    new MutationObserver((records, observer) => {
        if (document.querySelector('link[rel="stylesheet"]')) {
            window.__themeAtFirstStylesheet = document.documentElement.getAttribute('data-theme');
            observer.disconnect();
        }
    }).observe(document, { childList: true, subtree: true });
};

const PAGE_HELPERS = `
window.__audit = (() => {
    const parse = (value) => {
        if (!value) return null;
        let m = value.match(/^rgba?\\(([^)]+)\\)$/);
        if (m) {
            const p = m[1].split(/[\\s,/]+/).filter(Boolean).map(Number);
            return { r: p[0], g: p[1], b: p[2], a: p.length > 3 ? p[3] : 1 };
        }
        m = value.match(/^color\\(srgb ([^)]+)\\)$/);
        if (m) {
            const p = m[1].split(/[\\s/]+/).filter(Boolean).map(Number);
            return { r: p[0] * 255, g: p[1] * 255, b: p[2] * 255, a: p.length > 3 ? p[3] : 1 };
        }
        return null;
    };
    const over = (top, bottom) => ({
        r: top.r * top.a + bottom.r * (1 - top.a),
        g: top.g * top.a + bottom.g * (1 - top.a),
        b: top.b * top.a + bottom.b * (1 - top.a),
        a: 1,
    });
    const background = (el) => {
        const layers = [];
        for (let node = el; node; node = node.parentElement) {
            const c = parse(getComputedStyle(node).backgroundColor);
            if (c && c.a > 0) {
                layers.push(c);
                if (c.a >= 1) break;
            }
        }
        return layers.reverse().reduce((bottom, top) => over(top, bottom), { r: 255, g: 255, b: 255, a: 1 });
    };
    const luminance = ({ r, g, b }) => {
        const f = (v) => { v /= 255; return v <= 0.03928 ? v / 12.92 : ((v + 0.055) / 1.055) ** 2.4; };
        return 0.2126 * f(r) + 0.7152 * f(g) + 0.0722 * f(b);
    };
    const ratio = (a, b) => {
        const [x, y] = [luminance(a), luminance(b)].sort((p, q) => q - p);
        return (x + 0.05) / (y + 0.05);
    };
    const textContrast = (el) => {
        const bg = background(el);
        const fg = parse(getComputedStyle(el).color);
        return fg ? ratio(over(fg, bg), bg) : 0;
    };
    const describe = (el) => {
        const cls = typeof el.className === 'string' && el.className.trim() ? '.' + el.className.trim().split(/\\s+/).join('.') : '';
        const label = (el.getAttribute('aria-label') || el.textContent || '').trim().replace(/\\s+/g, ' ').slice(0, 40);
        return el.tagName.toLowerCase() + (el.id ? '#' + el.id : '') + cls + (label ? ' "' + label + '"' : '');
    };
    // forced colors: null when the element (or its pseudo-element) paints a background that differs from Canvas
    const drawn = (el, pseudo) => {
        const probe = document.createElement('div');
        probe.style.cssText = 'forced-color-adjust: none; background-color: Canvas; position: fixed; width: 1px; height: 1px';
        document.body.append(probe);
        const canvas = getComputedStyle(probe).backgroundColor;
        probe.remove();
        const value = getComputedStyle(el, pseudo).backgroundColor;
        const color = parse(value);
        return color && color.a > 0 && value !== canvas ? null : 'background-color ' + value + ' is not visible on Canvas ' + canvas;
    };
    return { parse, background, ratio, textContrast, describe, drawn };
})();
`;

function parseArgs(argv) {
    const opts = { site: 'build/site', out: 'build/ui-audit', only: null, snapshot: null, compare: null, mask: null };
    for (let i = 0; i < argv.length; i += 2) {
        const key = argv[i].replace(/^--/, '');
        if (!(key in opts)) throw new Error(`unknown option --${key}`);
        opts[key] = argv[i + 1];
    }
    if (opts.only) opts.only = opts.only.split(',');
    return opts;
}

function serve(root) {
    const types = {
        '.html': 'text/html; charset=utf-8', '.css': 'text/css', '.js': 'text/javascript',
        '.svg': 'image/svg+xml', '.png': 'image/png', '.jpg': 'image/jpeg', '.jpeg': 'image/jpeg',
        '.webp': 'image/webp', '.gif': 'image/gif', '.ico': 'image/x-icon', '.woff2': 'font/woff2',
        '.woff': 'font/woff', '.json': 'application/json', '.xml': 'application/xml',
    };
    const server = createServer(async (req, res) => {
        let file = join(root, normalize(decodeURIComponent(new URL(req.url, 'http://localhost').pathname)));
        if (!file.startsWith(root)) {
            res.writeHead(403);
            return res.end();
        }
        if (file.endsWith('/')) file = join(file, 'index.html');
        try {
            const body = await readFile(file);
            res.writeHead(200, { 'Content-Type': types[extname(file)] || 'application/octet-stream' });
            res.end(body);
        } catch {
            res.writeHead(404);
            res.end();
        }
    });
    return new Promise((ok) => server.listen(0, '127.0.0.1', () => ok(server)));
}

function hexToRgb(hex) {
    const n = parseInt(hex.slice(1), 16);
    return `rgb(${(n >> 16) & 255}, ${(n >> 8) & 255}, ${n & 255})`;
}

const unique = (list) => [...new Set(list)];

class Audit {
    constructor(browser, base) {
        this.browser = browser;
        this.base = base;
        this.notes = {};
    }

    async context(options = {}) {
        const context = await this.browser.newContext({ viewport: { width: 1440, height: 900 }, ...options });
        await context.addInitScript(PAGE_HELPERS);
        await context.route('**/*', (route) => {
            const url = new URL(route.request().url());
            const local = url.origin === new URL(this.base).origin;
            return local || ALLOWED_HOSTS.includes(url.hostname) || url.protocol === 'data:' ? route.continue() : route.abort();
        });
        return context;
    }

    async open(context, key) {
        const page = await context.newPage();
        await page.goto(this.base + PAGES[key], { waitUntil: 'load' });
        await page.addStyleTag({ content: 'html { scroll-behavior: auto }' });
        await page.waitForTimeout(500);
        return page;
    }

    async focus() {
        const context = await this.context();
        const page = await this.open(context, 'manager');
        const failures = [];
        let stops = 0;
        let wrapped = false;
        // walks the whole page: from the skip link until focus comes back to it; the cap only stops a keyboard trap
        for (let i = 0; i < MAX_TAB_PRESSES; i++) {
            await page.keyboard.press('Tab');
            const r = await page.evaluate(() => {
                const el = document.activeElement;
                if (!el || el === document.body) return null;
                const a = window.__audit;
                const cs = getComputedStyle(el);
                const visible = cs.outlineStyle !== 'none' && (parseFloat(cs.outlineWidth) || 0) >= 2;
                const color = a.parse(cs.outlineColor);
                const ring = visible && color ? a.ratio(color, a.background(el.parentElement || el)) : 0;
                return { skipLink: el.classList.contains('skip-link'), what: a.describe(el), visible, ring };
            });
            if (!r) continue;
            if (r.skipLink && stops > 1) {
                wrapped = true;
                break;
            }
            stops++;
            if (!r.visible) failures.push(`no visible focus outline at Tab stop ${stops}: ${r.what}`);
            else if (r.ring < 3) failures.push(`focus outline contrast ${r.ring.toFixed(2)}:1 is below 3:1 at Tab stop ${stops}: ${r.what}`);
        }
        if (!wrapped) failures.push(`focus did not return to the skip link within ${MAX_TAB_PRESSES} Tab presses (a keyboard trap?)`);
        this.notes.focus = `the walk visited ${stops} Tab stops`;
        await context.close();
        return unique(failures);
    }

    async skip() {
        const context = await this.context();
        const page = await this.open(context, 'manager');
        await page.keyboard.press('Tab');
        const first = await page.evaluate(() => document.activeElement.classList.contains('skip-link'));
        const failures = [];
        if (!first) {
            failures.push('the first Tab stop is not the skip link');
        } else {
            await page.keyboard.press('Enter');
            await page.waitForTimeout(300);
            const target = await page.evaluate(() => document.activeElement.id);
            if (target !== 'main-content') failures.push(`the skip link moves focus to "${target}", expected main-content`);
        }
        await context.close();
        return failures;
    }

    async names() {
        const context = await this.context();
        const page = await this.open(context, 'manager');
        const failures = await page.evaluate(() => {
            const out = [];
            for (const b of document.querySelectorAll('.nav-item-toggle')) {
                if (!b.getAttribute('aria-label')) out.push('a nav toggle has no aria-label');
                const active = b.parentElement.classList.contains('is-active');
                if (b.getAttribute('aria-expanded') !== String(active)) out.push('a nav toggle has aria-expanded out of sync with its item');
            }
            const ctx = document.querySelector('.nav-panel-explore .context');
            if (ctx && (ctx.tagName !== 'BUTTON' || !ctx.hasAttribute('aria-expanded'))) out.push('the explore toggle is not a button with aria-expanded');
            const copy = document.querySelector('.copy-button');
            if (copy && copy.getAttribute('aria-label') !== 'Copy to clipboard') out.push('the copy button is not named "Copy to clipboard"');
            const version = document.querySelector('.version-dropdown-toggle');
            if (version && !version.hasAttribute('aria-expanded')) out.push('the version toggle has no aria-expanded');
            const burger = document.querySelector('.navbar-burger');
            if (burger && !burger.getAttribute('aria-label')) out.push('the burger has no aria-label');
            const search = document.getElementById('search-input');
            if (!search.getAttribute('aria-label')) out.push('the search input has no aria-label');
            const home = document.querySelector('.home-link');
            if (home && !home.getAttribute('aria-label')) out.push('the home link has no aria-label');
            const navToggle = document.querySelector('.toolbar .nav-toggle');
            if (navToggle && (!navToggle.getAttribute('aria-label') || !navToggle.hasAttribute('aria-expanded'))) out.push('the toolbar nav toggle has no aria-label or no aria-expanded');
            return out;
        });
        await page.click('.nav-item:not(.is-active) > .nav-item-toggle');
        await page.waitForTimeout(100);
        const synced = await page.evaluate(() => [...document.querySelectorAll('.nav-item-toggle')]
            .every((b) => b.getAttribute('aria-expanded') === String(b.parentElement.classList.contains('is-active'))));
        if (!synced) failures.push('aria-expanded does not follow a nav toggle click');
        const small = await this.context({ viewport: { width: 375, height: 812 } });
        const mobile = await this.open(small, 'manager');
        if (await mobile.evaluate(() => getComputedStyle(document.querySelector('.nav-menu-toggle')).visibility) !== 'hidden') failures.push('"expand all" is visible while the navigation is closed');
        await mobile.click('.toolbar .nav-toggle');
        await mobile.waitForTimeout(100);
        if (await mobile.getAttribute('.toolbar .nav-toggle', 'aria-expanded') !== 'true') failures.push('aria-expanded does not follow a click on the toolbar nav toggle');
        await small.close();
        await context.close();
        return unique(failures);
    }

    async search() {
        const context = await this.context();
        const page = await this.open(context, 'manager');
        const failures = [];
        await page.waitForFunction(() => !document.getElementById('search-input').disabled, null, { timeout: 30000 });
        const state = () => page.evaluate(() => ({
            open: !!document.querySelector('.search-result-item'),
            value: document.getElementById('search-input').value,
            focus: document.activeElement.id || document.activeElement.className,
            inResults: !!document.activeElement.closest('.search-result-dropdown-menu'),
        }));
        await page.click('#search-input');
        await page.keyboard.type('fetch plan');
        await page.waitForSelector('.search-result-item', { timeout: 10000 });
        await page.keyboard.press('Tab');
        await page.waitForTimeout(400); // longer than the 100 ms debounce in search-ui.js
        let s = await state();
        if (!s.inResults) failures.push(`Tab from the search field does not reach the results (focus: ${s.focus})`);
        await page.keyboard.press('Escape');
        await page.waitForTimeout(400);
        s = await state();
        if (s.open || s.value || s.focus !== 'search-input') failures.push(`Escape in the results leaves open=${s.open} value="${s.value}" focus=${s.focus}`);
        await page.click('#search-input');
        await page.keyboard.type('fetch plan');
        await page.waitForSelector('.search-result-item', { timeout: 10000 });
        await page.keyboard.press('Shift+Tab');
        await page.waitForTimeout(400);
        s = await state();
        if (s.open || s.value) failures.push(`leaving the search area leaves open=${s.open} value="${s.value}"`);
        await context.close();
        return failures;
    }

    async contrast() {
        const context = await this.context();
        const pages = {};
        const failures = [];
        for (const [key, selector] of TEXT_CONTRAST) {
            pages[key] ??= await this.open(context, key);
            const ratio = await pages[key].evaluate((sel) => {
                const el = document.querySelector(sel);
                return el ? window.__audit.textContrast(el) : null;
            }, selector);
            if (ratio === null) failures.push(`${key}: ${selector} not found`);
            else if (ratio < 4.5) failures.push(`${key}: ${selector} has contrast ${ratio.toFixed(2)}:1, below 4.5:1`);
        }
        await context.close();
        return failures;
    }

    async styles() {
        const context = await this.context();
        const pages = {};
        const failures = [];
        for (const [key, selector, property, expected] of STYLE_EXPECTATIONS) {
            pages[key] ??= await this.open(context, key);
            const actual = await pages[key].evaluate(([sel, prop]) => {
                const [base, pseudo] = sel.split(/(?=::)/);
                const el = document.querySelector(base);
                return el ? getComputedStyle(el, pseudo || null).getPropertyValue(prop).trim() : null;
            }, [selector, property]);
            const want = expected.startsWith('#') ? hexToRgb(expected) : expected;
            if (actual === null) failures.push(`${key}: ${selector} not found`);
            else if (actual !== want) failures.push(`${key}: ${selector} ${property} is "${actual}", expected "${want}"`);
        }
        await context.close();
        return failures;
    }

    async fonts() {
        const context = await this.context();
        const page = await this.open(context, 'manager');
        const r = await page.evaluate(async () => {
            await document.fonts.ready;
            const family = (sel) => getComputedStyle(document.querySelector(sel)).fontFamily;
            return {
                text: family('article.doc .paragraph p'),
                code: family('article.doc pre code'),
                // document.fonts.check() is true for a family without any @font-face, so look at the loaded faces
                bold: [...document.fonts].some((f) => f.family.replace(/["']/g, '') === 'Roboto' && f.style === 'normal' && f.status === 'loaded' && /^(100 900|700)$/.test(f.weight)),
                mono: [...document.fonts].some((f) => f.family.replace(/["']/g, '') === 'JetBrains Mono' && f.status === 'loaded'),
                requests: performance.getEntriesByType('resource').map((e) => e.name).filter((n) => n.includes('/_/font/')),
            };
        });
        const failures = [];
        if (!/^"?Roboto"?,/.test(r.text)) failures.push(`text font is ${r.text}`);
        if (!/^"JetBrains Mono"/.test(r.code)) failures.push(`code font is ${r.code}`);
        if (!r.bold) failures.push('no loaded Roboto face covers weight 700');
        if (!r.mono) failures.push('JetBrains Mono is not loaded');
        const old = r.requests.filter((n) => /roboto-mono|roboto-latin-500|roboto-latin-400-normal\.woff2?$/.test(n));
        if (old.length) failures.push(`old bundle fonts are still requested: ${old.join(', ')}`);
        await context.close();
        return failures;
    }

    // The color theme: resolved from the stored preference and the system setting, and set before the first stylesheet
    async theme(out) {
        const failures = [];
        const attributes = (page) => page.evaluate(() => {
            const head = [...document.head.children];
            const script = head.findIndex((n) => n.tagName === 'SCRIPT' && n.textContent.includes('jmix-docs-theme'));
            const sheet = head.findIndex((n) => n.matches('link[rel="stylesheet"]'));
            const meta = document.querySelector('meta[name="color-scheme"]');
            return {
                theme: document.documentElement.getAttribute('data-theme'),
                preference: document.documentElement.getAttribute('data-theme-preference'),
                first: window.__themeAtFirstStylesheet,
                scriptFirst: script !== -1 && sheet !== -1 && script < sheet,
                meta: meta ? meta.content : null,
            };
        });
        const cases = [
            { colorScheme: 'light', stored: null, theme: 'light', preference: 'system' },
            { colorScheme: 'dark', stored: null, theme: 'dark', preference: 'system' },
            { colorScheme: 'dark', stored: 'light', theme: 'light', preference: 'light' },
            { colorScheme: 'light', stored: 'dark', theme: 'dark', preference: 'dark' },
            { colorScheme: 'dark', stored: 'sepia', theme: 'dark', preference: 'system' },
        ];
        for (const c of cases) {
            const context = await this.context({ colorScheme: c.colorScheme });
            await context.addInitScript(THEME_PROBE, c.stored);
            const a = await attributes(await this.open(context, 'manager'));
            const what = `${c.colorScheme} system, stored ${c.stored}`;
            if (a.theme !== c.theme || a.preference !== c.preference) failures.push(`${what}: data-theme "${a.theme}", data-theme-preference "${a.preference}", expected "${c.theme}" and "${c.preference}"`);
            if (a.first !== c.theme) failures.push(`${what}: data-theme was "${a.first}" when the first stylesheet entered the page, a flash of the wrong theme`);
            if (!a.scriptFirst) failures.push(`${what}: the theme script does not come before the first stylesheet link in <head>`);
            if (a.meta !== c.theme) failures.push(`${what}: the color-scheme meta element says "${a.meta}", expected "${c.theme}"`);
            await context.close();
        }
        // storage that throws, as in some private modes: the page follows the system
        const blocked = await this.context({ colorScheme: 'dark' });
        await blocked.addInitScript(() => {
            Object.defineProperty(window, 'localStorage', { configurable: true, get() { throw new DOMException('blocked', 'SecurityError'); } });
        });
        const b = await attributes(await this.open(blocked, 'manager'));
        if (b.theme !== 'dark' || b.preference !== 'system') failures.push(`blocked storage: data-theme "${b.theme}", data-theme-preference "${b.preference}", expected "dark" and "system"`);
        await blocked.close();
        // without JavaScript nothing sets the attributes, and the page stays light
        const noScript = await this.context({ colorScheme: 'dark', javaScriptEnabled: false });
        const plain = await noScript.newPage();
        await plain.goto(this.base + PAGES.manager, { waitUntil: 'load' });
        if (await plain.locator('html').getAttribute('data-theme') !== null) failures.push('without JavaScript <html> has a data-theme attribute');
        const plainBody = await plain.evaluate(() => getComputedStyle(document.body).backgroundColor);
        if (plainBody !== 'rgb(255, 255, 255)') failures.push(`without JavaScript the body background is ${plainBody}, expected white`);
        await noScript.close();
        return failures;
    }

    async forced(out) {
        const failures = [];
        for (const colorScheme of ['light', 'dark']) {
            const context = await this.context({ forcedColors: 'active', colorScheme });
            for (const key of ['manager', 'events', 'geomap']) {
                const page = await this.open(context, key);
                await page.screenshot({ path: join(out, `forced-${colorScheme}-${key}.png`) });
                await page.evaluate(() => document.querySelector('.admonitionblock, .listingblock').scrollIntoView({ block: 'center' }));
                await page.screenshot({ path: join(out, `forced-${colorScheme}-${key}-block.png`) });
                const widths = await page.evaluate(() => {
                    const w = (sel, side) => {
                        const el = document.querySelector(sel);
                        return el ? parseFloat(getComputedStyle(el)[`border${side}Width`]) : null;
                    };
                    return {
                        navbar: w('.navbar', 'Bottom'),
                        nav: w('.nav', 'Right'),
                        toolbar: w('.toolbar', 'Bottom'),
                        code: w('article.doc pre.highlight > code', 'Top'),
                        admonition: w('.admonitionblock > table', 'Top'),
                    };
                });
                for (const [what, width] of Object.entries(widths)) {
                    if (width === null) failures.push(`forced colors (${colorScheme}), ${key}: ${what} not found`);
                    else if (width === 0) failures.push(`forced colors (${colorScheme}), ${key}: ${what} has no border`);
                }
                await page.close();
            }
            await context.close();
            failures.push(...await this.forcedPhone(out, colorScheme));
        }
        return failures;
    }

    // Below 1024px upstream draws the burger's lines with background-color, which forced colors replaces with
    // Canvas, and the toolbar's nav toggle with a dark image. Part 2 redraws both; they must be drawn at phone width.
    async forcedPhone(out, colorScheme) {
        const context = await this.context({ forcedColors: 'active', colorScheme, viewport: { width: 375, height: 812 } });
        const page = await this.open(context, 'manager');
        await page.screenshot({ path: join(out, `forced-${colorScheme}-manager-phone.png`) });
        const inspect = () => page.evaluate(() => {
            const invisible = window.__audit.drawn;
            const lines = [...document.querySelectorAll('.navbar-burger span')];
            const toggle = document.querySelector('.toolbar .nav-toggle');
            const icon = toggle && getComputedStyle(toggle, '::before');
            return {
                burger: !lines.length ? 'not found' : lines.some((el) => !el.getClientRects().length) ? 'not displayed' : lines.map((el) => invisible(el)).find(Boolean) ?? null,
                toggle: !toggle ? 'not found' : !toggle.getClientRects().length ? 'not displayed' : icon.content === 'none' ? 'no ::before icon' : icon.maskImage === 'none' ? 'no mask image' : invisible(toggle, '::before'),
                mask: icon ? icon.maskImage : null,
            };
        });
        const failures = [];
        const fail = (message) => failures.push(`forced colors (${colorScheme}), phone: ${message}`);
        const closed = await inspect();
        if (closed.burger) fail(`the burger lines: ${closed.burger}`);
        if (closed.toggle) fail(`the toolbar nav toggle icon: ${closed.toggle}`);
        // while the navigation is open the toggle shows another icon, the back arrow
        await page.click('.toolbar .nav-toggle');
        await page.waitForTimeout(100);
        const open = await inspect();
        await page.screenshot({ path: join(out, `forced-${colorScheme}-manager-phone-nav.png`) });
        if (open.toggle) fail(`the toolbar nav toggle icon while the navigation is open: ${open.toggle}`);
        else if (open.mask === closed.mask) fail('the toolbar nav toggle shows the same icon while the navigation is open');
        await context.close();
        return failures;
    }

    async snapshot(dir, mask) {
        await mkdir(dir, { recursive: true });
        const context = await this.context();
        for (const key of SNAPSHOT_PAGES) {
            const page = await this.open(context, key);
            // the masked elements keep their space, so the rest of the page stays where it was
            if (mask) await page.addStyleTag({ content: `${mask} { visibility: hidden !important; }` });
            for (const position of [0, 50]) {
                await page.evaluate((p) => window.scrollTo(0, (document.documentElement.scrollHeight - innerHeight) * p / 100), position);
                await page.waitForTimeout(300);
                await page.screenshot({ path: join(dir, `${key}-${position}.png`) });
            }
            await page.close();
        }
        await context.close();
    }

    async compare(dir, out, mask) {
        const fresh = join(out, 'compare');
        await this.snapshot(fresh, mask);
        const page = await this.browser.newPage();
        const failures = [];
        for (const file of readdirSync(dir).filter((f) => f.endsWith('.png'))) {
            const [a, b] = await Promise.all([readFile(join(dir, file)), readFile(join(fresh, file))]);
            const share = await page.evaluate(async ([a64, b64]) => {
                const load = (src) => new Promise((ok) => {
                    const img = new Image();
                    img.onload = () => ok(img);
                    img.src = 'data:image/png;base64,' + src;
                });
                const [ia, ib] = await Promise.all([load(a64), load(b64)]);
                if (ia.width !== ib.width || ia.height !== ib.height) return 1;
                const canvas = document.createElement('canvas');
                canvas.width = ia.width;
                canvas.height = ia.height;
                const ctx = canvas.getContext('2d');
                ctx.drawImage(ia, 0, 0);
                const da = ctx.getImageData(0, 0, canvas.width, canvas.height).data;
                ctx.clearRect(0, 0, canvas.width, canvas.height);
                ctx.drawImage(ib, 0, 0);
                const db = ctx.getImageData(0, 0, canvas.width, canvas.height).data;
                let changed = 0;
                for (let i = 0; i < da.length; i += 4) {
                    if (Math.abs(da[i] - db[i]) > 24 || Math.abs(da[i + 1] - db[i + 1]) > 24 || Math.abs(da[i + 2] - db[i + 2]) > 24) changed++;
                }
                return changed / (da.length / 4);
            }, [a.toString('base64'), b.toString('base64')]);
            console.log(`${file}: ${(share * 100).toFixed(3)}% of pixels differ`);
            if (share > 0.002) failures.push(`${file}: ${(share * 100).toFixed(2)}% of pixels differ (see ${join(fresh, file)})`);
        }
        await page.close();
        return failures;
    }
}

const CHECKS = ['focus', 'skip', 'names', 'search', 'contrast', 'styles', 'fonts', 'forced', 'theme'];

async function main() {
    const opts = parseArgs(process.argv.slice(2));
    const root = resolve(opts.site);
    const out = resolve(opts.out);
    await mkdir(out, { recursive: true });
    const server = await serve(root);
    const base = `http://127.0.0.1:${server.address().port}/`;
    const browser = await launchChromium();
    const audit = new Audit(browser, base);
    let failed = 0;
    try {
        if (opts.snapshot) {
            await audit.snapshot(resolve(opts.snapshot), opts.mask);
            console.log(`snapshot saved to ${opts.snapshot}`);
        } else if (opts.compare) {
            const failures = await audit.compare(resolve(opts.compare), out, opts.mask);
            failures.forEach((f) => console.log(`  FAIL ${f}`));
            failed = failures.length;
        } else {
            for (const name of opts.only || CHECKS) {
                let failures;
                try {
                    failures = name === 'forced' || name === 'theme' ? await audit[name](out) : await audit[name]();
                } catch (e) {
                    failures = [`the check threw: ${e.message.split('\n')[0]}`];
                }
                console.log(`${failures.length ? 'FAIL' : 'PASS'} ${name}`);
                if (audit.notes[name]) console.log(`  (${audit.notes[name]})`);
                failures.forEach((f) => console.log(`  - ${f}`));
                failed += failures.length;
            }
        }
    } finally {
        await browser.close();
        server.close();
    }
    process.exit(failed ? 1 : 0);
}

main();
