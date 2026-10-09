# Ai Chat Ex1

This is a web application based on the [Jmix](https://www.jmix.io) framework.

## Getting Started

The newly created project requires Java 21 and uses the embedded HSQL database.

Use the following resources to learn more about Jmix:
* [Jmix Documentation](https://docs.jmix.io)
* [Online Demo Applications](https://www.jmix.io/live-demo)
* [Jmix AI Assistant](https://ai-assistant.jmix.io) (also available in the **Jmix AI** tool window of IntelliJ IDEA)

## Development

- [Setup](https://docs.jmix.io/jmix/setup.html) your development environment.
- [Open](https://docs.jmix.io/jmix/studio/project.html#opening-existing-project) the project in the IDE.
- If you want to use AI agents to develop the application, check out the [Jmix AI Agent Guidelines](https://github.com/jmix-framework/jmix-agent-guidelines) repository.

## Running

To start the application, use the **Ai Chat Ex1 Jmix Application** run configuration in your IDE, or run the following command in the project root directory:

```bash
./gradlew bootRun
```

The application will be available at <http://localhost:8080>.

The default user credentials are:
* Login: `admin`
* Password: `admin`

**WARNING**: Change admin password and remove `ui.login.defaultUsername` and `ui.login.defaultPassword` application properties when deploying the application to production.

## The language model

The chat views talk to a local [Ollama](https://ollama.com) server. Nothing is contacted at startup, so the application runs without it — a chat reports the failure on the first message instead.

Attachments need a model that accepts them. The default `qwen3:8b` handles text only and refuses any attached file — a plain text file as readily as an image — so the Attachments view reports an error until you point the application at a [multimodal model](https://ollama.com/search?c=vision):

```bash
OLLAMA_CHAT_MODEL=<model> ./gradlew bootRun
```
