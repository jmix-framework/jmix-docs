# Docs Dark Theme Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a dark theme and a System / Light / Dark theme menu to the Jmix docs site, with the light theme unchanged apart from the menu.

**Architecture:** An inline script in the head sets `data-theme` and `data-theme-preference` on `<html>` before the stylesheets load; `tokens.css` gains a `:root[data-theme="dark"]` block (screen only) that redefines tier 2 and the literal tier 3 tokens, so no component rule changes for the dark theme. A menu button in the header (`js/theme-menu.js`) changes and saves the preference. `tools/check-css.mjs` and `tools/ui-audit.mjs` learn to check the dark theme.

**Tech Stack:** Antora 3.0.3 with the pinned antora-ui-default bundle (`ui/ui-bundle.zip`), Handlebars partials in `content/supplemental/partials`, plain CSS with custom properties, vanilla JavaScript, Node.js tools with Playwright and Chromium (resolved by `tools/lib/playwright.mjs`, not a dependency of the repository).

**Spec:** `docs/superpowers/specs/2026-10-03-docs-dark-theme-design.md`

## Global Constraints

- Work in the main checkout `/Users/gorelov/Developer/Haulmont/Platform/jmix-framework/jmix-v3-docs` on branch `feature/ui-restyle`. Do not create branches or worktrees: the build needs the untracked `external/` repositories, which exist only there.
- Stage files by explicit path. Never `git add -A`, `git add .` or `git commit -a`: multi-gigabyte untracked files live at the repository root.
- Commit messages: one short imperative subject line, no body, no trailer.
- Do not push.
- Never open the built site (`build/site`) in a browser pane, a desktop browser or with `tools/screenshot-2x.mjs`: the pages load the production analytics container. Look at pages only through `tools/ui-audit.mjs` and the screenshots it writes.
- Outside `content/supplemental/css/tokens.css`, colors are written only as `var(--…)`; `transparent`, `currentColor`, `inherit`, `color-mix()` over those and tokens, and CSS system colors inside `@media (forced-colors: active)` blocks are the only exceptions (`node tools/check-css.mjs`).
- `content/supplemental/css/site.css` part 1 (everything above the line `/* ==== Part 2: Jmix ==== */`, about line 2472) is vendored upstream CSS and is not edited.
- Files derived from antora-ui-default keep their MPL-2.0 header (`tokens.css`, `site.css`, the partials that carry it). The new `js/theme-menu.js` gets no MPL header.
- Follow each file's existing style: 2-space indentation in `tokens.css`, `site.css`, `dropdown-menu.css`, `search.css`; 4-space indentation in `feedback-form.css`, the JavaScript files and the `.hbs` partials; `const`/`let`, semicolons and an IIFE in scripts.
- English everywhere. Markdown paragraphs are not hard-wrapped.
- Storage key `jmix-docs-theme`, values `light` and `dark`; System is the absence of the key. Attributes on `<html>`: `data-theme` (`light` | `dark`) and `data-theme-preference` (`system` | `light` | `dark`).
- Names: the menu button is "Color theme: System", "Color theme: Light" or "Color theme: Dark"; the menu is "Color theme"; the items are "System", "Light", "Dark".
- Build the site with `npx antora antora-playbook.yml` (several minutes). The audit is `node tools/ui-audit.mjs`, optionally `--only <checks>`; it serves `build/site` itself and blocks external hosts.
- The light reference screenshots live in `build/ui-audit-reference/light` (ignored by git through `build/`). Task 1 creates them before any style changes; later tasks compare against them.

## File Structure

| File | Responsibility |
|---|---|
| `content/supplemental/partials/head-styles.hbs` | Inline theme script, then the stylesheet links |
| `content/supplemental/partials/header-content.hbs` | Theme menu markup, first in `.header-btn` |
| `content/supplemental/partials/footer-scripts.hbs` | Loads `js/theme-menu.js` |
| `content/supplemental/partials/pagination.hbs` | Feedback form; the thumbs-up becomes a `span` |
| `content/supplemental/js/theme-menu.js` (new) | Menu behavior; applies, saves and syncs the preference |
| `content/supplemental/css/tokens.css` | New palette steps, new tokens and icons, the dark block |
| `content/supplemental/css/site.css` (part 2) | XML tag color token; the `light-background` image role |
| `content/supplemental/css/dropdown-menu.css` | Both header menus: the shared panel, the theme menu |
| `content/supplemental/css/feedback-form.css` | Input colors; the thumbs-up mask icon |
| `content/supplemental/img/feedback-form__thumb-up.svg` | Deleted |
| six `.adoc` pages | `role=light-background` on eight image macros |
| `tools/check-css.mjs`, `tools/check-css.test.mjs` | Dark block completeness rule |
| `tools/ui-audit.mjs` | `--mask`, dark runs, the `theme` check, forced colors additions |
| `AGENTS.md`, `CONTRIBUTING.md` | Documentation |

---

### Task 1: Audit groundwork and the light reference

**Files:**
- Modify: `tools/ui-audit.mjs` (header comment, `PAGE_HELPERS`, `parseArgs`, `snapshot`, `compare`, `forcedPhone`, `main`)

**Interfaces:**
- Produces: CLI option `--mask <selector>` for `--snapshot` and `--compare`; `Audit.snapshot(dir, mask)`, `Audit.compare(dir, out, mask)`; page helper `window.__audit.drawn(el, pseudo)` returning `null` when the element's (or pseudo-element's) background differs from the forced Canvas color, else a message string.
- Produces: the light reference in `build/ui-audit-reference/light`.

- [ ] **Step 1: Document the option in the header comment**

In `tools/ui-audit.mjs`, replace

```js
 * Options: --site <dir> (default build/site), --out <dir> (default build/ui-audit).
```

with

```js
 * Options: --site <dir> (default build/site), --out <dir> (default build/ui-audit), --mask <selector> (with
 * --snapshot and --compare: hide these elements in both runs, for example the header when it has changed on purpose).
```

- [ ] **Step 2: Accept `--mask`**

In `parseArgs`, replace

```js
    const opts = { site: 'build/site', out: 'build/ui-audit', only: null, snapshot: null, compare: null };
```

with

```js
    const opts = { site: 'build/site', out: 'build/ui-audit', only: null, snapshot: null, compare: null, mask: null };
```

- [ ] **Step 3: Hide the masked elements in snapshots**

Replace the start of `snapshot`

```js
    async snapshot(dir) {
        await mkdir(dir, { recursive: true });
        const context = await this.context();
        for (const key of SNAPSHOT_PAGES) {
            const page = await this.open(context, key);
            for (const position of [0, 50]) {
```

with

```js
    async snapshot(dir, mask) {
        await mkdir(dir, { recursive: true });
        const context = await this.context();
        for (const key of SNAPSHOT_PAGES) {
            const page = await this.open(context, key);
            // the masked elements keep their space, so the rest of the page stays where it was
            if (mask) await page.addStyleTag({ content: `${mask} { visibility: hidden !important; }` });
            for (const position of [0, 50]) {
```

In `compare`, replace

```js
    async compare(dir, out) {
        const fresh = join(out, 'compare');
        await this.snapshot(fresh);
```

with

```js
    async compare(dir, out, mask) {
        const fresh = join(out, 'compare');
        await this.snapshot(fresh, mask);
```

In `main`, replace

```js
            await audit.snapshot(resolve(opts.snapshot));
```

with

```js
            await audit.snapshot(resolve(opts.snapshot), opts.mask);
```

and

```js
            const failures = await audit.compare(resolve(opts.compare), out);
```

with

```js
            const failures = await audit.compare(resolve(opts.compare), out, opts.mask);
```

- [ ] **Step 4: Move the "drawn on Canvas" probe into the page helpers**

Task 4 needs the same probe `forcedPhone` uses, so it becomes a helper. In `PAGE_HELPERS`, replace

```js
    return { parse, background, ratio, textContrast, describe };
```

with

```js
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
```

(`PAGE_HELPERS` is a template literal: the helper must not contain backticks or `${`.)

In `forcedPhone`, replace the body of `inspect`

```js
        const inspect = () => page.evaluate(() => {
            // the Canvas color of the page: a probe that opts out of forced colors and asks for the system color
            const probe = document.createElement('div');
            probe.style.cssText = 'forced-color-adjust: none; background-color: Canvas; position: fixed; width: 1px; height: 1px';
            document.body.append(probe);
            const canvas = getComputedStyle(probe).backgroundColor;
            probe.remove();
            const invisible = (el, pseudo) => {
                const value = getComputedStyle(el, pseudo).backgroundColor;
                const color = window.__audit.parse(value);
                return color && color.a > 0 && value !== canvas ? null : `background-color ${value} is not visible on Canvas ${canvas}`;
            };
            const lines = [...document.querySelectorAll('.navbar-burger span')];
```

with

```js
        const inspect = () => page.evaluate(() => {
            const invisible = window.__audit.drawn;
            const lines = [...document.querySelectorAll('.navbar-burger span')];
```

- [ ] **Step 5: Build the site and run the audit as a baseline**

Run: `npx antora antora-playbook.yml`
Expected: the build finishes; `build/site/jmix/data-access/data-manager.html` exists.

Run: `node tools/ui-audit.mjs`
Expected: `PASS` for focus, skip, names, search, contrast, styles, fonts and forced; exit code 0.

- [ ] **Step 6: Save the light reference and compare it with itself**

Run: `node tools/ui-audit.mjs --snapshot build/ui-audit-reference/light --mask .header`
Expected: `snapshot saved to build/ui-audit-reference/light`; ten PNG files in that directory.

Run: `node tools/ui-audit.mjs --compare build/ui-audit-reference/light --mask .header`
Expected: every file reports `0.000% of pixels differ`; exit code 0.

- [ ] **Step 7: Commit**

```bash
git add tools/ui-audit.mjs
git commit -m "Let the UI audit mask elements in snapshots"
```

---

### Task 2: Set the theme before the stylesheets load

**Files:**
- Modify: `tools/ui-audit.mjs` (new `THEME_PROBE`, new `theme` check, `CHECKS`, `main`)
- Modify: `content/supplemental/partials/head-styles.hbs`

