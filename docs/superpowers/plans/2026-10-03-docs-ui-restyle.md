# Docs UI Restyle Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Restyle the Jmix docs UI on our own token-based CSS over a pinned Antora default UI bundle, with visible focus, keyboard access, forced colors support and the approved look (mockup C with the headings of mockup B).

**Architecture:** The default UI bundle is committed as a zip and remains the source of layouts, untouched partials and `site.js`. Its stylesheet is replaced, through Antora supplemental files, by our `site.css` (part 1: upstream `src/css` of the bundle's revision; part 2: Jmix styles) and `tokens.css` (custom properties in three tiers). Partial overrides and two small scripts add the accessibility features. Two Node tools are the tests: `tools/check-css.mjs` (static) and `tools/ui-audit.mjs` (browser).

**Tech Stack:** Antora 3.0.3, Handlebars partials, plain CSS (custom properties, `color-mix()`, `:focus-visible`, `@media (forced-colors: active)`), vanilla JS, Node 24 with `node:test`, Playwright resolved from the npx cache as `tools/screenshot-2x.mjs` does.

**Spec:** `docs/superpowers/specs/2026-10-03-docs-ui-restyle-design.md`

## Global Constraints

- Work on branch `feature/ui-restyle`. Commit after every task with a one-line imperative subject, no body and no trailers. Do not push.
- English for all code, comments and documentation. Markdown paragraphs are not hard-wrapped.
- Outside `content/supplemental/css/tokens.css`, a color is written only as `var(--…)`. Allowed keywords: `transparent`, `currentColor`, `inherit`, `initial`, `unset`, `revert`, `none`, and CSS system colors (`CanvasText`, `Canvas`, `LinkText`, `ButtonText`, `ButtonBorder`, `GrayText`, `Highlight`, `HighlightText`, `Field`, `FieldText`). `node tools/check-css.mjs` enforces this from Task 6 on.
- No `!important` in our CSS. No `outline: none` either, except `#main-content:focus`: the skip link target is a region, not a control (spec, Accessibility).
- Files derived from the default UI carry the MPL-2.0 header: `/* This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/. */` (as a block comment in CSS, `{{! … }}` lines in Handlebars, as the existing partials do).
- Upstream revision: `antora-ui-default` `0e38223adfd81eb74d4b1779e158f8ad05ff8923` (2026-07-16). Its `src/partials`, `src/layouts` and `src/helpers` are byte-identical to the bundle in `~/Library/Caches/antora/ui/88c6429e9c29a675bbb4686e403e1870e69cbccd.zip`.
- Fonts: Fontsource 5.3.0, OFL-1.1.
- Root font size: 18 px from 1024 px wide, 17 px below (upstream). `1rem` in the values below is 18 px on desktop.
- Build: `npx antora antora-playbook.yml` writes `build/site` (about three minutes). Every task that changes the UI ends with a build and `node tools/ui-audit.mjs`.
- After writing prose for people (`ui/README.md`, `AGENTS.md`, `CONTRIBUTING.md`, the PR text), run the `no-ai-slop` skill in detect mode on the changed lines and fix the findings, as `AGENTS.md` requires.
- Shell variables used by the steps: `SP=/private/tmp/claude-502/-Users-gorelov-Developer-Haulmont-Platform-jmix-framework-jmix-v3-docs/311779f5-f25b-4241-bd6d-cf27c5db3051/scratchpad` and `U=$SP/upstream/src-0e38223adfd81eb74d4b1779e158f8ad05ff8923`. Every code block that needs them defines them on its first line.
- Keep the class names `site.js` and `search-ui.js` bind to: `.nav-item-toggle`, `.nav-panel-explore .context`, `.navbar-burger`, `.version-dropdown-toggle`, `.version-dropdown-menu`, `#search-input`, `.copy-button`.

## Working material outside the repository

`SP=/private/tmp/claude-502/-Users-gorelov-Developer-Haulmont-Platform-jmix-framework-jmix-v3-docs/311779f5-f25b-4241-bd6d-cf27c5db3051/scratchpad`

| What | Path | Fallback download |
|---|---|---|
| Upstream sources | `$SP/upstream/src-0e38223adfd81eb74d4b1779e158f8ad05ff8923/` | `curl -sSfL -o ui-src.tar.gz https://gitlab.com/antora/antora-ui-default/-/archive/0e38223adfd81eb74d4b1779e158f8ad05ff8923/antora-ui-default-0e38223adfd81eb74d4b1779e158f8ad05ff8923.tar.gz && tar -xzf ui-src.tar.gz` |
| Fonts and licenses | `$SP/fonts/` | Task 4, step 1 lists the URLs |
| Approved mockup CSS (reference only) | `$SP/proto/base.css`, `b.css`, `c.css` (`c.css` imports `b.css`), `proto.js` | none; the spec carries the values |

Set `U=$SP/upstream/src-0e38223adfd81eb74d4b1779e158f8ad05ff8923` in shells that need the upstream sources.

## File structure

| File | Responsibility |
|---|---|
| `ui/ui-bundle.zip` | Pinned default UI bundle |
| `ui/README.md` | Where the bundle comes from and how to update it |
| `content/supplemental/css/tokens.css` | Every custom property: palette, semantic, component tiers |
| `content/supplemental/css/site.css` | Replaces the bundle stylesheet. Part 1: upstream `src/css`. Part 2: Jmix sections (typefaces, header, navigation, toolbar, TOC, article, code, admonitions, tables and blocks, end of page, banner and content buttons, accessibility), each with its forced colors rules |
| `content/supplemental/css/search.css` | Search field and Lunr results |
| `content/supplemental/css/dropdown-menu.css` | Header version menu |
| `content/supplemental/css/feedback-form.css` | "Was this page helpful?" form |
| `content/supplemental/font/` | Roboto and JetBrains Mono woff2 files and their licenses |
| `content/supplemental/img/icons/*.svg` | Mask icons: chevron, home, edit, search, copy, note, tip, warning, important, caution, addon |
| `content/supplemental/partials/head-styles.hbs` | The five stylesheet links |
| `content/supplemental/partials/head-meta.hbs` | Favicons |
| `content/supplemental/partials/header-content.hbs` | Skip link, header, inline icons, search field |
| `content/supplemental/partials/main.hbs` | `<main>` with the skip link target (new override) |
| `content/supplemental/partials/nav-tree.hbs` | Nav tree with named toggles (new override) |
| `content/supplemental/partials/nav-explore.hbs` | Explore panel with a button toggle |
| `content/supplemental/partials/footer-scripts.hbs` | Scripts at the end of the body |
| `content/supplemental/js/dropdown-menu.js` | Version menu behavior |
| `content/supplemental/js/a11y.js` | `aria-expanded` sync, copy button names, skip link focus, search keyboard access (new) |
| `tools/lib/playwright.mjs` | Shared Playwright and Chromium resolution (extracted from `screenshot-2x.mjs`) |
| `tools/check-css.mjs`, `tools/check-css.test.mjs` | Static check of our stylesheets and its tests |
| `tools/ui-audit.mjs` | Browser audit of the built site |
| `.github/workflows/ui-check.yml` | Runs the static check on pull requests |

Removed by the end: `content/supplemental/css/overrides.css`, `content/supplemental/img/git-icon.svg`, `content/supplemental/img/jmix-icon.svg`, `content/supplemental/img/jmix-ai-assistant-icon.svg`.

---

### Task 1: Pin the UI bundle

**Files:**
- Create: `ui/ui-bundle.zip`, `ui/README.md`
- Modify: `antora-playbook.yml` (the `ui:` block), `antora-playbook.ci.yml` (the `ui:` block), `.gitattributes`

**Interfaces:**
- Produces: `ui.bundle.url: ./ui/ui-bundle.zip` in both playbooks; every later task builds against it.

- [ ] **Step 1: Check the cached bundle is the 2026-07-16 build**

Run: `unzip -l ~/Library/Caches/antora/ui/88c6429e9c29a675bbb4686e403e1870e69cbccd.zip | grep -E 'css/site.css|js/site.js'`
Expected: both lines dated `07-16-2026`. If the file is missing or dated differently, stop and report: the vendored CSS in Task 3 must come from the same revision as the bundle.

- [ ] **Step 2: Copy the bundle into the repository**

```bash
mkdir -p ui
cp ~/Library/Caches/antora/ui/88c6429e9c29a675bbb4686e403e1870e69cbccd.zip ui/ui-bundle.zip
```

- [ ] **Step 3: Point both playbooks at it**

In `antora-playbook.yml` and in `antora-playbook.ci.yml`, replace

```yaml
ui:
  bundle:
    url: https://gitlab.com/antora/antora-ui-default/-/jobs/artifacts/master/raw/build/ui-bundle.zip?job=bundle-stable
    snapshot: true
  supplemental_files: ./content/supplemental
```

with

```yaml
ui:
  bundle:
    url: ./ui/ui-bundle.zip
  supplemental_files: ./content/supplemental
```

- [ ] **Step 4: Mark the new binary types**

Append to `.gitattributes`:

```
*.zip binary
*.woff2 binary
```

- [ ] **Step 5: Write `ui/README.md`**

```markdown
# Docs UI bundle

`ui-bundle.zip` is the Antora default UI bundle that both playbooks use. It was built on 2026-07-16 from revision [`0e38223a`](https://gitlab.com/antora/antora-ui-default/-/commit/0e38223adfd81eb74d4b1779e158f8ad05ff8923) of `antora-ui-default` (job `bundle-stable`). The bundle is pinned, so upstream changes reach the site only when someone takes them on purpose.

The bundle provides the layouts, the partials that `content/supplemental/partials` does not override, `js/site.js`, the vendor scripts and the images. The stylesheet is ours: `content/supplemental/css/site.css` replaces the bundle's `css/site.css`. Part 1 of that file is the upstream `src/css` of the same revision, part 2 holds the Jmix styles, and the custom properties live in `content/supplemental/css/tokens.css`.

## Updating the bundle

1. Download the new bundle and the upstream sources of the same revision: `https://gitlab.com/antora/antora-ui-default/-/archive/<sha>/antora-ui-default-<sha>.tar.gz`.
2. Diff `src/css`, `src/partials` and `src/js` between the old and the new revision.
3. Port the `src/css` changes into part 1 of `site.css` and new custom properties into `tokens.css`. Compare the partials we override with their upstream versions.
4. Replace `ui-bundle.zip`, update the revision above, build the site and run `node tools/check-css.mjs` and `node tools/ui-audit.mjs`.
```

Then run the `no-ai-slop` skill in detect mode on this file and fix the findings.

- [ ] **Step 6: Build and confirm the site uses the pinned bundle**

Run: `npx antora antora-playbook.yml && cmp build/site/_/css/site.css <(unzip -p ui/ui-bundle.zip css/site.css) && echo SAME`
Expected: the build finishes without errors and prints `SAME`.

- [ ] **Step 7: Commit**

```bash
git add ui antora-playbook.yml antora-playbook.ci.yml .gitattributes
git commit -m "Pin the Antora UI bundle"
```

---

### Task 2: UI tooling: the static check and the browser audit

These tools are the tests for every later task. Both fail on the current site; that is expected and recorded.

**Files:**
- Create: `tools/lib/playwright.mjs`, `tools/check-css.mjs`, `tools/check-css.test.mjs`, `tools/ui-audit.mjs`
- Modify: `tools/screenshot-2x.mjs` (use the shared library)

**Interfaces:**
- Produces: `node tools/check-css.mjs [dir]` (exit 1 on findings); `checkStylesheets(sheets: {name, text}[]): {name, line, message}[]` exported from `tools/check-css.mjs`; `node tools/ui-audit.mjs [--site build/site] [--out build/ui-audit] [--only a,b] [--snapshot dir] [--compare dir]`; the arrays `STYLE_EXPECTATIONS` and `TEXT_CONTRAST` in `tools/ui-audit.mjs`, which Tasks 7–11 extend; `launchChromium()` exported from `tools/lib/playwright.mjs`.

- [ ] **Step 1: Extract the Playwright helpers**

Create `tools/lib/playwright.mjs`:

```js
/*
 * Playwright for the docs tooling. Playwright is not a dependency of this repository: it is
 * resolved from wherever it is already installed (an npx cache is normal), and the browser
 * Playwright has already downloaded is used.
 */

import { createRequire } from 'node:module';
import { execSync } from 'node:child_process';

export function loadPlaywright() {
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

export function findInstalledChromium() {
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

export async function launchChromium() {
    const { chromium } = loadPlaywright();
    const executablePath = findInstalledChromium();
    return chromium.launch(executablePath ? { executablePath } : {});
}
```

In `tools/screenshot-2x.mjs`: delete the local `loadPlaywright` and `findInstalledChromium` functions and the `createRequire` and `execSync` imports, add `import { loadPlaywright, findInstalledChromium } from './lib/playwright.mjs';` next to the other imports, and leave the rest unchanged. Move the paragraph "Playwright is not a dependency of this repository…" out of its header comment only if it now duplicates the library comment word for word; otherwise keep it.

Run: `node tools/screenshot-2x.mjs` (no arguments)
Expected: `missing --url` and exit code 1, which shows the script still loads.

- [ ] **Step 2: Write the failing tests for the static check**

Create `tools/check-css.test.mjs`:

```js
import { test } from 'node:test';
import assert from 'node:assert/strict';
import { checkStylesheets } from './check-css.mjs';

const TOKENS = { name: 'tokens.css', text: ':root { --a: #fff; --b: red; }' };
const messages = (text) => checkStylesheets([TOKENS, { name: 'site.css', text }]).map((f) => f.message);

test('reports hex, functional and named color literals outside tokens.css', () => {
    assert.equal(messages('a { color: #333; }').length, 1);
    assert.equal(messages('a { background: rgb(0 0 0 / 0.5); }').length, 1);
    assert.equal(messages('a { border: 1px solid white; }').length, 1);
    assert.equal(messages('a { --local: #123; }').length, 1);
});

test('accepts tokens, keywords and system colors', () => {
    assert.deepEqual(messages('a { color: var(--a); background: transparent; fill: currentColor; }'), []);
    assert.deepEqual(messages('@media (forced-colors: active) { a { border-color: CanvasText; color: LinkText; } }'), []);
    assert.deepEqual(messages('a { text-decoration-color: color-mix(in oklab, currentColor 35%, transparent); }'), []);
});

test('ignores selectors, comments, strings, urls and property names', () => {
    assert.deepEqual(messages('#search-input:hover { white-space: nowrap; }'), []);
    assert.deepEqual(messages('a { /* color: red; */ content: "white"; mask: url(../img/icons/red.svg); }'), []);
    assert.deepEqual(messages('@media (prefers-reduced-motion: reduce) { html { scroll-behavior: auto; } }'), []);
});

test('allows literals in tokens.css', () => {
    assert.deepEqual(checkStylesheets([{ name: 'tokens.css', text: ':root { --x: #123456; }' }]), []);
});

test('reports custom properties that are used but never declared', () => {
    assert.deepEqual(messages('a { color: var(--missing); }'), ['--missing is used but never declared']);
    assert.deepEqual(messages('a { color: var(--missing, var(--a)); }'), []);
});
```

Run: `node --test tools/check-css.test.mjs`
Expected: FAIL, the module `./check-css.mjs` cannot be found.

- [ ] **Step 3: Implement the static check**

Create `tools/check-css.mjs`:

```js
/*
 * Checks the docs UI stylesheets in content/supplemental/css.
 *
 *  1. Colors come from design tokens: outside tokens.css a color is written only as var(--…).
 *     Keywords such as transparent and currentColor, and the CSS system colors used in forced
 *     colors mode (CanvasText, Highlight and so on), are allowed because they are not literals.
 *  2. Every custom property read with var() without a fallback is declared in one of the files.
 *
 * Usage: node tools/check-css.mjs [dir]    (default: content/supplemental/css; exit code 1 on findings)
 */

import { readdirSync, readFileSync } from 'node:fs';
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
        if (name === TOKENS_FILE) continue;
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

if (import.meta.url === pathToFileURL(process.argv[1]).href) {
    main();
}
```

- [ ] **Step 4: Run the tests**

Run: `node --test tools/check-css.test.mjs`
Expected: 5 tests pass.

- [ ] **Step 5: Run the check on the current stylesheets**

Run: `node tools/check-css.mjs; echo "exit $?"`
Expected: color literal findings in `overrides.css`, `search.css`, `feedback-form.css` and `dropdown-menu.css`, and `exit 1`. Write down the number of problems in your task report; Task 5 brings it to zero.

- [ ] **Step 6: Write the browser audit**

Create `tools/ui-audit.mjs`:

```js
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
 * Options: --site <dir> (default build/site), --out <dir> (default build/ui-audit).
 * Forced colors screenshots are written to --out for review. Exit code 1 when a check fails.
 * Requests to hosts other than the local server and the CDNs the pages need are blocked, so
 * the audit never sends analytics.
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
};

const ALLOWED_HOSTS = ['cdnjs.cloudflare.com', 'cdn.jsdelivr.net', 'kroki.io', 'img.icons8.com'];

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
];

const SNAPSHOT_PAGES = ['manager', 'events', 'geomap', 'features', 'intro'];

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
    return { parse, background, ratio, textContrast, describe };
})();
`;

function parseArgs(argv) {
    const opts = { site: 'build/site', out: 'build/ui-audit', only: null, snapshot: null, compare: null };
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
        for (let i = 0; i < 80; i++) {
            await page.keyboard.press('Tab');
            const r = await page.evaluate(() => {
                const el = document.activeElement;
                if (!el || el === document.body) return null;
                const a = window.__audit;
                const cs = getComputedStyle(el);
                const visible = cs.outlineStyle !== 'none' && (parseFloat(cs.outlineWidth) || 0) >= 2;
                const color = a.parse(cs.outlineColor);
                const ring = visible && color ? a.ratio(color, a.background(el.parentElement || el)) : 0;
                return { what: a.describe(el), visible, ring };
            });
            if (!r) continue;
            if (!r.visible) failures.push(`no visible focus outline: ${r.what}`);
            else if (r.ring < 3) failures.push(`focus outline contrast ${r.ring.toFixed(2)}:1 is below 3:1: ${r.what}`);
        }
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
            return out;
        });
        await page.click('.nav-item:not(.is-active) > .nav-item-toggle');
        await page.waitForTimeout(100);
        const synced = await page.evaluate(() => [...document.querySelectorAll('.nav-item-toggle')]
            .every((b) => b.getAttribute('aria-expanded') === String(b.parentElement.classList.contains('is-active'))));
        if (!synced) failures.push('aria-expanded does not follow a nav toggle click');
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
                    if (width === 0) failures.push(`forced colors (${colorScheme}), ${key}: ${what} has no border`);
                }
                await page.close();
            }
            await context.close();
        }
        return failures;
    }

    async snapshot(dir) {
        await mkdir(dir, { recursive: true });
        const context = await this.context();
        for (const key of SNAPSHOT_PAGES) {
            const page = await this.open(context, key);
            for (const position of [0, 50]) {
                await page.evaluate((p) => window.scrollTo(0, (document.documentElement.scrollHeight - innerHeight) * p / 100), position);
                await page.waitForTimeout(300);
                await page.screenshot({ path: join(dir, `${key}-${position}.png`) });
            }
            await page.close();
        }
        await context.close();
    }

    async compare(dir, out) {
        const fresh = join(out, 'compare');
        await this.snapshot(fresh);
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

const CHECKS = ['focus', 'skip', 'names', 'search', 'contrast', 'styles', 'fonts', 'forced'];

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
            await audit.snapshot(resolve(opts.snapshot));
            console.log(`snapshot saved to ${opts.snapshot}`);
        } else if (opts.compare) {
            const failures = await audit.compare(resolve(opts.compare), out);
            failures.forEach((f) => console.log(`  FAIL ${f}`));
            failed = failures.length;
        } else {
            for (const name of opts.only || CHECKS) {
                let failures;
                try {
                    failures = name === 'forced' ? await audit.forced(out) : await audit[name]();
                } catch (e) {
                    failures = [`the check threw: ${e.message.split('\n')[0]}`];
                }
                console.log(`${failures.length ? 'FAIL' : 'PASS'} ${name}`);
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
```

