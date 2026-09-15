package com.company.demo.view.messagelist;

import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.aichat.action.AiMessageActionPerformedEvent;
import io.jmix.aichat.component.messagelist.AiMessageList;
import io.jmix.aichat.component.messagelist.AiMessageListItem;
import io.jmix.aichat.component.messagelist.GenerationState;
import io.jmix.aichat.component.messagelist.ThinkingStatusItem;
import io.jmix.aichat.component.messagelist.ThinkingStatusPublisher;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.BackgroundWorker;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Route(value = "message-list", layout = MainView.class)
@ViewController(id = "MessageListView")
@ViewDescriptor(path = "message-list-view.xml")
public class MessageListView extends StandardView {

    private static final List<String> ANSWER_CHUNKS = List.of(
            "A fetch plan tells Jmix which attributes to load. ",
            "Declare one in the view descriptor, or pass it to `DataManager`, ",
            "and the reference is loaded in the same query instead of one query per row.");

    @ViewComponent
    private AiMessageList transcriptList;
    @ViewComponent
    private AiMessageList drivenList;
    @ViewComponent
    private AiMessageList customIndicatorList;
    @ViewComponent
    private AiMessageList actionsList;
    @ViewComponent
    private AiMessageList announceList;
    @ViewComponent
    private AiMessageList i18nList;
    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private Notifications notifications;
    @Autowired
    private BackgroundWorker backgroundWorker;

    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::items[]
        transcriptList.setItems(
                AiMessageListItem.user("How do I load orders for a customer?"),
                AiMessageListItem.assistant("""
                        ## Loading orders

                        Use `DataManager` with a JPQL query:

                        ```java
                        List<Order> orders = dataManager.load(Order.class)
                                .query("select o from Order o where o.customer = :customer")
                                .parameter("customer", customer)
                                .list();
                        ```

                        A few things to keep in mind:

                        - add a fetch plan when you need referenced entities
                        - use `maxResults` to page the result
                        """),
                AiMessageListItem.user(LONG_QUESTION)
        );
        // end::items[]

        actionsList.setItems(
                AiMessageListItem.user("Summarise this thread."),
                AiMessageListItem.assistant("The customer reports slow order loading; "
                        + "the fix is to add a fetch plan.")
        );
        announceList.setItems(AiMessageListItem.assistant("New messages are announced "
                + "to screen readers as they arrive."));
        i18nList.setItems(AiMessageListItem.assistant("""
                This list re-words its own labels:

                ```sql
                select * from ORDER_
                ```
                """));

        registerRetryListener();
    }

    // tag::add-item[]
    @Subscribe("addItemButton")
    public void onAddItemButtonClick(final ClickEvent<JmixButton> event) {
        transcriptList.addItem(AiMessageListItem.user("And how do I page the result?"));
    }
    // end::add-item[]

    // tag::driving[]
    @Subscribe("generateButton")
    public void onGenerateButtonClick(final ClickEvent<JmixButton> event) {
        drivenList.addItem(AiMessageListItem.user("What is a fetch plan?"));

        AiMessageListItem answer = AiMessageListItem.assistant("");
        drivenList.addItem(answer);
        drivenList.setState(GenerationState.GENERATING);

        backgroundWorker.handle(new AnswerTask(answer)).execute();
    }

    private class AnswerTask extends BackgroundTask<String, Void> {

        private final AiMessageListItem answer;

        private AnswerTask(AiMessageListItem answer) {
            super(30, TimeUnit.SECONDS, MessageListView.this);
            this.answer = answer;
        }

        @Override
        public Void run(TaskLifeCycle<String> taskLifeCycle) throws Exception {
            // tag::thinking-status[]
            ThinkingStatusPublisher statuses = drivenList.getThinkingStatusPublisher();

            ThinkingStatusItem searching = statuses.start("Searching the documentation");
            Thread.sleep(1200);
            searching.complete("4 pages");

            ThinkingStatusItem writing = statuses.start("Writing the answer");
            // end::thinking-status[]

            for (String chunk : ANSWER_CHUNKS) {
                Thread.sleep(400);
                taskLifeCycle.publish(chunk);
            }

            writing.complete();
            return null;
        }

        @Override
        public void progress(List<String> chunks) { // <1>
            chunks.forEach(answer::appendText);
        }

        @Override
        public void done(Void result) {
            drivenList.setState(GenerationState.IDLE);
            drivenList.getThinkingStatusPublisher().clear();
        }
    }
    // end::driving[]

    // tag::retry[]
    @Subscribe("failButton")
    public void onFailButtonClick(final ClickEvent<JmixButton> event) {
        drivenList.addItem(AiMessageListItem.user("Why did that fail?"));
        drivenList.setState(GenerationState.ERROR);
    }

    private void registerRetryListener() {
        drivenList.addRetryListener(event -> {
            AiMessageListItem userMessage = event.getUserMessage();
            notifications.show(messageBundle.formatMessage("retry.notification",
                    userMessage != null ? userMessage.getText() : "-"));
            drivenList.setState(GenerationState.IDLE);
        });
    }
    // end::retry[]

    @Subscribe("toggleIndicatorButton")
    public void onToggleIndicatorButtonClick(final ClickEvent<JmixButton> event) {
        if (customIndicatorList.getState() == GenerationState.GENERATING) {
            customIndicatorList.setState(GenerationState.IDLE);
            return;
        }
        // the indicator is shown on the message being generated, so the list
        // needs a trailing assistant message for it to attach to
        customIndicatorList.setItems(
                AiMessageListItem.user("What is a fetch plan?"),
                AiMessageListItem.assistant(""));
        customIndicatorList.setState(GenerationState.GENERATING);
    }

    // tag::action-handler[]
    @Subscribe("actionsList.reportAction")
    public void onReportAction(final AiMessageActionPerformedEvent event) {
        AiMessageListItem item = event.getItem();
        notifications.show(messageBundle.formatMessage("reportAction.notification",
                item.getText()));
    }
    // end::action-handler[]

    private static final String LONG_QUESTION = """
            Thanks, that helps. One more thing, and it is the part I keep going \
            back and forth on: we import orders from an external system every night, \
            and a fair number of them arrive without a customer attached at all, \
            usually because the upstream system lets an operator save a draft order \
            before the account has been created. The nightly import job should not \
            fail on those rows, and it should not silently drop them either, because \
            somebody has to look at them the next morning and assign the right \
            customer by hand. At the same time the customer attribute is genuinely \
            required everywhere else in the application, and I do not want to make it \
            optional across the whole data model just to accommodate one import path. \
            What is the cleanest way to model that in Jmix, and is there a pattern for \
            keeping the validation strict in the UI while letting the import write rows \
            that are temporarily incomplete?""";
}