**Interfaces:**
- Consumes: `Audit.context`, `Audit.open`, `PAGES` from the existing audit.
- Produces: `<html data-theme="light|dark" data-theme-preference="system|light|dark">` on every page before the first stylesheet; the audit check `theme`, called as `audit.theme(out)` (Tasks 3 and 4 extend it).

- [ ] **Step 1: Write the failing check**

In `tools/ui-audit.mjs`, add after the `MAX_TAB_PRESSES` constant:

```js
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
```

Add this method to the `Audit` class, after `fonts()`:

```js
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
        await noScript.close();
        return failures;
    }
```

Replace

```js
const CHECKS = ['focus', 'skip', 'names', 'search', 'contrast', 'styles', 'fonts', 'forced'];
```

with

```js
const CHECKS = ['focus', 'skip', 'names', 'search', 'contrast', 'styles', 'fonts', 'forced', 'theme'];
```

and in `main` replace

```js
                    failures = name === 'forced' ? await audit.forced(out) : await audit[name]();
```

with

```js
                    failures = name === 'forced' || name === 'theme' ? await audit[name](out) : await audit[name]();
```

- [ ] **Step 2: Run the check to see it fail**

Run: `node tools/ui-audit.mjs --only theme`
Expected: `FAIL theme`, with lines such as `light system, stored null: data-theme "null", data-theme-preference "null", expected "light" and "system"` for each case and for blocked storage.

- [ ] **Step 3: Add the inline script**

Replace the whole of `content/supplemental/partials/head-styles.hbs` with:

```hbs
{{! This Source Code Form is subject to the terms of the Mozilla Public }}
{{! License, v. 2.0. If a copy of the MPL was not distributed with this }}
{{! file, You can obtain one at http://mozilla.org/MPL/2.0/. }}
{{! The theme script stays before the stylesheet links: it sets the theme before anything is painted. js/theme-menu.js keeps the attributes up to date. }}
<script>
    (function () {
        let preference = 'system';
        try {
            const stored = localStorage.getItem('jmix-docs-theme');
            if (stored === 'light' || stored === 'dark') preference = stored;
        } catch (e) {
            // storage is blocked: follow the system
        }
        const dark = preference === 'dark' || (preference === 'system' && window.matchMedia('(prefers-color-scheme: dark)').matches);
        document.documentElement.setAttribute('data-theme', dark ? 'dark' : 'light');
        document.documentElement.setAttribute('data-theme-preference', preference);
        // the canvas color until the stylesheets arrive; once they do, color-scheme in tokens.css decides
        const meta = document.createElement('meta');
        meta.name = 'color-scheme';
        meta.content = dark ? 'dark' : 'light';
        document.head.appendChild(meta);
    })();
</script>
<link rel="stylesheet" href="{{{uiRootPath}}}/css/tokens.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/site.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/search.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/feedback-form.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/dropdown-menu.css">
```

- [ ] **Step 4: Build and run the check**

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs --only theme`
Expected: `PASS theme`.

- [ ] **Step 5: Confirm nothing else changed**

Run: `node tools/ui-audit.mjs`
Expected: every check passes.

Run: `node tools/ui-audit.mjs --compare build/ui-audit-reference/light --mask .header`
Expected: every file at most `0.002%`-ish, all within the 0.2% threshold; exit code 0. (Without a dark block the attribute changes nothing visible.)

- [ ] **Step 6: Commit**

```bash
git add content/supplemental/partials/head-styles.hbs tools/ui-audit.mjs
git commit -m "Set the color theme before the stylesheets load"
```

---

### Task 3: Dark theme tokens

**Files:**
- Modify: `tools/check-css.mjs`, `tools/check-css.test.mjs`
- Modify: `content/supplemental/css/tokens.css`
- Modify: `content/supplemental/css/site.css` (part 2, the syntax color rules)
- Modify: `tools/ui-audit.mjs` (`TEXT_CONTRAST`, `STYLE_EXPECTATIONS`, new `DARK_STYLE_EXPECTATIONS`, `focus`, `contrast`, `styles`, `theme`)

**Interfaces:**
- Consumes: `data-theme` from Task 2; `audit.theme(out)` from Task 2.
- Produces: tokens `--black`, `--ink-950` … `--ink-50`, `--jmix-violet-950`, `--jmix-violet-900`, `--jmix-violet-300`, `--syntax-tag`; the block `@media screen { :root[data-theme="dark"] { … } }`; the export `checkDarkTokens(text)` in `check-css.mjs` returning `[{ line, message }]`; the audit list `DARK_STYLE_EXPECTATIONS` (Tasks 4 to 6 add entries to it and to `STYLE_EXPECTATIONS`).

- [ ] **Step 1: Write the failing unit tests**

Append to `tools/check-css.test.mjs`:

```js
const withDark = (dark) => checkStylesheets([{
    name: 'tokens.css',
    text: ':root { --gray-900: #111; --color-text: var(--gray-900); --shadow-menu: 0 1px 2px #000; --radius-lg: 8px; }'
        + (dark === null ? '' : ` @media screen { :root[data-theme="dark"] { ${dark} } }`),
}]).map((f) => f.message);

test('requires the dark block to set every tier 2 token', () => {
    assert.deepEqual(withDark('--color-text: #eee; --shadow-menu: none;'), []);
    assert.deepEqual(withDark('--color-text: #eee;'), ['the dark block does not set --shadow-menu']);
    assert.deepEqual(withDark(null), ['no dark block :root[data-theme="dark"] for the tier 2 tokens']);
});

test('rejects dark tokens that the light block does not declare', () => {
    assert.deepEqual(withDark('--color-text: #eee; --shadow-menu: none; --color-txet: #fff;'),
        ['the dark block sets --color-txet, which the light :root block does not declare']);
});
```

- [ ] **Step 2: Run the tests to see them fail**

Run: `node --test tools/check-css.test.mjs`
Expected: the two new tests fail (the rule does not exist); the five old ones pass.

- [ ] **Step 3: Implement the rule**

In `tools/check-css.mjs`, extend the header comment after rule 2:

```js
 *  3. The dark block of tokens.css (:root[data-theme="dark"]) sets every tier 2 token of the light :root block
 *     (--color-* and --shadow-*), and nothing the light block does not declare.
```

Add after `const DECLARED_RX = …;`:

```js
const TIER2_RX = /^--(?:color|shadow)-/;
const LIGHT_BLOCK_RX = /(?:^|[\s}]):root\s*\{/;
const DARK_BLOCK_RX = /:root\[data-theme="dark"\]\s*\{/;
```

Add after `lineOf`:

```js
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
```

In `checkStylesheets`, replace

```js
        if (name === TOKENS_FILE) continue;
```

with

```js
        if (name === TOKENS_FILE) {
            findings.push(...checkDarkTokens(text).map((f) => ({ name, ...f })));
            continue;
        }
```

- [ ] **Step 4: Run the tests to see them pass, and the check on the real files to see it fail**

Run: `node --test tools/check-css.test.mjs`
Expected: 7 tests pass.

Run: `node tools/check-css.mjs`
Expected: exit code 1 with `content/supplemental/css/tokens.css:13: no dark block :root[data-theme="dark"] for the tier 2 tokens`.

- [ ] **Step 5: Commit the rule**

```bash
git add tools/check-css.mjs tools/check-css.test.mjs
git commit -m "Check that the dark theme sets every semantic token"
```

- [ ] **Step 6: Make the audit check contrast, styles and focus in both themes (failing)**

In `tools/ui-audit.mjs`, add to `PAGES`:

```js
    transactions: 'jmix/bpm/bpmn/transactions.html',
```

Append to `TEXT_CONTRAST` (before the closing `];`):

```js
    // pairs that differ between the themes
    ['manager', '.version-dropdown-toggle'],
    ['manager', '.is-current-page > .nav-link'],
    ['manager', 'article.doc .paragraph p code'],
    ['events', 'table.tableblock > thead > tr > th'],
    ['events', '.admonitionblock.note td.content'],
    ['gdg', '.paragraph.since p'],
    ['geomap', 'article.doc pre code.language-xml .hljs-tag'],
    ['geomap', 'article.doc pre code.language-xml .hljs-attr'],
    ['geomap', 'article.doc pre code.language-xml .hljs-string'],
```

Append to `STYLE_EXPECTATIONS` (before the closing `];`):

```js
    // dark theme work: XML tags keep the keyword color in light
    ['geomap', 'article.doc pre code.language-xml .hljs-tag', 'color', '#0033b3'],
    ['manager', 'html', 'color-scheme', 'light'],
```

Add after `STYLE_EXPECTATIONS`:

```js
// The same check in the dark theme (system dark, no stored preference): [page, selector, property, expected].
const DARK_STYLE_EXPECTATIONS = [
    ['manager', 'html', 'color-scheme', 'dark'],
    ['manager', 'nav.navbar', 'background-color', '#17171d'],
    ['manager', 'nav.navbar', 'border-bottom-color', '#2e2e39'],
    ['manager', '.navbar-logo-center', 'fill', '#f2f1f9'],
    ['manager', '.version-dropdown-toggle', 'background-color', '#2b2650'],
    ['manager', '.version-dropdown-toggle', 'color', '#a99bff'],
    ['manager', '#search-input', 'border-top-color', '#6e6e80'],
    ['manager', '.header-icon-link.git-link', 'color', '#f2f1f9'],
    ['manager', '.is-current-page > .nav-link', 'background-color', '#2b2650'],
    ['manager', '.is-current-page > .nav-link', 'color', '#a99bff'],
    ['manager', 'article.doc > h1.page', 'color', '#f2f1f9'],
    ['manager', 'article.doc pre.highlight > code', 'background-color', '#1e1f22'],
    ['manager', 'article.doc .hljs-keyword', 'color', '#cf8e6d'],
    ['manager', 'article.doc .conum[data-value]', 'background-color', '#f2f1f9'],
    ['geomap', 'article.doc pre code.language-xml .hljs-tag', 'color', '#d5b778'],
    ['events', '.admonitionblock.note > table', 'border-left-color', '#25cde3'],
    ['events', '.admonitionblock.note td.icon i', 'color', '#6bdcea'],
    ['geomap', '.admonitionblock.addon-component > table', 'border-left-color', '#a99bff'],
    ['events', 'table.tableblock > thead > tr > th', 'background-color', '#1f1f27'],
    ['events', 'table.tableblock > thead > tr > th', 'color', '#f2f1f9'],
    ['manager', '.feedback-form__btn', 'background-color', '#17171d'],
    ['manager', 'footer.footer', 'background-color', '#17171d'],
];
```

Replace the whole `contrast()` method with:

```js
    async contrast() {
        const failures = [];
        for (const colorScheme of ['light', 'dark']) {
            // dark: the system is dark and nothing is stored, so the page resolves System to the dark theme
            const context = await this.context({ colorScheme });
            const pages = {};
            for (const [key, selector] of TEXT_CONTRAST) {
                pages[key] ??= await this.open(context, key);
                const ratio = await pages[key].evaluate((sel) => {
                    const el = document.querySelector(sel);
                    return el ? window.__audit.textContrast(el) : null;
                }, selector);
                if (ratio === null) failures.push(`${colorScheme}, ${key}: ${selector} not found`);
                else if (ratio < 4.5) failures.push(`${colorScheme}, ${key}: ${selector} has contrast ${ratio.toFixed(2)}:1, below 4.5:1`);
            }
            await context.close();
        }
        return failures;
    }
