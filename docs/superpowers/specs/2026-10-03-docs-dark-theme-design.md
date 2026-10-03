# Dark theme and theme menu for the docs UI: design

Date: 2026-10-03. Branch `feature/ui-restyle`, pull request jmix-framework/jmix-docs#203 (base `release_3_1`). This continues the UI restyle; its design spec is in history at `e4d905bd:docs/superpowers/specs/2026-10-03-docs-ui-restyle-design.md`.

## Goal

Add a dark theme to the docs site and a menu in the header to choose the color theme: System, Light or Dark. System is the default and follows the operating system, also while the page is open. The light theme stays as it is; the only visible change in light is the menu in the header.

## Decisions

Made with Gleb during brainstorming on 2026-10-03; the mockups are in the session scratchpad, not in the repository.

| Topic | Decision | Considered and rejected |
|---|---|---|
| Form of the switcher | A menu button in the header. Its icon shows the chosen mode; the menu lists System, Light and Dark and marks the current one. | A cycling icon button (the next mode is hidden; from System on a light OS the first click changes only the icon). A segmented control of three radio buttons. |
| Place | After the search field, first in the header icon row, separated from the AI Assistant, GitHub and Jmix links by a hairline. Below 1024px it stays in the same icon row inside the burger panel. | |
| Palette | "Ink": dark grays with a slight violet cast. Violet stays the accent. | "Graphite", neutral grays of IntelliJ IDEA's New UI. "Space", the brand navy darkened. |
| Code | IntelliJ IDEA's Dark editor scheme (New UI) on its editor background `#1E1F22`; the comment color is lightened from `#7A7E85` (4.0:1) to `#868A91` (4.75:1). | |
| Images | Images stay as they are. Eight line diagrams that draw dark lines straight on transparency get the role `light-background`, which puts a white plate behind them. | A white plate behind every block image (about 270 window screenshots would sit on white rectangles). Dimming every image to 88%. |
| Mechanism | A `data-theme` attribute on `<html>` and a dark token block in `tokens.css`. | `light-dark()` in every token, driven by `color-scheme`: neater, but it needs Chrome 123, Firefox 120 and Safari 17.5, and older browsers would lose every color at once. |

## Theme model

- The preference is `system`, `light` or `dark`. It is stored in `localStorage` under `jmix-docs-theme` as `light` or `dark`; choosing System removes the key. A missing or unknown value means System. Every storage access is wrapped in try/catch: when storage throws, the choice still applies to the open page and is not saved.
- The resolved theme is `light` or `dark`. For System it comes from `matchMedia('(prefers-color-scheme: dark)')`.
- `<html>` carries two attributes: `data-theme` with the resolved theme, which the CSS reads, and `data-theme-preference` with the preference, so that the menu button shows the right icon from the first paint.
- While the preference is System, a change of the OS setting switches the open page (a `change` listener on the media query).
- Other open tabs follow a choice through the `storage` event.
- Without JavaScript neither attribute is set: the page is light and the menu button is hidden, because it could not work.
- The switch is instant. Nothing in the stylesheets animates colors (the only transitions rotate chevrons), so no rule is needed to suppress transitions.
- Print is always light: the dark block applies only to `screen`.
- Forced colors stay as they are; system colors win in both themes.
- The favicons keep following the OS through their `media` attributes: they sit in the browser's tab strip, which follows the OS, not the page.

## Loading without a flash

