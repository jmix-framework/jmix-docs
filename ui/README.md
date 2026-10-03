# Docs UI bundle

`ui-bundle.zip` is the Antora default UI bundle that both playbooks use. It was built on 2026-07-16 from revision [`0e38223a`](https://gitlab.com/antora/antora-ui-default/-/commit/0e38223adfd81eb74d4b1779e158f8ad05ff8923) of `antora-ui-default` (job `bundle-stable`). The bundle is pinned, so upstream changes reach the site only when someone takes them on purpose.

The bundle provides the layouts, the partials that `content/supplemental/partials` does not override, `js/site.js`, the vendor scripts and the images. `content/supplemental/css/site.css` replaces the bundle's `css/site.css`, so the stylesheet is ours. Part 1 of that file is the upstream `src/css` of the same revision, part 2 holds the Jmix styles, and the design tokens live in `content/supplemental/css/tokens.css`.

## Updating the bundle

1. Download the new bundle and the upstream sources of the same revision: `https://gitlab.com/antora/antora-ui-default/-/archive/<sha>/antora-ui-default-<sha>.tar.gz`.
2. Diff `src/css`, `src/partials` and `src/js` between the old and the new revision.
3. Port the `src/css` changes into part 1 of `site.css` and new custom properties into `tokens.css`. Compare the partials we override with their upstream versions.
4. Replace `ui-bundle.zip`, update the revision above, build the site and run `node tools/check-css.mjs` and `node tools/ui-audit.mjs`.