- [ ] **Step 7: Run the audit on the current build**

Run: `node tools/ui-audit.mjs; echo "exit $?"`
Expected: `FAIL focus` (version toggle, search input, header buttons, nav toggles, TOC links), `FAIL skip`, `FAIL names`, `FAIL search` (Tab clears the query), `FAIL contrast` (tip, warning and note labels, the edit link), `PASS styles` (no expectations yet), `FAIL fonts` (Roboto Mono), `PASS forced` or failures for elements without borders, and `exit 1`. This is the failing baseline. A check that throws is reported as `the check threw: …`; on the baseline none should, so fix the script if one does.

- [ ] **Step 8: Commit**

```bash
git add tools
git commit -m "Add CSS check and UI audit tools"
```

---

### Task 3: Own the stylesheet without changing the look

**Files:**
- Create: `content/supplemental/css/tokens.css`, `content/supplemental/css/site.css`
- Modify: `content/supplemental/partials/head-styles.hbs:4` (the `site.css` link)

**Interfaces:**
- Consumes: the pinned bundle (Task 1), `--snapshot`/`--compare` (Task 2).
- Produces: `site.css` with the markers `/* ==== Part 1: Antora default UI ==== */`, `/* ---- upstream: <file>.css ---- */` per upstream file and `/* ==== Part 2: Jmix ==== */` at the end; `tokens.css` containing the upstream `:root` block. Later tasks edit inside these markers.

- [ ] **Step 1: Save reference screenshots of the bundle stylesheet**

Run: `node tools/ui-audit.mjs --snapshot build/ui-audit/bundle`
Expected: `snapshot saved to build/ui-audit/bundle` and 10 PNG files in that directory.

- [ ] **Step 2: Create `tokens.css` from the upstream `vars.css`**

```bash
SP=/private/tmp/claude-502/-Users-gorelov-Developer-Haulmont-Platform-jmix-framework-jmix-v3-docs/311779f5-f25b-4241-bd6d-cf27c5db3051/scratchpad
U=$SP/upstream/src-0e38223adfd81eb74d4b1779e158f8ad05ff8923
{
  printf '/*\n * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL\n * was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.\n *\n * Custom properties of the docs UI. Started from src/css/vars.css of antora-ui-default at revision 0e38223a.\n */\n\n'
  cat "$U/src/css/vars.css"
} > content/supplemental/css/tokens.css
```

- [ ] **Step 3: Create `site.css` from the upstream sources**

```bash
SP=/private/tmp/claude-502/-Users-gorelov-Developer-Haulmont-Platform-jmix-framework-jmix-v3-docs/311779f5-f25b-4241-bd6d-cf27c5db3051/scratchpad
U=$SP/upstream/src-0e38223adfd81eb74d4b1779e158f8ad05ff8923
OUT=content/supplemental/css/site.css
{
  printf '/*\n * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL\n * was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.\n *\n * Stylesheet of the Jmix docs UI. It replaces css/site.css of the Antora default UI bundle (ui/ui-bundle.zip).\n *\n * Part 1 is src/css of antora-ui-default at revision 0e38223a, the revision of the bundle, concatenated in the\n * order of its site.css; vars.css lives in tokens.css. Part 1 changes only where a line says so: font files,\n * color literals replaced with tokens, removed focus outline resets. Part 2 holds the Jmix styles and overrides\n * part 1 where the design differs. Colors come only from tokens.css (node tools/check-css.mjs).\n */\n\n/* ==== Part 1: Antora default UI ==== */\n'
  for f in typeface-roboto typeface-roboto-mono base body nav main toolbar breadcrumbs page-versions toc doc pagination header footer highlight print; do
    printf '\n/* ---- upstream: %s.css ---- */\n\n' "$f"
    cat "$U/src/css/$f.css"
  done
  printf '\n/* ==== Part 2: Jmix ==== */\n'
} > "$OUT"
sed -i '' -e 's#url(~@fontsource/roboto/files/#url(../font/#g' -e 's#url(~@fontsource/roboto-mono/files/#url(../font/#g' "$OUT"
grep -c '~@fontsource' "$OUT"
```

Expected: the last command prints `0`. The font files these URLs name exist in the bundle's `font/` directory, which Antora publishes as `_/font/`.

- [ ] **Step 4: Load `tokens.css` before `site.css`**

In `content/supplemental/partials/head-styles.hbs`, replace

```hbs
<link rel="stylesheet" href="{{{uiRootPath}}}/css/site.css">
```

with

```hbs
<link rel="stylesheet" href="{{{uiRootPath}}}/css/tokens.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/site.css">
```

Leave the other links and the inline `<style>` for Task 6.

- [ ] **Step 5: Build and compare with the reference**

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs --compare build/ui-audit/bundle`
Expected: every file at most 0.2% different and exit code 0. Native `calc()` keeps more precision than the bundle's precomputed values, so a few antialiasing pixels may differ; anything above 0.2% means a rule is missing or reordered: open the image in `build/ui-audit/compare/`, find the difference and fix `site.css`.

- [ ] **Step 6: Commit**

```bash
git add content/supplemental/css/site.css content/supplemental/css/tokens.css content/supplemental/partials/head-styles.hbs
git commit -m "Vendor the default UI stylesheet with its custom properties"
```

---

### Task 4: Self-hosted fonts

**Files:**
- Create: `content/supplemental/font/` with the 12 woff2 files below, `LICENSE-roboto.txt`, `LICENSE-jetbrains-mono.txt`
- Modify: `content/supplemental/css/site.css` (part 1 typeface sections, new part 2 section "Typefaces")
- Modify: `content/supplemental/css/tokens.css` (`--body-font-family`, `--monospace-font-family`)

**Interfaces:**
- Produces: font families `"Roboto"` (normal 100–900 variable, italic 400) and `"JetBrains Mono"` (400 normal and italic); later tasks use only these weights and styles.

- [ ] **Step 1: Copy the font files**

```bash
SP=/private/tmp/claude-502/-Users-gorelov-Developer-Haulmont-Platform-jmix-framework-jmix-v3-docs/311779f5-f25b-4241-bd6d-cf27c5db3051/scratchpad
mkdir -p content/supplemental/font
for s in latin latin-ext cyrillic; do
  cp "$SP/fonts/roboto-$s-wght-normal.woff2" "$SP/fonts/roboto-$s-400-italic.woff2" \
     "$SP/fonts/jetbrains-mono-$s-400-normal.woff2" "$SP/fonts/jetbrains-mono-$s-400-italic.woff2" content/supplemental/font/
done
cmp -s "$SP/fonts/LICENSE-roboto-variable.txt" "$SP/fonts/LICENSE-roboto.txt" && echo "Roboto licenses identical"
cp "$SP/fonts/LICENSE-roboto-variable.txt" content/supplemental/font/LICENSE-roboto.txt
cp "$SP/fonts/LICENSE-jetbrains-mono.txt" content/supplemental/font/LICENSE-jetbrains-mono.txt
ls content/supplemental/font | wc -l
```

Expected: `14`. If the two Roboto license files differ, also copy `LICENSE-roboto.txt` from the static package as `LICENSE-roboto-static.txt`. If `$SP/fonts` is missing, download each file from `https://cdn.jsdelivr.net/npm/@fontsource-variable/roboto@5.3.0/files/roboto-<subset>-wght-normal.woff2`, `https://cdn.jsdelivr.net/npm/@fontsource/roboto@5.3.0/files/roboto-<subset>-400-italic.woff2` and `https://cdn.jsdelivr.net/npm/@fontsource/jetbrains-mono@5.3.0/files/jetbrains-mono-<subset>-400-<normal|italic>.woff2`, and the licenses from `https://cdn.jsdelivr.net/npm/@fontsource-variable/roboto@5.3.0/LICENSE` and `https://cdn.jsdelivr.net/npm/@fontsource/jetbrains-mono@5.3.0/LICENSE`, after confirming the download with the user.

- [ ] **Step 2: Retire the upstream typefaces**

In part 1 of `site.css`, replace the whole body of the sections `/* ---- upstream: typeface-roboto.css ---- */` and `/* ---- upstream: typeface-roboto-mono.css ---- */` (everything up to the next `/* ---- upstream:` marker) with the single line `/* replaced by part 2, "Typefaces" */`, keeping the markers. Then add to the header comment of `site.css`, after "removed focus outline resets", the words ", the bundle typefaces".

- [ ] **Step 3: Declare the new typefaces**

Append to part 2 of `site.css`:

