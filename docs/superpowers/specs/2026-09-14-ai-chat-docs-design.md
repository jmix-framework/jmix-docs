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

## Page content map

Each page's material, traced to its source. Written against the specs, not the cheat sheet.

**`index.adoc`** — what the add-on is (a Flow UI component library for building AI chat interfaces, not a ready-made assistant); key features; `[NOTE]` that it requires an Enterprise subscription; installation with the premium repository block and `implementation 'io.jmix.aichat:jmix-aichat-flowui-starter'`; the add-on is `@Experimental` as a whole; a cross-reference explaining the relation to `ai-tools` (AI Tools ships a working assistant with a tool registry and persisted history; AI Chat Components ships the components you assemble your own chat from). The reciprocal link is added to `ai-tools:index.adoc`.

**`getting-started.adoc`** — prerequisites; `@Push` on the application class and why it is required (streaming and thinking statuses); implementing `LLMProvider` (`Flux<String> stream(LLMRequest)`) and connecting it through `@Install(to = "chat", subject = "llmProvider")`; a zero-configuration `<aiChat height="100%"/>`; running it; one screenshot. One sentence noting that the Studio designer preview renders placeholder boxes for these components.

**`aiCodeBlock.adoc`** — standalone use and automatic embedding in fenced blocks of assistant messages; `language` and `AiCodeBlockLanguage`; the `code` attribute versus the multi-line CDATA `<code>` element; copy and wrap toggle; independent horizontal scrolling; the hybrid toolbar with and without a language; `appendCode(delta)` for streaming; the swappable `Highlighter` contract and the lazily loaded highlight.js default; theming knobs; attribute/handler/element tables.

**`aiMessageInput.adoc`** — adaptive collapsed/expanded layout and `fixed-toolbar`; `maxRows` and the short-viewport cap; send/stop button and when it is disabled; `EnterAction` (`SEND` / `NEWLINE`) and `enterkeyhint`; the `prefix`, `suffix`, `header`, `footer` and `tooltip` slots, all injectable by id; `no-header-footer-gap`; composite-level focus and blur events; `AiMessageInputVariant`; attribute/handler/element tables. Attachment configuration is cross-referenced to `attachments.adoc`, not repeated.

**`aiMessageList.adoc`** — standalone use (`setItems`, `addItem`, `appendText`); assistant versus user rendering (Markdown versus plain text bubble); embedded code blocks; streaming and delta coalescing; the thinking indicator, `ThinkingStage` escalation and the replaceable indicator slot; the thinking status feed (`ThinkingStatusPublisher`, `ThinkingStatusItem`, the publisher owning `ui.access`, the `@Push` requirement, the height cap); auto-follow scrolling and the scroll-to-bottom button; keyboard navigation between messages; per-message actions per role, the two built-ins `aichat_messageCopy` and `aichat_messageRegenerate`, custom actions and `AiMessageActionPerformedEvent`; user-message collapse/expand; `announceMessages`; the error banner; attribute/handler/element tables.

**`aiChat.adoc`** — the assembly and what it composes; zero configuration; the `AiChatMessage` contract and `AiMessageRole`; `SimpleAiChatMessage` and the in-memory path; `ListAiChatItems` versus `ContainerAiChatItems` and `dataContainer`; `AiChatMessageFactory` and `AiChatMessageContext` through `@Install`; `DefaultAiChatMessageFactory` and replacing it with a `@Primary` bean; persistence — `autoSave`, explicit save/remove delegates, or `MessageChangeEvent`; the generation lifecycle (`IDLE`/`GENERATING`/`ERROR`), stop, `regenerate` and `regenerateFrom`, the error banner and retry, cancellation safety; the three events and the fact that terminal callbacks run off the UI thread but carry a usable Jmix context; the empty state and its two slots; composer bands and their variants; chat-level actions through `HasActions`; delegated configuration of the inner components; `--jmix-ai-chat-max-width`; the `aria-live` region; attribute/handler/element tables.

