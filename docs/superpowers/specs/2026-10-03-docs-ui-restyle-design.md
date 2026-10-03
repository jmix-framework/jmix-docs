# Docs UI restyle: design

Branch: `feature/ui-restyle`, created from `release_3_1`. This document is temporary: delete it before the branch is merged.

## Goal

Give the documentation site clear boundaries between page regions, a visible focus indicator on every control, support for forced colors mode, and a layer of CSS custom properties that the dark theme (a separate, later PR) can build on by changing values only. The site stays a documentation site: no marketing layout, no decoration that competes with the content.

## Findings that drive the design

These come from the live site and the UI bundle as of 2026-10-02.

- The UI bundle is not pinned. Both playbooks use the `antora-ui-default` `master` snapshot, so every CI build takes whatever upstream has. The bundle of a November 2023 local build and the current one (built 2026-07-16) differ in explore panel spacing, breadcrumb line height, lead paragraph size, checklist indent and image alignment. Nobody reviewed those changes.
- The published `site.css` has no custom properties. The default UI sources use them (`src/css/vars.css`), but the production build resolves them (`postcssVar({ preserve: preview })`).
- Styling is spread over the upstream `site.css`, `overrides.css`, `search.css`, `feedback-form.css`, `dropdown-menu.css` and an inline `<style>` in `head-styles.hbs`, with hex colors throughout.
- Dead assets: `docsearch.min.css` (Algolia, unused since search moved to Lunr), the highlight.js 10.7.2 `default.min.css` theme, the copy icon swapped at runtime for a black PNG from icons8.com, and banner CSS without a banner.
- `search.css` loads twice: through a `<link>` in `head-meta.hbs`, and again from `search-ui.js` because of its `data-stylesheet` attribute. The second copy is appended last, so it overrides any later rule with the same specificity.
- Focus: `outline: none` on the header buttons (inherited from `.header-link`, 2021), the version toggle (copied from Antora's `.version-menu-toggle`) and the live demo, Vaadin, MDN and ECharts buttons. Upstream adds it to `summary`, the nav toggles, TOC links, the copy button, the search input and the burger. The copy button is `visibility: hidden` until hover, so it cannot be reached by keyboard. There is no skip link; the article is more than 50 tab stops away.
- Search results cannot be reached by keyboard: `onfocusout="this.value=''"` on the search input clears the query on Tab and the results disappear.
- The nav toggles (the expand/collapse buttons in the nav tree) have no accessible name, and neither do the home link and the toolbar's nav toggle (the button that opens the navigation below 1024px). The explore panel toggle is a clickable `div`. "Expand all" at the top of the nav menu is `visibility: hidden` until the menu is hovered, so the keyboard cannot reach it.
- Contrast: white on the TIP label 2.8:1, WARNING 2.9:1, NOTE 4.0:1; "Edit this Page" 3.1:1. Links differ from body text by color only, at 1.2:1.
- Forced colors: the header, nav, toolbar, code blocks and admonitions lose every boundary because they rely on background colors. The GitHub and Jmix header icons vanish in light mode (white SVGs set through `content: url()`). The AI Assistant icon is two-tone and loses its letters and lines.
- Nav toggles are 21×21 px. Smooth scrolling ignores `prefers-reduced-motion`.
- Visual: the sidebar edge is invisible (`#fafafa` on white, no border), the search field is a bare input, headings are thin violet, `h4` equals body size, code blocks barely separate from the page and show the language and copy button only on hover, admonitions differ by color only, the TOC is 13.5 px gray and breaks long identifiers mid-word, the footer is the stock Antora text.
- Code line lengths: of 3053 code blocks, 25.6% contain a line longer than 80 characters and 14.2% longer than 90. Narrowing the content column would make a quarter of the examples scroll horizontally, so the column keeps its width.

## Decisions

1. Own CSS over a pinned bundle. Rejected: forking the default UI into a separate repository (second repository, gulp toolchain, a bundle release for every style change) and an override layer over upstream `site.css` (hundreds of re-declared color selectors that break on any upstream change).
2. The dark theme is the next PR. This PR designs the tokens for two palettes and declares `color-scheme: light`.
3. Visual direction: mockup C ("Brand": white header, logo colors as admonition hues) with the headings of mockup B (Roboto, not Montserrat) and prose spanning the full column, as today.
4. Antora's explore panel (component and version switcher at the bottom of the nav) stays and is restyled.
5. The footer keeps the stock Antora text, which is the MPL-2.0 notice. It is restyled only.
6. Work happens on a feature branch from `release_3_1`.

## Architecture

### UI bundle

- Commit the current default UI bundle as `ui/ui-bundle.zip`. It was built on 2026-07-16 by the `bundle-stable` job from `antora-ui-default` `master` at `0e38223adfd81eb74d4b1779e158f8ad05ff8923`; the `src/partials`, `src/layouts` and `src/helpers` of that revision are byte-identical to the bundle's. Both playbooks point `ui.bundle.url` at `./ui/ui-bundle.zip` and drop `snapshot: true`.
- `ui/README.md` records the upstream revision the bundle and the vendored CSS come from, and how to update: replace the zip, diff the upstream `src/css` between the old and new revision, port the changes into our files.
- The bundle still supplies the layouts, the partials we do not override, `site.js`, the vendor scripts and images. Its `css/site.css` is replaced. Its Roboto and Roboto Mono files are still published to `_/font/` but no longer referenced; Antora cannot leave bundle files out.

### Stylesheets

`head-styles.hbs` loads, in this order, with plain `<link>` elements and no `@import`:

1. `css/tokens.css`: every custom property.
2. `css/site.css`: replaces the bundle's file at the same path, in two parts. Part 1 is the upstream `src/css` at the pinned revision, concatenated in upstream order with a section comment per upstream file and custom properties kept. It changes only where needed: font files, color literals replaced with tokens, the `outline: none` resets removed. Part 2 holds the Jmix styles by component, each with its forced colors rules, and overrides part 1 where the design differs. `site.css` replaces `overrides.css` and the banner styles in `head-styles.hbs`.
3. `css/search.css`, `css/feedback-form.css`, `css/dropdown-menu.css`: kept as separate files, refactored to tokens.

Removed: `overrides.css`, the `docsearch.min.css` link, the highlight.js theme link, the inline `<style>`. `head-meta.hbs` keeps only the favicons. The highlight.js script from the CDN stays; our theme replaces its stylesheet.

Files derived from the default UI keep the MPL-2.0 header. `content/supplemental/partials/LICENSE` already contains the license text.

Rule: outside `tokens.css`, a color is written only as `var(--…)`. System colors (`CanvasText`, `Highlight` and so on) inside forced colors blocks, `transparent`, `currentColor` and `inherit` are allowed. `tools/check-css.mjs` enforces the rule and also reports custom properties that are used without a fallback but never declared. `.github/workflows/ui-check.yml` runs it, with its unit tests, on changes under `content/supplemental/**`.

### Tokens

Three tiers. A dark theme redefines tier 2 and the tier 3 values that do not reference tier 2; components are not touched.

Tier 1, palette. Brand colors use the brand book names and values. Derived steps are marked.

| Token | Value | Source |
|---|---|---|
| `--jmix-space` | `#17124B` | brand book |
| `--jmix-fiesta` | `#FC1264` | brand book |
| `--jmix-sun` | `#FDB42B` | brand book |
| `--jmix-sky` | `#25CDE3` | brand book |
| `--jmix-grace` | `#22D685` | brand book |
| `--jmix-violet-800` | `#261D7D` | derived |
| `--jmix-violet-700` | `#342A98` | current docs headings and links |
| `--jmix-violet-500` | `#7B6DFF` | jmix.io links |
| `--jmix-violet-200` | `#D3CDF8` | derived |
| `--jmix-violet-50` | `#F0EEFF` | derived |
| `--gray-900` | `#2A2C33` | |
| `--gray-700` | `#3A3D46` | |
| `--gray-600` | `#5C606B` | |
| `--gray-500` | `#8A8F99` | |
| `--gray-300` | `#CFD3DA` | |
| `--gray-200` | `#E4E6EB` | |
| `--gray-100` | `#EFF1F4` | |
| `--gray-50` | `#F6F7F9` | |

Tier 2, semantic.

| Token | Light value | Use |
|---|---|---|
| `--color-text` | `--gray-900` | body text (13.9:1 on white) |
| `--color-heading` | `--jmix-space` | headings, header text, strong UI text |
| `--color-text-nav` | `--gray-700` | nav items |
| `--color-text-muted` | `--gray-600` | breadcrumbs, TOC, captions, labels (6.3:1 on white) |
| `--color-text-on-accent` | `#FFFFFF` | text and icons on accent and heading fills: current version pill, AI Assistant button, "Copied!" toast, callout numbers, submit button |
| `--color-link` | `--jmix-violet-700` | links |
| `--color-link-hover` | `--jmix-violet-800` | hovered links |
| `--color-accent` | `--jmix-violet-700` | active nav item, active TOC item, current version, primary buttons |
| `--color-accent-tint` | `--jmix-violet-50` | active backgrounds, highlights |
| `--color-accent-line` | `--jmix-violet-200` | hover borders, selection |
| `--color-focus` | `--jmix-violet-700` | focus ring |
| `--color-surface` | `#FFFFFF` | page, header, nav, toolbar |
| `--color-surface-subtle` | `--gray-50` | table headers, inline code, inputs |
| `--color-surface-hover` | `--gray-100` | hover backgrounds |
| `--color-line` | `--gray-200` | hairlines and borders |
| `--color-line-strong` | `--gray-300` | button borders, table header rule, `kbd` |
| `--color-control-border` | `--gray-500` | borders of text inputs: the search field and the feedback form (3.2:1 on white, 3.0:1 on `--gray-50`) |
| `--color-overlay` | Space at 30% | nav overlay while the explore panel is open |
| `--shadow-menu` | `0 10px 28px` Space 14%, `0 2px 6px` Space 8% | menus and search results |

Tier 3, component. Upstream names from `vars.css` stay where they exist (`--navbar-background`, `--nav-background`, `--toc-border-color`, `--code-background` and so on) and reference tier 2. Component-specific values:

| Group | Tokens and light values |
|---|---|
| Header | `--header-background`: surface; `--header-text`: heading; `--header-logo-center`: `--jmix-space` (the logo's center diamond) |
| Code | `--code-block-background`: `#F7F8FA`; `--code-block-text`: `#080808`; `--inline-code-text`: `#1F2128`. Upstream's `--code-background` keeps its meaning, the inline code background. |
| Syntax | keyword `#0033B3`, string `#067D17`, number `#1750EB`, comment `#6F6F6F` italic, annotation `#7A6A0A`, method declaration `#00627A`, field and variable `#871094`, XML attribute `#174AD4`, properties key `#083080`, JSON and YAML key `#871094`, diff added background `#E6F4EA`, diff removed background `#FCE8E6` (an IntelliJ IDEA Light-like palette; comment and annotation are darkened from IntelliJ's `#8C8C8C` and `#9E880D`, which give 3.2:1 and 3.3:1 on the code background, to 4.7:1 and 5.1:1) |
| Admonitions | per type `--<type>-color` (label and icon), `--<type>-background`, `--<type>-border-color`, `--<type>-accent` (left edge); values in the admonitions section |
| Banner | `--banner-background`: `--jmix-violet-700`; `--banner-text`: white (10.9:1; the current `#8882FF` gives 3.1:1) |

Other tokens: the fonts keep the upstream names, `--body-font-family: "Roboto", system-ui, sans-serif` and `--monospace-font-family: "JetBrains Mono", ui-monospace, monospace`; radii `--radius-lg: 8px`, `--radius-md: 6px`, `--radius-sm: 4px`. Upstream dimension tokens stay, except `--toc-width--widescreen` (14rem, see below) and `--toolbar-height` (2.75rem); the explore panel's context row, the sticky TOC offset and the nav panel heights follow `--toolbar-height`.

### Templates and scripts

| File | Change |
|---|---|
| `antora-playbook.yml`, `antora-playbook.ci.yml` | `ui.bundle.url: ./ui/ui-bundle.zip`, no `snapshot` |
| `partials/head-styles.hbs` | rewritten: the five stylesheet links only |
| `partials/head-meta.hbs` | favicons only |
| `partials/header-content.hbs` | skip link before `<header>`; header icons as inline SVG with `fill="currentColor"`; the logo's center path gets a class so CSS can color it; the search input is wrapped in `<span class="search-field">` with a `<kbd class="search-kbd">/</kbd>` hint, gets `aria-label="Search docs"`, loses the inline style and `onfocusout`; the version toggle gets `aria-expanded` and `aria-controls` pointing at the menu, which gets an `id`; the burger gets `aria-label="Toggle the menu"`, `aria-controls="topbar-nav"` and `aria-expanded` |
| `partials/main.hbs` (new override) | `<main class="article" id="main-content" tabindex="-1">` |
| `partials/nav-tree.hbs` (new override) | `.nav-item-toggle` gets `aria-label` with the item title (`detag`) and `aria-expanded` |
| `partials/nav-explore.hbs` | the `.context` element becomes a `<button class="context" aria-expanded="false">`; `site.js` binds by class, so its handler keeps working |
| `partials/toolbar.hbs` (new override) | the home link gets `aria-label="Home"` |
| `partials/nav-toggle.hbs` (new override) | the toolbar's nav toggle gets `type="button"`, `aria-label="Toggle the navigation"` and `aria-expanded` |
| `partials/footer-scripts.hbs` | drop the icons8 swap and `data-stylesheet`; load `js/a11y.js` as a plain script right after `site.js`, so the copy buttons exist when it runs; keep the `/` shortcut |
| `js/dropdown-menu.js` | sync `aria-expanded`; close on Escape (focus returns to the toggle) and on outside click |
| `js/a11y.js` (new) | keep `aria-expanded` of the nav toggles, the explore button, the burger and the toolbar's nav toggle in sync with the `is-active` classes that `site.js` sets, at load and afterwards through a `MutationObserver` (`site.js` changes them from several places, including "expand all"); set `aria-label="Copy to clipboard"` on copy buttons and hide their toast from assistive technology; move focus to `#main-content` when the skip link is used (`site.js` intercepts in-page links to scroll them); search keyboard behavior (below) |

Search keyboard behavior. How `search-ui.js` works: it appends `.search-result-dropdown-menu` to the input's parent, which becomes the `.search-field` wrapper; it listens to `keydown` on the input with a 100 ms debounce, where Escape clears the query and the results and any other key runs the search again and re-renders the results; a click anywhere on the page clears the results. The design:

- With `onfocusout` gone, Tab moves from the input into the result links. `a11y.js` stops Tab and Shift+Tab `keydown` events on the input from reaching `search-ui.js` (a capturing listener on `document`). Otherwise the search runs again 100 ms later, replaces the link that just got focus, and focus falls back to the page.
- Escape in the input already works upstream. Escape in the results: `a11y.js` moves focus back to the input and dispatches a synthetic `keydown` with `key: 'Escape'` on it, so `search-ui.js` clears the query and the results itself.
- Focus leaving the search area (input and results) dispatches the same synthetic Escape, which clears the query and the results, as `onfocusout` did for the input alone.

### Fonts

Self-hosted in `content/supplemental/font/` from Fontsource 5.3.0, OFL-1.1, license texts next to the files. Subsets `latin`, `latin-ext` and `cyrillic`, each limited by `unicode-range` so only the needed ones load.

- Roboto variable, normal (latin 43 KB; one file per subset covers weights 100 to 900, the design uses 400 to 700), plus Roboto 400 italic static (latin 24 KB). Replaces the bundle's four static faces and gives the heading weights without synthesized bold.
- JetBrains Mono 400 normal and 400 italic, static (latin 21 KB and 22 KB), for code. Replaces Roboto Mono. All monospace text, including the `/` hint and the language label, is set at weight 400; there is no 500 face.
- First visit costs about 40–60 KB more than today; after that the fonts come from cache.

### Icons

- Mask icons as tier 3 tokens in `tokens.css`, `--icon-chevron`, `--icon-home`, `--icon-edit`, `--icon-search`, `--icon-copy`, `--icon-note`, `--icon-tip`, `--icon-warning`, `--icon-important`, `--icon-caution`, `--icon-addon`, and `--icon-unfold` and `--icon-fold` for "expand all", each a data-URI SVG. CSS draws them with `mask: var(--icon-…)` and `background-color: currentColor` (or a token). They are not separate files because Chromium blocks mask images loaded from files when the site is opened from `file://`, which is how authors preview a build.
- Header icons inline in `header-content.hbs`. The AI Assistant icon is redrawn as a single-color icon (shapes with cut-outs instead of navy strokes on white), so it works on the navy button, on a light background and in forced colors. `git-icon.svg`, `jmix-icon.svg` and `jmix-ai-assistant-icon.svg` are removed once inlined, together with the unused `warning-icon.svg`, the duplicate `img/img/feedback-form__thumb-up.svg` and the dead Slack icon rule in `search.css`.

## Accessibility

Focus:

- Every `outline: none` is removed, upstream and ours.
- One rule: `:focus-visible { outline: 2px solid var(--color-focus); outline-offset: 2px }`. The header is white, so one color works on the whole page.
- `scroll-margin: 6px` on `:focus-visible` and `scroll-padding-top` on `html` (header and toolbar height plus 1rem) keep a focused element and its ring in view, below the fixed header and the sticky toolbar.
- A skip link ("Skip to content") is the first tab stop. It is visually hidden until focused, then shown fixed at the top left, and targets `#main-content`. `#main-content` is a region focused only by the skip link, not a control, so `#main-content:focus { outline: none }` is the one allowed outline reset.

Keyboard and names:

- The copy button is always visible, in `--color-text-muted` (5.9:1 on the code background, no reduced opacity), focusable and named "Copy to clipboard".
- Nav toggles have a name and `aria-expanded`. So do the burger and the toolbar's nav toggle. The home link is named "Home".
- "Expand all" stays in the tab order: it is hidden with `opacity`, not `visibility`, until the menu is hovered or the button has focus, and always shown on touch screens.
- The header version menu exposes `aria-expanded`, closes on Escape and on outside click.
- The explore panel toggle is a button with `aria-expanded`.
- Search results are reachable by Tab, as described above.

Sizes and contrast:

- Targets: nav toggles and "expand all" 24×24 px; header icon buttons 2.25rem, the copy button and the home link 1.75rem (40 px and 31 px on desktop). Nothing interactive is smaller than 24×24 px.
- Text at least 4.5:1 against its background, syntax colors included. The focus ring at least 3:1. The borders of text inputs at least 3:1 (`--color-control-border`), because the border is what shows the field. Hairlines between regions and blocks and the admonition left edges are decorative: regions differ by position, and an admonition's label and icon (5.6–7.0:1) show its type. The audit checks the pairs listed in Verification.
- Links in running text are underlined: 1px, offset 0.2em, `currentColor` at 35%, full on hover. No underline in headings, the TOC, the nav, buttons and button-like links.

Forced colors, written next to each component in a `@media (forced-colors: active)` block:

- Borders in `CanvasText` for the header bottom, nav right, toolbar bottom, code blocks and their title tabs, admonitions, sidebar and example blocks, the feedback form and the search field. `GrayText` border for inline code.
- Active states lose their tinted backgrounds, so the current nav item, the active TOC item and the current version get a `Highlight` border.
- The header icons are inline SVG with `fill: currentColor`; because the AI Assistant icon uses an SVG mask, the SVGs set `forced-color-adjust: none` and, in forced colors, `color: LinkText` (Chromium would otherwise let them inherit the author color). The logo center gets `fill: CanvasText`. Mask icons get `forced-color-adjust: none` and a system color (`CanvasText`, `LinkText`, `ButtonText` or `GrayText`), because the icon carries meaning.
- Callout numbers in code get a border. Syntax highlighting is left to the browser: in forced colors it collapses to the text color, which is fine because color carries no meaning there.

Motion and scheme:

- `scroll-behavior: smooth` and the chevron rotation transitions apply only under `prefers-reduced-motion: no-preference`.
- `:root { color-scheme: light }`. The dark theme PR changes it to `light dark`.

## Visual specification

Values are for desktop (root font size 18 px from 1024 px up, 17 px below, as upstream).

### Header

- 3.5rem high, `--header-background`, bottom border 1px `--color-line`.
- Logo: the inline SVG; the center diamond uses `--header-logo-center`. Wordmark "Jmix Documentation": Roboto 600, `--header-text`. The divider after it: 1px `--color-line`.
- Version toggle: a pill with `--color-accent-tint` background, `--color-accent` text, 600 0.8rem, padding 0.35rem 0.55rem 0.35rem 0.65rem, radius 6px, chevron 0.6rem; hover background `--color-accent-line`. Menu: min width 10rem, margin top 0.5rem, padding 0.375rem, border 1px `--color-line`, radius 8px, `--shadow-menu`; items padding 0.45rem 0.625rem, radius 5px, 0.8rem, hover `--color-surface-hover`.
- Search: input 18rem wide (100% below 1024px), 2.25rem high, padding 0 2.1rem, border 1px `--color-control-border`, radius 8px, background `--color-surface-subtle`, text `--color-text` 0.8rem, placeholder `--color-text-muted`; hover background `--color-surface-hover`; focus background `--color-surface` and border `--color-accent` plus the focus ring. Magnifier icon 0.9rem at 0.65rem from the left, `--color-text-muted`. The `/` hint at 0.55rem from the right: border 1px `--color-line-strong`, radius 4px, JetBrains Mono 400 0.68rem, `--color-text-muted`; hidden while the input has focus.
- Search results: radius 8px, `--shadow-menu`; the list has a 1px `--color-line` border and padding 0.25rem 0.75rem 0.75rem; component header `--color-text-muted` 500 0.75rem; document titles `--color-text-muted` with a right border `--color-line`; snippets at regular weight (today they are bold); result links radius 6px with `--color-surface-hover` on hover; matches with `--color-accent-tint` background, `--color-accent` text, 600.
- Icon buttons: 2.25rem square, radius 8px, `--header-text`, hover `--color-surface-hover`. AI Assistant: 2.75rem wide, background `--color-heading`, icon `--color-text-on-accent`; hover background `--color-accent`.
- Below 1024px: burger lines `--header-text`, the menu panel `--color-surface`, the search text 1rem, because iOS Safari zooms into fields with text below 16px, the `/` hint hidden (no keyboard shortcut there), and the search results allowed to overflow the menu panel instead of being clipped by it.

### Navigation

- Background `--color-surface`, right border 1px `--color-line` from 1024px up.
- Menu padding 1rem 0.75rem 3rem 1.125rem, line height 1.4. Component title (`h3.title`): 0.8rem 600 `--color-heading`.
- Items 1px apart; nested lists indented 0.875rem (0.25rem at the top level).
- Links and plain text items (`.nav-link`, `.nav-text`): block, padding 0.3rem 0.5rem, radius 6px, `--color-text-nav`; link hover `--color-surface-hover` and `--color-heading`; current page `--color-accent-tint` background, `--color-accent` text, 600.
- Toggles: 24×24 at the item's left (margin 3px 0 0 -24px), radius 5px, a chevron mask of 14px in `--color-text-muted` that rotates 90° when the item is open; hover `--color-surface-hover`.
- "Expand all": 24×24 at the top right of the menu, beside the component title, radius 5px, a 16px unfold mask in `--color-text-muted` (fold while everything is expanded); hover `--color-surface-hover`.
- Explore panel: the context row is 2.75rem high, padding 0 0.75rem 0 1rem, top border 1px `--color-line`, `--color-surface`, `--color-text-muted` 0.8rem, the title in `--color-heading` 500, a chevron that points up when closed and down when open, hover `--color-surface-hover` and `--color-heading`. While open, the menu above is covered by `--color-overlay`. The component list has `--color-surface-subtle` background and a top border; version pills: padding 0.3em 0.7em, border 1px `--color-line-strong`, radius 6px, `--color-surface`, `--color-text` 0.78rem 500; hover border and text `--color-accent`; current version filled `--color-accent` with `--color-text-on-accent` text.

### Toolbar

- 2.75rem high (`--toolbar-height`), `--color-surface`, bottom border 1px `--color-line`, `--color-text-muted` 0.8rem.
- Home link: a 1.75rem target with radius 6px and a 1rem mask icon in `currentColor`, 1rem from the left; hover `--color-surface-hover` and `--color-heading`.
- Breadcrumbs: separators `--color-line-strong`, the current page `--color-text`, hover `--color-heading`.
- "Edit this Page": a 0.875rem pencil icon before the text, `--color-text-muted`, hover `--color-heading`, 1rem from the right.

### Table of contents

- 14rem wide from 1216px up (upstream 12rem); 9rem between 1024px and 1215px as upstream.
- Title 0.78rem 600 `--color-heading`. Items 0.78rem, line height 1.35, padding 0.3rem 0 0.3rem 0.75rem, left border 1px `--color-line`, `--color-text-muted`; level 2 and 3 indents 1.5rem and 2.25rem; `overflow-wrap: break-word`. Hover `--color-heading`. Active item `--color-accent`, 500, 2px `--color-accent` left border.

### Article typography

- Body: Roboto 0.94444rem (17 px), line height 1.65, `--color-text`. The content column keeps its upstream width (46rem from 1024px up). Paragraphs use `text-wrap: pretty`.
- Headings: `--color-heading`, Roboto 650, letter spacing −0.01em, `text-wrap: balance`. `h1` 2.125rem, line height 1.2, 700, letter spacing −0.02em, margin 2.25rem 0 1.25rem. `h2` 1.5rem, line height 1.3, without the upstream bottom border and negative margins. `h3` 1.1875rem. `h4` 1rem.
- Section spacing: 3rem between top-level sections, 2rem before second-level sections.
- Heading anchors: `#` in `--color-text-muted`, shown on hover, replacing `§`.
- Links: `--color-link`, hover `--color-link-hover`, underline as in the accessibility section.
- "Since" badge (`.paragraph.since`): pill, radius 999px, padding 0.15em 0.65em, `--color-accent-tint` background, `--color-accent` text, 500 0.75rem.
- Block titles (listings, images, tables, admonitions) and table captions: `--color-text-muted`, 500, not italic.
- Inline code: JetBrains Mono 0.86em, padding 0.1em 0.35em, border 1px `--color-line`, radius 4px, `--color-surface-subtle` background, `--inline-code-text`. Plain inside headings.

### Code blocks

- JetBrains Mono, 0.78rem (14 px), line height 1.6, ligatures off (`font-variant-ligatures: none`): IntelliJ IDEA keeps JetBrains Mono's ligatures off by default, and readers copy this code.
- Block: padding 0.9rem 1.125rem, border 1px `--color-line`, radius 8px, `--code-block-background`, `--code-block-text`, no inset shadow.
- Titled blocks: the title becomes a tab attached to the block, as most titles are file names. Inline block, padding 0.4rem 0.8rem 0.35rem, border 1px `--color-line` without the bottom side, radius 8px 8px 0 0, `--code-block-background`, `--color-text` 0.75rem 500, overlapping the block's top border by 1px; the block's top-left radius becomes 0.
- Toolbox, top right (0.45rem, 0.5rem): the language in lowercase JetBrains Mono 400 0.7rem `--color-text-muted`, no `|` separator; the copy button 1.75rem square with radius 6px, a 0.9rem mask icon in `--color-text-muted`, hover `--color-surface-hover` and `--color-heading`; the "Copied!" toast under the button with `--color-heading` background and `--color-text-on-accent` 0.7rem text.
- Callout numbers: 1.35em circles filled `--color-heading` with `--color-text-on-accent` 600 digits, 0.68rem in callout lists and 0.62rem inside code.
- Syntax colors: the tier 3 syntax tokens. The highlight.js classes are mapped as follows: `keyword`, `literal`, `tag`, `name`, `section`, `selector-tag` take keyword; `string`, `regexp`, `link` take string; `number`, `symbol`, `bullet` take number; `comment`, `quote` take comment in italic; `doctag` takes comment at 600; `meta` takes annotation, and keyword in XML; `title` and `selector-class` take method declaration; the class title, `type`, `params` and `built_in` take the code text; `variable` and `template-variable` take field; `attr` takes XML attribute, field in JSON and YAML, properties key in properties files; `attribute` takes properties key; `addition` and `deletion` take the diff backgrounds. Nothing is bold except `doctag` and `strong`.

### Admonitions

- Layout: the table becomes a block box with padding 0.75rem 1.125rem 0.875rem 1rem, border 1px `--<type>-border-color`, left border 4px `--<type>-accent`, radius 8px, `--<type>-background`.
- Label: its own line above the content, an icon of 1.05rem and the type name in sentence case from the `title` attribute ("Note", "Tip", "Important", "Warning", "Caution"; "Add-on component" for the add-on role), 650 0.8rem, `--<type>-color`.
- Content 0.9rem.
- The add-on role is applied on top of NOTE (`admonitionblock note addon-component`), so its rule comes after the type rules and its values win. Its label changes from "Add-on Component" to "Add-on component", in sentence case like the others.

| Type | Label and icon | Background | Border | Left edge |
|---|---|---|---|---|
| Note | `#0A6874` | `#EFFBFD` | `#C9EFF5` | Sky |
| Tip | `#0D7348` | `#EFFCF5` | `#C6F0DC` | Grace |
| Warning | `#8A5300` | `#FFF8E6` | `#FBE3AE` | Sun |
| Important | `#B4104A` | `#FFF1F6` | `#FBCADB` | Fiesta |
| Caution | `#4B3FB8` | `#F4F2FF` | `#DAD5FB` | violet 500 |
| Add-on component | Space | `#F4F2FF` | `#DAD5FB` | Space |

### Tables

- Borders `--color-line`. The authors' `grid` and `frame` options keep their effect (156 tables use `grid=none`, 135 `frame=none`, 93 `grid=rows`).
- Header cells: `--color-surface-subtle` background, `--color-heading` 600, bottom border 1px `--color-line-strong` (upstream 2.5px).
- Cell padding 0.55rem 0.75rem. Striped rows `--color-surface-subtle`. Horizontal definition list labels (`td.hdlist1`) `--color-heading`.

### Other blocks

- Sidebar block: border 1px `--color-line`, radius 8px, `--color-surface-subtle`.
- Example block: border 1px `--color-line` (upstream 0.25rem dark gray), radius 8px.
- `details` summary: `--color-heading` 500.
- `kbd`: border `--color-line-strong`, `--color-surface`, a 1px `--color-line-strong` bottom shadow, 0.72rem.
- Quote and verse blocks: left border 3px `--color-line-strong`, text `--color-text-muted`.
- Images: unchanged.
- Live demo, Vaadin, MDN and ECharts buttons: their colors move into tier 3 tokens unchanged; radius 6px; the standard focus ring.
- Banner (`.jmix-banner`, used from time to time for events): styles move to `site.css`, colors from the banner tokens.

### End of page

- Pagination: no top border, gap 1rem, margin top 3rem. Each link is a card: block, padding 0.75rem 1rem 0.875rem, border 1px `--color-line`, radius 8px, title `--color-heading` 600, line height 1.35, with "Previous" or "Next" above it in `--color-text-muted` 0.75rem. Hover: border `--color-accent`, background `--color-accent-tint`, text `--color-accent`. A single card keeps its half of the row (previous on the left, next on the right).
- Feedback form: margin top 1.5rem, padding 0.875rem 1.25rem, border 1px `--color-line`, radius 8px; title `--color-heading` 0.9rem 600; buttons outlined (border `--color-line-strong`, radius 6px, `--color-surface`, `--color-text` 0.8rem 500; hover border and text `--color-accent`); the submit button filled `--color-accent` with `--color-text-on-accent` text; inputs with border `--color-control-border`, radius 6px.
- Footer: the stock Antora text unchanged; padding 1.25rem 2rem, top border 1px `--color-line`, `--color-surface`, `--color-text-muted` 0.75rem.

### Mobile and print

- Below 1024px the layout stays upstream's: the header burger opens a white panel with a full-width search, the nav is a drawer, the TOC is embedded at the top of the article with the TOC styles.
- Print: upstream print rules stay; tokens resolve to their light values.

## Out of scope

- The dark theme: dark values, `color-scheme: light dark`, the theme switcher, light screenshots and Kroki diagrams on a dark background, a dark syntax palette.
- Search behavior beyond the keyboard fix.
- Content and page structure.
- The v1 and v2 documentation sites.
- A redesign of the live demo, Vaadin, MDN and ECharts buttons.
- Registering more highlight.js languages. Groovy (141 blocks), Gradle and Dockerfile stay plain text, as today.

## Verification

- Build the site with `npx antora antora-playbook.yml`. No Java code changes, so `compileAll` is not needed.
- Review at 1440, 1280 and 390 px wide: `intro`, `data-access/data-manager`, `bpm/bpmn/bpmn-events`, `flow-ui/vc/components/geoMap`, `studio/studio-features` (a layout table), a guide from an external repository, a page with a sidebar block, a page with `details`, a page with a Kroki diagram, the 404 page.
- The first steps change nothing visible: pin the bundle, vendor `site.css`, then compare screenshots of the old and the new stylesheet before any restyling.
- `tools/ui-audit.mjs` (Playwright through `tools/lib/playwright.mjs`, which `tools/screenshot-2x.mjs` also uses), reusable by the dark theme PR:
  - a Tab walk that fails on any focused element without a 2px outline, or with an outline below 3:1 against its background;
  - the skip link as the first Tab stop, moving focus to `#main-content`;
  - accessible names and `aria-expanded` of the nav toggles, the explore toggle, the burger, the toolbar's nav toggle, the version toggle, the copy button, the search field and the home link;
  - the search keyboard flow: Tab reaches the results, Escape in the results clears and returns to the input, leaving the area clears;
  - text contrast against 4.5:1 for body text, nav, TOC, breadcrumbs, the edit link, the language label, the syntax colors and every admonition label;
  - computed style expectations taken from this spec;
  - the loaded fonts;
  - forced colors, light and dark: screenshots for review and a check that the header, nav, toolbar, code blocks and admonitions have borders;
  - screenshot comparison with a saved snapshot, for the no-visible-change steps.
- `tools/check-css.mjs` and its tests pass.
- Before and after screenshots go into the pull request.

## Documentation of the change

`AGENTS.md` and `CONTRIBUTING.md` get a short section on the UI: where the styles live, the token rule and its check, the pinned bundle and how to update it. Prose changes go through the `no-ai-slop` check, as `AGENTS.md` requires.

## Risks and notes

- Downloads during implementation, each confirmed with the user first: the `antora-ui-default` sources at the pinned revision, and the Fontsource font files.
- `search-ui.js` internals, described in the search keyboard behavior above, belong to `@jmix-framework/lunr-extension`; an upgrade of the extension must be checked against them.
- `site.js` binds the explore toggle and the nav toggles by class; the new markup keeps the classes.
- Prototype stylesheets layered over the old CSS used `!important` to beat upstream rules. The real files remove those upstream rules instead, so no `!important` is needed.
- The mockups (current site and directions A, B, C) live in the session scratchpad and are not part of the repository. This document records the values.
