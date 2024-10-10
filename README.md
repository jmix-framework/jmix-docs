# Jmix Documentation

This is the main repository of [Jmix](https://jmix.io) documentation. Guides are located in the repositories of the respective sample projects.  

The documentation is published at https://docs.jmix.io.

## Development

* Install IntelliJ IDEA and AsciiDoc plugin.
* Clone the repository and open the root folder in IntelliJ IDEA.
* Import the Gradle project.
* Repositories of guides sample projects will be automatically cloned into the `external` folder.

Main documentation modules are located in the `content` folder. Guides are located in the `external/<sample>/doc` folders.

The AsciiDoc IntelliJ plugin correctly recognizes all modules by their `antora.yml` files and allows you to make cross-references.

## Building

* Install Node LTS release from https://nodejs.org

* Open terminal in `jmix-docs` root folder and run:
  ```
  npm i
  npx antora antora-playbook.yml
  ```

* Open `jmix-docs/build/site/index.html` in a web browser.

The `antora-playbook.yml` file allows you to build a single version from the branch checked out locally. To build the entire site from the current state of remote repositories use `antora-playbook.ci.yml` and `--fetch` option:

```
npx antora --fetch antora-playbook.ci.yml
```