```css
/* ---- Typefaces ---- */
/* Fontsource 5.3.0, OFL-1.1 (font/LICENSE-*.txt). Roboto is one variable file per subset for all weights. */

@font-face {
  font-family: "Roboto";
  font-style: normal;
  font-weight: 100 900;
  font-display: swap;
  src: url(../font/roboto-latin-wght-normal.woff2) format("woff2");
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+0304, U+0308, U+0329, U+2000-206F, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}

@font-face {
  font-family: "Roboto";
  font-style: normal;
  font-weight: 100 900;
  font-display: swap;
  src: url(../font/roboto-latin-ext-wght-normal.woff2) format("woff2");
  unicode-range: U+0100-02BA, U+02BD-02C5, U+02C7-02CC, U+02CE-02D7, U+02DD-02FF, U+0304, U+0308, U+0329, U+1D00-1DBF, U+1E00-1E9F, U+1EF2-1EFF, U+2020, U+20A0-20AB, U+20AD-20C0, U+2113, U+2C60-2C7F, U+A720-A7FF;
}

@font-face {
  font-family: "Roboto";
  font-style: normal;
  font-weight: 100 900;
  font-display: swap;
  src: url(../font/roboto-cyrillic-wght-normal.woff2) format("woff2");
  unicode-range: U+0301, U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;
}

@font-face {
  font-family: "Roboto";
  font-style: italic;
  font-weight: 400;
  font-display: swap;
  src: url(../font/roboto-latin-400-italic.woff2) format("woff2");
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+0304, U+0308, U+0329, U+2000-206F, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}

@font-face {
  font-family: "Roboto";
  font-style: italic;
  font-weight: 400;
  font-display: swap;
  src: url(../font/roboto-latin-ext-400-italic.woff2) format("woff2");
  unicode-range: U+0100-02BA, U+02BD-02C5, U+02C7-02CC, U+02CE-02D7, U+02DD-02FF, U+0304, U+0308, U+0329, U+1D00-1DBF, U+1E00-1E9F, U+1EF2-1EFF, U+2020, U+20A0-20AB, U+20AD-20C0, U+2113, U+2C60-2C7F, U+A720-A7FF;
}

@font-face {
  font-family: "Roboto";
  font-style: italic;
  font-weight: 400;
  font-display: swap;
  src: url(../font/roboto-cyrillic-400-italic.woff2) format("woff2");
  unicode-range: U+0301, U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;
}

@font-face {
  font-family: "JetBrains Mono";
  font-style: normal;
  font-weight: 400;
  font-display: swap;
  src: url(../font/jetbrains-mono-latin-400-normal.woff2) format("woff2");
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+0304, U+0308, U+0329, U+2000-206F, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}

@font-face {
  font-family: "JetBrains Mono";
  font-style: normal;
  font-weight: 400;
  font-display: swap;
  src: url(../font/jetbrains-mono-latin-ext-400-normal.woff2) format("woff2");
  unicode-range: U+0100-02BA, U+02BD-02C5, U+02C7-02CC, U+02CE-02D7, U+02DD-02FF, U+0304, U+0308, U+0329, U+1D00-1DBF, U+1E00-1E9F, U+1EF2-1EFF, U+2020, U+20A0-20AB, U+20AD-20C0, U+2113, U+2C60-2C7F, U+A720-A7FF;
}

@font-face {
  font-family: "JetBrains Mono";
  font-style: normal;
  font-weight: 400;
  font-display: swap;
  src: url(../font/jetbrains-mono-cyrillic-400-normal.woff2) format("woff2");
  unicode-range: U+0301, U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;
}

@font-face {
  font-family: "JetBrains Mono";
  font-style: italic;
  font-weight: 400;
  font-display: swap;
  src: url(../font/jetbrains-mono-latin-400-italic.woff2) format("woff2");
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+0304, U+0308, U+0329, U+2000-206F, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}

@font-face {
  font-family: "JetBrains Mono";
  font-style: italic;
  font-weight: 400;
  font-display: swap;
  src: url(../font/jetbrains-mono-latin-ext-400-italic.woff2) format("woff2");
  unicode-range: U+0100-02BA, U+02BD-02C5, U+02C7-02CC, U+02CE-02D7, U+02DD-02FF, U+0304, U+0308, U+0329, U+1D00-1DBF, U+1E00-1E9F, U+1EF2-1EFF, U+2020, U+20A0-20AB, U+20AD-20C0, U+2113, U+2C60-2C7F, U+A720-A7FF;
}

@font-face {
  font-family: "JetBrains Mono";
  font-style: italic;
  font-weight: 400;
  font-display: swap;
  src: url(../font/jetbrains-mono-cyrillic-400-italic.woff2) format("woff2");
  unicode-range: U+0301, U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;
}
```

- [ ] **Step 4: Point the font tokens at them**

In `tokens.css` set:

```css
  --body-font-family: "Roboto", system-ui, sans-serif;
  --monospace-font-family: "JetBrains Mono", ui-monospace, monospace;
```

- [ ] **Step 5: Build and run the font check**

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs --only fonts`
Expected: `PASS fonts`.

- [ ] **Step 6: Commit**

```bash
git add content/supplemental/font content/supplemental/css/site.css content/supplemental/css/tokens.css
git commit -m "Self-host Roboto and JetBrains Mono"
```

---

### Task 5: Tokens and the migration of our stylesheets

After this task every color comes from `tokens.css` and the palette is the final one. Intermediate state until Task 6: the header turns white while its icon images are still white, so the three header icons are invisible. Task 6 replaces them.

**Files:**
- Modify (rewrite): `content/supplemental/css/tokens.css`
- Modify: `content/supplemental/css/site.css` (part 1 literals; new part 2 sections "Event banner" and "Content additions")
- Modify: `content/supplemental/css/search.css`, `content/supplemental/css/feedback-form.css`, `content/supplemental/css/dropdown-menu.css` (colors only)
- Modify: `content/supplemental/partials/head-styles.hbs`, `content/supplemental/partials/head-meta.hbs`
- Delete: `content/supplemental/css/overrides.css`

**Interfaces:**
- Produces: every token of the spec's tiers, with the names below. Later tasks use exactly these names.

- [ ] **Step 1: Rewrite `tokens.css`**

Naming: the code block tokens are `--code-block-background` and `--code-block-text`, because upstream's `--code-background` already means the inline code background and part 1 reads it that way. The font tokens keep the upstream names `--body-font-family` and `--monospace-font-family`. The spec uses the same names.

Replace the file with:

```css
/*
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Custom properties of the docs UI. Started from src/css/vars.css of antora-ui-default at revision 0e38223a;
 * tier 3 keeps the upstream names, so part 1 of site.css reads them unchanged.
 *
 * Tier 1: palette. Tier 2: semantic roles. Tier 3: components.
 * Outside this file colors are written only as var(--…) (node tools/check-css.mjs).
 * A dark theme redefines tier 2 and the tier 3 values that do not point to tier 2.
 */

:root {
  color-scheme: light;

  /* ---- Tier 1: palette ---- */

  /* brand book */
  --jmix-space: #17124b;
  --jmix-fiesta: #fc1264;
  --jmix-sun: #fdb42b;
  --jmix-sky: #25cde3;
  --jmix-grace: #22d685;

  /* accent violet: 700 is the current docs violet, 500 the jmix.io link color, the other steps are derived */
  --jmix-violet-800: #261d7d;
  --jmix-violet-700: #342a98;
  --jmix-violet-500: #7b6dff;
  --jmix-violet-200: #d3cdf8;
  --jmix-violet-50: #f0eeff;

  --gray-900: #2a2c33;
  --gray-700: #3a3d46;
  --gray-600: #5c606b;
  --gray-500: #8a8f99;
  --gray-300: #cfd3da;
  --gray-200: #e4e6eb;
  --gray-100: #eff1f4;
  --gray-50: #f6f7f9;
  --white: #fff;

  /* ---- Tier 2: semantic roles ---- */

  --color-text: var(--gray-900);
  --color-heading: var(--jmix-space);
  --color-text-nav: var(--gray-700);
  --color-text-muted: var(--gray-600);
  --color-text-on-accent: var(--white);
  --color-link: var(--jmix-violet-700);
  --color-link-hover: var(--jmix-violet-800);
  --color-accent: var(--jmix-violet-700);
  --color-accent-tint: var(--jmix-violet-50);
  --color-accent-line: var(--jmix-violet-200);
  --color-focus: var(--jmix-violet-700);
  --color-surface: var(--white);
  --color-surface-subtle: var(--gray-50);
  --color-surface-hover: var(--gray-100);
  --color-line: var(--gray-200);
  --color-line-strong: var(--gray-300);
  --color-control-border: var(--gray-500);
  --color-overlay: color-mix(in srgb, var(--jmix-space) 30%, transparent);
  --shadow-menu: 0 10px 28px color-mix(in srgb, var(--jmix-space) 14%, transparent), 0 2px 6px color-mix(in srgb, var(--jmix-space) 8%, transparent);

  --radius-lg: 8px;
  --radius-md: 6px;
  --radius-sm: 4px;

  /* ---- Tier 3: components ---- */

  /* header */
  --header-background: var(--color-surface);
  --header-text: var(--color-heading);
  --header-logo-center: var(--jmix-space);

  /* code blocks and syntax colors, IntelliJ IDEA Light-like; comment and annotation darkened to 4.5:1 */
  --code-block-background: #f7f8fa;
  --code-block-text: #080808;
  --inline-code-text: #1f2128;
  --syntax-keyword: #0033b3;
  --syntax-string: #067d17;
  --syntax-number: #1750eb;
  --syntax-comment: #6f6f6f;
  --syntax-annotation: #7a6a0a;
  --syntax-function: #00627a;
  --syntax-field: #871094;
  --syntax-attribute: #174ad4;
  --syntax-property-key: #083080;
  --syntax-added: #e6f4ea;
  --syntax-removed: #fce8e6;

  /* admonitions: label and icon, background, border, left edge */
  --note-color: #0a6874;
  --note-background: #effbfd;
  --note-border-color: #c9eff5;
  --note-accent: var(--jmix-sky);
  --tip-color: #0d7348;
  --tip-background: #effcf5;
  --tip-border-color: #c6f0dc;
  --tip-accent: var(--jmix-grace);
  --warning-color: #8a5300;
  --warning-background: #fff8e6;
  --warning-border-color: #fbe3ae;
  --warning-accent: var(--jmix-sun);
  --important-color: #b4104a;
  --important-background: #fff1f6;
  --important-border-color: #fbcadb;
  --important-accent: var(--jmix-fiesta);
  --caution-color: #4b3fb8;
  --caution-background: #f4f2ff;
  --caution-border-color: #dad5fb;
  --caution-accent: var(--jmix-violet-500);
  --addon-color: var(--jmix-space);
  --addon-background: #f4f2ff;
  --addon-border-color: #dad5fb;
  --addon-accent: var(--jmix-space);
  /* upstream names: text of the upstream label pill */
  --caution-on-color: var(--color-text-on-accent);
  --important-on-color: var(--color-text-on-accent);
  --note-on-color: var(--color-text-on-accent);
  --tip-on-color: var(--color-text-on-accent);
  --warning-on-color: var(--color-text-on-accent);

  /* event banner and content buttons */
  --banner-background: var(--jmix-violet-700);
  --banner-text: var(--color-text-on-accent);
  --live-demo-background: #fdcb6e;
  --live-demo-background-hover: #ffd588;
  --live-demo-text: #32346b;
  --vaadin-docs-background: #2486f9;
  --vaadin-docs-background-hover: #3890f8;
  --vaadin-docs-text: #fdfdfd;
  --mdn-docs-background: #575757;
  --mdn-docs-background-hover: #727272;
  --mdn-docs-text: #f8f8f9;
  --echarts-docs-background: #b03a5b;
  --echarts-docs-text: #fdfdfd;

  /* upstream: fonts */
  --rem-base: 18; /* used to compute rem value from desired pixel value (e.g., calc(18 / var(--rem-base) * 1rem) = 18px) */
  --body-font-size: 1.0625em; /* 17px */
  --body-font-size--desktop: 1.125em; /* 18px */
  --body-font-size--print: 0.9375em; /* 15px */
  --body-line-height: 1.15;
  --body-font-color: var(--color-text);
  --body-font-family: "Roboto", system-ui, sans-serif;
  --body-font-weight-bold: 600;
  --monospace-font-family: "JetBrains Mono", ui-monospace, monospace;
  --monospace-font-weight-bold: 600;
  /* upstream: base */
  --body-background: var(--color-surface);
  --panel-background: var(--color-surface-subtle);
  --panel-border-color: var(--color-line);
  --scrollbar-track-color: var(--color-surface-subtle);
  --scrollbar-thumb-color: var(--color-line-strong);
  --scrollbar_hover-thumb-color: var(--color-control-border);
  /* upstream: navbar */
  --navbar-background: var(--header-background);
  --navbar-font-color: var(--header-text);
  --navbar_hover-background: var(--color-surface-hover);
  --navbar-button-background: var(--color-surface);
  --navbar-button-border-color: var(--color-line);
  --navbar-button-font-color: var(--color-text);
  --navbar-menu-border-color: var(--color-line);
  --navbar-menu-background: var(--color-surface);
  --navbar-menu-font-color: var(--color-text);
  --navbar-menu_hover-background: var(--color-surface-hover);
  /* upstream: nav */
  --nav-background: var(--color-surface);
  --nav-border-color: var(--color-line);
  --nav-line-height: 1.4;
  --nav-heading-font-color: var(--color-heading);
  --nav-muted-color: var(--color-text-muted);
  --nav-panel-divider-color: var(--color-line);
  --nav-secondary-background: var(--color-surface-subtle);
  /* upstream: toolbar */
  --toolbar-background: var(--color-surface);
  --toolbar-border-color: var(--color-line);
  --toolbar-font-color: var(--color-text-muted);
  --toolbar-muted-color: var(--color-text-muted);
  --page-version-menu-background: var(--color-surface-subtle);
  --page-version-missing-font-color: var(--color-text-muted);
  /* upstream: doc */
  --doc-font-color: var(--color-text);
  --doc-font-size: inherit;
  --doc-font-size--desktop: calc(17 / var(--rem-base) * 1rem);
  --doc-line-height: 1.65;
  --doc-margin: 0 auto;
  --doc-margin--desktop: 0 2rem;
  --heading-font-color: var(--color-heading);
  --heading-font-weight: 650;
  --alt-heading-font-weight: 650;
  --section-divider-color: var(--color-line);
  --link-font-color: var(--color-link);
  --link_hover-font-color: var(--color-link-hover);
  --link_unresolved-font-color: var(--important-color);
  --abstract-background: var(--color-surface-subtle);
  --abstract-font-color: var(--color-text);
  --abstract-border-color: var(--color-line);
  --admonition-background: var(--color-surface-subtle);
  --admonition-label-font-weight: 650;
  --caption-font-color: var(--color-text-muted);
  --caption-font-style: normal;
  --caption-font-weight: 500;
  --code-background: var(--color-surface-subtle);
  --code-font-color: var(--inline-code-text);
  --example-background: var(--color-surface);
  --example-border-color: var(--color-line);
  --kbd-background: var(--color-surface);
  --kbd-border-color: var(--color-line-strong);
  --pre-background: var(--code-block-background);
  --pre-border-color: var(--color-line);
  --pre-annotation-font-color: var(--color-text-muted);
  --quote-background: var(--color-surface-subtle);
  --quote-border-color: var(--color-line-strong);
  --quote-font-color: var(--color-text-muted);
  --quote-attribution-font-color: var(--color-text-muted);
  --sidebar-background: var(--color-surface-subtle);
  --table-border-color: var(--color-line);
  --table-stripe-background: var(--color-surface-subtle);
  --table-footer-background: linear-gradient(to bottom, var(--color-surface-subtle) 0%, var(--color-surface) 100%);
  /* upstream: toc */
  --toc-font-color: var(--color-text-muted);
  --toc-heading-font-color: var(--color-heading);
  --toc-border-color: var(--color-line);
  --toc-line-height: 1.35;
  /* upstream: footer */
  --footer-line-height: var(--doc-line-height);
  --footer-background: var(--color-surface);
  --footer-font-color: var(--color-text-muted);
  --footer-link-font-color: var(--color-text-muted);
  /* upstream: dimensions and positioning; the toolbar height and the wide TOC width differ from upstream */
  --navbar-height: calc(63 / var(--rem-base) * 1rem);
  --toolbar-height: calc(49.5 / var(--rem-base) * 1rem);
  --drawer-height: var(--toolbar-height);
  --body-top: var(--navbar-height);
  --body-min-height: calc(100vh - var(--body-top));
  --nav-height: calc(var(--body-min-height) - var(--toolbar-height));
  --nav-height--desktop: var(--body-min-height);
  --nav-panel-menu-height: calc(100% - var(--drawer-height));
  --nav-panel-explore-height: calc(50% + var(--drawer-height));
  --nav-width: calc(270 / var(--rem-base) * 1rem);
  --toc-top: calc(var(--body-top) + var(--toolbar-height));
  --toc-height: calc(100vh - var(--toc-top) - 2.5rem);
  --toc-width: calc(162 / var(--rem-base) * 1rem);
  --toc-width--widescreen: calc(252 / var(--rem-base) * 1rem);
  --doc-max-width: calc(720 / var(--rem-base) * 1rem);
  --doc-max-width--desktop: calc(828 / var(--rem-base) * 1rem);
  /* upstream: stacking */
  --z-index-nav: 1;
  --z-index-toolbar: 2;
  --z-index-page-version-menu: 3;
  --z-index-navbar: 4;
}
```

- [ ] **Step 2: Replace the color literals in part 1**

In part 1 of `site.css` (the line numbers are those of the upstream files; search by the old text):

| Section | Old | New |
|---|---|---|
| `nav.css`, `.nav-panel-menu:not(.is-active)::after` | `background: rgba(0, 0, 0, 0.5);` | `background: var(--color-overlay);` |
| `doc.css`, `.doc .source-toolbox .copy-toast` | `color: var(--color-white);` | `color: var(--color-text-on-accent);` |
| `header.css`, `#search-input` | `color: #333;` | `color: var(--color-text);` |
| `header.css`, `#search-input` | `border: 1px solid #dbdbdb;` | `border: 1px solid var(--color-control-border);` |
| `header.css`, `#search-input:disabled` | `background-color: #dbdbdb;` | `background-color: var(--color-surface-hover);` |
| `header.css`, `#search-input:disabled::placeholder` | `color: #4c4c4c;` | `color: var(--color-text-muted);` |
| `header.css`, `.navbar-menu` (in the max-width media query) | `box-shadow: 0 8px 16px rgba(10, 10, 10, 0.1);` | `box-shadow: var(--shadow-menu);` |
| `highlight.css` | `color: #998;` | `color: var(--syntax-comment);` |
| `highlight.css` | `color: #333;` | `color: var(--syntax-keyword);` |
| `highlight.css` | `color: #008080;` | `color: var(--syntax-number);` |
| `highlight.css` | `color: #d14;` | `color: var(--syntax-string);` |
| `highlight.css` | `color: #900;` | `color: var(--syntax-function);` |
| `highlight.css` | `color: #458;` | `color: var(--code-block-text);` |
| `highlight.css` | `color: #000080;` | `color: var(--syntax-keyword);` |
| `highlight.css` | `color: #009926;` | `color: var(--syntax-string);` |
| `highlight.css` | `color: #990073;` | `color: var(--syntax-number);` |
| `highlight.css` | `color: #0086b3;` | `color: var(--code-block-text);` |
| `highlight.css` | `color: #999;` | `color: var(--syntax-annotation);` |
| `highlight.css` | `background: #fdd;` | `background: var(--syntax-removed);` |
| `highlight.css` | `background: #dfd;` | `background: var(--syntax-added);` |