```

Replace the whole `styles()` method with:

```js
    async styles() {
        const failures = [];
        for (const [colorScheme, expectations] of [['light', STYLE_EXPECTATIONS], ['dark', DARK_STYLE_EXPECTATIONS]]) {
            const context = await this.context({ colorScheme });
            const pages = {};
            for (const [key, selector, property, expected] of expectations) {
                pages[key] ??= await this.open(context, key);
                const actual = await pages[key].evaluate(([sel, prop]) => {
                    const [base, pseudo] = sel.split(/(?=::)/);
                    const el = document.querySelector(base);
                    return el ? getComputedStyle(el, pseudo || null).getPropertyValue(prop).trim() : null;
                }, [selector, property]);
                const want = expected.startsWith('#') ? hexToRgb(expected) : expected;
                if (actual === null) failures.push(`${colorScheme}, ${key}: ${selector} not found`);
                else if (actual !== want) failures.push(`${colorScheme}, ${key}: ${selector} ${property} is "${actual}", expected "${want}"`);
            }
            await context.close();
        }
        return failures;
    }
```

In `focus()`, make the walk run in both themes. Replace

```js
    async focus() {
        const context = await this.context();
        const page = await this.open(context, 'manager');
        const failures = [];
        let stops = 0;
        let wrapped = false;
```

with

```js
    async focus() {
        const failures = [];
        const visited = [];
        for (const colorScheme of ['light', 'dark']) {
            const found = await this.focusWalk(colorScheme);
            failures.push(...found.failures.map((f) => `${colorScheme}: ${f}`));
            visited.push(`${found.stops} (${colorScheme})`);
        }
        this.notes.focus = `the walk visited ${visited.join(' and ')} Tab stops`;
        return unique(failures);
    }

    async focusWalk(colorScheme) {
        const context = await this.context({ colorScheme });
        const page = await this.open(context, 'manager');
        const failures = [];
        let stops = 0;
        let wrapped = false;
```

and at the end of the former `focus()` body replace

```js
        if (!wrapped) failures.push(`focus did not return to the skip link within ${MAX_TAB_PRESSES} Tab presses (a keyboard trap?)`);
        this.notes.focus = `the walk visited ${stops} Tab stops`;
        await context.close();
        return unique(failures);
    }
```

with

```js
        if (!wrapped) failures.push(`focus did not return to the skip link within ${MAX_TAB_PRESSES} Tab presses (a keyboard trap?)`);
        await context.close();
        return { failures: unique(failures), stops };
    }
```

In `theme()`, before `return failures;`, add the print case:

```js
        // print keeps the light values: the dark block is screen only (upstream print styles drop the body background)
        const printing = await this.context({ colorScheme: 'dark' });
        const sheet = await this.open(printing, 'manager');
        await sheet.emulateMedia({ media: 'print' });
        const printed = await sheet.evaluate(() => ({
            scheme: getComputedStyle(document.documentElement).colorScheme,
            text: getComputedStyle(document.querySelector('article.doc .paragraph p')).color,
        }));
        if (printed.scheme !== 'light' || printed.text !== 'rgb(42, 44, 51)') failures.push(`print with the dark theme: color-scheme ${printed.scheme}, text ${printed.text}, expected light and rgb(42, 44, 51)`);
        await printing.close();
        // the manager page in both themes, for review and for the pull request
        for (const colorScheme of ['light', 'dark']) {
            const shots = await this.context({ colorScheme });
            await (await this.open(shots, 'manager')).screenshot({ path: join(out, `theme-${colorScheme}-manager.png`) });
            await (await this.open(shots, 'events')).screenshot({ path: join(out, `theme-${colorScheme}-events.png`) });
            await shots.close();
        }
        // the chat widget host stays in the light color scheme: color-scheme is inherited, and the widget draws itself
        // for a light page. The audit blocks Google Tag Manager, so the widget never loads here; an element with its id
        // stands in for it.
        const widget = await this.context({ colorScheme: 'dark' });
        const host = await (await this.open(widget, 'manager')).evaluate(() => {
            const el = document.createElement('div');
            el.id = 'docsbotai-root';
            document.body.append(el);
            return getComputedStyle(el).colorScheme;
        });
        if (host !== 'light') failures.push(`the chat widget host #docsbotai-root has color-scheme ${host} in the dark theme, expected light`);
        await widget.close();
```

- [ ] **Step 7: Run the audit to see the dark checks fail**

Run: `node tools/ui-audit.mjs --only contrast,styles,theme`
Expected: `PASS contrast` (without a dark block the "dark" run still gets the light values, which pass); `FAIL styles` with lines such as `dark, manager: html color-scheme is "light", expected "dark"` and `dark, manager: nav.navbar background-color is "rgb(255, 255, 255)", expected "rgb(23, 23, 29)"`; `PASS theme` (print already gets the light values).

- [ ] **Step 8: Add the palette steps and the new tokens**

In `content/supplemental/css/tokens.css`, replace the header comment line

```css
 * A dark theme redefines tier 2 and the tier 3 values that do not point to tier 2.
```

with

```css
 * The dark theme, at the end of this file, redefines tier 2 and the tier 3 values that do not point to tier 2;
 * node tools/check-css.mjs checks that it sets every tier 2 token.
```

Replace the accent violet group

```css
  /* accent violet: 700 is the current docs violet, 500 the jmix.io link color, the other steps are derived */
  --jmix-violet-800: #261d7d;
  --jmix-violet-700: #342a98;
  --jmix-violet-500: #7b6dff;
  --jmix-violet-200: #d3cdf8;
  --jmix-violet-50: #f0eeff;
```

with

```css
  /* accent violet: 700 is the current docs violet, 500 the jmix.io link color, the other steps are derived;
     950, 900 and 300 serve the dark theme */
  --jmix-violet-950: #2b2650;
  --jmix-violet-900: #3a3470;
  --jmix-violet-800: #261d7d;
  --jmix-violet-700: #342a98;
  --jmix-violet-500: #7b6dff;
  --jmix-violet-300: #a99bff;
  --jmix-violet-200: #d3cdf8;
  --jmix-violet-50: #f0eeff;
```

Replace

```css
  --gray-50: #f6f7f9;
  --white: #fff;
```

with

```css
  --gray-50: #f6f7f9;
  --white: #fff;
  --black: #000;

  /* "Ink": the dark theme's grays, with a slight violet cast; derived */
  --ink-950: #17171d;
  --ink-900: #1f1f27;
  --ink-850: #272730;
  --ink-800: #2e2e39;
  --ink-700: #3f3f4c;
  --ink-500: #6e6e80;
  --ink-400: #9e9eb1;
  --ink-300: #c6c6d2;
  --ink-200: #d8d8e2;
  --ink-50: #f2f1f9;
```

Replace

```css
  --syntax-property-key: #083080;
```

with

```css
  --syntax-property-key: #083080;
  /* XML and HTML tags: IntelliJ's light scheme draws them like keywords, its dark scheme in yellow */
  --syntax-tag: #0033b3;
```

Replace

```css
  --banner-text: var(--color-text-on-accent);
```

with

```css
  /* white in both themes: the banner stays violet, while the text on accent fills turns navy in the dark theme */
  --banner-text: var(--white);
```

- [ ] **Step 9: Add the dark block**

Append to the end of `content/supplemental/css/tokens.css` (after the closing `}` of `:root`):

```css

/* ---- Dark theme ---- */
/* data-theme on <html> comes from the inline script in partials/head-styles.hbs and from js/theme-menu.js.
   Screen only, so print keeps the light values. Contrast of every pair: see the dark theme design spec. */
