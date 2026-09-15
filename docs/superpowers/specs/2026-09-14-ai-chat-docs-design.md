# AI Chat Components — documentation design

Date: 2026-09-14
Status: approved, not yet implemented
Branch: `feature/ai-chat`

## What this covers

The product documentation for the `jmix-aichat` premium add-on (AI Chat Components) in this Antora site: the page inventory of the `ai-chat` module, the demo code in `content/modules/ai-chat/examples/ai-chat-ex1` that every snippet is included from, the repository plumbing that wires both in, and the order the work is done in.

It does not cover the add-on itself, which is developed in the `jmix-all` workspace (`jmix-premium/jmix-aichat`).

## Source material

| Source | What it gives |
|---|---|
| `jmix-all/docs/features/aichat/demo-cheatsheet.md` | A per-component, per-feature list of everything there is to show. Effectively the outline of the human-readable docs. Transient — do not cite it, mine it. |
| `jmix-all/docs/features/aichat/specs/components/*.md` | ~2100 lines of exact, current API per component. The authority for attribute names, defaults, and behavior. |
| `jmix-all/docs/features/aichat/specs/overview.md` | Module layout, artifact coordinates, styling layout, `@Experimental` status. |
| `jmix-all/docs/features/aichat/guidelines/flow-server.md` | Vaadin's `LLMProvider` contract, and the Spring AI blank-`userMessage` trap. |
| `jmix-all/docs/features/aichat/guidelines/styling.md` | Theming mechanics: `::part` surface, `--jmix-ai-*` knobs, Lumo/Aura asymmetry. |
| `jmix-all/jmix-premium/jmix-aichat` | The code itself, when a spec is ambiguous. |

Deliberately excluded from the product docs: the `decisions/` directory (80 ADRs — "why it was built this way", not reader-facing), increment numbers and coverage matrices, and `backlog/`. The traps recorded in those files do go in, as `NOTE`/`WARNING` blocks — see "Traps to carry over".

## Decisions

### D1. Component reference pages live in the `ai-chat` module, not in `flow-ui:vc/components`

Every other component add-on in this repository (groupDataGrid, kanban, chart, geoMap, pivotTable, supersetDashboard, fullCalendar, spreadsheet) splits its docs in two: the add-on module holds overview, installation and topics; `content/modules/flow-ui/pages/vc/components/<xmlTag>.adoc` holds the component reference, carrying a `[NOTE.addon-component]` back-link and pulling images and snippets back out of the add-on module.

We are deliberately not following that here: all AI Chat pages, reference included, live in `content/modules/ai-chat/pages/`.

Consequences, accepted:

- The add-on's components do not appear in the alphabetical component list under `flow-ui:vc/components`.
- `documentationLink` in `aichat-flowui-kit`'s Studio meta must point at `%VERSION%/ai-chat/<page>.html#<anchor>` instead of the usual `%VERSION%/flow-ui/vc/components/<tag>.html#<anchor>`. Nothing prevents this; the links work. `aichat-flowui-kit` currently declares no `documentationLink` at all, so no existing link breaks. Adding them is a change in `jmix-all`, out of scope here.

### D2. Getting started is a short setup, not a narrative tutorial

Prerequisites → `@Push` → connect an `LLMProvider` → a zero-configuration `<aiChat>` → run it. Modeled on `ai-tools:getting-started.adoc`, not on the kanban/charts "build an app from scratch" tutorials. The reader leaves it with a working chat and goes to the component pages from there.

### D3. Page inventory: component pages plus three cross-cutting topics

```
content/modules/ai-chat/pages/
  index.adoc            Overview, key features, installation, relation to AI Tools
  getting-started.adoc  Setup through to a working zero-config chat
  aiCodeBlock.adoc      Highlighting, copy, wrap, declarative code, streaming API
  aiMessageInput.adoc   Adaptive layout, send/stop, Enter behavior, slots, sizing, focus events
  aiMessageList.adoc    Rendering, streaming, thinking indicator, thinking status feed,
                        scrolling, per-message actions, user-message collapse/expand
  aiChat.adoc           Data binding, persistence, generation lifecycle, events, empty state,
                        composer bands, chat-level actions, delegated configuration
  attachments.adoc      End-to-end flow, composer configuration, rejection reasons,
                        display in the transcript, aiUploadTrigger, download
  localization.adoc     Message bundle plus the nested <i18n> element
  styling.adoc          ::part surface, --jmix-ai-* knobs, theme variants, Aura and Lumo
```