- [ ] **Step 3: Move `overrides.css` and the banner into part 2**

Append to part 2 of `site.css` (the rules of `overrides.css` and of the inline `<style>` in `head-styles.hbs`, with tokens instead of literals; the `outline: none` focus resets of the buttons are dropped, and the heading, link and navbar colors are not copied because tokens now provide them):

```css
/* ---- Event banner (markup added for events: #jmix-banner.jmix-banner in the header) ---- */

.jmix-banner {
  display: none;
  flex: 1 1 auto;
  align-items: center;
  align-self: center;
  justify-content: center;
  min-width: 0;
  min-height: 1.8rem;
  height: auto;
  margin-left: 1rem;
  padding: 0.2rem 1rem;
  border-radius: var(--radius-sm);
  background-color: var(--banner-background);
  color: var(--banner-text);
  font-size: 0.85rem;
}

.jmix-banner-content {
  flex: 1 1 auto;
  min-width: 0;
  line-height: 1.2;
  text-align: center;
  white-space: normal;
}

.jmix-banner-link,
.jmix-banner-link:visited {
  color: var(--banner-text);
  text-decoration: underline;
}

.jmix-banner-close-button {
  margin-left: 1rem;
  border: none;
  background: none;
  color: var(--banner-text);
  font-size: 1.2rem;
  line-height: 1;
  cursor: pointer;
}

@media screen and (max-width: 1400px) {
  .jmix-banner {
    font-size: 0.8rem;
  }
}

@media screen and (max-width: 1250px) {
  .jmix-banner {
    margin-left: 1rem;
    padding: 0.2rem 0.5rem;
    font-size: 0.72rem;
  }

  .jmix-banner-close-button {
    margin-left: 0.5rem;
    font-size: 1rem;
  }
}

@media screen and (max-width: 1150px) {
  .jmix-banner {
    font-size: 0.65rem;
  }
}

@media screen and (max-width: 1080px) {
  .jmix-banner {
    display: none;
  }
}

/* ---- Content additions: roles and buttons used by the pages ---- */

.doc {
  hyphens: none;
}

.doc pre.highlight code {
  font-size: 0.8rem;
}

.jmix-ui-live-demo-container {
  float: right;
  padding: 1px;
}

.doc .paragraph.since {
  margin-top: 0.5rem;
}

.doc .paragraph.since p {
  display: inline-block;
  margin: 0;
  padding: 0.15em 0.55em;
  border-radius: var(--radius-md);
  background: var(--color-accent-tint);
  color: var(--color-accent);
  font-size: 0.75rem;
  font-weight: 500;
  letter-spacing: normal;
  line-height: 1.4;
  white-space: nowrap;
}

.doc .admonitionblock.addon-component td.icon i.fa {
  background-color: var(--addon-color);
  color: var(--color-text-on-accent);
}

.doc .admonitionblock.addon-component td.icon i::after {
  content: "Add-on Component";
}

.doc .admonitionblock.addon-component + .jmix-ui-live-demo-container {
  margin-top: 0.5rem;
}

a.live-demo-btn,
a.vaadin-docs-btn,
a.mdn-docs-btn,
a.echarts-docs-btn {
  margin-left: 20px;
  margin-bottom: 10px;
  border-radius: var(--radius-md);
  font-weight: bold;
  text-decoration: none;
}

a.live-demo-btn {
  padding: 8px 20px;
  background-color: var(--live-demo-background);
  color: var(--live-demo-text);
}

a.live-demo-btn:hover {
  background-color: var(--live-demo-background-hover);
}

a.vaadin-docs-btn,
a.mdn-docs-btn,
a.echarts-docs-btn {
  padding: 6px 15px;
  font-size: smaller;
}

a.vaadin-docs-btn {
  background-color: var(--vaadin-docs-background);
  color: var(--vaadin-docs-text);
}

a.vaadin-docs-btn:hover {
  background-color: var(--vaadin-docs-background-hover);
  color: var(--vaadin-docs-text);
}

a.mdn-docs-btn {
  background-color: var(--mdn-docs-background);
  color: var(--mdn-docs-text);
}

a.mdn-docs-btn:hover {
  background-color: var(--mdn-docs-background-hover);
  color: var(--mdn-docs-text);
}

a.echarts-docs-btn,
a.echarts-docs-btn:hover {
  background-color: var(--echarts-docs-background);
  color: var(--echarts-docs-text);
}
```

Then delete the file: `git rm content/supplemental/css/overrides.css`.

- [ ] **Step 4: Replace the literals in our three other stylesheets**

| File | Old | New |
|---|---|---|
| `search.css` | `border-left: 1px solid #FFF;` | `border-left: 1px solid var(--navbar-font-color);` |
| `search.css` | `color: #333;` (both) | `color: var(--color-text);` |
| `search.css` | `border: 1px solid #dbdbdb;` | `border: 1px solid var(--color-control-border);` |
| `search.css` | `background-color: #17124b;` (media query) | `background-color: var(--navbar-menu-background);` |
| `search.css` | `box-shadow: 0 1px 0 0 rgba(0, 0, 0, 0.2), 0 2px 3px 0 rgba(0, 0, 0, 0.1);` | `box-shadow: var(--shadow-menu);` |
| `search.css` | `border: 1px solid #d9d9d9;` | `border: 1px solid var(--color-line);` |
| `search.css` | `background: #fff;` | `background: var(--color-surface);` |
| `search.css` | `color: #174d8c;` | `color: var(--color-accent);` |
| `search.css` | `background: rgba(143, 187, 237, 0.1);` | `background: var(--color-accent-tint);` |
| `search.css` | `border-right: 1px solid #ddd;` | `border-right: 1px solid var(--color-line);` |
| `search.css` | `color: #a4a7ae;` | `color: var(--color-text-muted);` |
| `search.css` | `color: #02060c;` | `color: var(--color-text);` |
| `search.css` | `background-color: rgba(69, 142, 225, 0.05);` | `background-color: var(--color-surface-hover);` |
| `search.css` | the `.header-link, .header-purple-link` rule's `outline: none;` | delete the line |
| `search.css` | `.header-link { color: #17124b; background: #fdb42b; }` | delete the rule, and `.header-link, ` from the two selectors that list it (the class is not in the markup) |
| `search.css` | `color: white;` and `background-color: #17124b;` in `.header-purple-link` | `color: var(--navbar-font-color);` and `background-color: var(--navbar-background);` |
| `feedback-form.css` | `border: 1px solid #E1E1E1;` | `border: 1px solid var(--color-line);` |
| `feedback-form.css` | `border: 1px solid #E8E2FF;` and `background: #E8E2FF;` | `border: 1px solid var(--color-accent-tint);` and `background: var(--color-accent-tint);` |
| `feedback-form.css` | `color: #342A98;` (`.feedback-form__btn`) | `color: var(--color-accent);` |
| `feedback-form.css` | `border-color: #342A98; background: #342A98; color: #fff;` (`_dark`) | `border-color: var(--color-accent); background: var(--color-accent); color: var(--color-text-on-accent);` |
| `feedback-form.css` | `color: #424242;` (title) | `color: var(--color-heading);` |
| `feedback-form.css` | `border: 1px solid #C1C1C1;` | `border: 1px solid var(--color-control-border);` |
| `feedback-form.css` | `color: #424242;` (label) | `color: var(--color-text);` |
| `feedback-form.css` | `color: indianred;` | `color: var(--important-color);` |
| `dropdown-menu.css` | `background-color: #FFFFFF;` | `background-color: var(--color-surface);` |
| `dropdown-menu.css` | `border: 1px solid #E1E1E1;` | `border: 1px solid var(--color-line);` |
| `dropdown-menu.css` | `color: #424242;` | `color: var(--color-text);` |

- [ ] **Step 5: Rewrite the head partials**

`content/supplemental/partials/head-styles.hbs` becomes (the highlight.js theme link stays until Task 9; `docsearch.min.css` and the inline `<style>` go):

```hbs
{{! This Source Code Form is subject to the terms of the Mozilla Public }}
{{! License, v. 2.0. If a copy of the MPL was not distributed with this }}
{{! file, You can obtain one at http://mozilla.org/MPL/2.0/. }}
<link rel="stylesheet" href="{{{uiRootPath}}}/css/tokens.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/site.css">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/10.7.2/styles/default.min.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/search.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/feedback-form.css">
<link rel="stylesheet" href="{{{uiRootPath}}}/css/dropdown-menu.css">
```

`content/supplemental/partials/head-meta.hbs` becomes:

```hbs
{{! This Source Code Form is subject to the terms of the Mozilla Public }}
{{! License, v. 2.0. If a copy of the MPL was not distributed with this }}
{{! file, You can obtain one at http://mozilla.org/MPL/2.0/. }}
<link rel="icon" href="{{uiRootPath}}/img/favicon-light.ico" type="image/x-icon" media="(prefers-color-scheme: light)">
<link rel="icon" href="{{uiRootPath}}/img/favicon-dark.ico" type="image/x-icon" media="(prefers-color-scheme: dark)">
```

- [ ] **Step 6: Run the static check**

Run: `node --test tools/check-css.test.mjs && node tools/check-css.mjs`
Expected: tests pass and `5 stylesheets checked, no problems.` If a finding remains, replace that literal with the closest token from Step 1.

- [ ] **Step 7: Build and look**

Run: `npx antora antora-playbook.yml && node tools/ui-audit.mjs --only contrast,fonts`
Expected: `PASS fonts`; `contrast` fails only on `manager: article.doc pre .hljs-comment`, because the highlight.js `default.min.css` theme, linked after `site.css` until Task 9, sets comments to `#888` (3.35:1 on the code background). The admonition label pills pass: white on the new label colors is 5.9:1 or better. Open `build/site/jmix/data-access/data-manager.html` through a local server (`python3 -m http.server 4500 --directory build/site`, then `http://localhost:4500/jmix/data-access/data-manager.html`) and check that the page renders with a white header and nav; the header icons are expected to be invisible.

- [ ] **Step 8: Commit**

```bash
git add -A content/supplemental/css content/supplemental/partials/head-styles.hbs content/supplemental/partials/head-meta.hbs
git commit -m "Move the UI colors to design tokens"
```

---

### Task 6: Header, icons and search

**Files:**
- Create: `content/supplemental/img/icons/chevron.svg`, `home.svg`, `edit.svg`, `search.svg`, `copy.svg`, `note.svg`, `tip.svg`, `warning.svg`, `important.svg`, `caution.svg`, `addon.svg`
- Modify (rewrite): `content/supplemental/partials/header-content.hbs`, `content/supplemental/css/search.css`, `content/supplemental/css/dropdown-menu.css`
- Modify: `content/supplemental/css/site.css` (new part 2 section "Header")
- Modify: `tools/ui-audit.mjs` (`STYLE_EXPECTATIONS`)
- Delete: `content/supplemental/img/git-icon.svg`, `jmix-icon.svg`, `jmix-ai-assistant-icon.svg`, `warning-icon.svg`, `img/img/feedback-form__thumb-up.svg`

**Interfaces:**
- Produces: header markup classes `.navbar-logo-center`, `.header-icon-link` (with `.ai-assistant-link`, `.git-link`, `.jmix-link`), `.search-field`, `.search-kbd`; the mask icons at `../img/icons/<name>.svg` relative to `_/css/`; the menu id `version-dropdown-menu`; `aria-expanded="false"` on `.version-dropdown-toggle` and `.navbar-burger` (Task 7 keeps them in sync).

- [ ] **Step 1: Write the failing style expectations**

In `tools/ui-audit.mjs`, fill `STYLE_EXPECTATIONS`:

```js
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
    ['manager', '.header-icon-link.ai-assistant-link', 'background-color', '#17124b'],
    ['manager', '.header-icon-link.git-link', 'color', '#17124b'],
];
```

Run: `node tools/ui-audit.mjs --only styles`
Expected: FAIL, the new selectors are not found or have other values.

- [ ] **Step 2: Create the mask icons**

Each file is a single-color stroke icon on a 24×24 grid; CSS uses it as a `mask`, so the color in the file does not matter.

`content/supplemental/img/icons/chevron.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2.4" stroke-linecap="round" stroke-linejoin="round"><path d="m9 6 6 6-6 6"/></svg>
```
`home.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 10.5 12 3l9 7.5"/><path d="M5 9.5V21h5v-6h4v6h5V9.5"/></svg>
```
`edit.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 20h4L19 9l-4-4L4 16v4z"/><path d="m13.5 6.5 4 4"/></svg>
```
`search.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2.2" stroke-linecap="round"><circle cx="11" cy="11" r="7"/><path d="m20 20-3.5-3.5"/></svg>
```
`copy.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="9" y="9" width="12" height="12" rx="2"/><path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"/></svg>
```
`note.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 11v5"/><path d="M12 7.5h.01"/></svg>
```
`tip.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 18h6"/><path d="M10 21h4"/><path d="M12 3a6 6 0 0 0-3.6 10.8c.7.6 1.1 1.3 1.1 2.2h5c0-.9.4-1.6 1.1-2.2A6 6 0 0 0 12 3z"/></svg>
```
`warning.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3.5 2.5 20h19L12 3.5z"/><path d="M12 10v4.5"/><path d="M12 17.5h.01"/></svg>
```
`important.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 7v6"/><path d="M12 16.5h.01"/></svg>
```
`caution.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M8.5 3h7L21 8.5v7L15.5 21h-7L3 15.5v-7z"/><path d="M12 8v5"/><path d="M12 16h.01"/></svg>
```
`addon.svg`:
```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3 3.5 7.5v9L12 21l8.5-4.5v-9L12 3z"/><path d="M3.5 7.5 12 12l8.5-4.5"/><path d="M12 12v9"/></svg>
```

