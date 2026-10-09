# Docs UI bundle

`ui-bundle.zip` is the Antora default UI bundle that both playbooks use. It was built on 2026-07-16 from revision [`0e38223a`](https://gitlab.com/antora/antora-ui-default/-/commit/0e38223adfd81eb74d4b1779e158f8ad05ff8923) of `antora-ui-default` (job `bundle-stable`). The bundle is pinned, so upstream changes reach the site only when someone takes them on purpose.

The bundle provides the layouts, the Handlebars helpers, the partials that `content/supplemental/partials` does not override, `js/site.js` and the images. It also holds fonts and `js/vendor/highlight.js`, which the site does not use. The site has its own fonts in `content/supplemental/font` and loads highlight.js from cdnjs. `content/supplemental/css/site.css` replaces the bundle's `css/site.css`, so the stylesheet is ours. Part 1 of that file is the upstream `src/css` of the same revision, part 2 holds the Jmix styles, and the design tokens live in `content/supplemental/css/tokens.css`.

## Updating the bundle

1. Download the new bundle, the `bundle-stable` job artifact of upstream master: `https://gitlab.com/antora/antora-ui-default/-/jobs/artifacts/master/raw/build/ui-bundle.zip?job=bundle-stable`. Download the upstream sources of the commit that job ran on: `https://gitlab.com/antora/antora-ui-default/-/archive/<sha>/antora-ui-default-<sha>.tar.gz`.
2. Diff `src/css`, `src/partials` and `src/js` between the old and the new revision.
3. Port the `src/css` changes into part 1 of `site.css` and new custom properties into `tokens.css`. Compare the partials we override with their upstream versions.
4. Replace `ui-bundle.zip`, update the revision above, build the site and run `node tools/check-css.mjs` and `node tools/ui-audit.mjs`.

## License

The root `LICENSE.md` (CC BY 4.0) covers the documentation text. The bundle and the files derived from it, `content/supplemental/css/site.css`, `content/supplemental/css/tokens.css` and the partials that carry the MPL header, are under the [Mozilla Public License 2.0](https://mozilla.org/MPL/2.0/). The license text is in `content/supplemental/partials/LICENSE`.