Three cross-cutting pages rather than sections repeated on each component page, because the features genuinely span components: attachments are configured on `aiMessageInput`, rendered by `aiMessageList`, threaded end-to-end by `AiChat`, and opened through `AttachmentClickEvent`; the `<i18n>` element mirrors the component tree and cannot be cut along component lines without losing its shape; theming is one `--jmix-ai-*` namespace with knobs that project from the chat onto its children.

A separate page for the thinking-status feed was considered and rejected as over-slicing — it stays a section of `aiMessageList.adoc` with its own anchor.

### D4. Page files are named after the XML tag, in camelCase

`aiChat.adoc`, `aiMessageList.adoc`, `aiMessageInput.adoc`, `aiCodeBlock.adoc` — matching how every component reference in this repository is named (`groupDataGrid.adoc`, `fullCalendar.adoc`), and giving URLs that read as the component: `docs.jmix.io/jmix/ai-chat/aiChat.html`. Kebab-case would have produced `ai-chat/ai-chat.html`, a page named after its module.

The three topic pages keep kebab-case, since they are topics, not components.

### D5. `aiUploadTrigger` has no page of its own

It is documented as a section of `attachments.adoc`. The component exists only to open the native file picker from a live user gesture; on its own it has nothing to say, and inside the attachments story it has everything.

### D6. One small demo view per page, in feature-named folders

Following `groupdatagrid-ex1`, which uses one view folder per demonstrated feature. Small, single-purpose views keep each included snippet sourced from clean code instead of from one crowded screen.

### D7. The persisted message entity uses a composition for attachments

`AiChatMessage.getAttachments()` returns `List<FileRef>`. A single `FileRef` maps to a string column out of the box; a list does not. The demo therefore models attachments as a composition:

```
entity/ChatMessage            role (String column <-> AiMessageRole), content, createdDate, attachments
entity/ChatMessageAttachment  file (FileRef)
```

Storing the list serialized into one column would be shorter but is not what a real application writes, and this code will be copied.

No `Conversation` entity: `AiChat` binds to a flat message list, and the demo orders it by the persistent `createdDate`, not by the contract's derived `time` (see "Traps to carry over").

### D8. Work proceeds by ascending component complexity

`aiCodeBlock` → `aiMessageInput` → `aiMessageList` → `aiChat`, after `index` and `getting-started`. The simplest component page is written first so the page template is calibrated on cheap material before it is applied to the expensive pages.

### D9. The audience is Java developers; front-end internals and non-operative detail stay out

The reader is a Jmix application developer working in Java and XML. Client-side JavaScript contracts the add-on exposes are therefore left out of the product docs entirely, even when a spec describes them: they are not what this audience programs against, and in the one case that came up — `AiCodeBlock`'s swappable `Highlighter` registry — there is no established practice behind the seam to document.

The rule is broader than JavaScript APIs. Every component of this add-on has a client element behind it, and the add-on's own specs describe those elements in depth — shadow parts, measurement strategies, layout mechanics, forked Vaadin internals. None of it belongs in the product docs. Neither does any other technical detail that does not change how the component is used.

What stays in: behavior a Java developer observes and controls from Java or XML. "Highlighting runs only when a language is set and streaming is off" is behavior and stays; the `Highlighter` contract, `setHighlighter`, and highlight.js lazy loading are mechanism and go. CSS is not affected by this rule — `::part()` selectors, `--jmix-ai-*` knobs and theme variants are how an application styles these components and remain in `styling.adoc`.

### D10. API that exists only as a seam for other components in the add-on is not documented

`AiCodeBlock.appendCode(String)` and `setStreaming(boolean)` are there so that `AiChat` and `AiMessageList` can drive an embedded block while an answer streams in. An application developer does not call them, so they are left out, and with them the batching mechanism behind them.

