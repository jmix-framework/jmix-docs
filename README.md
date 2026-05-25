# Jmix 文档

这是 [Jmix](https://jmix.cn) 文档的主仓库。指南文档位于各个指南示例项目的仓库中。

文档的发布地址：https://docs.jmix.cn

## 开发

* 安装 IntelliJ IDEA 和 AsciiDoc 插件。
* 克隆本仓库并在 IntelliJ IDEA 中打开项目的根目录。
* 导入 Gradle 项目。
* 指南示例项目的仓库会自动克隆到 `external` 目录。

主文档的模块位于 `content` 文件夹。指南位于各个 `external/<sample>/doc` 文件夹中。

AsciiDoc IntelliJ 插件可以通过 `antora.yml` 文件正确识别所有的模块，并支持模块间的横向引用。

### 为 Jmix 指南做贡献

如需为 Jmix 文档添加新的指南，请按照 [CONTRIBUTING.md](CONTRIBUTING.md) 中的步骤进行。主要的几个步骤是：

* 基于 Jmix 模板项目配置新的示例项目。
* 按照 Antora 的要求组织文档结构。
* 提交指南进行审核，通过后可以集成至 Jmix 文档。

## 构建方法

* 从 https://nodejs.org 下载并安装最新的 Node LTS 版本

* 在 `jmix-docs` 根目录打开控制台并执行下列命令：
  ```
  npm i
  npx antora antora-playbook.yml
  ```

* 在浏览器打开 `jmix-docs/build/site/index.html`。

通过 `antora-playbook.yml` 文件可以构建本地检出分支的单一版本文档。如需构建远端仓库当前状态的全部文档，可以使用 `antora-playbook.ci.yml` 和 `--fetch` 选项：

```
npx antora --fetch antora-playbook.ci.yml
```