@media screen {
  :root[data-theme="dark"] {
    color-scheme: dark;

    /* tier 2 */
    --color-text: var(--ink-200);
    --color-heading: var(--ink-50);
    --color-text-nav: var(--ink-300);
    --color-text-muted: var(--ink-400);
    --color-text-on-accent: var(--jmix-space);
    --color-link: var(--jmix-violet-300);
    --color-link-hover: var(--jmix-violet-200);
    --color-accent: var(--jmix-violet-300);
    --color-accent-tint: var(--jmix-violet-950);
    --color-accent-line: var(--jmix-violet-900);
    --color-focus: var(--jmix-violet-300);
    --color-surface: var(--ink-950);
    --color-surface-subtle: var(--ink-900);
    --color-surface-hover: var(--ink-850);
    --color-line: var(--ink-800);
    --color-line-strong: var(--ink-700);
    --color-control-border: var(--ink-500);
    --color-overlay: color-mix(in srgb, var(--black) 55%, transparent);
    --shadow-menu: 0 10px 28px color-mix(in srgb, var(--black) 45%, transparent), 0 2px 6px color-mix(in srgb, var(--black) 30%, transparent);

    /* tier 3: header */
    --header-logo-center: var(--ink-50);

    /* tier 3: code, IntelliJ IDEA Dark (New UI); the comment is lightened from #7a7e85 (4.0:1) to 4.75:1 */
    --code-block-background: #1e1f22;
    --code-block-text: #bcbec4;
    --inline-code-text: #e2e2ea;
    --syntax-keyword: #cf8e6d;
    --syntax-string: #6aab73;
    --syntax-number: #2aacb8;
    --syntax-comment: #868a91;
    --syntax-annotation: #b3ae60;
    --syntax-function: #56a8f5;
    --syntax-field: #c77dbb;
    --syntax-attribute: #bababa;
    --syntax-property-key: #cf8e6d;
    --syntax-tag: #d5b778;
    --syntax-added: #294436;
    --syntax-removed: #4a2a2e;

    /* tier 3: admonitions; backgrounds are the edge hue at 9% over the surface, borders at 24%; the brand edges stay */
    --note-color: #6bdcea;
    --note-background: #18272f;
    --note-border-color: #1a434d;
    --tip-color: #5ee0a5;
    --tip-background: #182826;
    --tip-border-color: #1a4536;
    --warning-color: #f9c45e;
    --warning-background: #2c251e;
    --warning-border-color: #4e3d20;
    --important-color: #ff86ab;
    --important-background: #2c1723;
    --important-border-color: #4e162e;
    --caution-color: #b9afff;
    --caution-background: #201f31;
    --caution-border-color: #2f2c53;
    --addon-color: #ddd8ff;
    --addon-background: #201f31;
    --addon-border-color: #2f2c53;
    /* Space would vanish on the dark surface */
    --addon-accent: var(--jmix-violet-300);
  }
}
```

- [ ] **Step 10: Build and see what is still missing**

Run: `node tools/check-css.mjs && node --test tools/check-css.test.mjs`
Expected: `5 stylesheets checked, no problems.` and 7 passing tests.

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs --only contrast,styles,theme`
Expected: `PASS contrast`; `FAIL styles` with exactly one line, `dark, geomap: article.doc pre code.language-xml .hljs-tag color is "rgb(207, 142, 109)", expected "rgb(213, 183, 120)"` (XML tags still read the keyword color); `FAIL theme` with exactly one line, `the chat widget host #docsbotai-root has color-scheme dark in the dark theme, expected light`.

- [ ] **Step 11: Give XML its tag color and keep the chat widget light**

In `content/supplemental/css/site.css` part 2, replace

```css
/* syntax colors: highlight.js 10.7.2 classes */
.doc :is(.hljs-keyword, .hljs-literal, .hljs-selector-tag, .hljs-section, .hljs-tag, .hljs-name) {
  color: var(--syntax-keyword);
  font-weight: 400;
}
```

with

```css
/* syntax colors: highlight.js 10.7.2 classes */
.doc :is(.hljs-keyword, .hljs-literal, .hljs-selector-tag, .hljs-section) {
  color: var(--syntax-keyword);
  font-weight: 400;
}

/* XML and HTML tags: like keywords in IntelliJ's light scheme, yellow in its dark one */
.doc :is(.hljs-tag, .hljs-name) {
  color: var(--syntax-tag);
  font-weight: 400;
}
```

and replace

```css
.doc .language-xml .hljs-meta {
  color: var(--syntax-keyword);
}
```

with

```css
/* the XML prolog and DOCTYPE take the tag color, so a whole XML block stays in one tag color */
.doc .language-xml .hljs-meta {
  color: var(--syntax-tag);
}
```

Append to the end of `content/supplemental/css/site.css`:

```css

/* ---- Chat widget ---- */

/* The DocsBot chat widget, injected by Google Tag Manager, mounts into #docsbotai-root and draws itself for a light
   page. color-scheme is inherited, so without this the dark theme would turn the parts it leaves to the browser
   (fields, placeholders, scrollbars) dark inside its light panel. */
#docsbotai-root {
  color-scheme: light;
}
```

- [ ] **Step 12: Check, build and run the audit**

Run: `node tools/check-css.mjs`
Expected: `5 stylesheets checked, no problems.`

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs`
Expected: every check passes, including `contrast` and `styles` in both themes and `theme` with the print and chat widget cases. The `focus` note reports Tab stops for light and dark.

Run: `node tools/ui-audit.mjs --compare build/ui-audit-reference/light --mask .header`
Expected: all files within the threshold; the light theme did not change.

- [ ] **Step 13: Look at the dark theme**

Open with the Read tool `build/ui-audit/theme-light-manager.png`, `theme-dark-manager.png`, `theme-light-events.png`, `theme-dark-events.png`, `forced-light-manager.png` and `forced-dark-manager.png`. Expected: the light screenshots look as before; the dark ones have the Ink surfaces, a light logo center, IntelliJ-like code colors and dark admonitions with the brand edges; the forced colors ones draw borders and text in system colors.

- [ ] **Step 14: Commit**

```bash
git add content/supplemental/css/tokens.css content/supplemental/css/site.css tools/ui-audit.mjs
git commit -m "Add the dark theme tokens"
```

---

### Task 4: The theme menu

**Files:**
- Modify: `content/supplemental/css/tokens.css` (four mask icons)
- Modify: `content/supplemental/partials/header-content.hbs`
- Modify: `content/supplemental/partials/footer-scripts.hbs`
- Create: `content/supplemental/js/theme-menu.js`
- Modify: `content/supplemental/css/dropdown-menu.css`
- Modify: `tools/ui-audit.mjs` (`theme`, `forced`, style expectations)

**Interfaces:**
- Consumes: the attributes from Task 2, the dark tokens from Task 3, `window.__audit.drawn` from Task 1, `THEME_PROBE` from Task 2.
- Produces: markup `.theme-menu > button.theme-menu-toggle + ul.theme-menu-list#theme-menu-list > li > button.theme-menu-item[data-theme-option]`; tokens `--icon-monitor`, `--icon-sun`, `--icon-moon`, `--icon-check`.

- [ ] **Step 1: Write the failing audit checks**

In `tools/ui-audit.mjs`, in `theme()`, before the print case added in Task 3, add:

```js
        // the menu: semantics, keyboard, pointer, other tabs, the system setting and persistence
        failures.push(...await this.themeMenu(out));
        // without JavaScript the menu could not work, so it is hidden
        const noMenu = await this.context({ javaScriptEnabled: false });
        const bare = await noMenu.newPage();
        await bare.goto(this.base + PAGES.manager, { waitUntil: 'load' });
        if (await bare.locator('.theme-menu').isVisible()) failures.push('without JavaScript the theme menu is visible');
        await noMenu.close();
```

Add these methods to the `Audit` class after `theme()`:

