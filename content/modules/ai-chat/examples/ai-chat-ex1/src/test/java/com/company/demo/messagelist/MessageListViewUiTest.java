package com.company.demo.messagelist;

import com.company.demo.AiChatEx1Application;
import com.company.demo.view.messagelist.MessageListView;
import io.jmix.aichat.component.messagelist.AiMessageList;
import io.jmix.aichat.component.messagelist.AiMessageListItem;
import io.jmix.aichat.component.messagelist.GenerationState;
import io.jmix.aichat.data.MessageRole;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * UI integration test that keeps the snippets included by the aiMessageList
 * documentation page correct.
 */
@UiTest
@SpringBootTest(classes = {AiChatEx1Application.class, FlowuiTestAssistConfiguration.class})
@ActiveProfiles("test")
public class MessageListViewUiTest {

    @Autowired
    ViewNavigators viewNavigators;

    @Test
    void test_transcriptIsFilledWithAlternatingRoles() {
        MessageListView view = navigateToMessageListView();

        AiMessageList transcriptList = UiTestUtils.getComponent(view, "transcriptList");
        List<AiMessageListItem> items = transcriptList.getItems();
        Assertions.assertEquals(3, items.size());
        Assertions.assertEquals(MessageRole.USER, items.get(0).getRole());
        Assertions.assertEquals(MessageRole.ASSISTANT, items.get(1).getRole());
        Assertions.assertEquals(MessageRole.USER, items.get(2).getRole());

        // the assistant message is Markdown, including a fenced code block
        String answer = items.get(1).getText();
        Assertions.assertTrue(answer.contains("## Loading orders"));
        Assertions.assertTrue(answer.contains("```java"));
    }

    @Test
    void test_addItemAppendsToTheTranscript() {
        MessageListView view = navigateToMessageListView();

        AiMessageList transcriptList = UiTestUtils.getComponent(view, "transcriptList");
        int before = transcriptList.getItems().size();

        JmixButton addItemButton = UiTestUtils.getComponent(view, "addItemButton");
        addItemButton.click();

        Assertions.assertEquals(before + 1, transcriptList.getItems().size());
    }

    @Test
    void test_actionsAreDeclaredPerRole() {
        MessageListView view = navigateToMessageListView();

        AiMessageList actionsList = UiTestUtils.getComponent(view, "actionsList");
        Assertions.assertEquals(List.of("userCopy"),
                actionsList.getUserActions().stream().map(a -> a.getId()).toList());
        Assertions.assertEquals(List.of("assistantCopy", "reportAction"),
                actionsList.getAssistantActions().stream().map(a -> a.getId()).toList());
    }

    @Test
    void test_nestedI18nOverridesOnlyDeclaredLabels() {
        MessageListView view = navigateToMessageListView();

        AiMessageList i18nList = UiTestUtils.getComponent(view, "i18nList");

        // declared in <aichat:i18n>
        Assertions.assertEquals("Read the whole question", i18nList.getI18n().getShowMore());
        Assertions.assertEquals("Collapse the question", i18nList.getI18n().getShowLess());
        // declared in the nested <aichat:codeBlock>
        Assertions.assertEquals("Copy this snippet", i18nList.getI18n().getCodeBlock().getCopy());

        // not declared: the value from the add-on message bundle is kept
        Assertions.assertEquals("Retry", i18nList.getI18n().getRetry());
        Assertions.assertEquals("Copy", i18nList.getI18n().getCopy());
    }

    @Test
    void test_announceMessagesIsEnabled() {
        MessageListView view = navigateToMessageListView();

        Assertions.assertTrue(
                UiTestUtils.<AiMessageList>getComponent(view, "announceList").isAnnounceMessages());
        Assertions.assertFalse(
                UiTestUtils.<AiMessageList>getComponent(view, "transcriptList").isAnnounceMessages());
    }

    @Test
    void test_thinkingStagesAreDeclared() {
        MessageListView view = navigateToMessageListView();

        AiMessageList drivenList = UiTestUtils.getComponent(view, "drivenList");
        Assertions.assertEquals(2, drivenList.getThinkingStages().size());
        Assertions.assertEquals("Thinking", drivenList.getThinkingStages().get(0).getText());
        Assertions.assertEquals(GenerationState.IDLE, drivenList.getState());

        // the status feed starts empty and belongs to the list
        Assertions.assertNotNull(drivenList.getThinkingStatusPublisher());
        Assertions.assertTrue(drivenList.getThinkingStatusPublisher().getItems().isEmpty());
    }

    @Test
    void test_customThinkingIndicatorIsInstalled() {
        MessageListView view = navigateToMessageListView();

        AiMessageList customIndicatorList = UiTestUtils.getComponent(view, "customIndicatorList");
        Assertions.assertNotNull(customIndicatorList.getThinkingIndicator());

        // the built-in default reports null, a custom one reports the component
        AiMessageList drivenList = UiTestUtils.getComponent(view, "drivenList");
        Assertions.assertNull(drivenList.getThinkingIndicator());
    }

    private MessageListView navigateToMessageListView() {
        viewNavigators.view(UiTestUtils.getCurrentView(), MessageListView.class).navigate();
        return UiTestUtils.getCurrentView();
    }
}