- [ ] **Step 3: Rewrite `header-content.hbs`**

Replace the file with the markup below. Path data marked "from …" is copied verbatim from the named file before Step 6 deletes it: the logo paths from the current `header-content.hbs`, the GitHub path from `img/git-icon.svg`, the five Jmix paths from `img/jmix-icon.svg` (drop their `fill` attributes and the `clip-path` group; the clip rectangle covers the whole view box), and from `img/jmix-ai-assistant-icon.svg`: the first path (the large bubble), the first white path inside the `clip0_0_1` group (the small bubble), the two navy stroke paths and the navy `fill="#17124B"` path (the letters). The second white path in that group lies outside its clip rectangle and is not copied.

```hbs
{{! This Source Code Form is subject to the terms of the Mozilla Public }}
{{! License, v. 2.0. If a copy of the MPL was not distributed with this }}
{{! file, You can obtain one at http://mozilla.org/MPL/2.0/. }}
<header class="header">
    <nav class="navbar">
        <div class="navbar-brand">
            <a class="navbar-item" href="{{{or site.url (or siteRootUrl siteRootPath)}}}">
                <svg class="navbar-logo" width="38" height="38" viewBox="0 0 38 38" fill="none" aria-hidden="true" focusable="false" xmlns="http://www.w3.org/2000/svg">
                    <path d="(from header-content.hbs: the first path)" fill="#FC1264" />
                    <path class="navbar-logo-center" d="(from header-content.hbs: the second path, the one with fill=&quot;white&quot;)" />
                    <path d="(from header-content.hbs: the third path)" fill="#25CDE3" />
                    <path d="M31.0646 23.5038L31.8584 24.2972L31.0646 23.5038Z" fill="#25CDE3" />
                    <path d="M4.93164 11.5374L6.12227 12.728L4.93164 11.5374Z" fill="#FC1264" />
                    <path d="(from header-content.hbs: the sixth path)" fill="#FDB42B" />
                    <path d="(from header-content.hbs: the seventh path)" fill="#22D685" />
                </svg>
                &nbsp;
                {{site.title}}
            </a>

            <div class="vertical-line"></div>

            <div class="navbar-item navbar-item-dropdown">
                <div class="version-dropdown">
                  <button class="version-dropdown-toggle" type="button" title="Show other versions of page" aria-expanded="false" aria-controls="version-dropdown-menu">
                    Version 3
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" class="version-dropdown-icon" aria-hidden="true" focusable="false">
                        <path d="M3.67 6.59L1.455 8.615 15 23.375l13.545-14.76L26.33 6.59 15 18.76z" fill="currentColor"/>
                    </svg>
                  </button>
                  <ul class="version-dropdown-menu" id="version-dropdown-menu">
                    <li><a class="version-dropdown-item" href="https://docs.jmix.io/2.x/">Version 2</a></li>
                    <li><a class="version-dropdown-item" href="https://docs.jmix.io/1.x/">Version 1</a></li>
                  </ul>
                </div>
            </div>

            <button class="navbar-burger" type="button" data-target="topbar-nav" aria-label="Toggle the menu" aria-controls="topbar-nav" aria-expanded="false">
                <span></span>
                <span></span>
                <span></span>
            </button>
        </div>
        <div id="topbar-nav" class="navbar-menu">
            <div class="navbar-end">
                <div class="navbar-item">
                    <span class="search-field">
                        <input id="search-input" placeholder="Search docs" aria-label="Search docs" autocomplete="off">
                        <kbd class="search-kbd" aria-hidden="true">/</kbd>
                    </span>
                </div>
            </div>

            <div class="header-btn">
                <a class="header-icon-link ai-assistant-link" href="https://ai-assistant.jmix.io" target="_blank" aria-label="AI Assistant" title="AI Assistant">
                    <svg width="80" height="59" viewBox="0 0 80 59" aria-hidden="true" focusable="false" xmlns="http://www.w3.org/2000/svg">
                        <mask id="ai-assistant-icon-cutouts" maskUnits="userSpaceOnUse" x="0" y="0" width="80" height="59">
                            <rect width="80" height="59" fill="white"/>
                            <path d="M50 14L60.4318 14L67 14" stroke="black" stroke-width="6" stroke-linecap="round"/>
                            <path d="M54 26L63.75 26H67" stroke="black" stroke-width="6" stroke-linecap="round"/>
                            <path d="(from jmix-ai-assistant-icon.svg: the letters path)" fill="black"/>
                        </mask>
                        <g mask="url(#ai-assistant-icon-cutouts)">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="(from jmix-ai-assistant-icon.svg: the large bubble)"/>
                            <path fill-rule="evenodd" clip-rule="evenodd" d="(from jmix-ai-assistant-icon.svg: the small bubble)"/>
                        </g>
                    </svg>
                </a>
                <a class="header-icon-link git-link" href="https://github.com/jmix-framework/jmix" target="_blank" aria-label="Star us on GitHub" title="Star us on GitHub">
                    <svg width="16" height="16" viewBox="0 0 16 16" aria-hidden="true" focusable="false" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" clip-rule="evenodd" d="(from git-icon.svg)"/>
                    </svg>
                </a>
                <a class="header-icon-link jmix-link" href="https://www.jmix.io/" target="_blank" aria-label="Go to Jmix website" title="Go to Jmix website">
                    <svg width="38" height="38" viewBox="0 0 38 38" aria-hidden="true" focusable="false" xmlns="http://www.w3.org/2000/svg">
                        <path d="(from jmix-icon.svg: path 1)"/>
                        <path d="(from jmix-icon.svg: path 2)"/>
                        <path d="(from jmix-icon.svg: path 3)"/>
                        <path d="(from jmix-icon.svg: path 4)"/>
                        <path d="(from jmix-icon.svg: path 5)"/>
                    </svg>
                </a>
            </div>
        </div>
    </nav>
</header>
```

The `(from …)` markers are copy instructions, not content: after Step 3 no `(from` text may remain. Check with `grep -c '(from' content/supplemental/partials/header-content.hbs`, expected `0`.

- [ ] **Step 4: Add the header styles**

Append to part 2 of `site.css`:

```css
/* ---- Header ---- */

.navbar {
  border-bottom: 1px solid var(--color-line);
}

.navbar-brand .navbar-item:first-child {
  font-size: 1rem;
  font-weight: 600;
  letter-spacing: -0.005em;
}

.navbar-logo-center {
  fill: var(--header-logo-center);
}

.navbar-brand .vertical-line {
  margin: 0.95rem 0.875rem;
  border-left: 1px solid var(--color-line);
}

@media screen and (min-width: 1024px) {
  .navbar-brand {
    flex-grow: 1;
  }

  .navbar-menu {
    flex-grow: 0;
  }
}

.header-btn {
  display: flex;
  gap: 0.25rem;
  align-items: center;
  margin-right: 0.75rem;
}

.header-icon-link {
  display: grid;
  place-items: center;
  width: 2.25rem;
  height: 2.25rem;
  border-radius: var(--radius-lg);
  color: var(--header-text);
}

.header-icon-link:hover {
  background: var(--navbar_hover-background);
}

.header-icon-link svg {
  width: auto;
  height: 1.25rem;
  fill: currentColor;
  /* keeps the AI Assistant mask intact; currentColor still follows the forced link color */
  forced-color-adjust: none;
}

.header-icon-link.ai-assistant-link {
  width: 2.75rem;
  background: var(--color-heading);
  color: var(--color-text-on-accent);
}

.header-icon-link.ai-assistant-link:hover {
  background: var(--color-accent);
}

@media screen and (max-width: 1023px) {
  .header-btn {
    flex-wrap: wrap;
    justify-content: center;
    margin: 0.5rem 0;
  }
}

@media screen and (max-width: 420px) {
  .navbar-brand .navbar-item:first-child {
    font-size: 0.75rem;
  }
}

@media screen and (max-width: 340px) {
  .navbar-brand .vertical-line {
    display: none;
  }
}

@media (forced-colors: active) {
  .header-icon-link.ai-assistant-link {
    border: 1px solid ButtonText;
  }
}
```

- [ ] **Step 5: Rewrite `search.css` and `dropdown-menu.css`**

`content/supplemental/css/search.css`:

```css
/* Search field in the header and the Lunr search results (@jmix-framework/lunr-extension). */

.search-field {
  position: relative;
  display: inline-flex;
  align-items: center;
}

.search-field::before {
  content: "";
  position: absolute;
  left: 0.65rem;
  width: 0.9rem;
  height: 0.9rem;
  background-color: var(--color-text-muted);
  mask: url(../img/icons/search.svg) center / contain no-repeat;
  pointer-events: none;
}

#search-input {
  width: 18rem;
  height: 2.25rem;
  padding: 0 2.1rem;
  border: 1px solid var(--color-control-border);
  border-radius: var(--radius-lg);
  background: var(--color-surface-subtle);
  color: var(--color-text);
  font: inherit;
  font-size: 0.8rem;
  line-height: 1.5;
}

#search-input::placeholder {
  color: var(--color-text-muted);
  opacity: 1;
}

#search-input:hover {
  background: var(--color-surface-hover);
}

#search-input:focus {
  border-color: var(--color-accent);
  background: var(--color-surface);
}

.search-kbd {
  position: absolute;
  right: 0.55rem;
  padding: 0 0.35rem;
  border: 1px solid var(--color-line-strong);
  border-radius: var(--radius-sm);
  color: var(--color-text-muted);
  font: 400 0.68rem/1.35 var(--monospace-font-family);
  pointer-events: none;
}

#search-input:focus + .search-kbd {
  display: none;
}

@media screen and (max-width: 1023px) {
  .search-field,
  #search-input {
    width: 100%;
  }

  #search-input {
    /* 16px or more, or iOS Safari zooms in when the field gets focus (the old inline style did this) */
    font-size: 1rem;
  }
}

.search-result-dropdown-menu {
  position: absolute;
  z-index: 100;
  top: 100%;
  right: 0;
  left: inherit;
  display: block;
  min-width: 500px;
  max-width: 600px;
  height: auto;
  margin: 0.5rem 0 0;
  padding: 0;
  border: none;
  border-radius: var(--radius-lg);
  background: transparent;
  box-shadow: var(--shadow-menu);
  text-align: left;
}

@media screen and (max-width: 768px) {
  .search-result-dropdown-menu {
    min-width: calc(100vw - 3.75rem);
  }
}

.search-result-dataset {
  position: relative;
  overflow: auto;
  max-height: calc(100vh - 5.25rem);
  padding: 0.25rem 0.75rem 0.75rem;
  border: 1px solid var(--color-line);
  border-radius: var(--radius-lg);
  background: var(--color-surface);
  color: var(--color-text);
}

.search-result-component-header {
  padding: 0.5rem 0 0.25rem;
  color: var(--color-text-muted);
  font-size: 0.75rem;
  font-weight: 500;
}

.search-result-highlight {
  padding: 0.1em 0.05em;
  background: var(--color-accent-tint);
  color: var(--color-accent);
  font-weight: 600;
}

.search-result-item {
  display: flex;
  margin: 0.5rem 0;
  font-size: 1rem;
}

.search-result-document-title {
  position: relative;
  width: 33%;
  padding: 0.25rem 0.5rem 0.25rem 0;
  border-right: 1px solid var(--color-line);
  color: var(--color-text-muted);
  font-size: 0.8rem;
  text-align: right;
  overflow-wrap: break-word;
}

.search-result-document-hit {
  flex: 1;
  color: var(--color-text);
  font-size: 0.75em;
  font-weight: 400;
}

.search-result-document-hit > a {
  display: block;
  margin-bottom: 0.25rem;
  padding: 0.5rem 0 0.5rem 1rem;
  border-radius: var(--radius-md);
  color: inherit;
}

.search-result-document-hit > a:hover {
  background: var(--color-surface-hover);
  text-decoration: none;
}

@media (forced-colors: active) {
  #search-input,
  .search-result-dataset {
    border-color: CanvasText;
  }

  .search-field::before {
    forced-color-adjust: none;
    background-color: GrayText;
  }
}
```

`content/supplemental/css/dropdown-menu.css`:

```css
/* Version menu in the header. Behavior: js/dropdown-menu.js. */

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

.version-dropdown-menu {
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

.version-dropdown-menu.opened {
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

  .version-dropdown-menu {
    border-color: CanvasText;
  }
}
```

- [ ] **Step 6: Delete the replaced images**

```bash
git rm content/supplemental/img/git-icon.svg content/supplemental/img/jmix-icon.svg content/supplemental/img/jmix-ai-assistant-icon.svg content/supplemental/img/warning-icon.svg content/supplemental/img/img/feedback-form__thumb-up.svg
grep -rn "git-icon\|jmix-icon\|jmix-ai-assistant-icon\|warning-icon\|slack-icon\|header-purple-link" content/supplemental || echo "no references left"
```

Expected: `no references left`.

- [ ] **Step 7: Build, check and look**

Run: `npx antora antora-playbook.yml && node tools/check-css.mjs && node tools/ui-audit.mjs --only styles,forced`
Expected: `PASS styles`, and `forced` without header failures. Open `build/ui-audit/forced-light-manager.png` and `forced-dark-manager.png`: the AI Assistant, GitHub and Jmix icons are visible, and the AI icon shows its two bubbles with the lines and the letters cut out. In a normal browser view the AI icon is white on the Space button with the same cut-outs. Type a query into the search field: the results open under it.

- [ ] **Step 8: Commit**

```bash
git add -A content/supplemental tools/ui-audit.mjs
git commit -m "Restyle the header and the search field"
```

---

### Task 7: Accessibility: focus, skip link, names and keyboard access

**Files:**
- Modify: `content/supplemental/css/site.css` (part 1: delete nine lines; part 2: new section "Accessibility")
- Create: `content/supplemental/partials/main.hbs`, `content/supplemental/partials/nav-tree.hbs`, `content/supplemental/js/a11y.js`
- Modify: `content/supplemental/partials/header-content.hbs` (skip link), `content/supplemental/partials/nav-explore.hbs`, `content/supplemental/partials/footer-scripts.hbs`, `content/supplemental/js/dropdown-menu.js`

**Interfaces:**
- Consumes: `aria-expanded` attributes, `.search-field` and the icons from Task 6.
- Produces: `#main-content`; `.skip-link`; named `.nav-item-toggle` buttons; `<button class="context">` in the explore panel.

- [ ] **Step 1: Confirm the failing checks**

Run: `node tools/ui-audit.mjs --only focus,skip,names,search`
Expected: all four FAIL.

- [ ] **Step 2: Remove the focus resets from part 1**

In part 1 of `site.css`, delete the line `outline: none;` in exactly these rules: `summary` (base.css), `.nav-menu-toggle` and `.nav-item-toggle` (nav.css), `.nav-toggle` (toolbar.css), `.page-versions .version-menu-toggle` (page-versions.css), `.sidebar.toc .toc-menu a` (toc.css), `.doc .source-toolbox .copy-button` (doc.css), `#search-input:focus` and `.navbar-burger` (header.css). Delete the then empty `#search-input:focus {}` rule.

Run: `grep -n "outline: none" content/supplemental/css/*.css`
Expected: no output.

- [ ] **Step 3: Add the accessibility section**