**`attachments.adoc`** — the end-to-end path from drop or pick to a `FileRef` in storage to a rendered chip; `maxFiles`, `maxFileSize`, `acceptedMimeTypes`, `acceptedFileExtensions`, `uploadDropZoneEnabled`; drag and drop and the chips tray; `AttachmentRejectedEvent` and its reasons; `aiUploadTrigger` — `aiChatId` or nearest enclosing chat, placement in `inputPrefix` or in a `dropdownButton` menu item, auto-disabling; display in the transcript (image thumbnails versus file chips); `AttachmentClickEvent` and downloading; where the bytes are written (`DefaultAiChatMessageFactory`, the add-on's only `FileStorage` write site).

**`localization.adoc`** — the `messages.properties` bundle and overriding its keys application-wide; the nested `<i18n>` element mirroring the component tree; override-not-replace semantics (a declared attribute wins, an absent one keeps the bundle value, an empty element is a no-op); `msg://` resolution; the `*I18n` Java beans.

**`styling.adoc`** — the public `::part()` surface per component; the `--jmix-ai-*` custom property namespace and the knobs worth naming; `--jmix-ai-chat-max-width` projecting onto the inner components; theme variants (`AiChatVariant`, `AiMessageInputVariant`) and how chat variants project onto inner components; Aura and Lumo; dark mode, `forced-colors`, `prefers-reduced-motion`, RTL.

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

### Tests

`./gradlew testAll` runs example tests, and `AGENTS.md` states that example tests are what keep documented snippets correct. One light `@UiTest` per view: open it, assert the component is present and that history rendered. Where a test needs deterministic generation, use a synchronous `Flux` provider — the harness the add-on itself uses.

### Open: the LLM provider

Which provider the example carries is deferred to step 2, to be decided against the user's existing demo project. The relevant precedent in this repository: `ai-tools-ex1` depends only on `org.springframework.ai:spring-ai-model` (the API, no model starter, no key, no `spring.ai.*` properties) and shows the OpenAI starter as an inline snippet in the docs rather than including it from the example. Options on the table: a real Spring AI provider with the key from an environment variable, a deterministic stub returning a canned `Flux`, or both beans with the real one conditional.

## Repository plumbing

- `settings.gradle`: `includeBuild 'content/modules/ai-chat/examples/ai-chat-ex1'`.
- `content/modules/ROOT/nav.adoc`: `include::ai-chat:partial$nav.adoc[]` inside the add-ons block, before `ai-tools` (alphabetical).
- `content/modules/ai-chat/partials/nav.adoc`: index → getting-started → four component pages in complexity order → three topic pages.
- `ai-chat-ex1/build.gradle`: the premium repository block (as in `kanban-ex1`, using `rootProject['premiumRepoUser']` / `rootProject['premiumRepoPass']`) and `implementation 'io.jmix.aichat:jmix-aichat-flowui-starter'`. No version — the Jmix BOM already carries `io.jmix.aichat:*` at `premiumVersion`.
- `AiChatEx1Application.java`: `@Push`.
- `content/modules/ai-chat/images/`: screenshots as `aichat-*.png`, optimized with `pngquant --quality=65-85 --strip --force --ext .png`, under the 500 KB budget enforced by the pre-commit hook and CI.

Neither playbook nor `content/antora.yml` needs a change: `examples/` under a module is picked up as `example$` automatically.

## Risks

- **CI is red until the add-on is published.** `.github/workflows/test.yml` runs `compileAll testAll` against the premium Nexus. `io.jmix.aichat:*` at `3.1.999-SNAPSHOT` must be published there before the example compiles in CI. Locally, `publish-to-maven-local` in `jmix-all` plus the `mavenLocal()` entry already present in the example's repositories covers it.
- **The add-on is experimental.** Every package of `aichat-flowui` carries `@Experimental`. The docs should say so once, in `index.adoc`.
- **Snippets drift.** Mitigated by including from tagged regions of compiling code and by the per-view `@UiTest`s, per the repository's existing practice.

## Work sequence

| # | Where | Work |
|---|---|---|
| 0 | docs | This plan. |
| 1 | docs | Plumbing (see above) plus skeletons of all nine pages with headings and anchors, so cross-references can be written from the start. |
| 2 | ai-chat-ex1 | Provider decision; `@Push`; `view/quickstart`; menu and message keys; `@UiTest`. |
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