```js
    async themeMenu(out) {
        const failures = [];
        const context = await this.context({ colorScheme: 'light' });
        const page = await this.open(context, 'manager');
        const state = (p = page) => p.evaluate(() => {
            const toggle = document.querySelector('.theme-menu-toggle');
            const items = [...document.querySelectorAll('.theme-menu-item')];
            let stored;
            try {
                stored = localStorage.getItem('jmix-docs-theme');
            } catch (e) {
                stored = 'blocked';
            }
            const active = document.activeElement;
            const meta = document.querySelector('meta[name="color-scheme"]');
            return {
                theme: document.documentElement.getAttribute('data-theme'),
                preference: document.documentElement.getAttribute('data-theme-preference'),
                meta: meta ? meta.content : null,
                stored,
                expanded: toggle.getAttribute('aria-expanded'),
                open: getComputedStyle(document.querySelector('.theme-menu-list')).display !== 'none',
                label: toggle.getAttribute('aria-label'),
                checked: items.filter((i) => i.getAttribute('aria-checked') === 'true').map((i) => i.dataset.themeOption),
                focus: active === toggle ? 'toggle' : active.dataset.themeOption || active.tagName.toLowerCase(),
            };
        });
        const expect = async (step, wanted, p = page) => {
            const actual = await state(p);
            for (const [k, v] of Object.entries(wanted)) {
                if (JSON.stringify(actual[k]) !== JSON.stringify(v)) failures.push(`theme menu, ${step}: ${k} is ${JSON.stringify(actual[k])}, expected ${JSON.stringify(v)}`);
            }
        };
        const semantics = await page.evaluate(() => {
            const toggle = document.querySelector('.theme-menu-toggle');
            const list = toggle && document.getElementById(toggle.getAttribute('aria-controls'));
            return {
                haspopup: toggle && toggle.getAttribute('aria-haspopup'),
                role: list && list.getAttribute('role'),
                items: list ? list.querySelectorAll('[role="menuitemradio"]').length : 0,
            };
        });
        if (semantics.haspopup !== 'menu' || semantics.role !== 'menu' || semantics.items !== 3) {
            failures.push(`theme menu semantics: aria-haspopup ${semantics.haspopup}, the controlled element's role ${semantics.role}, ${semantics.items} menuitemradio items`);
            await context.close();
            return failures;
        }
        await expect('at load', { theme: 'light', preference: 'system', stored: null, expanded: 'false', open: false, label: 'Color theme: System', checked: ['system'] });
        // keyboard
        await page.focus('.theme-menu-toggle');
        await page.keyboard.press('Enter');
        await expect('Enter on the button', { expanded: 'true', open: true, focus: 'system' });
        await page.keyboard.press('ArrowDown');
        await expect('ArrowDown', { focus: 'light' });
        await page.keyboard.press('End');
        await expect('End', { focus: 'dark' });
        await page.keyboard.press('ArrowDown');
        await expect('ArrowDown on the last item', { focus: 'system' });
        await page.keyboard.press('ArrowUp');
        await expect('ArrowUp on the first item', { focus: 'dark' });
        await page.keyboard.press('Home');
        await expect('Home', { focus: 'system' });
        await page.keyboard.press('End');
        await page.keyboard.press('Enter');
        await expect('Enter on Dark', { theme: 'dark', preference: 'dark', meta: 'dark', stored: 'dark', expanded: 'false', open: false, focus: 'toggle', label: 'Color theme: Dark', checked: ['dark'] });
        await page.keyboard.press('Space');
        await expect('Space on the button', { open: true, focus: 'dark' });
        await page.keyboard.press('Escape');
        await expect('Escape', { open: false, expanded: 'false', focus: 'toggle' });
        await page.keyboard.press('ArrowDown');
        await expect('ArrowDown on the button', { open: true, focus: 'dark' });
        await page.keyboard.press('Tab');
        await expect('Tab out of the menu', { open: false, expanded: 'false' });
        // another page of the same site follows a choice
        const other = await this.open(context, 'manager');
        await expect('another page at load', { theme: 'dark', preference: 'dark' }, other);
        // pointer
        await page.click('.theme-menu-toggle');
        await expect('a click on the button', { open: true });
        await page.click('.theme-menu-item[data-theme-option="light"]');
        await expect('a click on Light', { theme: 'light', preference: 'light', meta: 'light', stored: 'light', open: false, checked: ['light'] });
        await page.waitForTimeout(200);
        await expect('the other page after Light', { theme: 'light', preference: 'light' }, other);
        await page.click('.theme-menu-toggle');
        await page.click('article.doc > h1.page');
        await expect('a click outside', { open: false, expanded: 'false' });
        await page.click('.theme-menu-toggle');
        await page.click('.nav-item:not(.is-active) > .nav-item-toggle');
        await expect('a click on a navigation toggle, which site.js keeps from bubbling', { open: false, expanded: 'false' });
        // the system setting: an explicit choice ignores it, System follows it live
        await page.emulateMedia({ colorScheme: 'dark' });
        await page.waitForTimeout(100);
        await expect('a dark system with Light chosen', { theme: 'light' });
        await page.click('.theme-menu-toggle');
        await page.click('.theme-menu-item[data-theme-option="system"]');
        await expect('a click on System', { theme: 'dark', preference: 'system', stored: null, checked: ['system'] });
        await page.emulateMedia({ colorScheme: 'light' });
        await page.waitForTimeout(100);
        await expect('the system turning light', { theme: 'light', preference: 'system' });
        await page.emulateMedia({ colorScheme: 'dark' });
        await page.waitForTimeout(100);
        await expect('the system turning dark', { theme: 'dark', preference: 'system' });
        // persistence
        await page.click('.theme-menu-toggle');
        await page.click('.theme-menu-item[data-theme-option="dark"]');
        await page.reload({ waitUntil: 'load' });
        await expect('a reload with Dark chosen', { theme: 'dark', preference: 'dark', stored: 'dark', label: 'Color theme: Dark', checked: ['dark'] });
        await page.click('.theme-menu-toggle');
        await page.screenshot({ path: join(out, 'theme-dark-manager-menu.png') });
        await page.keyboard.press('Escape');
        // a page restored from the back/forward cache reads the preference again; another tab's write is simulated
        await page.evaluate(() => {
            localStorage.setItem('jmix-docs-theme', 'light');
            window.dispatchEvent(new PageTransitionEvent('pageshow', { persisted: true }));
        });
        await expect('a pageshow from the back/forward cache', { theme: 'light', preference: 'light', checked: ['light'] });
        await context.close();
        // storage that throws: a choice still applies to the page
        const blocked = await this.context({ colorScheme: 'light' });
        await blocked.addInitScript(() => {
            Object.defineProperty(window, 'localStorage', { configurable: true, get() { throw new DOMException('blocked', 'SecurityError'); } });
        });
        const b = await this.open(blocked, 'manager');
        await b.click('.theme-menu-toggle');
        await b.click('.theme-menu-item[data-theme-option="dark"]');
        await expect('a choice with blocked storage', { theme: 'dark', preference: 'dark', stored: 'blocked' }, b);
        await blocked.close();
        // the header at the narrowest desktop width
        const narrow = await this.context({ colorScheme: 'light', viewport: { width: 1024, height: 768 } });
        const np = await this.open(narrow, 'manager');
        const fits = await np.evaluate(() => {
            const last = document.querySelector('.header-btn > :last-child').getBoundingClientRect();
            const search = document.querySelector('.search-field').getBoundingClientRect();
            const version = document.querySelector('.version-dropdown-toggle').getBoundingClientRect();
            return last.right <= window.innerWidth && search.left >= version.right;
        });
        if (!fits) failures.push('theme menu: at 1024px the header items overflow or overlap');
        await narrow.close();
        // phone width: the menu sits in the burger panel and stays inside the viewport
        const phone = await this.context({ colorScheme: 'light', viewport: { width: 375, height: 812 } });
        const pp = await this.open(phone, 'manager');
        await pp.click('.navbar-burger');
        await pp.click('.theme-menu-toggle');
        const box = await pp.evaluate(() => {
            const r = document.querySelector('.theme-menu-list').getBoundingClientRect();
            return { left: r.left, right: r.right, width: window.innerWidth };
        });
        if (box.left < 0 || box.right > box.width) failures.push(`theme menu at phone width: the menu spans ${Math.round(box.left)} to ${Math.round(box.right)}px of ${box.width}px`);
        await pp.screenshot({ path: join(out, 'theme-light-manager-phone-menu.png') });
        // closing the burger panel closes the menu too, so it is not open when the panel comes back
        await pp.click('.navbar-burger');
        await expect('closing the burger panel', { open: false, expanded: 'false' }, pp);
        await pp.click('.navbar-burger');
        await pp.click('.theme-menu-toggle');
        await pp.click('.theme-menu-item[data-theme-option="dark"]');
        await expect('a choice at phone width', { theme: 'dark' }, pp);
        await pp.screenshot({ path: join(out, 'theme-dark-manager-phone.png') });
        await phone.close();
        return failures;
    }
```

In `forced()`, inside the `for (const key of …)` loop, after the `widths` failures loop and before `await page.close();`, add:

```js
                if (key === 'manager') {
                    const icon = await page.evaluate(() => window.__audit.drawn(document.querySelector('.theme-menu-toggle'), '::before'));
                    if (icon) failures.push(`forced colors (${colorScheme}), manager: the theme menu icon: ${icon}`);
                }
```

Append to `STYLE_EXPECTATIONS`:

```js
    ['manager', '.theme-menu-toggle', 'color', '#17124b'],
    ['manager', '.theme-menu-toggle', 'width', '40.5px'],
```

Append to `DARK_STYLE_EXPECTATIONS`:

```js
    ['manager', '.theme-menu-toggle', 'color', '#f2f1f9'],
    ['manager', '.theme-menu-list', 'background-color', '#17171d'],
```

- [ ] **Step 2: Run the checks to see them fail**

Run: `node tools/ui-audit.mjs --only theme,styles,forced`
Expected: `FAIL theme` with `theme menu semantics: aria-haspopup null …`; `FAIL styles` with `.theme-menu-toggle not found`; `FAIL forced` (the evaluate throws on a missing element and the check reports `the check threw`).

- [ ] **Step 3: Add the icons**

In `content/supplemental/css/tokens.css`, after the `--icon-back` line, add:

```css
  --icon-monitor: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='black' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><rect x='3' y='4' width='18' height='12' rx='2'/><path d='M8 20h8'/><path d='M12 16v4'/></svg>");
  --icon-sun: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='black' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><circle cx='12' cy='12' r='4'/><path d='M12 2.5v2'/><path d='M12 19.5v2'/><path d='m5.3 5.3 1.4 1.4'/><path d='m17.3 17.3 1.4 1.4'/><path d='M2.5 12h2'/><path d='M19.5 12h2'/><path d='m5.3 18.7 1.4-1.4'/><path d='m17.3 6.7 1.4-1.4'/></svg>");
  --icon-moon: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='black' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M20 14.5A8 8 0 0 1 9.5 4a8 8 0 1 0 10.5 10.5z'/></svg>");
  --icon-check: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='black' stroke-width='2.4' stroke-linecap='round' stroke-linejoin='round'><path d='m5 12.5 4.5 4.5L19 7.5'/></svg>");
```

- [ ] **Step 4: Add the markup**

In `content/supplemental/partials/header-content.hbs`, replace

```hbs
            <div class="header-btn">
                <a class="header-icon-link ai-assistant-link"
```

with

```hbs
            <div class="header-btn">
                <div class="theme-menu">
                    <button class="header-icon-link theme-menu-toggle" type="button" aria-haspopup="menu" aria-expanded="false" aria-controls="theme-menu-list" aria-label="Color theme" title="Color theme"></button>
                    <ul class="theme-menu-list" id="theme-menu-list" role="menu" aria-label="Color theme">
                        <li role="none"><button class="theme-menu-item" type="button" role="menuitemradio" aria-checked="false" tabindex="-1" data-theme-option="system">System</button></li>
                        <li role="none"><button class="theme-menu-item" type="button" role="menuitemradio" aria-checked="false" tabindex="-1" data-theme-option="light">Light</button></li>
                        <li role="none"><button class="theme-menu-item" type="button" role="menuitemradio" aria-checked="false" tabindex="-1" data-theme-option="dark">Dark</button></li>
                    </ul>
                </div>
                <a class="header-icon-link ai-assistant-link"