This rule needs re-checking per page rather than applied blind, and the first re-check reversed the default. `AiMessageList.setState`, `getActiveItem`, `AiMessageListItem.appendText` and `addRetryListener` look like the seam `AiChat` drives the list through, and on that reading they would have been cut. They stay in: Gleb's call, on the ground that this API is what lets an application assemble its own AI chat out of the base components instead of being restricted to `aiChat`. Driving the list by hand — add a user item, switch to `GENERATING`, stream with `appendText`, return to `IDLE` — is a supported use of the add-on and gets a section of its own.

The distinction that separates this from `AiCodeBlock.appendCode`: a code block is never the thing an application assembles a feature out of, while the message list is. Ask whether the component is a building block in its own right before deciding that its state API is internal.

## Page content map

Each page's material, traced to its source. Written against the specs, not the cheat sheet.

**`index.adoc`** — what the add-on is (a Flow UI component library for building AI chat interfaces, not a ready-made assistant); key features; `[NOTE]` that it requires an Enterprise subscription; installation with the premium repository block and `implementation 'io.jmix.aichat:jmix-aichat-flowui-starter'`; the add-on is `@Experimental` as a whole; a cross-reference explaining the relation to `ai-tools` (AI Tools ships a working assistant with a tool registry and persisted history; AI Chat Components ships the components you assemble your own chat from). The reciprocal link is added to `ai-tools:index.adoc`.

**`getting-started.adoc`** — prerequisites; `@Push` on the application class and why it is required (streaming and thinking statuses); implementing `LLMProvider` (`Flux<String> stream(LLMRequest)`) and connecting it through `@Install(to = "chat", subject = "llmProvider")`; a zero-configuration `<aiChat height="100%"/>`; running it; one screenshot. One sentence noting that the Studio designer preview renders placeholder boxes for these components.

**`aiCodeBlock.adoc`** — standalone use and automatic embedding in fenced blocks of assistant messages; `language` and `AiCodeBlockLanguage`; the `code` attribute versus the multi-line CDATA `<code>` element; copy and wrap toggle and the wrap reset on replacement; independent horizontal scrolling and vertical scrolling under a fixed height; the hybrid toolbar with and without a language; per-instance localization; theming knobs; attribute/handler/element tables. Excluded per D9 and D10: the client-side `Highlighter` seam, and `appendCode` / `setStreaming`. The `highlight` attribute keeps a row in the attributes table; that highlighting needs a language is stated under Language.

**`aiMessageInput.adoc`** — adaptive collapsed/expanded layout and `fixed-toolbar`; `maxRows` and the short-viewport cap; send/stop button and when it is disabled; `EnterAction` (`SEND` / `NEWLINE`) and `enterkeyhint`; the `prefix`, `suffix`, `header`, `footer` and `tooltip` slots, all injectable by id; `no-header-footer-gap`; composite-level focus and blur events; `AiMessageInputVariant`; attribute/handler/element tables. Attachment configuration is cross-referenced to `attachments.adoc`, not repeated.

**`aiMessageList.adoc`** — standalone use (`setItems`, `addItem`, `appendText`); assistant versus user rendering (Markdown versus plain text bubble); embedded code blocks; streaming and delta coalescing; the thinking indicator, `ThinkingStage` escalation and the replaceable indicator slot; the thinking status feed (`ThinkingStatusPublisher`, `ThinkingStatusItem`, the publisher owning `ui.access`, the `@Push` requirement, the height cap); auto-follow scrolling and the scroll-to-bottom button; keyboard navigation between messages; per-message actions per role, the two built-ins `aichat_messageCopy` and `aichat_messageRegenerate`, custom actions and `AiMessageActionPerformedEvent`; user-message collapse/expand; `announceMessages`; the error banner; attribute/handler/element tables.

