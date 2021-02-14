# Jmix Documentation

This repository contains documentation for all [Jmix](https://jmix.io) framework modules. The documentation is published at https://docs.jmix.io.

## Building

* Install Node LTS release from https://nodejs.org

* Install Antora: https://docs.antora.org/antora/2.3/install/install-antora/#install-antora-globally-using-npm

* Install Kroki extension for rendering diagrams:
  ```
  npm i -g asciidoctor-kroki
  ```

* Open terminal in `jmix-docs` root folder and run:
  ```
  antora antora-playbook.yml
  ```

* Open `jmix-docs/build/site/index.html` in a web browser.