Append to part 2 of `site.css`:

```css
/* ---- Accessibility ---- */

:focus-visible {
  outline: 2px solid var(--color-focus);
  outline-offset: 2px;
}

#main-content:focus {
  /* the skip link target is a region, not a control */
  outline: none;
}

.skip-link {
  position: fixed;
  z-index: 100;
  top: 0.5rem;
  left: 0.75rem;
  padding: 0.5rem 0.875rem;
  border: 1px solid var(--color-line);
  border-radius: var(--radius-md);
  background: var(--color-surface);
  box-shadow: var(--shadow-menu);
  color: var(--color-heading);
  font-weight: 600;
  text-decoration: none;
  translate: 0 -200%;
}

.skip-link:focus-visible {
  translate: 0 0;
}

/* the copy button is visible and reachable by keyboard, not only on hover */
.doc .source-toolbox {
  visibility: visible;
}

.doc .source-toolbox .copy-icon {
  display: none;
}

.doc .source-toolbox .copy-button::before {
  content: "";
  flex: none;
  width: 1em;
  height: 1em;
  background-color: currentColor;
  mask: url(../img/icons/copy.svg) center / contain no-repeat;
}

/* the explore panel toggle is a button now */
.nav-panel-explore .context {
  width: 100%;
  border: 0;
  background: transparent;
  font: inherit;
  text-align: left;
}

@media (prefers-reduced-motion: reduce) {
  html {
    scroll-behavior: auto;
  }
}

@media (forced-colors: active) {
  .doc .source-toolbox .copy-button::before {
    forced-color-adjust: none;
    background-color: ButtonText;
  }
}
```

- [ ] **Step 4: Add the skip link and the main target**

In `content/supplemental/partials/header-content.hbs`, insert directly before `<header class="header">`:

```hbs
<a class="skip-link" href="#main-content">Skip to content</a>
```

Create `content/supplemental/partials/main.hbs` (upstream `src/partials/main.hbs` plus the id and tabindex):

```hbs
{{! This Source Code Form is subject to the terms of the Mozilla Public }}
{{! License, v. 2.0. If a copy of the MPL was not distributed with this }}
{{! file, You can obtain one at http://mozilla.org/MPL/2.0/. }}
<main class="article" id="main-content" tabindex="-1">
{{> toolbar}}
  <div class="content">
{{#if (eq page.layout '404')}}
{{> article-404}}
{{else}}
{{> toc}}
{{> article}}
{{/if}}
  </div>
</main>
```

- [ ] **Step 5: Name the nav toggles and make the explore toggle a button**

Create `content/supplemental/partials/nav-tree.hbs` (upstream `src/partials/nav-tree.hbs` with a named toggle):

```hbs
{{! This Source Code Form is subject to the terms of the Mozilla Public }}
{{! License, v. 2.0. If a copy of the MPL was not distributed with this }}
{{! file, You can obtain one at http://mozilla.org/MPL/2.0/. }}
{{#if navigation.length}}
<ul class="nav-list">
  {{#each navigation}}
  <li class="nav-item{{#if (eq ./url @root.page.url)}} is-current-page{{/if}}" data-depth="{{or ../level 0}}">
    {{#if ./content}}
    {{#if ./items.length}}
    <button class="nav-item-toggle" type="button" aria-label="{{{detag ./content attribute=true}}}" aria-expanded="false"></button>
    {{/if}}
    {{#if ./url}}
    <a class="nav-link" href="
      {{~#if (eq ./urlType 'internal')}}{{{relativize ./url}}}
      {{~else}}{{{./url}}}{{~/if}}">{{{./content}}}</a>
    {{else}}
    <span class="nav-text">{{{./content}}}</span>
    {{/if}}
    {{/if}}
{{> nav-tree navigation=./items level=(increment ../level)}}
  </li>
  {{/each}}
</ul>
{{/if}}
```

In `content/supplemental/partials/nav-explore.hbs`, replace

```hbs
  <div class="context">
    <span class="title">{{page.component.title}}</span>
    <span class="version">{{page.componentVersion.displayVersion}}</span>
  </div>
```

with

```hbs
  <button class="context" type="button" aria-expanded="false">
    <span class="title">{{page.component.title}}</span>
    <span class="version">{{page.componentVersion.displayVersion}}</span>
  </button>
```

- [ ] **Step 6: Give the version menu its keyboard behavior**

Replace `content/supplemental/js/dropdown-menu.js` with:

```js
(function () {
    const versionDropdownWrapperList = document.querySelectorAll('.version-dropdown');
    versionDropdownWrapperList.forEach(versionDropdownWrapper => {
        const dropdownToggle = versionDropdownWrapper.querySelector('.version-dropdown-toggle');
        const dropdownMenu = versionDropdownWrapper.querySelector('.version-dropdown-menu');
        const setOpened = function (opened) {
            dropdownToggle.classList.toggle('opened', opened);
            dropdownMenu.classList.toggle('opened', opened);
            dropdownToggle.setAttribute('aria-expanded', String(opened));
        };
        dropdownToggle.addEventListener('click', function () {
            setOpened(!dropdownToggle.classList.contains('opened'));
        });
        versionDropdownWrapper.addEventListener('keydown', function (event) {
            if (event.key === 'Escape' && dropdownToggle.classList.contains('opened')) {
                setOpened(false);
                dropdownToggle.focus();
            }
        });
        document.addEventListener('click', function (event) {
            if (!versionDropdownWrapper.contains(event.target)) {
                setOpened(false);
            }
        });
    });
})()
```

- [ ] **Step 7: Write `a11y.js`**

Create `content/supplemental/js/a11y.js`:

```js
// Accessibility for markup that site.js and the Lunr search UI control: aria-expanded on the toggles
// whose state site.js keeps in classes, a name for the copy buttons, focus for the skip link, and
// keyboard access to the search results.
(function () {
    // site.js toggles .is-active on nav items, the explore panel and the burger
    const syncNavToggle = function (item) {
        const toggle = item.querySelector(':scope > .nav-item-toggle');
        if (toggle) toggle.setAttribute('aria-expanded', String(item.classList.contains('is-active')));
    };
    const explorePanel = document.querySelector('.nav-panel-explore');
    const exploreToggle = explorePanel && explorePanel.querySelector('.context');
    const burger = document.querySelector('.navbar-burger');
    const syncPanels = function () {
        if (exploreToggle) exploreToggle.setAttribute('aria-expanded', String(explorePanel.classList.contains('is-active')));
        if (burger) burger.setAttribute('aria-expanded', String(burger.classList.contains('is-active')));
    };
    document.querySelectorAll('.nav-item').forEach(syncNavToggle);
    syncPanels();
    const observer = new MutationObserver(function (records) {
        records.forEach(function (record) {
            if (record.target.classList.contains('nav-item')) syncNavToggle(record.target);
        });
        syncPanels();
    });
    const nav = document.querySelector('.nav');
    if (nav) observer.observe(nav, { subtree: true, attributes: true, attributeFilter: ['class'] });
    if (burger) observer.observe(burger, { attributes: true, attributeFilter: ['class'] });

    // site.js names the copy buttons through a hidden icon's alt text and an invisible toast
    document.querySelectorAll('.doc .source-toolbox .copy-button').forEach(function (button) {
        button.setAttribute('aria-label', 'Copy to clipboard');
        const toast = button.querySelector('.copy-toast');
        if (toast) toast.setAttribute('aria-hidden', 'true');
    });

    // site.js intercepts in-page links to scroll them, so the skip link moves focus itself
    const skipLink = document.querySelector('.skip-link');
    const main = document.getElementById('main-content');
    if (skipLink && main) {
        skipLink.addEventListener('click', function () {
            main.focus({ preventScroll: true });
        });
    }

    // search-ui.js runs the search 100 ms after every keydown in the input and re-renders the results;
    // Escape there clears the query and the results
    const searchInput = document.getElementById('search-input');
    const searchArea = searchInput && searchInput.closest('.search-field');
    if (searchArea) {
        const clearSearch = function () {
            searchInput.dispatchEvent(new KeyboardEvent('keydown', { key: 'Escape', bubbles: true }));
        };
        // keep Tab away from search-ui.js, or the result that just received focus is replaced
        document.addEventListener('keydown', function (event) {
            if (event.target === searchInput && event.key === 'Tab') event.stopPropagation();
        }, true);
        searchArea.addEventListener('keydown', function (event) {
            if (event.key === 'Escape' && event.target !== searchInput) {
                searchInput.focus();
                clearSearch();
            }
        });
        searchArea.addEventListener('focusout', function (event) {
            if (!event.relatedTarget || !searchArea.contains(event.relatedTarget)) clearSearch();
        });
    }
})()
```

- [ ] **Step 8: Update the footer scripts**

Replace `content/supplemental/partials/footer-scripts.hbs` with (no icons8 swap; no `data-stylesheet`; `a11y.js` after `site.js` and `search-ui.js`; the `/` shortcut no longer fires while the user types in another field):

```hbs
{{! This Source Code Form is subject to the terms of the Mozilla Public }}
{{! License, v. 2.0. If a copy of the MPL was not distributed with this }}
{{! file, You can obtain one at http://mozilla.org/MPL/2.0/. }}
<script src="{{{uiRootPath}}}/js/site.js"></script>
<script
  src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.7.2/highlight.min.js"
></script>
<script>
  hljs.highlightAll();
</script>
<script src="{{uiRootPath}}/js/vendor/lunr.js"></script>
<script src="{{uiRootPath}}/js/search-ui.js" id="search-ui-script" data-site-root-path="{{uiRootPath}}/.." data-snippet-length="100"></script>
<script async src="{{uiRootPath}}/../search-index.js"></script>
<script async src="{{uiRootPath}}/js/feedback-form.js"></script>
<script async src="{{uiRootPath}}/js/dropdown-menu.js"></script>
<script src="{{uiRootPath}}/js/a11y.js"></script>
<script>
    document.addEventListener('keydown', function(event) {
        if (event.key === '/' && !event.ctrlKey && !event.metaKey && !event.altKey) {
            const searchInput = document.getElementById('search-input');
            const typing = event.target.closest && event.target.closest('input, textarea, select, [contenteditable]');
            if (searchInput && !typing) {
                event.preventDefault();
                searchInput.focus();
            }
        }
    });
</script>
```

- [ ] **Step 9: Build and run the checks**

Run: `npx antora antora-playbook.yml && node tools/check-css.mjs && node tools/ui-audit.mjs --only focus,skip,names,search`
Expected: all PASS. (`forced` still reports the nav, toolbar, code and admonition borders, which Tasks 8 and 9 add.) If `focus` reports an element below 3:1, its outline sits on a dark or tinted background: fix it in that component's section, not in the global rule.

- [ ] **Step 10: Commit**

```bash
git add -A content/supplemental
git commit -m "Add focus styles, skip link and keyboard access"
```

---

### Task 8: Navigation, explore panel, toolbar and table of contents

**Files:**
- Modify: `content/supplemental/css/site.css` (new part 2 sections "Navigation", "Toolbar", "Table of contents")
- Modify: `tools/ui-audit.mjs` (`STYLE_EXPECTATIONS`)

**Interfaces:**
- Consumes: the chevron, home and edit icons (Task 6), the explore button (Task 7).

- [ ] **Step 1: Write the failing expectations**

Append to `STYLE_EXPECTATIONS`:

```js
    // Task 8: navigation, explore panel, toolbar, TOC
    ['manager', '.nav', 'border-right-width', '1px'],
    ['manager', '.is-current-page > .nav-link', 'background-color', '#f0eeff'],
    ['manager', '.is-current-page > .nav-link', 'color', '#342a98'],
    ['manager', '.nav-item-toggle', 'width', '24px'],
    ['manager', '.nav-panel-explore .context', 'height', '49.5px'],
    ['manager', '.toolbar', 'height', '49.5px'],
    ['manager', '.toolbar', 'border-bottom-width', '1px'],
    ['manager', '.edit-this-page a', 'color', '#5c606b'],
    ['manager', 'aside.toc.sidebar', 'flex-basis', '252px'],
    ['manager', '.toc .toc-menu a', 'border-left-width', '1px'],
```

Run: `node tools/ui-audit.mjs --only styles`
Expected: FAIL at least on the nav link, nav toggle, toolbar border and TOC border entries (the heights and the TOC width already come from the Task 5 tokens).

- [ ] **Step 2: Add the sections**

Append to part 2 of `site.css`:

```css
/* ---- Navigation ---- */

@media screen and (min-width: 1024px) {
  .nav {
    border-right: 1px solid var(--color-line);
  }
}

.nav-menu {
  padding: 1rem 0.75rem 3rem 1.125rem;
}

.nav-menu h3.title {
  padding: 0 0.5rem 0.375rem;
  color: var(--color-heading);
  font-size: 0.8rem;
  font-weight: 600;
}

.nav-menu h3.title a:hover {
  text-decoration: none;
}

.nav-item {
  margin-top: 1px;
}

.nav-list {
  margin-left: 0.875rem;
}

.nav-menu > .nav-list {
  margin-left: 0.25rem;
}

/* .nav prefix: upstream .nav a { color: inherit } is (0,1,1) */
.nav .nav-link,
.nav .nav-text {
  display: block;
  padding: 0.3rem 0.5rem;
  border-radius: var(--radius-md);
  color: var(--color-text-nav);
}

.nav-link:hover {
  background: var(--color-surface-hover);
  color: var(--color-heading);
  text-decoration: none;
}

.is-current-page > .nav-link {
  background: var(--color-accent-tint);
  color: var(--color-accent);
  font-weight: 600;
}

.nav-item-toggle {
  width: 24px;
  height: 24px;
  margin: 3px 0 0 -24px;
  border-radius: 5px;
  background: none;
}

.nav-item-toggle::before {
  content: "";
  display: block;
  width: 100%;
  height: 100%;
  background-color: var(--color-text-muted);
  mask: url(../img/icons/chevron.svg) center / 14px no-repeat;
}

.nav-item-toggle:hover {
  background: var(--color-surface-hover);
}

/* explore panel: component and version switcher at the bottom of the nav */

.nav-panel-explore .context {
  padding: 0 0.75rem 0 1rem;
  border-top: 1px solid var(--color-line);
  box-shadow: none;
  background: var(--color-surface);
  color: var(--color-text-muted);
  font-size: 0.8rem;
  cursor: pointer;
}

.nav-panel-explore .context:hover {
  background: var(--color-surface-hover);
  color: var(--color-heading);
}

.nav-panel-explore .context .title {
  color: var(--color-heading);
  font-weight: 500;
}

.nav-panel-explore .context .version::after {
  width: 1rem;
  height: 1rem;
  background: currentColor;
  mask: url(../img/icons/chevron.svg) center / 12px no-repeat;
  rotate: -90deg;
}

.nav-panel-explore.is-active .context .version::after {
  rotate: 90deg;
}

.nav-panel-explore .components {
  padding: 0.875rem 1rem 0;
  border-top: 1px solid var(--color-line);
  box-shadow: none;
  background: var(--color-surface-subtle);
}

.nav-panel-explore .component .title {
  color: var(--color-heading);
  font-weight: 600;
  text-decoration: none;
}

.nav-panel-explore .versions {
  gap: 0.375rem;
  margin: 0.5rem 0 0;
}

.nav-panel-explore .component .version {
  margin: 0;
}

.nav-panel-explore .component .version a {
  padding: 0.3em 0.7em;
  border: 1px solid var(--color-line-strong);
  border-radius: var(--radius-md);
  background: var(--color-surface);
  color: var(--color-text);
  font-size: 0.78rem;
  font-weight: 500;
  opacity: 1;
}

.nav-panel-explore .component .version a:hover {
  border-color: var(--color-accent);
  color: var(--color-accent);
  text-decoration: none;
}

.nav-panel-explore .component .is-current a {
  border-color: var(--color-accent);
  background: var(--color-accent);
  color: var(--color-text-on-accent);
}

.nav-panel-explore .component .is-current a:hover {
  border-color: var(--color-accent);
  color: var(--color-text-on-accent);
}

@media (prefers-reduced-motion: no-preference) {
  .nav-item-toggle {
    transition: transform 0.15s;
  }

  .nav-panel-explore .context .version::after {
    transition: rotate 0.15s;
  }
}

@media (forced-colors: active) {
  .nav-item-toggle::before,
  .nav-panel-explore .context .version::after {
    forced-color-adjust: none;
    background-color: ButtonText;
  }

  .is-current-page > .nav-link,
  .nav-panel-explore .component .is-current a {
    border: 1px solid Highlight;
  }
}

/* ---- Toolbar ---- */

.toolbar {
  border-bottom: 1px solid var(--color-line);
  box-shadow: none;
  font-size: 0.8rem;
}

.home-link,
.home-link.is-current,
.home-link:hover {
  display: grid;
  place-items: center;
  width: 1.75rem;
  height: 1.75rem;
  margin: 0 0 0 1rem;
  border-radius: var(--radius-md);
  background: none;
}

.home-link::before {
  content: "";
  width: 1rem;
  height: 1rem;
  background-color: currentColor;
  mask: url(../img/icons/home.svg) center / contain no-repeat;
}

.home-link:hover {
  background: var(--color-surface-hover);
  color: var(--color-heading);
}

/* .toolbar prefix: upstream a + .breadcrumbs is (0,1,1) */
.toolbar .breadcrumbs {
  padding-left: 0.375rem;
}

.breadcrumbs li::after {
  color: var(--color-line-strong);
}

.breadcrumbs li:last-of-type {
  color: var(--color-text);
}

.breadcrumbs a:hover {
  color: var(--color-heading);
}

.toolbar .edit-this-page {
  padding-right: 1rem;
}

.toolbar .edit-this-page a {
  display: inline-flex;
  gap: 0.375rem;
  align-items: center;
  color: var(--color-text-muted);
}

.toolbar .edit-this-page a::before {
  content: "";
  width: 0.875rem;
  height: 0.875rem;
  background-color: currentColor;
  mask: url(../img/icons/edit.svg) center / contain no-repeat;
}

.toolbar .edit-this-page a:hover {
  color: var(--color-heading);
  text-decoration: none;
}

@media (forced-colors: active) {
  .home-link::before,
  .toolbar .edit-this-page a::before {
    forced-color-adjust: none;
    background-color: LinkText;
  }
}

/* ---- Table of contents ---- */

.toc .toc-menu h3 {
  font-weight: 600;
}

@media screen and (min-width: 1024px) {
  .toc .toc-menu h3,
  .toc .toc-menu ul {
    font-size: 0.78rem;
  }
}

.toc .toc-menu a {
  padding: 0.3rem 0 0.3rem 0.75rem;
  border-left-width: 1px;
  overflow-wrap: break-word;
}

.toc .toc-menu li[data-level="2"] a {
  padding-left: 1.5rem;
}

.toc .toc-menu li[data-level="3"] a {
  padding-left: 2.25rem;
}

.toc .toc-menu a:hover {
  color: var(--color-heading);
}

.toc .toc-menu a.is-active {
  margin-left: -0.5px;
  border-left: 2px solid var(--color-accent);
  color: var(--color-accent);
  font-weight: 500;
}

@media (forced-colors: active) {
  .toc .toc-menu a.is-active {
    border-left-color: Highlight;
  }
}
```

- [ ] **Step 3: Build and run the checks**

Run: `npx antora antora-playbook.yml && node tools/check-css.mjs && node tools/ui-audit.mjs`
Expected: `styles`, `focus`, `skip`, `names`, `search` and `fonts` pass; `contrast` fails only on `manager: article.doc pre .hljs-comment` and `forced` only on the `code` and `admonition` borders, both until Task 9. Open the explore panel with the mouse and with Enter on its button: the version pills show, v3 filled violet, and the menu above is covered by the Space-tinted overlay.

- [ ] **Step 4: Commit**

```bash
git add content/supplemental/css/site.css tools/ui-audit.mjs
git commit -m "Restyle the navigation, toolbar and table of contents"
```

---

### Task 9: Article typography, code blocks and admonitions

**Files:**
- Modify: `content/supplemental/css/site.css` (part 2: new sections "Article", "Code blocks", "Admonitions"; delete from "Content additions" the rule `.doc pre.highlight code { font-size: 0.8rem; }` and the two `.addon-component td.icon` rules)
- Modify: `content/supplemental/partials/head-styles.hbs` (remove the highlight.js theme link)
- Modify: `tools/ui-audit.mjs` (`STYLE_EXPECTATIONS`)

- [ ] **Step 1: Write the failing expectations**

Append to `STYLE_EXPECTATIONS`:

```js
    // Task 9: article, code, admonitions
    ['manager', 'article.doc > h1.page', 'font-weight', '700'],
    ['manager', 'article.doc > h1.page', 'color', '#17124b'],
    ['manager', 'article.doc h2', 'border-bottom-width', '0px'],
    ['manager', 'article.doc pre.highlight > code', 'background-color', '#f7f8fa'],
    ['manager', 'article.doc pre.highlight > code', 'border-top-left-radius', '8px'],
    ['manager', 'article.doc .hljs-keyword', 'color', '#0033b3'],
    ['manager', 'article.doc .source-toolbox', 'visibility', 'visible'],
    ['manager', 'article.doc .conum[data-value]', 'background-color', '#17124b'],
    ['events', '.admonitionblock.note > table', 'border-left-color', '#25cde3'],
    ['events', '.admonitionblock.note td.icon i', 'background-color', 'rgba(0, 0, 0, 0)'],
    ['events', '.admonitionblock.note td.icon i', 'color', '#0a6874'],
    ['geomap', '.admonitionblock.addon-component > table', 'border-left-color', '#17124b'],
    ['geomap', '.admonitionblock.addon-component td.icon i::after', 'content', '"Add-on component"'],
```

Run: `node tools/ui-audit.mjs --only styles`
Expected: FAIL on the new entries, except the toolbox visibility, which Task 7 already made visible.

- [ ] **Step 2: Drop the highlight.js theme and the superseded content rules**

In `head-styles.hbs` delete the line `<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/10.7.2/styles/default.min.css">`. In part 2 "Content additions" delete `.doc pre.highlight code { font-size: 0.8rem; }`, `.doc .admonitionblock.addon-component td.icon i.fa { … }` and `.doc .admonitionblock.addon-component td.icon i::after { … }`.

- [ ] **Step 3: Add the sections**

Append to part 2 of `site.css`:

```css
/* ---- Article ---- */

.doc p {
  text-wrap: pretty;
}

.doc :is(h1, h2, h3, h4, h5, h6) {
  letter-spacing: -0.01em;
  text-wrap: balance;
}

.doc > h1.page:first-child {
  margin: 2.25rem 0 1.25rem;
  font-size: 2.125rem;
  font-weight: 700;
  letter-spacing: -0.02em;
  line-height: 1.2;
}

.doc h2:not(.discrete) {
  margin: 0;
  padding: 0;
  border-bottom: 0;
  font-size: 1.5rem;
  line-height: 1.3;
}

#preamble + .sect1,
.doc .sect1 + .sect1 {
  margin-top: 3rem;
}

.doc h3 {
  font-size: 1.1875rem;
}

.doc .sect2 {
  margin-top: 2rem;
}

.doc h4 {
  font-size: 1rem;
}

.doc :is(h1, h2, h3, h4, h5, h6) .anchor {
  color: var(--color-text-muted);
  font-weight: 400;
}

.doc :is(h1, h2, h3, h4, h5, h6) .anchor::before {
  content: "#";
}

/* links in running text do not rely on color alone */
.doc a:not(.anchor, [class$="-btn"]) {
  text-decoration: underline;
  text-decoration-thickness: 1px;
  text-underline-offset: 0.2em;
  text-decoration-color: color-mix(in oklab, currentColor 35%, transparent);
}

.doc a:not(.anchor, [class$="-btn"]):hover {
  text-decoration-color: currentColor;
}

.doc :is(h1, h2, h3, h4, h5, h6) a:not(.anchor),
.doc .toc a {
  text-decoration: none;
}

.doc .paragraph.since p {
  padding: 0.15em 0.65em;
  border-radius: 999px;
}

.doc :is(.listingblock, .imageblock, .tableblock, .admonitionblock) > .title,
.doc table.tableblock caption {
  font-style: normal;
  font-weight: 500;
}

/* part 1 fills only p, thead and colist code; this covers code in dt, summary and block titles too */
.doc :not(pre) > code,
.doc .colist > table code {
  padding: 0.1em 0.35em;
  border: 1px solid var(--color-line);
  border-radius: var(--radius-sm);
  background: var(--code-background);
  color: var(--code-font-color);
  font-size: 0.86em;
}

.doc :is(h1, h2, h3, h4, h5, h6) code {
  padding: 0;
  border: 0;
  background: none;
  color: inherit;
}

@media (forced-colors: active) {
  .doc :not(pre) > code,
  .doc .colist > table code {
    border-color: GrayText;
  }
}

/* ---- Code blocks ---- */

.doc pre {
  line-height: 1.6;
}

.doc pre.highlight code {
  font-size: 0.78rem;
}

.doc .listingblock pre:not(.highlight),
.doc .literalblock pre,
.doc pre.highlight > code {
  padding: 0.9rem 1.125rem;
  border: 1px solid var(--color-line);
  border-radius: var(--radius-lg);
  box-shadow: none;
  color: var(--code-block-text);
}

/* most block titles are file names, so a titled block reads as an editor tab */
.doc .listingblock > .title {
  position: relative;
  z-index: 1;
  top: 1px;
  display: inline-block;
  margin: 0;
  padding: 0.4rem 0.8rem 0.35rem;
  border: 1px solid var(--color-line);
  border-bottom: 0;
  border-radius: var(--radius-lg) var(--radius-lg) 0 0;
  background: var(--code-block-background);
  color: var(--color-text);
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 500;
  letter-spacing: 0;
}

.doc .listingblock > .title + .content :is(pre.highlight > code, pre:not(.highlight)) {
  border-top-left-radius: 0;
}

.doc .source-toolbox {
  top: 0.45rem;
  right: 0.5rem;
  gap: 0.25rem;
  align-items: center;
  color: var(--color-text-muted);
  font: 400 0.7rem/1 var(--monospace-font-family);
}

.doc .source-toolbox .source-lang {
  letter-spacing: 0;
  text-transform: none;
}

.doc .source-toolbox > :not(:last-child)::after {
  content: none;
}

.doc .source-toolbox .copy-button {
  justify-content: center;
  width: 1.75rem;
  height: 1.75rem;
  border-radius: var(--radius-md);
  font-size: 1rem;
}

.doc .source-toolbox .copy-button::before {
  width: 0.9rem;
  height: 0.9rem;
}

.doc .source-toolbox .copy-button:hover {
  background: var(--color-surface-hover);
  color: var(--color-heading);
}

.doc .source-toolbox .copy-toast {
  position: absolute;
  top: 100%;
  margin-top: 0.375rem;
  background: var(--color-heading);
  color: var(--color-text-on-accent);
  font: 400 0.7rem/1 var(--body-font-family);
}

.doc .source-toolbox .copy-toast::after {
  border-left-color: var(--color-heading);
}

.doc .conum[data-value] {
  width: 1.35em;
  height: 1.35em;
  border: 0;
  border-radius: 50%;
  background: var(--color-heading);
  color: var(--color-text-on-accent);
  font: 600 0.68rem/1.35em var(--body-font-family);
  letter-spacing: 0;
  text-indent: 0;
  vertical-align: 0.1em;
}

.doc pre .conum[data-value] {
  font-size: 0.62rem;
}

.doc .colist {
  margin-top: 0.5rem;
}

.doc .colist > table > tbody > tr > :first-child {
  padding-top: 0.3em;
}

/* syntax colors: highlight.js 10.7.2 classes */
.doc .hljs {
  background: transparent;
  color: var(--code-block-text);
}

.doc :is(.hljs-keyword, .hljs-literal, .hljs-selector-tag, .hljs-section, .hljs-tag, .hljs-name) {
  color: var(--syntax-keyword);
  font-weight: 400;
}

.doc :is(.hljs-string, .hljs-regexp, .hljs-link) {
  color: var(--syntax-string);
}

.doc :is(.hljs-number, .hljs-symbol, .hljs-bullet) {
  color: var(--syntax-number);
}

.doc :is(.hljs-comment, .hljs-quote) {
  color: var(--syntax-comment);
  font-style: italic;
}

.doc .hljs-doctag {
  color: var(--syntax-comment);
  font-weight: 600;
}

.doc .hljs-meta {
  color: var(--syntax-annotation);
  font-weight: 400;
}

.doc .language-xml .hljs-meta {
  color: var(--syntax-keyword);
}

.doc :is(.hljs-title, .hljs-selector-class, .hljs-selector-id) {
  color: var(--syntax-function);
  font-weight: 400;
}

/* the class title selector gives every member of this list a higher specificity than .hljs-title above */
.doc :is(.hljs-class .hljs-title, .hljs-type, .hljs-params, .hljs-built_in, .hljs-builtin-name, .hljs-subst) {
  color: var(--code-block-text);
  font-weight: 400;
}

.doc :is(.hljs-variable, .hljs-template-variable) {
  color: var(--syntax-field);
}

.doc .hljs-attr {
  color: var(--syntax-attribute);
}

.doc :is(.language-json, .language-yaml) .hljs-attr {
  color: var(--syntax-field);
}

.doc .language-properties .hljs-attr,
.doc .hljs-attribute {
  color: var(--syntax-property-key);
}

.doc .hljs-addition {
  background: var(--syntax-added);
}

.doc .hljs-deletion {
  background: var(--syntax-removed);
}

.doc .hljs-strong {
  font-weight: 600;
}

.doc .hljs-emphasis {
  font-style: italic;
}

@media (forced-colors: active) {
  .doc .conum[data-value] {
    border: 1px solid CanvasText;
  }

  .doc .listingblock > .title {
    border-color: CanvasText;
  }
}

/* ---- Admonitions ---- */

.doc .admonitionblock.note {
  --adm-color: var(--note-color);
  --adm-background: var(--note-background);
  --adm-border: var(--note-border-color);
  --adm-accent: var(--note-accent);
  --adm-icon: url(../img/icons/note.svg);
}

.doc .admonitionblock.tip {
  --adm-color: var(--tip-color);
  --adm-background: var(--tip-background);
  --adm-border: var(--tip-border-color);
  --adm-accent: var(--tip-accent);
  --adm-icon: url(../img/icons/tip.svg);
}

.doc .admonitionblock.warning {
  --adm-color: var(--warning-color);
  --adm-background: var(--warning-background);
  --adm-border: var(--warning-border-color);
  --adm-accent: var(--warning-accent);
  --adm-icon: url(../img/icons/warning.svg);
}

.doc .admonitionblock.important {
  --adm-color: var(--important-color);
  --adm-background: var(--important-background);
  --adm-border: var(--important-border-color);
  --adm-accent: var(--important-accent);
  --adm-icon: url(../img/icons/important.svg);
}

.doc .admonitionblock.caution {
  --adm-color: var(--caution-color);
  --adm-background: var(--caution-background);
  --adm-border: var(--caution-border-color);
  --adm-accent: var(--caution-accent);
  --adm-icon: url(../img/icons/caution.svg);
}

/* the add-on role sits on top of NOTE ("admonitionblock note addon-component"), so it comes last */
.doc .admonitionblock.addon-component {
  --adm-color: var(--addon-color);
  --adm-background: var(--addon-background);
  --adm-border: var(--addon-border-color);
  --adm-accent: var(--addon-accent);
  --adm-icon: url(../img/icons/addon.svg);
}

.doc .admonitionblock {
  margin-top: 1.5rem;
}

.doc .admonitionblock > table,
.doc .admonitionblock > table > tbody,
.doc .admonitionblock > table > tbody > tr {
  display: block;
}

.doc .admonitionblock > table {
  padding: 0.75rem 1.125rem 0.875rem 1rem;
  border: 1px solid var(--adm-border);
  border-left: 4px solid var(--adm-accent);
  border-radius: var(--radius-lg);
  background: var(--adm-background);
}

.doc .admonitionblock td.icon {
  position: static;
  display: block;
  padding: 0 0 0.3rem;
  transform: none;
  font-size: 0.8rem;
}

/* i.fa matches the specificity of the upstream per-type pill rules (.doc .admonitionblock td.icon i.icon-note) */
.doc .admonitionblock td.icon i.fa {
  gap: 0.4rem;
  height: auto;
  padding: 0;
  border-radius: 0;
  background: none;
  color: var(--adm-color);
}

.doc .admonitionblock td.icon i.fa::before {
  content: "";
  width: 1.05rem;
  height: 1.05rem;
  background-color: currentColor;
  mask: var(--adm-icon) center / contain no-repeat;
}

.doc .admonitionblock td.icon i.fa::after {
  letter-spacing: 0;
  text-transform: none;
}

.doc .admonitionblock.addon-component td.icon i.fa::after {
  content: "Add-on component";
}

.doc .admonitionblock td.content {
  display: block;
  padding: 0;
  background: none;
  font-size: 0.9rem;
}

.doc .admonitionblock td.content p {
  font-size: inherit;
}

@media (forced-colors: active) {
  .doc .admonitionblock td.icon i.fa::before {
    forced-color-adjust: none;
    background-color: CanvasText;
  }
}
```