```

In `content/supplemental/partials/footer-scripts.hbs`, replace

```hbs
<script async src="{{uiRootPath}}/js/dropdown-menu.js"></script>
```

with

```hbs
<script async src="{{uiRootPath}}/js/dropdown-menu.js"></script>
<script async src="{{uiRootPath}}/js/theme-menu.js"></script>
```

- [ ] **Step 5: Write the script**

Create `content/supplemental/js/theme-menu.js`:

```js
// The color theme menu in the header: System, Light or Dark. The inline script in partials/head-styles.hbs sets
// data-theme and data-theme-preference on <html> before the stylesheets load; this script keeps them up to date,
// saves the choice, follows the system setting while System is chosen, and follows choices made in other tabs.
// The menu follows the WAI-ARIA menu button pattern with menuitemradio items.
(function () {
    const KEY = 'jmix-docs-theme';
    const LABELS = { system: 'System', light: 'Light', dark: 'Dark' };
    const root = document.documentElement;
    const menu = document.querySelector('.theme-menu');
    if (!menu) return;
    const toggle = menu.querySelector('.theme-menu-toggle');
    const list = menu.querySelector('.theme-menu-list');
    const items = Array.from(list.querySelectorAll('.theme-menu-item'));
    const system = window.matchMedia('(prefers-color-scheme: dark)');

    // the stored preference, or null when storage is blocked
    const read = function () {
        try {
            const value = localStorage.getItem(KEY);
            return value === 'light' || value === 'dark' ? value : 'system';
        } catch (e) {
            return null;
        }
    };
    const save = function (preference) {
        try {
            if (preference === 'system') localStorage.removeItem(KEY);
            else localStorage.setItem(KEY, preference);
        } catch (e) {
            // storage is blocked: the choice holds for this page only
        }
    };

    let preference = LABELS[root.getAttribute('data-theme-preference')] ? root.getAttribute('data-theme-preference') : read() || 'system';

    // every path goes through here: a choice, the system setting, another tab, a page restored from the cache
    const apply = function (next) {
        preference = next;
        const dark = next === 'dark' || (next === 'system' && system.matches);
        root.setAttribute('data-theme', dark ? 'dark' : 'light');
        root.setAttribute('data-theme-preference', next);
        const meta = document.querySelector('meta[name="color-scheme"]');
        if (meta) meta.content = dark ? 'dark' : 'light';
        const label = 'Color theme: ' + LABELS[next];
        toggle.setAttribute('aria-label', label);
        toggle.setAttribute('title', label);
        items.forEach(function (item) {
            item.setAttribute('aria-checked', String(item.dataset.themeOption === next));
        });
    };

    const isOpen = function () {
        return list.classList.contains('opened');
    };
    const open = function () {
        list.classList.add('opened');
        toggle.setAttribute('aria-expanded', 'true');
        const checked = items.find(function (item) {
            return item.dataset.themeOption === preference;
        });
        (checked || items[0]).focus();
    };
    const close = function (returnFocus) {
        list.classList.remove('opened');
        toggle.setAttribute('aria-expanded', 'false');
        if (returnFocus) toggle.focus();
    };

    // Enter and Space reach the buttons as clicks
    toggle.addEventListener('click', function () {
        if (isOpen()) close(false);
        else open();
    });
    toggle.addEventListener('keydown', function (event) {
        if (event.key === 'ArrowDown' || event.key === 'ArrowUp') {
            event.preventDefault();
            open();
        }
    });
    items.forEach(function (item) {
        item.addEventListener('click', function () {
            apply(item.dataset.themeOption);
            save(preference);
            close(true);
        });
    });
    list.addEventListener('keydown', function (event) {
        const index = items.indexOf(document.activeElement);
        const target = {
            ArrowDown: (index + 1) % items.length,
            ArrowUp: (index - 1 + items.length) % items.length,
            Home: 0,
            End: items.length - 1,
        }[event.key];
        if (target !== undefined) {
            event.preventDefault();
            items[target].focus();
        }
    });
    menu.addEventListener('keydown', function (event) {
        if (event.key === 'Escape' && isOpen()) {
            event.preventDefault();
            close(true);
        }
    });
    // focus moving elsewhere (Tab, the / shortcut) or a click elsewhere closes the menu. Not focusout: Safari does
    // not focus a clicked button, so focus leaves the menu on mousedown and the click would never reach the item.
    document.addEventListener('focusin', function (event) {
        if (isOpen() && !menu.contains(event.target)) close(false);
    });
    // capture phase: site.js stops clicks in the navigation, on the burger and on the toolbar's nav toggle from bubbling
    document.addEventListener('click', function (event) {
        if (isOpen() && !menu.contains(event.target)) close(false);
    }, true);

    system.addEventListener('change', function () {
        if (preference === 'system') apply('system');
    });
    // other tabs write the key; this tab does not get its own storage events. key null means localStorage.clear().
    window.addEventListener('storage', function (event) {
        if (event.key === KEY || event.key === null) apply(read() || preference);
    });
    // a page restored from the back/forward cache missed the storage events of the meantime
    window.addEventListener('pageshow', function (event) {
        if (event.persisted) apply(read() || preference);
    });

    apply(preference);
})();
```

- [ ] **Step 6: Style the menu**

Replace the whole of `content/supplemental/css/dropdown-menu.css` with:

```css
/* Header menus: the version menu (js/dropdown-menu.js) and the color theme menu (js/theme-menu.js). */

.navbar-item-dropdown {
  align-self: center;
  padding: 0;
  font-size: 1rem;
  line-height: 1;
}

.version-dropdown-toggle {
  position: relative;
  display: flex;
  gap: 0.4rem;
  align-items: center;
  padding: 0.35rem 0.55rem 0.35rem 0.65rem;
  border: 0;
  border-radius: var(--radius-md);
  background: var(--color-accent-tint);
  color: var(--color-accent);
  font: inherit;
  font-size: 0.8rem;
  font-weight: 600;
  line-height: inherit;
}

.version-dropdown-toggle:hover {
  background: var(--color-accent-line);
}

.version-dropdown-icon {
  width: 0.6rem;
  height: 0.6rem;
  color: inherit;
}

.version-dropdown-toggle.opened .version-dropdown-icon {
  transform: rotate(180deg);
}

@media (prefers-reduced-motion: no-preference) {
  .version-dropdown-icon {
    transition: transform 0.1s ease-in;
  }
}

/* the panel of both menus */
.version-dropdown-menu,
.theme-menu-list {
  position: absolute;
  z-index: 1000;
  display: none;
  min-width: 10rem;
  margin: 0.5rem 0 0;
  padding: 0.375rem;
  border: 1px solid var(--color-line);
  border-radius: var(--radius-lg);
  background-color: var(--color-surface);
  box-shadow: var(--shadow-menu);
  list-style: none;
  text-align: left;
}

.version-dropdown-menu.opened,
.theme-menu-list.opened {
  display: block;
}

.version-dropdown-item {
  display: block;
  padding: 0.45rem 0.625rem;
  border-radius: 5px;
  color: var(--color-text);
  font-size: 0.8rem;
  font-weight: 400;
  text-decoration: none;
  white-space: nowrap;
}

.version-dropdown-item:hover {
  background: var(--color-surface-hover);
  text-decoration: none;
}

/* the theme menu: a header icon button, then a hairline before the header links */
.theme-menu {
  position: relative;
  display: flex;
  align-items: center;
}

/* a border, so forced colors keep it */
.theme-menu::after {
  content: "";
  height: 1.25rem;
  margin: 0 0.25rem 0 0.5rem;
  border-left: 1px solid var(--color-line);
}

/* the attributes come from the inline theme script; without JavaScript the menu could not work */
:root:not([data-theme]) .theme-menu {
  display: none;
}

.theme-menu-toggle {
  padding: 0;
  border: 0;
  background: transparent;
  font: inherit;
  cursor: pointer;
}

.theme-menu-toggle[aria-expanded="true"] {
  background: var(--navbar_hover-background);
}

/* the icon shows the chosen mode; data-theme-preference is set before the header is painted */
.theme-menu-toggle::before {
  content: "";
  width: 1.25rem;
  height: 1.25rem;
  background-color: currentColor;
  mask: var(--icon-monitor) center / contain no-repeat;
}

:root[data-theme-preference="light"] .theme-menu-toggle::before {
  mask-image: var(--icon-sun);
}

:root[data-theme-preference="dark"] .theme-menu-toggle::before {
  mask-image: var(--icon-moon);
}

.theme-menu-list {
  top: 100%;
  left: 0;
}

.theme-menu-item {
  display: flex;
  gap: 0.625rem;
  align-items: center;
  width: 100%;
  padding: 0.45rem 0.625rem;
  border: 0;
  border-radius: var(--radius-md);
  background: transparent;
  color: var(--color-text);
  font: inherit;
  font-size: 0.8rem;
  line-height: 1.2;
  text-align: left;
  white-space: nowrap;
  cursor: pointer;
}

.theme-menu-item:hover,
.theme-menu-item:focus {
  background: var(--color-surface-hover);
}

/* the items fill the panel, where a ring outside the box would touch the neighbors: it goes inside */
.theme-menu-item:focus-visible {
  outline-offset: -2px;
}

.theme-menu-item::before,
.theme-menu-item::after {
  content: "";
  flex: none;
  width: 1rem;
  height: 1rem;
  background-color: var(--color-text-muted);
  mask: var(--icon-monitor) center / contain no-repeat;
}

.theme-menu-item[data-theme-option="light"]::before {
  mask-image: var(--icon-sun);
}

.theme-menu-item[data-theme-option="dark"]::before {
  mask-image: var(--icon-moon);
}

.theme-menu-item::after {
  margin-left: auto;
  mask-image: var(--icon-check);
  visibility: hidden;
}

.theme-menu-item[aria-checked="true"] {
  color: var(--color-accent);
  font-weight: 600;
}

.theme-menu-item[aria-checked="true"]::before,
.theme-menu-item[aria-checked="true"]::after {
  background-color: var(--color-accent);
}

.theme-menu-item[aria-checked="true"]::after {
  visibility: visible;
}

@media screen and (max-width: 420px) {
  .navbar-item-dropdown {
    font-size: 0.75rem;
  }

  .version-dropdown-menu {
    margin: 0.25rem 0;
    padding: 0.25rem;
  }
}

@media screen and (max-width: 340px) {
  .navbar-item.navbar-item-dropdown {
    display: none;
  }
}

@media (forced-colors: active) {
  .version-dropdown-toggle {
    border: 1px solid ButtonText;
  }

  .version-dropdown-menu,
  .theme-menu-list {
    border-color: CanvasText;
  }

  /* mask icons carry meaning: they opt out and take a system color */
  .theme-menu-toggle::before,
  .theme-menu-item::before,
  .theme-menu-item[aria-checked="true"]::before {
    forced-color-adjust: none;
    background-color: ButtonText;
  }

  .theme-menu-item[aria-checked="true"]::after {
    forced-color-adjust: none;
    background-color: Highlight;
  }
}
```

- [ ] **Step 7: Check, build and run the audit**

Run: `node tools/check-css.mjs`
Expected: `5 stylesheets checked, no problems.`

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs`
Expected: every check passes, `theme` included.

Run: `node tools/ui-audit.mjs --compare build/ui-audit-reference/light --mask .header`
Expected: all files within the threshold.

- [ ] **Step 8: Look at the screenshots**

Open with the Read tool: `build/ui-audit/theme-light-manager.png`, `theme-dark-manager.png`, `theme-dark-manager-menu.png`, `theme-light-manager-phone-menu.png`, `theme-dark-manager-phone.png`, and `forced-light-manager.png`, `forced-dark-manager.png`. Expected: the button sits between the search field and the AI Assistant link with a hairline before the links; the open menu shows System, Light and Dark with icons and a check on the current mode; the dark page uses the Ink colors; nothing overlaps at phone width. Fix and re-run if not.

- [ ] **Step 9: Commit**