**`aiChat.adoc`** — the assembly and what it composes; zero configuration; the `AiChatMessage` contract and `AiMessageRole`; `SimpleAiChatMessage` and the in-memory path; `ListAiChatItems` versus `ContainerAiChatItems` and `dataContainer`; `AiChatMessageFactory` and `AiChatMessageContext` through `@Install`; `DefaultAiChatMessageFactory` and replacing it with a `@Primary` bean; persistence — `autoSave`, explicit save/remove delegates, or `MessageChangeEvent`; the generation lifecycle (`IDLE`/`GENERATING`/`ERROR`), stop, `regenerate` and `regenerateFrom`, the error banner and retry, cancellation safety; the three events and the fact that terminal callbacks run off the UI thread but carry a usable Jmix context; the empty state and its two slots; composer bands and their variants; chat-level actions through `HasActions`; delegated configuration of the inner components; `--jmix-ai-chat-max-width`; the `aria-live` region; attribute/handler/element tables.

**`attachments.adoc`** — the end-to-end path from drop or pick to a `FileRef` in storage to a rendered chip; `maxFiles`, `maxFileSize`, `acceptedMimeTypes`, `acceptedFileExtensions`, `uploadDropZoneEnabled`; drag and drop and the chips tray; `AttachmentRejectedEvent` and its reasons; `aiUploadTrigger` — `aiChatId` or nearest enclosing chat, placement in `inputPrefix` or in a `dropdownButton` menu item, auto-disabling; display in the transcript (image thumbnails versus file chips); `AttachmentClickEvent` and downloading; where the bytes are written (`DefaultAiChatMessageFactory`, the add-on's only `FileStorage` write site).

**`localization.adoc`** — the `messages.properties` bundle and overriding its keys application-wide; the nested `<i18n>` element mirroring the component tree; override-not-replace semantics (a declared attribute wins, an absent one keeps the bundle value, an empty element is a no-op); `msg://` resolution; the `*I18n` Java beans.

**`styling.adoc`** — the public `::part()` surface per component; the `--jmix-ai-*` custom property namespace and the knobs worth naming; `--jmix-ai-chat-max-width` projecting onto the inner components; theme variants (`AiChatVariant`, `AiMessageInputVariant`) and how chat variants project onto inner components; Aura and Lumo; dark mode, `forced-colors`, `prefers-reduced-motion`, RTL.

## Component page template

Established on `aiCodeBlock.adoc` (step 5) and to be reused for the remaining component pages.

Shape, following `flow-ui:vc/components/markdown.adoc`, the closest existing analogue:

```
= <xmlTag>                       one-sentence description
[cols table]                     XML Element / Java Class
                                 a sentence placing the component in the add-on
== Overview                      one tight screenshot of the component itself
== Basics                        namespace declaration, Studio TIP, the minimal example
== <feature sections>            one per feature, anchored
== Attributes                    own attributes table, then the shared-attribute xref line
== Handlers                      shared-handler xref line
== Elements                      one subsection per nested element
```

Mechanics learned the hard way, all of which recur on every page:

- **`indent=0` is incompatible with `CDATA`.** The directive strips the region's *common* leading whitespace. When the code inside a `CDATA` block is indented relative to its own first line, that indentation becomes the common minimum and is silently removed — a nested YAML key ends up level with its parent. Omit `indent=0` on any include whose region contains `CDATA`; the snippet then carries the descriptor's own indentation, which is honest.
- **An `*` inside backticks still pairs as bold.** Two mentions of a wildcard family in one paragraph (`--jmix-ai-code-block-color-*` and `--jmix-ai-code-block-icon-*`) render as one bold run with both asterisks eaten. Wrap them in a passthrough: `+`...`+`. This will recur on `styling.adoc` and `localization.adoc`.
- **Build success is not page correctness.** Both defects above produced a clean Antora build. Render the page and read the output — `build/site/jmix/ai-chat/<page>.html` — before calling it done.
- **Tag names in the shared message bundle carry a component prefix.** `messages_en.properties` is shared by every demo view, and AsciiDoc emits *every* region matching a tag, so a second view tagging its keys `i18n` silently appends them to the code block page's `i18n` snippet. Bundle regions are therefore `code-block-i18n`, `message-input-bands`, and so on. In per-view files — XML, Java, CSS — the plain case name is fine, because the file belongs to one component.
- **`kbd:[...]` does not work here.** The macro needs `:experimental:`, which this site does not set, so it renders literally. Keyboard keys and shortcuts are written in bold, following the repository's own usage (`*Ctrl/Cmd+S*`).
- **Never write `$VAR[...]` when generating a page from a shell heredoc.** The shell is zsh, where `$V[tags=basics]` is array-subscript syntax and expands to nothing — every `include::` line silently became an empty block, and the Antora build stayed clean because an empty source block is valid. Write include paths literally, or generate the file from Python.

- **Anchor names collide between sections and attribute rows.** An attribute row keeps the plain attribute name as its anchor (`[[language]]`); the section about that attribute needs a different one (`[[setting-language]]`).

**A declarative surface implies application API.** The peer's test for the seam question, and a better one than mine: `aiMessageList` ships `thinkingIndicator` and `thinkingStages` as authoring elements, and the only thing that makes either visible is `setState(GENERATING)`. If the state setter were internal, the add-on would ship two elements an application could never cause to appear. Where a component offers XML for a feature, the Java that drives the feature is application API.

**Threading rules differ within one component and must be stated.** On `aiMessageList` the thinking status feed takes the UI lock itself and is safe from any thread, while `appendText`, `addItem` and `setState` require the UI thread and fail with a session-lock error otherwise. Verified in the source: `pushThinkingStatuses` wraps its write in `ui.access`, the item-update path goes through `ui.beforeClientResponse`. A reader who gets this wrong sees an exception that names the lock, not the fix.

**Localization is split, not duplicated.** Each component page keeps a short Localization section: the component's own key family in the add-on bundle (`aiCodeBlock.i18n.*`, `aiMessageInput.i18n.*`, …), and its own nested `<i18n>` element with one example. `localization.adoc` carries what spans components — the four key families together, `msg://` resolution, and the nested `<i18n>` tree under `aiChat` that mirrors the component tree. The one sentence that recurs on every page is the override-not-replace rule, which is short enough that repeating it beats sending the reader away for it.

**Attribute defaults come from the Java source, not from the specs' prose.** Verified for both written pages against the field initializers and `getProperty` fallbacks in `aichat-flowui`: `maxRows` 10, `enterAction` SEND, `attachmentsEnabled` false, `maxFiles` 10, `maxFileSize` 26214400, `uploadDropZoneEnabled` true, `highlight` true, `codeWrapped` false, `copyActionEnabled` and `wrapActionEnabled` true. Three of these were left as em-dashes on first writing because the prose did not state them.

Sections are cut when the behavior is what a reader already expects — a code block scrolling its own long lines did not earn a section.

**Before documenting a surprise, establish that a reader would meet it.** A demo view stacking eight code blocks made them shrink instead of the view scrolling, and that went onto the page as a section with a `css="flex: none"` remedy carried by every snippet. It should not have: the effect needs many blocks in one constrained container, which no real view has, so it was a property of our own scaffolding. Documenting it made the component read as defective and put a workaround in front of every example. Scaffolding that keeps a demo view usable belongs in the demo and stays invisible — a wrapper the snippets do not include, not an attribute they do.

**Screenshots are taken at 2x and declared at half width.** Verified against existing pages: `groupdatagrid-basic.png` is 2194x900 at `width="1097"`, `overview-embedded-kanban.png` is 1780x1152 at `width="890"`. Capture with `deviceScaleFactor: 2` and set `width` to half the file's pixel width, so the image stays sharp on high-DPI displays.

## Traps to carry over

These come from the add-on's decision records and live verification. They belong in the product docs as `NOTE`/`WARNING` blocks on the relevant page:

- A provider that may receive attachment-only sends must substitute wording for a blank `userMessage()`; `ChatClient.user(String)` asserts non-blank and throws before the model is reached. → `getting-started.adoc` and `attachments.adoc`.
- `acceptedFileExtensions` requires a leading dot (`.png`, not `png`); a dotless value loads fine and fails at the first upload. → `attachments.adoc`.
- Do not `@Subscribe` to a built-in typed action: it replaces the behavior and silently disables the rewind. Subclass and override `execute` with a `super` call. → `aiMessageList.adoc`.
- Order a container-bound chat by a persistent attribute (`order by m.createdDate`), not by the contract's derived `time`, which JPQL cannot resolve. A turn saved in one `save()` needs a tiebreaker. → `aiChat.adoc`.
- `regenerate()` cannot resend attachments after a page reload (they are transient); `regenerate(item)` can, reading them back from storage. → `aiChat.adoc`.
- A message row whose role is outside `{user, assistant}` makes the view fail to open after an upgrade; migrate or filter such rows first. → `aiChat.adoc`.
- `@Push` is required; without it streaming and thinking statuses do not reach the browser. → `getting-started.adoc`.

## Demo project

`content/modules/ai-chat/examples/ai-chat-ex1`, package `com.company.demo`, currently a bare Jmix `3.1.999-SNAPSHOT` application with the standard `User`, login, main and user views.

### Views to add

| Folder | Feeds | Contents |
|---|---|---|
| `view/quickstart` | `getting-started` | Zero-configuration `<aiChat>`, in-memory, one `@Install` for the provider |
| `view/codeblock` | `aiCodeBlock` | `language`, CDATA code element, streaming through `appendCode`, a custom `Highlighter` |
| `view/messageinput` | `aiMessageInput` | Standalone composer: slots, `enterAction`, `maxRows`, focus events |
| `view/messagelist` | `aiMessageList` | Standalone list: `setItems`/`addItem`/`appendText`, per-message actions, `thinkingStages`, thinking statuses |
| `view/chat` | `aiChat` | `dataContainer` + `autoSave` + `messageFactory`, empty-state slots, composer bands, a chat-level action, events, stop/regenerate/error |
| `view/attachments` | `attachments` | Limits, `aiUploadTrigger` in `inputPrefix` and in a `dropdownButton`, rejection handling, download from history |
| `view/localization` | `localization` | The nested `<i18n>` tree |
| `view/styling` | `styling` | `themeNames`, `--jmix-ai-*` overrides, CSS in the project theme |

Each view also gets a `menu.xml` item and its keys in `messages_en.properties`.

`view/chat` is expected to need splitting (binding and persistence apart from generation lifecycle) once the text is written. That call is made when the page is written, not now.

### Domain model

`ChatMessage` and `ChatMessageAttachment` per D7, plus a Liquibase changelog. Introduced at step 10, not earlier: `quickstart`, `codeblock`, `messageinput` and `messagelist` all run on `ListAiChatItems` with the built-in `SimpleAiChatMessage` and need no entity.

### No tests

The example carries no tests for these views. Gleb's call, and the reasoning holds: the project exists so that snippets are pulled from a live application instead of being hardcoded in the docs, and that is its whole job. Compilation already provides the protection that matters — the views are compiled by `compileAll`, so a change in the add-on's API breaks the build without any assertions, and assertions over rendering added little beyond that while needing maintenance.

The example's whole `src/test` tree is removed, the three `@UiTest` classes written before this decision along with the generated `UserTest` / `UserUiTest` scaffolding. `ai-tools-ex1`, the newest example in the repository, likewise carries no tests, so this is the consistent end state rather than a departure. Verification of a demo view is now: it compiles, and it was run and looked at. That is where every valuable finding has come from in any case.

### The LLM provider: Ollama, for real

Settled. The example depends on `org.springframework.ai:spring-ai-starter-model-ollama` and configures it as Gleb's own demo project does — `spring.ai.model.chat=ollama`, a `base-url` and `chat.model` from the environment with local defaults, and `spring.ai.ollama.init.pull-model-strategy=never`. Ollama needs no API key, so the example carries genuinely runnable configuration rather than a placeholder nobody can start.

What the documentation actually teaches is the glue, which is four lines and identical for any Spring AI model — swapping the starter and the properties is the only difference:

```java
@Install(to = "chat", subject = "llmProvider")
private Flux<String> llmProvider(LLMProvider.LLMRequest request) {
    return chatClient.prompt().user(request.userMessage()).stream().content();
}
```

Deliberately not copied from that project: its `PlaceholderChatModel` and `QwenChatModel`. Both are its own stand scaffolding — a scripted model for exercising the UI, and a wrapper that strips demo-screen options before delegating to Ollama — and neither is something a reader writes.

Open until verified: whether the application starts and stays usable with no Ollama running. `pull-model-strategy=never` suggests nothing is fetched at boot, and the whole arrangement depends on it.

## Repository plumbing

- `settings.gradle`: `includeBuild 'content/modules/ai-chat/examples/ai-chat-ex1'`.
- `content/modules/ROOT/nav.adoc`: `include::ai-chat:partial$nav.adoc[]` inside the add-ons block, before `ai-tools` (alphabetical).
- `content/modules/ai-chat/partials/nav.adoc`: index → getting-started → four component pages in complexity order → three topic pages.
- `ai-chat-ex1/build.gradle`: the premium repository block (as in `kanban-ex1`, using `rootProject['premiumRepoUser']` / `rootProject['premiumRepoPass']`) and `implementation 'io.jmix.aichat:jmix-aichat-flowui-starter'`. No version — the Jmix BOM already carries `io.jmix.aichat:*` at `premiumVersion`.
- `AiChatEx1Application.java`: `@Push`.
- `content/modules/ai-chat/images/`: screenshots as `aichat-*.png`, optimized with `pngquant --quality=65-85 --strip --force --ext .png`, under the 500 KB budget enforced by the pre-commit hook and CI.

Neither playbook nor `content/antora.yml` needs a change: `examples/` under a module is picked up as `example$` automatically.

## Risks

- ~~CI is red until the add-on is published.~~ **Retired.** `io.jmix.aichat:jmix-aichat-flowui-starter` resolves straight from `https://nexus.jmix.io/repository/premium` at `3.1.999-SNAPSHOT`; no local publishing is needed. Verified by the example resolving it with an empty `~/.m2/repository/io/jmix`.
- **`compileAll` does not fit the default Gradle heap.** Configuring the ~30 included builds exhausts 512 MiB and the daemon dies of GC thrashing. Run it with `-Dorg.gradle.jvmargs="-Xmx6g -XX:MaxMetaspaceSize=1g"`. Note also that `--dry-run` does not propagate into included builds, so a "dry" run of `compileAll` compiles everything for real.
- **The add-on is experimental.** Every package of `aichat-flowui` carries `@Experimental`. The docs should say so once, in `index.adoc`.
- **Snippets drift.** Mitigated by including from tagged regions of compiling code and by the per-view `@UiTest`s, per the repository's existing practice.

## Work sequence

| # | Where | Work |
|---|---|---|
| 0 | docs | This plan. |
| 1 | docs | Plumbing (see above) plus skeletons of all nine pages with headings and anchors, so cross-references can be written from the start. |
| 2 | ai-chat-ex1 | Provider decision; `@Push`; `view/quickstart`; menu and message keys. |
| 3 | docs | `index.adoc`, `getting-started.adoc`, first screenshot. |
| 4 | ai-chat-ex1 | `view/codeblock`. |
| 5 | docs | `aiCodeBlock.adoc` — establishes the reusable component-page template: header table (XML element / Java class), Basics with the namespace declaration, feature sections, snippet presentation, attribute/handler/element tables. |
| 6 | ai-chat-ex1 | `view/messageinput`. |
| 7 | docs | `aiMessageInput.adoc`. |
| 8 | ai-chat-ex1 | `view/messagelist`. |
| 9 | docs | `aiMessageList.adoc`. |
| 10 | ai-chat-ex1 | `ChatMessage`, `ChatMessageAttachment`, Liquibase changelog, `view/chat`. |
| 11 | docs | `aiChat.adoc`. |
| 12 | ai-chat-ex1 | `view/attachments`, `view/localization`, `view/styling`. |
| 13 | docs | `attachments.adoc`, `localization.adoc`, `styling.adoc`. |
| 14 | docs | Final pass: reciprocal links with `ai-tools`, revisit the key-features list in `index.adoc` now that everything is written, broken-xref check, image budget, full site build, `compileAll testAll`. |

Sessions marked "ai-chat-ex1" are run from inside the example project, which carries its own Jmix agent skills.
