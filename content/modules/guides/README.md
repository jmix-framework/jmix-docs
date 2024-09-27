### How to create an example for a new Jmix guide

In order to write a guide based on the Jmix petclinic the following steps should be taken:

1. `git clone git@github.com:jmix-framework/jmix-petclinic-2.git` to download the original example
2. `cp -R jmix-petclinic-2 jmix-business-logic-sample` to create a new guide example (stick to `jmix-topic-name-sample` naming pattern)
3. create a repo in the jmix-framework github org: `gh repo create jmix-framework/jmix-business-logic-sample --public`
4. `git remote set-url origin git@github.com:jmix-framework/jmix-business-logic-sample.git`
5. `git remote add upstream git@github.com:jmix-framework/jmix-petclinic-2.git` to point to the original upstream repo
6. `git remote -v` to view the git remote settings
7. change `rootProject.name = 'jmix-petclinic'` to the new name in `settings.gradle`. 
7. do your changes for the example
8. occasionally update from the upstream petclinic example via `git fetch upstream && git checkout main && git merge upstream/main`