```bash
git add content/supplemental/css/tokens.css content/supplemental/partials/header-content.hbs content/supplemental/partials/footer-scripts.hbs content/supplemental/js/theme-menu.js content/supplemental/css/dropdown-menu.css tools/ui-audit.mjs
git commit -m "Add the color theme menu to the header"
```

---

### Task 5: Feedback form in both themes

**Files:**
- Modify: `content/supplemental/css/tokens.css` (one mask icon)
- Modify: `content/supplemental/partials/pagination.hbs:24`
- Modify: `content/supplemental/css/feedback-form.css`
- Delete: `content/supplemental/img/feedback-form__thumb-up.svg`
- Modify: `tools/ui-audit.mjs` (style expectations)

**Interfaces:**
- Consumes: `--tip-color`, `--color-surface`, `--color-text` (light and dark from Task 3); `STYLE_EXPECTATIONS` and `DARK_STYLE_EXPECTATIONS` from Task 3.
- Produces: token `--icon-thumb-up`; `span.feedback-form__icon`.

- [ ] **Step 1: Write the failing expectations**

Append to `STYLE_EXPECTATIONS`:

```js
    ['manager', '.feedback-form__icon', 'background-color', '#0d7348'],
    ['manager', '.feedback-form__input', 'background-color', '#ffffff'],
```

Append to `DARK_STYLE_EXPECTATIONS`:

```js
    ['manager', '.feedback-form__icon', 'background-color', '#5ee0a5'],
    ['manager', '.feedback-form__input', 'background-color', '#17171d'],
    ['manager', '.feedback-form__input', 'color', '#d8d8e2'],
```

- [ ] **Step 2: Run to see them fail**

Run: `node tools/ui-audit.mjs --only styles`
Expected: `FAIL styles` with `.feedback-form__icon background-color is "rgba(0, 0, 0, 0)"` (the `img` has no background) and the dark input values.

- [ ] **Step 3: Add the icon token**

In `content/supplemental/css/tokens.css`, after the `--icon-check` line, add (the glyph of the former `img/feedback-form__thumb-up.svg`):

```css
  --icon-thumb-up: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 18 18'><path fill-rule='evenodd' d='M7.70972 0.463679C7.78997 0.283115 7.96903 0.166748 8.16663 0.166748C8.96228 0.166748 9.72534 0.482819 10.2879 1.04543C10.8506 1.60804 11.1666 2.3711 11.1666 3.16675V6.00008H15.3807C15.6937 5.99698 16.0037 6.06174 16.2893 6.18993C16.5759 6.31854 16.831 6.50791 17.0371 6.74492C17.2432 6.98193 17.3953 7.26092 17.4829 7.56255C17.5705 7.86418 17.5914 8.18125 17.5443 8.49178L16.3943 15.9917C16.3943 15.9918 16.3943 15.9916 16.3943 15.9917C16.3159 16.5082 16.0534 16.9794 15.6554 17.3177C15.258 17.6555 14.7521 17.8386 14.2307 17.8334H2.33329C1.75866 17.8334 1.20756 17.6051 0.801228 17.1988C0.394899 16.7925 0.166626 16.2414 0.166626 15.6667V9.83341C0.166626 9.25878 0.394899 8.70768 0.801228 8.30135C1.20756 7.89502 1.75866 7.66675 2.33329 7.66675H4.50836L7.70972 0.463679ZM5.33329 8.27286L8.48055 1.19153C8.89468 1.25735 9.28058 1.45227 9.58084 1.75253C9.95591 2.12761 10.1666 2.63631 10.1666 3.16675V6.50008C10.1666 6.77622 10.3905 7.00008 10.6666 7.00008H15.3833L15.389 7.00005C15.5581 6.99813 15.7256 7.03301 15.8799 7.10226C16.0342 7.17151 16.1716 7.27348 16.2825 7.4011C16.3935 7.52872 16.4754 7.67895 16.5226 7.84136C16.5697 8.00371 16.581 8.17436 16.5557 8.3415C16.5556 8.34157 16.5557 8.34143 16.5557 8.3415L15.4057 15.841C15.3635 16.1192 15.2221 16.3736 15.0077 16.5558C14.7933 16.738 14.5203 16.8366 14.2389 16.8334L5.33329 16.8334V8.27286ZM4.33329 16.8334V8.66675H2.33329C2.02387 8.66675 1.72713 8.78966 1.50833 9.00846C1.28954 9.22725 1.16663 9.524 1.16663 9.83341V15.6667C1.16663 15.9762 1.28954 16.2729 1.50833 16.4917C1.72713 16.7105 2.02387 16.8334 2.33329 16.8334H4.33329Z'/></svg>");
```

- [ ] **Step 4: Replace the image with a span**

In `content/supplemental/partials/pagination.hbs`, replace

```hbs
        <img class="feedback-form__icon" src="{{uiRootPath}}/img/feedback-form__thumb-up.svg" alt="">
```

with

```hbs
        <span class="feedback-form__icon" aria-hidden="true"></span>
```

Delete the image:

```bash
git rm content/supplemental/img/feedback-form__thumb-up.svg
```

- [ ] **Step 5: Style the icon and the input**

In `content/supplemental/css/feedback-form.css`, replace

```css
.feedback-form__icon {
    margin-right: 8px;
}
```

with

```css
.feedback-form__icon {
    flex: none;
    width: 18px;
    height: 18px;
    margin-right: 8px;
    background-color: var(--tip-color);
    mask: var(--icon-thumb-up) center / contain no-repeat;
}
```

Replace

```css
.feedback-form__input {
    width: 100%;
    max-width: 394px;
    padding: 12px 8px;
    border: 1px solid var(--color-control-border);
    border-radius: var(--radius-md);
    font: inherit;
}
```

with

```css
.feedback-form__input {
    width: 100%;
    max-width: 394px;
    padding: 12px 8px;
    border: 1px solid var(--color-control-border);
    border-radius: var(--radius-md);
    background: var(--color-surface);
    color: var(--color-text);
    font: inherit;
}
```

Replace

```css
@media (forced-colors: active) {
    .feedback-form {
        border-color: CanvasText;
    }
}
```

with

```css
@media (forced-colors: active) {
    .feedback-form {
        border-color: CanvasText;
    }

    .feedback-form__icon {
        forced-color-adjust: none;
        background-color: CanvasText;
    }
}
```

- [ ] **Step 6: Check, build and run the audit**

Run: `node tools/check-css.mjs`
Expected: `5 stylesheets checked, no problems.`

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs`
Expected: every check passes.

Run: `grep -rn 'feedback-form__thumb-up' content/supplemental`
Expected: no output.

- [ ] **Step 7: Commit**

```bash
git add content/supplemental/css/tokens.css content/supplemental/partials/pagination.hbs content/supplemental/css/feedback-form.css content/supplemental/img/feedback-form__thumb-up.svg tools/ui-audit.mjs
git commit -m "Draw the feedback icon from tokens and theme the feedback input"
```

---

### Task 6: A light plate for line diagrams on transparency

**Files:**
- Modify: `content/supplemental/css/tokens.css` (one tier 3 token)
- Modify: `content/supplemental/css/site.css` (part 2, a new Images section before "Tables and other blocks")
- Modify: `content/modules/bpm/pages/bpmn/transactions.adoc:15,55`, `content/modules/bpm/pages/bpmn/bpmn-service-task.adoc:224`, `content/modules/bpm/pages/bpmn/bpmn-events.adoc:347,1517`, `content/modules/bpm/pages/dmn-1-3.adoc:98`, `content/modules/bpm/pages/process-artifacts.adoc:26`, `content/modules/flow-ui/pages/views/view-events.adoc:205`
- Modify: `tools/ui-audit.mjs` (style expectations, `forced`)

**Interfaces:**
- Consumes: `PAGES.transactions` (Task 3), the expectation lists, `forced()`.
- Produces: token `--image-plate-background`; the AsciiDoc role `light-background` for block and inline images.

- [ ] **Step 1: Write the failing expectations**

Append to `STYLE_EXPECTATIONS` and to `DARK_STYLE_EXPECTATIONS`:

```js
    ['transactions', '.imageblock.light-background img', 'background-color', '#ffffff'],
```

In `forced()`, change the page list from `['manager', 'events', 'geomap']` to `['manager', 'events', 'geomap', 'transactions']`, and after the theme icon check from Task 4 add:

```js
                if (key === 'transactions') {
                    const plate = await page.evaluate(() => {
                        const img = document.querySelector('.imageblock.light-background img');
                        return img ? getComputedStyle(img).backgroundColor : null;
                    });
                    if (plate !== 'rgb(255, 255, 255)') failures.push(`forced colors (${colorScheme}), transactions: the light-background plate is ${plate}`);
                }
```

The `widths` checks run on every page of that list: if `transactions` has no code block or admonition, replace its `null` widths with a skip by changing the failure line

```js
                    if (width === null) failures.push(`forced colors (${colorScheme}), ${key}: ${what} not found`);
```

to

```js
                    if (width === null && key !== 'transactions') failures.push(`forced colors (${colorScheme}), ${key}: ${what} not found`);
```

- [ ] **Step 2: Run to see them fail**

Run: `node tools/ui-audit.mjs --only styles,forced`
Expected: `FAIL styles` with `transactions: .imageblock.light-background img not found` in both themes; `FAIL forced` with the plate message.

- [ ] **Step 3: Add the token and the role**

In `content/supplemental/css/tokens.css`, tier 3, before the `/* mask icons; …` comment, add:

```css
  /* images: the plate of the light-background role, for diagrams drawn with dark lines on transparency;
     white in both themes, so it shows only on the dark one */
  --image-plate-background: var(--white);

```

In `content/supplemental/css/site.css` part 2, insert before the line `/* ---- Tables and other blocks ---- */`:

```css
/* ---- Images ---- */

/* role="light-background": a diagram drawn with dark lines on a transparent background keeps a light plate,
   which the dark theme needs; screenshots bring their own background and stay as they are */
.doc :is(.imageblock, .image).light-background img {
  border-radius: var(--radius-sm);
  background-color: var(--image-plate-background);
}

@media (forced-colors: active) {
  /* a dark forced colors palette would replace the plate with Canvas */
  .doc :is(.imageblock, .image).light-background img {
    forced-color-adjust: none;
  }
}

