# Agent Instructions

This file provides guidance to AI agents when working with code in this repository.

## What this repository is

The main source of the [Jmix](https://jmix.io) framework documentation, published at
https://docs.jmix.io/. It is an [Antora](https://antora.org) site written in AsciiDoc.
There is almost no application code here — the repository's job is to assemble AsciiDoc pages plus
real, compilable example projects into a static site.

## Building the site

```bash
npm i                                # install Antora (Node LTS required)
npx antora antora-playbook.yml       # build from locally checked-out branches (HEAD)
# open build/site/index.html
```

- `antora-playbook.yml` builds a **single version** from whatever branch is checked out locally —
  every content source uses `branches: HEAD`. This is the playbook for local authoring.
- `antora-playbook.ci.yml` builds the full site from the **remote** repositories; use it with
  `--fetch`: `npx antora --fetch antora-playbook.ci.yml`.

## Content structure

- `content/` is a single Antora component (`jmix`, version `master`, displayed as `v3`).
  Component metadata and global AsciiDoc attributes live in `content/antora.yml`.
- `content/modules/<module>/` holds each documentation module with the standard Antora layout:
  `pages/` (`.adoc` pages), `partials/`, `images/`, and `nav.adoc`. The top-level navigation is
  `content/modules/ROOT/nav.adoc`.
- Guides live in **separate sample repositories** under `external/`, each contributing a `doc`
  start path. They are cloned automatically (see below) and listed as content sources in both
  playbooks. Authoring a new guide is documented in `CONTRIBUTING.md`.

## Example projects (this is the important part)

Code shown in the docs is **not pasted inline** — it is included from real Jmix applications that
compile and have tests. A page pulls a tagged region from an example's source like this:

```asciidoc
include::example$/data-model-ex1/src/main/java/com/company/demo/entity/Customer.java[tags=entity]
```

- Example apps live at `content/modules/<module>/examples/<example-name>/` and are full Gradle
  Jmix projects.
- Each is wired into the root build as a Gradle **composite build** via `includeBuild` in
  `settings.gradle`, registered under a short project name (e.g. `data-model-ex1`).
- When you change documented code, edit it in the example project (inside the relevant
  `tags=...`/`end::...` markers), not in the `.adoc` page. Some examples have tests that assert the
  documented snippets stay correct — keep them passing.

`settings.gradle` also calls `cloneOrPull(...)` to clone each `external/jmix-*-sample` guide repo on
first Gradle import (pass `-PpullExamples` to refresh them). The branch to check out comes from the
`samplesBranch` property in `gradle.properties` (`release_3` on this branch), so a feature branch
still uses the release branch of the samples. Override it with `-PsamplesBranch=<branch>` for all
repositories or `-PsamplesBranch.<repo-name>=<branch>` for one. A repository that always lives on
its own branch passes it as the third argument of `cloneOrPull(...)`, as `jmix-ui-samples` does with
`main`. Already-cloned repositories are never switched automatically — a warning is printed when the
checked-out branch differs.

## Compiling and testing examples

Java 21. From the repository root:

```bash
./gradlew compileAll        # compile (testClasses) every example + the modularity 'base' project
./gradlew testAll           # run tests for every example (masquerade-ex1 is excluded)
```

`compileAll` and `testAll` are defined in `build.gradle` and aggregate over the included builds.
To work on a single example, run Gradle inside that example directory:

```bash
cd content/modules/data-model/examples/data-model-ex1
./gradlew test --tests com.company.demo.SomeTest
```

CI (`.github/workflows/test.yml`) runs `./gradlew compileAll testAll` and needs premium repo
credentials (`-PpremiumRepoUser` / `-PpremiumRepoPass`).

## Images: size budget is enforced

Image commits are size-limited to keep clone times down (PNG/WEBP/JPG ≤ 500 KB, SVG ≤ 100 KB,
GIF not allowed — host animations externally). A **pre-commit hook** and a CI check enforce this.
Enable the hook once per clone:

```bash
git config core.hooksPath .githooks
```

Optimize PNG screenshots with `pngquant --quality=65-85 --strip --force --ext .png <file>`.
See `CONTRIBUTING.md` for the full table. Bypass (rarely) with `git commit --no-verify`.

## Translation

When asked to translate to Russian, translate the words:

- add-on -> дополнение
- builder -> билдер
- detail view -> экран деталей
- endpoint -> эндпойнт
- entity -> сущность
- fetch plan -> фетч-план
- in a bubble -> в пузырьке
- inject -> инжектировать
- list view -> экран списка
- lookup view -> экран выбора
- read view -> экран чтения
- release -> релиз
- resource role -> ресурсная роль
- row-level role -> роль уровня строк 
- view -> экран

Do not translate:
- changelog
- fluent API

Always keep AsciiDoc formatting.

A diagram drawn with dark lines on a transparent background disappears in the dark theme. Give its image macro `role=light-background`, as in `image::transactions/transactions-1.png[,500,role=light-background]`, which puts a white plate behind it. Screenshots have their own background and need no role.

## UI: styles, tokens and the pinned bundle

The site uses the Antora default UI bundle pinned in `ui/ui-bundle.zip`. `ui/README.md` says which revision it is and how to update it. The styles are ours and live in `content/supplemental/css/`:

- `tokens.css` holds the design tokens in three tiers: palette, semantic roles, components. The dark theme is the `:root[data-theme="dark"]` block at the end of the file, inside `@media screen` so print stays light. It sets the tier 2 colors and shadow, and the tier 3 colors that must differ in the dark theme (header logo, code, admonitions). No other stylesheet has a rule under `data-theme="dark"`. The only custom properties declared elsewhere are the `--adm-*` aliases in the Admonitions section of `site.css`.
- `site.css` replaces the bundle stylesheet. Part 1 is the upstream `src/css` of the bundle's revision; change it only to port upstream changes, and override it in part 2 otherwise. Part 2 holds the Jmix styles, one section per component. A section that needs forced colors rules keeps them in its own `@media (forced-colors: active)` block.
- `search.css` and `dropdown-menu.css` style the search and the two header menus (version and color theme).
- `content/supplemental/js/` holds the scripts that add to the bundle's `site.js`: `a11y.js` (ARIA state, copy button names, the skip link and keyboard access to search results), `code-toolbox.js` (starts a code block below the copy toolbox when the toolbox would cover its first line), `dropdown-menu.js` (the version menu) and `theme-menu.js` (the color theme menu).
- The color theme is the `data-theme` attribute (`light` or `dark`) on `<html>`, and `data-theme-preference` holds the reader's choice (`system`, `light` or `dark`). Only `light` and `dark` are saved, in `localStorage` under `jmix-docs-theme`; choosing System removes the key. An inline script at the start of `content/supplemental/partials/head-styles.hbs` sets both attributes and adds a `color-scheme` meta element before the stylesheets load, so the page never shows the wrong theme; keep it before the stylesheet links. `theme-menu.js` changes them later. The DocsBot chat widget that Google Tag Manager adds draws itself for a light page, so `site.css` keeps its host, `#docsbotai-root`, at `color-scheme: light`.

Outside `tokens.css`, write colors as `var(--…)`. Keywords such as `transparent`, `currentColor` and `inherit`, `color-mix()` over keywords and tokens, and the CSS system colors in forced colors blocks are the only exceptions. `node tools/check-css.mjs` rejects other color literals and reports custom properties that are used but never declared. It also makes sure the dark block sets every `--color-*` and `--shadow-*` token of the light block, and only tokens that the light block declares. CI runs it, with its tests (`node --test tools/check-css.test.mjs`), on pull requests that change `content/supplemental/` or the check.

After a UI change, build the site and run `node tools/ui-audit.mjs`. It checks keyboard focus, contrast and the expected styles in both themes, the skip link, accessible names, keyboard access to search results, fonts, forced colors mode, and the color theme: that it is set before the first stylesheet, and how the theme menu behaves. It needs Playwright with Chromium, which is not a dependency of this repository. Run `npx playwright@latest install chromium` once. The built pages load the production analytics container, so check them only through the audit or a Playwright script that blocks external hosts. Do not open them in a browser pane, because it does not block the container.

## Conventions

- In multi-locale examples use German (`de`) as the secondary locale.
- Use anchored xrefs when linking to named application properties.
- Use bold text only for UI elements. Don't use bold for emphasis.
