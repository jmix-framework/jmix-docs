# Jmix 文档

本仓库包含 [Jmix](https://jmix.cn) 框架所有模块的文档。文档的发布地址：https://docs.jmix.cn

## 构建方法

* 从 https://nodejs.org 下载并安装最新的 Node LTS 版本

* 在 `jmix-docs` 根目录打开控制台并执行下列命令：
  ```
  npm i
  npx antora antora-playbook.yml
  ```

* 在浏览器打开 `jmix-docs/build/site/index.html` 。