```

- [ ] **Step 4: Add the role to the eight image macros**

Make these exact replacements (one per line listed):

| File | Replace | With |
|---|---|---|
| `content/modules/bpm/pages/bpmn/transactions.adoc` | `image::transactions/transactions-1.png[,500]` | `image::transactions/transactions-1.png[,500,role=light-background]` |
| `content/modules/bpm/pages/bpmn/transactions.adoc` | `image::bpm:/transactions/transactions-2.png[,800]` | `image::bpm:/transactions/transactions-2.png[,800,role=light-background]` |
| `content/modules/bpm/pages/bpmn/bpmn-service-task.adoc` | `image::bpmn-service-task/java-delegate-instantiating.png[,600]` | `image::bpmn-service-task/java-delegate-instantiating.png[,600,role=light-background]` |
| `content/modules/bpm/pages/bpmn/bpmn-events.adoc` | `image::bpmn-events/end-events-examples.png[,500]` | `image::bpmn-events/end-events-examples.png[,500,role=light-background]` |
| `content/modules/bpm/pages/bpmn/bpmn-events.adoc` | `image::bpmn-events/workaround-escalation-events.png[,600]` | `image::bpmn-events/workaround-escalation-events.png[,600,role=light-background]` |
| `content/modules/bpm/pages/dmn-1-3.adoc` | `image::dmn/business-rule-full.png[,900]` | `image::dmn/business-rule-full.png[,900,role=light-background]` |
| `content/modules/bpm/pages/process-artifacts.adoc` | `image::modeling-and-execution/process-artifacts.png[,900]` | `image::modeling-and-execution/process-artifacts.png[,900,role=light-background]` |
| `content/modules/flow-ui/pages/views/view-events.adoc` | `image::views/open-detail-view.svg[align="center"]` | `image::views/open-detail-view.svg[align="center",role=light-background]` |

Run: `grep -rn 'role=light-background' content/modules | wc -l`
Expected: `8`.

- [ ] **Step 5: Check, build and run the audit**

Run: `node tools/check-css.mjs`
Expected: `5 stylesheets checked, no problems.`

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs`
Expected: every check passes.

Run: `grep -c 'class="imageblock[^"]*light-background' build/site/jmix/bpm/bpmn/transactions.html build/site/jmix/flow-ui/views/view-events.html`
Expected: `2` and `1` (Asciidoctor puts the role after other classes, as in `imageblock text-center light-background`).

Open `build/ui-audit/forced-dark-transactions.png` with the Read tool. Expected: the diagram shows on a white plate.

- [ ] **Step 6: Commit**

```bash
git add content/supplemental/css/tokens.css content/supplemental/css/site.css content/modules/bpm/pages/bpmn/transactions.adoc content/modules/bpm/pages/bpmn/bpmn-service-task.adoc content/modules/bpm/pages/bpmn/bpmn-events.adoc content/modules/bpm/pages/dmn-1-3.adoc content/modules/bpm/pages/process-artifacts.adoc content/modules/flow-ui/pages/views/view-events.adoc tools/ui-audit.mjs
git commit -m "Put a light plate behind line diagrams drawn on transparency"
```

---

### Task 7: Documentation and the final check

**Files:**
- Modify: `AGENTS.md` (section "UI: styles, tokens and the pinned bundle", section "Images: size budget is enforced")
- Modify: `CONTRIBUTING.md` (sections "Image budget" and "Changing the UI")

**Interfaces:**
- Consumes: everything above.

- [ ] **Step 1: Update AGENTS.md**

In `AGENTS.md`, replace

```markdown
- `tokens.css` holds the design tokens in three tiers: palette, semantic roles, components. A dark theme changes values here, not in the components. The only custom properties declared elsewhere are the `--adm-*` aliases in the Admonitions section of `site.css`.
```

with

```markdown
- `tokens.css` holds the design tokens in three tiers: palette, semantic roles, components. The dark theme is the `:root[data-theme="dark"]` block at the end of the file: it redefines tier 2 and the tier 3 values that do not point to tier 2, and components never change for it. The only custom properties declared elsewhere are the `--adm-*` aliases in the Admonitions section of `site.css`.
```

Replace

```markdown
- `search.css`, `dropdown-menu.css` and `feedback-form.css` style the search, the version menu and the feedback form.
- `content/supplemental/js/` holds the scripts that add to the bundle's `site.js`: `a11y.js` (ARIA state, copy button names, the skip link and keyboard access to search results), `code-toolbox.js` (starts a code block below the copy toolbox when the toolbox would cover its first line), `dropdown-menu.js` (the version menu) and `feedback-form.js`.
```

with

```markdown
- `search.css`, `dropdown-menu.css` and `feedback-form.css` style the search, the two header menus (version and color theme) and the feedback form.
- `content/supplemental/js/` holds the scripts that add to the bundle's `site.js`: `a11y.js` (ARIA state, copy button names, the skip link and keyboard access to search results), `code-toolbox.js` (starts a code block below the copy toolbox when the toolbox would cover its first line), `dropdown-menu.js` (the version menu), `theme-menu.js` (the color theme menu) and `feedback-form.js`.
- The color theme is the `data-theme` attribute (`light` or `dark`) on `<html>`, and `data-theme-preference` holds the reader's choice (`system`, `light` or `dark`, saved in `localStorage` under `jmix-docs-theme`). An inline script at the start of `partials/head-styles.hbs` sets both, and a `color-scheme` meta element, before the stylesheets load, so the page never shows the wrong theme; keep it before the stylesheet links. `theme-menu.js` changes them later. The DocsBot chat widget that Google Tag Manager adds draws itself for a light page, so `site.css` keeps its host, `#docsbotai-root`, at `color-scheme: light`.
```

Replace

```markdown
Outside `tokens.css`, write colors as `var(--…)`. Keywords such as `transparent`, `currentColor` and `inherit`, `color-mix()` over keywords and tokens, and the CSS system colors in forced colors blocks are the only exceptions. `node tools/check-css.mjs` rejects other color literals and reports custom properties that are used but never declared. CI runs it, with its tests (`node --test tools/check-css.test.mjs`), on pull requests that change `content/supplemental/` or the check.
```

with

```markdown
Outside `tokens.css`, write colors as `var(--…)`. Keywords such as `transparent`, `currentColor` and `inherit`, `color-mix()` over keywords and tokens, and the CSS system colors in forced colors blocks are the only exceptions. `node tools/check-css.mjs` rejects other color literals, reports custom properties that are used but never declared, and makes sure the dark block sets every tier 2 token. CI runs it, with its tests (`node --test tools/check-css.test.mjs`), on pull requests that change `content/supplemental/` or the check.
```

Replace

```markdown
After a UI change, build the site and run `node tools/ui-audit.mjs`. It checks keyboard focus, the skip link, accessible names, keyboard access to search results, contrast, the expected styles, fonts and forced colors mode.
```

with

```markdown
After a UI change, build the site and run `node tools/ui-audit.mjs`. It checks keyboard focus, contrast and the expected styles in both themes, the skip link, accessible names, keyboard access to search results, fonts, forced colors mode, and the color theme: that it is set before the first stylesheet, and how the theme menu behaves.
```

In the section "Images: size budget is enforced", after the paragraph that ends with `` `width="413"` for an 826px-wide file), so they stay sharp on high-DPI displays. `` … (the 2x paragraph), add a new paragraph:

```markdown
A diagram drawn with dark lines on a transparent background disappears in the dark theme. Give its image macro `role=light-background`, as in `image::transactions/transactions-1.png[,500,role=light-background]`, which puts a white plate behind it. Screenshots bring their own background and need nothing.
```

- [ ] **Step 2: Update CONTRIBUTING.md**

In `CONTRIBUTING.md`, at the end of the section "Image budget" (after `Typical reduction for UI screenshots: 70-80% size, no visible quality loss.`), add:

```markdown

### Images in the dark theme

The site has a dark theme. Screenshots keep their own light background there, but a diagram drawn with dark lines on a transparent background disappears. Add `role=light-background` to the image macro of such a diagram, for example `image::transactions/transactions-1.png[,500,role=light-background]`, or export it with a white background.
```

In the section "Changing the UI", replace

```markdown
UI styles live in `content/supplemental/css/`. Use the custom properties from `tokens.css` for colors; `node tools/check-css.mjs` rejects color literals in the other files.
```

with

```markdown
UI styles live in `content/supplemental/css/`. Use the custom properties from `tokens.css` for colors; `node tools/check-css.mjs` rejects color literals in the other files. A new semantic color token needs a value in the dark block of `tokens.css` too; the check reports it when it is missing.
```

- [ ] **Step 3: Check the prose**

Run the `no-ai-slop` skill in detect mode on the lines changed in `AGENTS.md` and `CONTRIBUTING.md`, with the adjustments in the `AGENTS.md` section "Writing prose" (check only the changed lines, keep markup, plain words for non-native readers). Fix what it finds.

- [ ] **Step 4: Run every check**

Run: `node tools/check-css.mjs && node --test tools/check-css.test.mjs`
Expected: no problems; 7 tests pass.

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs`
Expected: every check passes; exit code 0.

Run: `node tools/ui-audit.mjs --compare build/ui-audit-reference/light --mask .header`
Expected: all files within the threshold.

- [ ] **Step 5: Review the screenshots**

Open with the Read tool every `theme-*.png` and `forced-*.png` in `build/ui-audit`. Expected: the light pages look as before apart from the menu; the dark pages use the Ink palette, IntelliJ-like code colors and the dark admonitions; forced colors screenshots draw borders and icons in system colors.

- [ ] **Step 6: Commit**

```bash
git add AGENTS.md CONTRIBUTING.md
git commit -m "Document the dark theme and the theme menu"
```

- [ ] **Step 7: Hand over**

Draft the new section of the description of pull request jmix-framework/jmix-docs#203 (what the dark theme and the menu do, the decisions in the spec's table, how it was checked, and where the screenshots are: `build/ui-audit/theme-*.png`), run `no-ai-slop` on it, and show it to Gleb. Do not edit the pull request and do not push until he says so. Ask him whether to remove the spec and this plan from the branch now, as was done for the restyle (`git rm` of both files, commit "Remove the dark theme spec and plan").
