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
first Gradle import (pass `-PpullExamples` to refresh them).

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
- detail view -> экран деталей
- endpoint -> эндпойнт
- entity -> сущность
- fetch plan -> фетч-план
- inject -> инжектировать
- list view -> экран списка
- resource role -> ресурсная роль
- row-level role -> роль уровня строк 
- view -> экран
- Do not translate "changelog".

Always keep AsciiDoc formatting.

## Conventions

- In multi-locale examples use German (`de`) as the secondary locale.
- Use anchored xrefs when linking to named application properties.