An inline script at the start of `partials/head-styles.hbs`, before the stylesheet links (the place Spring's UI uses), sets both attributes before the body is parsed:

```html
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
    })();
</script>
```

It is inline because an external file would add a render-blocking request. Placed before the links, it runs at once instead of waiting for the stylesheets. The 404 layout uses the same head, header and footer partials, so it gets the theme and the menu too.

## Theme menu

### Markup

The first child of `.header-btn` in `partials/header-content.hbs`:

```html
<div class="theme-menu">
  <button class="header-icon-link theme-menu-toggle" type="button" aria-haspopup="menu" aria-expanded="false" aria-controls="theme-menu-list" aria-label="Color theme" title="Color theme"></button>
  <ul class="theme-menu-list" id="theme-menu-list" role="menu" aria-label="Color theme">
    <li role="none"><button class="theme-menu-item" type="button" role="menuitemradio" aria-checked="false" tabindex="-1" data-theme-option="system">System</button></li>
    <li role="none"><button class="theme-menu-item" type="button" role="menuitemradio" aria-checked="false" tabindex="-1" data-theme-option="light">Light</button></li>
    <li role="none"><button class="theme-menu-item" type="button" role="menuitemradio" aria-checked="false" tabindex="-1" data-theme-option="dark">Dark</button></li>
  </ul>
</div>
```

`js/theme-menu.js` sets `aria-checked` and the button's name and tooltip, "Color theme: System", "Color theme: Light" or "Color theme: Dark", when it loads and after every change. It is loaded from `partials/footer-scripts.hbs` like `js/dropdown-menu.js`.

### Look

- The button looks like the header links: `.header-icon-link`, 2.25rem square, `--header-text`, hover background `--navbar_hover-background`, which it also keeps while the menu is open. Its icon is a mask icon from tokens (monitor, sun or moon) chosen by `data-theme-preference` on `<html>`, so it is right before the script runs. Stroke icons in the style of the existing mask icons.
- A hairline after the menu separates it from the links: a 1px `--color-line` border, 1.25rem high. It is a border so that forced colors keep it.
- The menu panel shares its look with the version menu: `--color-surface`, 1px `--color-line` border, `--radius-lg`, `--shadow-menu`, 0.375rem padding, at least 10rem wide, 0.5rem below the button, aligned with the button's left edge. `css/dropdown-menu.css` holds both menus, and the panel rules are written once for both.
- Items fill the panel width: a 1rem icon in `--color-text-muted`, the label in `--color-text` at 0.8rem, 0.45rem by 0.625rem padding, `--radius-md`. Hover and focus give `--color-surface-hover`. The checked item has `--color-accent` text and icon, weight 600, and a check mark at the right. The focus ring is drawn inside the item (`outline-offset: -2px`), like other rings in filled lists.

### Keyboard, pointer and screen readers

The WAI-ARIA menu button pattern with `menuitemradio` items:

| Key | On the button | In the menu |
|---|---|---|
| Enter, Space, ArrowDown | Opens the menu and focuses the checked item | Enter and Space choose the focused item, close the menu and return focus to the button. ArrowDown moves to the next item, wrapping. |
| ArrowUp | Opens the menu and focuses the checked item | Moves to the previous item, wrapping |
| Home, End | | First and last item |
| Escape | Closes the menu if it is open | Closes the menu and returns focus to the button |
| Tab | Moves on | Closes the menu; focus moves on |

A click on the button opens or closes the menu, a click on an item chooses it and closes the menu, a click outside closes it. After a choice, focus is back on the button, whose name now includes the new mode, so screen readers announce it without a live region.

### Below 1024px

The menu stays in the icon row of the burger panel (`.header-btn` wraps and centers there). The panel already lets content hang over the page below it (`search.css` sets `overflow: visible` on `.navbar-menu`), so the open menu does too. The audit checks that the open menu fits a 375px viewport.

### Forced colors

The mask icons of the button and the items get `forced-color-adjust: none` and `ButtonText`, the check mark `Highlight`, and the panel border `CanvasText`, as the other mask icons and menus do.

### Without JavaScript

`:root:not([data-theme]) .theme-menu { display: none; }`. The hairline belongs to `.theme-menu`, so it goes too.

## Tokens

`tokens.css` keeps its `:root` block with the light values and `color-scheme: light`. A dark block follows it:

```css
@media screen {
  :root[data-theme="dark"] {
    color-scheme: dark;
    /* tier 2 and the tier 3 values that do not point to tier 2 */
  }
}
```

Components keep reading tier 2 and tier 3 tokens only, and the dark theme itself changes no component rule. The component changes this design does make are listed under Components: the XML tag token, the feedback form's input and icon, and the image role, plus the new menu.

### Tier 1 additions

| Token | Value | Use in dark |
|---|---|---|
| `--black` | `#000` | shadows and the overlay |
| `--ink-950` | `#17171D` | surface |
| `--ink-900` | `#1F1F27` | subtle surface |
| `--ink-850` | `#272730` | hover |
| `--ink-800` | `#2E2E39` | hairlines |
| `--ink-700` | `#3F3F4C` | strong lines |
| `--ink-500` | `#6E6E80` | text input borders |
| `--ink-400` | `#9E9EB1` | muted text |
| `--ink-300` | `#C6C6D2` | nav text |
| `--ink-200` | `#D8D8E2` | body text |
| `--ink-50` | `#F2F1F9` | headings |
| `--jmix-violet-950` | `#2B2650` | accent tint |
| `--jmix-violet-900` | `#3A3470` | accent line |
| `--jmix-violet-300` | `#A99BFF` | links, accent, focus |

All Ink and violet steps are derived, not from the brand book.

### Tier 2 in dark

| Token | Light | Dark | Dark contrast |
|---|---|---|---|
| `--color-text` | `--gray-900` | `--ink-200` | 12.6:1 on surface, 10.5:1 on hover |
| `--color-heading` | `--jmix-space` | `--ink-50` | 15.9:1 |
| `--color-text-nav` | `--gray-700` | `--ink-300` | 10.6:1, 8.7:1 on hover |
| `--color-text-muted` | `--gray-600` | `--ink-400` | 6.8:1 on surface, 6.2:1 on subtle, 5.6:1 on hover, 6.3:1 on code |
| `--color-text-on-accent` | `--white` | `--jmix-space` | 7.2:1 on the accent, 15.3:1 on the heading color |
| `--color-link` | `--jmix-violet-700` | `--jmix-violet-300` | 7.5:1 on surface, 6.9:1 on subtle |
| `--color-link-hover` | `--jmix-violet-800` | `--jmix-violet-200` | 11.8:1 |
| `--color-accent` | `--jmix-violet-700` | `--jmix-violet-300` | 5.9:1 on the accent tint |
| `--color-accent-tint` | `--jmix-violet-50` | `--jmix-violet-950` | |
| `--color-accent-line` | `--jmix-violet-200` | `--jmix-violet-900` | the accent on it 4.6:1 (hovered version button) |
| `--color-focus` | `--jmix-violet-700` | `--jmix-violet-300` | 5.9:1 to 7.5:1 against every surface, 6.9:1 on code |
| `--color-surface` | `--white` | `--ink-950` | |
| `--color-surface-subtle` | `--gray-50` | `--ink-900` | |
| `--color-surface-hover` | `--gray-100` | `--ink-850` | |
| `--color-line` | `--gray-200` | `--ink-800` | |
| `--color-line-strong` | `--gray-300` | `--ink-700` | |
| `--color-control-border` | `--gray-500` | `--ink-500` | 3.6:1 on surface, 3.3:1 on subtle |
| `--color-overlay` | Space at 30% | `--black` at 55% | |
| `--shadow-menu` | Space at 14% and 8% | `--black` at 45% and 30%, same offsets | |

### Tier 3 in dark

Header: `--header-logo-center` becomes `--ink-50`, so the logo's center is light on the dark header.

Code. `--code-block-background` `#1E1F22`, `--code-block-text` `#BCBEC4` (8.9:1), `--inline-code-text` `#E2E2EA` (12.7:1 on `--code-background`, which is the subtle surface). Syntax colors, contrast on `#1E1F22`:

| Token | Light | Dark | Dark contrast |
|---|---|---|---|
| `--syntax-keyword` | `#0033B3` | `#CF8E6D` | 6.1:1 |
| `--syntax-string` | `#067D17` | `#6AAB73` | 6.0:1 |
| `--syntax-number` | `#1750EB` | `#2AACB8` | 6.0:1 |
| `--syntax-comment` | `#6F6F6F` | `#868A91` | 4.75:1 |
| `--syntax-annotation` | `#7A6A0A` | `#B3AE60` | 7.2:1 |
| `--syntax-function` | `#00627A` | `#56A8F5` | 6.5:1 |
| `--syntax-field` | `#871094` | `#C77DBB` | 5.6:1 |
| `--syntax-attribute` | `#174AD4` | `#BABABA` | 8.5:1 |
| `--syntax-property-key` | `#083080` | `#CF8E6D` | 6.1:1 |
| `--syntax-tag` (new) | `#0033B3` | `#D5B778` | 8.5:1 |
| `--syntax-added` | `#E6F4EA` | `#294436` | code text on it 5.7:1 |
| `--syntax-removed` | `#FCE8E6` | `#4A2A2E` | code text on it 6.8:1 |

`--syntax-tag` is new because IntelliJ colors XML tags like keywords in its light scheme but yellow in its dark one. The rule for `.hljs-tag` and `.hljs-name` in `site.css` part 2 moves from `--syntax-keyword` to it; in light the color stays `#0033B3`.

Admonitions. The background is the edge hue at 9% over the surface, the border the same hue at 24%; the label colors are lighter tints of the hue:

| Type | `--<type>-color` (label) | `--<type>-background` | `--<type>-border-color` | `--<type>-accent` (edge) | Label contrast |
|---|---|---|---|---|---|
| note | `#6BDCEA` | `#18272F` | `#1A434D` | Sky, unchanged | 9.5:1 |
| tip | `#5EE0A5` | `#182826` | `#1A4536` | Grace, unchanged | 9.3:1 |
| warning | `#F9C45E` | `#2C251E` | `#4E3D20` | Sun, unchanged | 9.4:1 |
| important | `#FF86AB` | `#2C1723` | `#4E162E` | Fiesta, unchanged | 7.4:1 |
| caution | `#B9AFFF` | `#201F31` | `#2F2C53` | `--jmix-violet-500`, unchanged | 8.2:1 |
| addon | `#DDD8FF` | `#201F31` | `#2F2C53` | `--jmix-violet-300` (Space would vanish on dark) | 11.8:1 |

Body text reaches 10.7:1 to 11.8:1 and links 6.3:1 to 7.0:1 on every admonition background.

Unchanged in dark, by design: the brand edges of note, tip, warning and important, the caution edge, the event banner background, and the live demo, Vaadin, MDN and ECharts buttons, which carry their own fills and text colors. Every upstream name that points to tier 2 (`--navbar-background`, `--nav-background`, `--toc-border-color`, `--*-on-color`, `--link_unresolved-font-color` and so on) follows without a dark value of its own.

### New tokens in both themes

- `--syntax-tag`, above.
- `--image-plate-background: var(--white)`, the plate of the `light-background` role. The same in both themes: in light it is white on white.
- Mask icons `--icon-monitor`, `--icon-sun`, `--icon-moon` and `--icon-check` for the menu, and `--icon-thumb-up` with the glyph of the feedback form's current thumbs-up SVG.
- `--banner-text` now points to `--white` instead of `--color-text-on-accent`, because that one turns navy in dark while the banner stays violet. The light value does not change.

## Components

- Header. The logo center follows `--header-logo-center`; the icons, the burger and the search field follow tier 2.
- Event banner. It stays `--jmix-violet-700` with white text in both themes (10.9:1), and its focus ring stays the text color.
- Version menu, search field and search results. They are drawn from tier 2 tokens and need no rule changes. The panel rules of the version menu are shared with the theme menu.
- Feedback form. The text input gets an explicit `--color-surface` background and `--color-text` color; today it uses the browser's field colors, which follow `color-scheme`. The thumbs-up image (`img/feedback-form__thumb-up.svg`, a fixed `#397300`, 3.1:1 on Ink) becomes a `span` with the mask icon in `--tip-color` (10.8:1 in dark). The glyph stays; in light its green moves from `#397300` to the tip green `#0D7348`. The SVG file is removed.
- Images. Block and inline images are not changed. The role `light-background` (`.doc :is(.imageblock, .image).light-background img`) gives the image `--image-plate-background`, `--radius-sm` corners and `forced-color-adjust: none`, so the plate also survives a dark forced colors palette. The role goes on these image macros:

  | Page | Image |
  |---|---|
  | `bpm/pages/bpmn/transactions.adoc` | `transactions/transactions-1.png`, `transactions/transactions-2.png` |
  | `bpm/pages/bpmn/bpmn-service-task.adoc` | `bpmn-service-task/java-delegate-instantiating.png` |
  | `bpm/pages/bpmn/bpmn-events.adoc` | `bpmn-events/end-events-examples.png`, `bpmn-events/workaround-escalation-events.png` |
  | `bpm/pages/dmn-1-3.adoc` | `dmn/business-rule-full.png` |
  | `bpm/pages/process-artifacts.adoc` | `modeling-and-execution/process-artifacts.png` |
  | `flow-ui/pages/views/view-events.adoc` | `views/open-detail-view.svg` |

  The list comes from rendering all 1,477 images in `content/modules` in Chromium: 296 PNGs and 26 SVGs have transparent edges, but almost all are window screenshots whose transparent part is the shadow margin, or inline icons. Only these eight draw dark lines or labels directly on transparency. The 103 images of the external guides need nothing. Inline icons stay as they are; the IntelliJ gray `#6C707E` of the Studio icons gives 3.6:1 on Ink.
- The HTML email sample in `message-templates/pages/template-definition.adoc` has its own white card with inline colors and stays as it is, like a screenshot.
- The DocsBot chat widget is injected by Google Tag Manager, renders in its own shadow root with its own styles, and stays light. It is not ours to restyle.
- Print uses the light values (the dark block is `screen` only).

## Tooling

### `tools/check-css.mjs`

A third rule: the dark block of `tokens.css` (`:root[data-theme="dark"]`) declares every tier 2 token of the light `:root` block, which are the custom properties named `--color-*` and `--shadow-*`, and declares nothing the light block lacks. New tier 2 tokens therefore cannot be added without a dark value. The rule gets unit tests in `check-css.test.mjs`.

### `tools/ui-audit.mjs`

- The `contrast`, `styles` and `focus` checks run in both themes. The dark run uses a context with `colorScheme: 'dark'` and no stored preference, so it also exercises System.
- `TEXT_CONTRAST` gains pairs that differ between the themes: the version button, the current nav item, a table header, the Since badge, and the XML tag, attribute and string colors on a page with an XML block (`geomap`).
- A list of dark style expectations next to `STYLE_EXPECTATIONS`, for example: navbar background `#17171d`, logo center `#f2f1f9`, version button background `#2b2650` and color `#a99bff`, search field border `#6e6e80`, code background `#1e1f22`, keyword `#cf8e6d`, XML tag `#d5b778`, note edge `#25cde3` and label `#6bdcea`, add-on edge `#a99bff`, table header background `#1f1f27`, footer background `#17171d`, `color-scheme` `dark` on `<html>`, and a white plate on a `light-background` image. The light list gains the XML tag color `#0033b3` and the plate.
- A new `theme` check:
  - no flash: with `dark` stored and a light OS, `data-theme` is already `dark` when the first stylesheet link enters the DOM (recorded by a `MutationObserver` from an init script);
  - resolution: light OS, dark OS, `light` stored on a dark OS, `dark` stored on a light OS, and storage that throws (the page follows the OS and a choice still applies to the page);
  - semantics: `aria-haspopup="menu"`, `aria-expanded`, `aria-controls`, a `menu` with three `menuitemradio` items of which exactly one is checked, and the button's name with the mode;
  - keyboard: the flows of the table above, including that a choice sets both attributes, writes or removes the key, closes the menu, returns focus and renames the button;
  - pointer: open, choose, close by an outside click;
  - live System: `page.emulateMedia({ colorScheme })` switches the page while the preference is System and does not while it is Light;
  - other tabs: a choice in one page of a context switches the other page;
  - persistence across a reload;
  - no JavaScript: no attributes, the menu hidden, a white page;
  - print: with the dark theme, `emulateMedia({ media: 'print' })` gives a white body and dark text;
  - phone width: at 375px the open menu lies inside the viewport;
  - screenshots of the manager page in both themes at 1440 and 375px, written to `--out` for review and for the pull request.
- `forced` also checks that the theme button's icon is drawn.
- `--mask <selector>` for `--snapshot` and `--compare` hides the matching elements (`visibility: hidden`) in both runs, so the light theme can be compared with the header excluded.
- `PAGES` gains the BPM transactions page for the image role.
- The `background()` page helper falls back to the body's background instead of white, so a missing opaque ancestor cannot make a dark pair pass.

## Files

| File | Change |
|---|---|
| `content/supplemental/partials/head-styles.hbs` | the inline theme script before the stylesheet links |
| `content/supplemental/partials/header-content.hbs` | the theme menu as the first child of `.header-btn` |
| `content/supplemental/partials/footer-scripts.hbs` | load `js/theme-menu.js` |
| `content/supplemental/partials/pagination.hbs` | the thumbs-up `img` becomes a `span` |
| `content/supplemental/js/theme-menu.js` | new: menu behavior, applying and saving the choice, live System, other tabs |
| `content/supplemental/css/tokens.css` | tier 1 additions, new tokens, `--banner-text`, the dark block |
| `content/supplemental/css/site.css` | part 2 only: the XML tag rule reads `--syntax-tag`; the `light-background` role |
| `content/supplemental/css/dropdown-menu.css` | the theme menu; the panel rules shared with the version menu |
| `content/supplemental/css/feedback-form.css` | the input colors; the thumbs-up mask icon |
| `content/supplemental/img/feedback-form__thumb-up.svg` | removed |
| the six `.adoc` pages listed under Images | `role=light-background` on eight image macros |
| `tools/check-css.mjs`, `tools/check-css.test.mjs` | the dark block rule and its tests |
| `tools/ui-audit.mjs` | dark runs, the `theme` check, `--mask`, the helper fallback |
| `AGENTS.md`, `CONTRIBUTING.md` | the dark theme, the menu, the role |

Files derived from antora-ui-default keep their MPL-2.0 header; `theme-menu.js` is new code and gets none.

## Documentation

- `AGENTS.md`, section "UI: styles, tokens and the pinned bundle": the dark block in `tokens.css` and its check rule, the inline script in `head-styles.hbs`, `js/theme-menu.js`, `dropdown-menu.css` holding both header menus, the `light-background` role, and that the audit checks both themes.
- `CONTRIBUTING.md`: a diagram drawn with dark lines on a transparent background needs `role=light-background`; check new pages in both themes.
- The description of pull request #203 gets a section on the dark theme. Gleb decides when to push.
- Every prose change goes through the `no-ai-slop` skill in detect mode, as `AGENTS.md` requires.

## Verification

1. Before any change to the styles: build the site and save the light reference, `node tools/ui-audit.mjs --snapshot <dir> --mask .header`. The `--mask` option comes first in the plan for this reason.
2. After the change: `node tools/check-css.mjs` and `node --test tools/check-css.test.mjs`; `node tools/ui-audit.mjs` with every check passing; `node tools/ui-audit.mjs --compare <dir> --mask .header` within its 0.2% threshold.
3. Review the audit's screenshots and pages at 1440 and 375px in both themes: `intro`, `data-access/data-manager`, `bpm/bpmn/bpmn-events`, `bpm/bpmn/transactions`, `flow-ui/vc/components/geoMap`, `flow-ui/views/view-events`, `studio/studio-features`, a guide from an external repository, the 404 page. Built pages load the production analytics container, so they are opened only through the audit or a Playwright script that blocks external hosts, never in a browser pane.

## Out of scope

- Styling the DocsBot widget.
- Dark variants of screenshots and diagrams.
- An animated switch.
- Images of the external guide repositories.
- Registering more highlight.js languages.

## Risks and notes

- The flash protection depends on the head partials keeping their order; the audit's flash test catches a bundle update that changes it.
- Moving `.hljs-tag` and `.hljs-name` to `--syntax-tag` must not change the light colors; the light style expectation for the XML tag color guards it.
- New line diagrams on transparency will be unreadable in dark until they get the role; `CONTRIBUTING.md` says so.
- Browsers without `color-mix()` lose the dark overlay and menu shadow, as they lose the light ones today.
- This spec and its plan are removed from the branch before the pull request is merged, as the restyle's were.
