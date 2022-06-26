# Jmix Documentation

This repository contains documentation for all [Jmix](https://jmix.io) framework modules. The documentation is published at https://docs.jmix.io.

## Building

* Install Node LTS release from https://nodejs.org

* Open terminal in `jmix-docs` root folder and run:
  ```
  npm i
  npx antora antora-playbook.yml
  ```
  To build the docs with the [Lunr](https://lunrjs.com) search index (takes a bit more time), run:
  ```
  DOCSEARCH_ENABLED=true DOCSEARCH_ENGINE=lunr DOCSEARCH_INDEX_VERSION=latest DOCSEARCH_LANGS=zh npx antora --generator antora-site-generator-lunr antora-playbook.yml
  ```

* Open `jmix-docs/build/site/index.html` in a web browser.