- [ ] **Step 4: Build and run the checks**

Run: `npx antora antora-playbook.yml && node tools/check-css.mjs && node tools/ui-audit.mjs`
Expected: every check passes, `contrast` included. Look at `bpm/bpmn/bpmn-events.html` (a titled XML block becomes a tab; the Note box has a cyan edge, an icon and the label "Note") and `flow-ui/vc/components/geoMap.html` (the add-on box with the Space edge and "Add-on component"). If an upstream rule still wins over one of these selectors, raise the selector's specificity in part 2 the way `i.fa` does; do not edit part 1.

- [ ] **Step 5: Commit**

```bash
git add content/supplemental/css/site.css content/supplemental/partials/head-styles.hbs tools/ui-audit.mjs
git commit -m "Restyle article typography, code blocks and admonitions"
```

---

### Task 10: Tables, other blocks and the end of the page

**Files:**
- Modify: `content/supplemental/css/site.css` (part 2: new sections "Tables and other blocks", "End of page")
- Modify (rewrite): `content/supplemental/css/feedback-form.css`
- Modify: `tools/ui-audit.mjs` (`STYLE_EXPECTATIONS`)

- [ ] **Step 1: Write the failing expectations**

Append to `STYLE_EXPECTATIONS`:

```js
    // Task 10: tables, blocks, end of page
    ['events', 'table.tableblock > thead > tr > th', 'background-color', '#f6f7f9'],
    ['events', 'table.tableblock > thead > tr > th', 'color', '#17124b'],
    ['manager', 'nav.pagination a', 'border-top-width', '1px'],
    ['manager', 'nav.pagination .next a::before', 'content', '"Next"'],
    ['manager', '.feedback-form', 'border-top-left-radius', '8px'],
    ['manager', '.feedback-form__btn', 'background-color', '#ffffff'],
    ['manager', 'footer.footer', 'background-color', '#ffffff'],
    ['manager', 'footer.footer', 'border-top-width', '1px'],
```

Run: `node tools/ui-audit.mjs --only styles`
Expected: FAIL on the new entries.

- [ ] **Step 2: Add the sections**

Append to part 2 of `site.css`:

```css
/* ---- Tables and other blocks ---- */

/* the line colors come from tokens; the authors' grid and frame options keep their effect */
.doc table.tableblock td,
.doc table.tableblock th {
  padding: 0.55rem 0.75rem;
}

.doc table.tableblock > thead > tr > th {
  background: var(--color-surface-subtle);
  color: var(--color-heading);
  font-weight: 600;
}

.doc table.grid-all > thead th,
.doc table.grid-rows > thead th {
  border-bottom-width: 1px;
  border-bottom-color: var(--color-line-strong);
}

.doc td.hdlist1 {
  color: var(--color-heading);
}

.doc .sidebarblock {
  border: 1px solid var(--color-line);
  border-radius: var(--radius-lg);
}

.doc .exampleblock > .content {
  border: 1px solid var(--color-line);
  border-radius: var(--radius-lg);
}

.doc details > summary {
  color: var(--color-heading);
  font-weight: 500;
}

.doc kbd {
  box-shadow: 0 1px 0 var(--color-line-strong);
  font-size: 0.72rem;
}

.doc .quoteblock,
.doc .verseblock {
  border-left-width: 3px;
}

@media (forced-colors: active) {
  .doc .sidebarblock,
  .doc .exampleblock > .content {
    border-color: CanvasText;
  }
}

/* ---- End of page ---- */

nav.pagination {
  gap: 1rem;
  margin: 3rem 0 0;
  padding: 0;
  border-top: 0;
}

nav.pagination :is(.prev, .next)::before {
  content: none;
}

nav.pagination .prev,
nav.pagination .next {
  padding: 0;
}

nav.pagination span {
  /* half the row minus half the gap: upstream flex: 50% lets a lone card grow to the full row */
  flex: 0 0 calc(50% - 0.5rem);
}

nav.pagination a {
  display: block;
  padding: 0.75rem 1rem 0.875rem;
  border: 1px solid var(--color-line);
  border-radius: var(--radius-lg);
  color: var(--color-heading);
  font-weight: 600;
  line-height: 1.35;
  text-decoration: none;
}

nav.pagination a:hover {
  border-color: var(--color-accent);
  background: var(--color-accent-tint);
  color: var(--color-accent);
  text-decoration: none;
}

nav.pagination .prev a::before,
nav.pagination .next a::before {
  position: static;
  display: block;
  width: auto;
  padding-bottom: 0.25rem;
  transform: none;
  color: var(--color-text-muted);
  font-size: 0.75rem;
  font-weight: 400;
  line-height: 1.3;
}

nav.pagination .prev a::before {
  content: "Previous";
}

nav.pagination .next a::before {
  content: "Next";
}

nav.pagination .next a::after {
  content: none;
}

footer.footer {
  padding: 1.25rem 2rem;
  border-top: 1px solid var(--color-line);
  font-size: 0.75rem;
}

.footer p {
  margin: 0.25rem 0;
}

@media (forced-colors: active) {
  nav.pagination a {
    border-color: LinkText;
  }
}
```

- [ ] **Step 3: Rewrite `feedback-form.css`**

```css
/* "Was this page helpful?" form at the end of each page. Behavior: js/feedback-form.js. */

.feedback-form {
    display: none;
    margin: 1.5rem 0 0;
    padding: 0.875rem 1.25rem;
    border: 1px solid var(--color-line);
    border-radius: var(--radius-lg);
}

.feedback-form_visible {
    display: block;
}

.feedback-form__vote {
    display: none;
    align-items: center;
}

.feedback-form__vote_visible {
    display: flex;
}

.feedback-form__icon {
    margin-right: 8px;
}

.feedback-form__btn {
    padding: 0.45rem 1rem;
    border: 1px solid var(--color-line-strong);
    border-radius: var(--radius-md);
    background: var(--color-surface);
    color: var(--color-text);
    font: inherit;
    font-size: 0.8rem;
    font-weight: 500;
    line-height: 16px;
    text-align: center;
    cursor: pointer;
    transition: width 0.1ms;
}

.feedback-form__btn:hover {
    border-color: var(--color-accent);
    color: var(--color-accent);
}

.feedback-form__btn_loading:before {
    content: '';
    display: inline-block;
    border: 1px solid;
    border-radius: 50%;
    border-bottom-color: transparent;
    width: 10px;
    height: 10px;
    margin-right: 10px;
    animation: 1s infinite ease-in-out rotateInfinite;
}

.feedback-form__btn_dark,
.feedback-form__btn_dark:hover {
    border-color: var(--color-accent);
    background: var(--color-accent);
    color: var(--color-text-on-accent);
}

.feedback-form__btn + .feedback-form__btn {
    margin-left: 8px;
}

.feedback-form__title {
    margin-right: 20px;
    color: var(--color-heading);
    font-size: 0.9rem;
    font-weight: 600;
    line-height: 26px;
}

.feedback-form__text {
    margin-top: 4px;
    font-size: 14px;
    font-weight: 400;
    line-height: 20px;
}

.feedback-form__input {
    width: 100%;
    max-width: 394px;
    padding: 12px 8px;
    border: 1px solid var(--color-control-border);
    border-radius: var(--radius-md);
    font: inherit;
}

.feedback-form__message-input {
    max-width: 788px;
}

.feedback-form__input-row {
    margin-top: 12px;
}

.feedback-form__label {
    color: var(--color-text);
    font-size: 14px;
    line-height: 20px;
}

.feedback-form__form {
    display: none;
}

.feedback-form__form_visible {
    display: block;
}

.feedback-form__success {
    display: none;
}

.feedback-form__success_visible {
    display: flex;
}

.feedback-form__error-message {
    display: none;
    align-items: center;
}

.feedback-form__error-message_visible {
    display: flex;
}

.feedback-form__error-message:before {
    content: 'X';
    display: inline-block;
    margin-right: 10px;
    padding: 10px;
    border: 2px solid;
    border-radius: 50%;
    color: var(--important-color);
    font-family: sans-serif;
    line-height: 0.7;
}

@keyframes rotateInfinite {
    from {
        transform: rotate(0deg);
    }

    to {
        transform: rotate(360deg);
    }
}

@media (prefers-reduced-motion: reduce) {
    .feedback-form__btn_loading:before {
        animation-duration: 3s;
    }
}

@media (forced-colors: active) {
    .feedback-form {
        border-color: CanvasText;
    }
}
```

- [ ] **Step 4: Build and run the checks**

Run: `npx antora antora-playbook.yml && node tools/check-css.mjs && node tools/ui-audit.mjs`
Expected: every check passes. On `intro.html` (only a "Next" link) the card stays on the right half.

- [ ] **Step 5: Commit**

```bash
git add content/supplemental/css/site.css content/supplemental/css/feedback-form.css tools/ui-audit.mjs
git commit -m "Restyle tables, blocks, pagination, feedback form and footer"
```

---

### Task 11: Documentation, CI and final verification

**Files:**
- Create: `.github/workflows/ui-check.yml`
- Modify: `AGENTS.md` (new section after "Images: size budget is enforced"), `CONTRIBUTING.md` (new section, placed where it fits the existing structure)

- [ ] **Step 1: Add the workflow**

```yaml
name: UI check

on:
  pull_request:
    paths:
      - 'content/supplemental/**'
      - 'tools/check-css.mjs'
      - 'tools/check-css.test.mjs'

jobs:
  check:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Install Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '24'

      - name: Test the stylesheet check
        run: node --test tools/check-css.test.mjs

      - name: Check the stylesheets
        run: node tools/check-css.mjs
```

- [ ] **Step 2: Document the UI in `AGENTS.md`**

Insert after the section "Images: size budget is enforced":

```markdown
## UI: styles, tokens and the pinned bundle

The site uses the Antora default UI bundle pinned in `ui/ui-bundle.zip`. `ui/README.md` says which revision it is and how to update it. The styles are ours and live in `content/supplemental/css/`:

- `tokens.css` holds every custom property in three tiers: palette, semantic roles, components. A dark theme changes values here, not in the components.
- `site.css` replaces the bundle stylesheet. Part 1 is the upstream `src/css` of the bundle's revision; change it only to port upstream changes. Part 2 holds the Jmix styles, one section per component, each with its forced colors rules.
- `search.css`, `dropdown-menu.css` and `feedback-form.css` style the search, the version menu and the feedback form.

Outside `tokens.css`, write colors only as `var(--…)`. `node tools/check-css.mjs` checks that and reports custom properties that are used but never declared; CI runs it on pull requests. After a UI change, build the site and run `node tools/ui-audit.mjs`. It checks keyboard focus, the skip link, accessible names, keyboard access to search results, contrast, the expected styles, fonts and forced colors mode.
```

- [ ] **Step 3: Document the UI in `CONTRIBUTING.md`**

Read `CONTRIBUTING.md` and add a section "Changing the UI" in the place that matches its structure:

```markdown
## Changing the UI

UI styles live in `content/supplemental/css/`. Use the custom properties from `tokens.css` for colors; `node tools/check-css.mjs` rejects color literals in the other files. Build the site and run `node tools/ui-audit.mjs` before you open a pull request. The default UI bundle is pinned; see `ui/README.md` before you replace it.
```

Run the `no-ai-slop` skill in detect mode on the two new sections and fix the findings.

- [ ] **Step 4: Run everything**

Run: `npx antora antora-playbook.yml && node --test tools/check-css.test.mjs && node tools/check-css.mjs && node tools/ui-audit.mjs`
Expected: all tests and checks pass.

- [ ] **Step 5: Review the pages at three widths**

```bash
python3 -m http.server 4500 --directory build/site >/dev/null 2>&1 &
SERVER=$!
for page in intro data-access/data-manager bpm/bpmn/bpmn-events flow-ui/vc/components/geoMap studio/studio-features; do
  for width in 1440 1280 390; do
    node tools/screenshot-2x.mjs --url "http://localhost:4500/jmix/$page.html" --selector body --out "build/ui-audit/review/$(echo $page | tr / -)-$width.png" --width $width --height 900 --scale 1 --wait 1500
  done
done
kill $SERVER
```

Open the PNG files and check each against the spec's visual specification: header, nav, toolbar, TOC, headings, code blocks, admonitions, tables, pagination, footer. Also open one guide from an external repository, a page with a sidebar block, a page with `details`, a page with a Kroki diagram and `404.html` in a browser. Fix what disagrees with the spec, then repeat Step 4.

- [ ] **Step 6: Save before and after screenshots**

Run: `node tools/ui-audit.mjs --snapshot build/ui-audit/after`
The "before" set is `build/ui-audit/bundle` from Task 3. Both stay out of git (`build/` is ignored) and are for the pull request description.

- [ ] **Step 7: Commit**

```bash
git add .github/workflows/ui-check.yml AGENTS.md CONTRIBUTING.md
git commit -m "Document the UI stylesheets and check them in CI"
```

The spec and this plan stay on the branch for review; delete `docs/superpowers` before the branch is merged.
