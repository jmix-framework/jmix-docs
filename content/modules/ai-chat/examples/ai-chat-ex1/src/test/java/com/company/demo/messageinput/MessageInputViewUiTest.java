package com.company.demo.messageinput;

import com.company.demo.AiChatEx1Application;
import com.company.demo.view.messageinput.MessageInputView;
import com.vaadin.flow.component.html.Span;
import io.jmix.aichat.component.messageinput.AiMessageInput;
import io.jmix.aichat.component.messageinput.AiMessageInputVariant;
import io.jmix.aichat.component.messageinput.EnterAction;
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

/**
 * UI integration test that keeps the snippets included by the aiMessageInput
 * documentation page correct.
 */
@UiTest
@SpringBootTest(classes = {AiChatEx1Application.class, FlowuiTestAssistConfiguration.class})
@ActiveProfiles("test")
public class MessageInputViewUiTest {

    @Autowired
    ViewNavigators viewNavigators;

    @Test
    void test_declaredComposersAreLoaded() {
        MessageInputView view = navigateToMessageInputView();

        // defaults: maxRows 10, EnterAction.SEND
        AiMessageInput basicsComposer = UiTestUtils.getComponent(view, "basicsComposer");
        Assertions.assertEquals(10, basicsComposer.getMaxRows());
        Assertions.assertEquals(EnterAction.SEND, basicsComposer.getEnterAction());

        AiMessageInput maxRowsComposer = UiTestUtils.getComponent(view, "maxRowsComposer");
        Assertions.assertEquals(3, maxRowsComposer.getMaxRows());

        AiMessageInput newlineComposer = UiTestUtils.getComponent(view, "newlineComposer");
        Assertions.assertEquals(EnterAction.NEWLINE, newlineComposer.getEnterAction());

        AiMessageInput fixedToolbarComposer = UiTestUtils.getComponent(view, "fixedToolbarComposer");
        Assertions.assertTrue(fixedToolbarComposer.getThemeNames()
                .contains(AiMessageInputVariant.FIXED_TOOLBAR.getVariantName()));

        AiMessageInput noGapComposer = UiTestUtils.getComponent(view, "noGapComposer");
        Assertions.assertTrue(noGapComposer.getThemeNames()
                .contains(AiMessageInputVariant.NO_HEADER_FOOTER_GAP.getVariantName()));
    }

    @Test
    void test_slotAndBandContentIsReachableById() {
        MessageInputView view = navigateToMessageInputView();

        // prefix/suffix children resolve by id, through HasPrefix/HasSuffix
        JmixButton promptLibraryButton = UiTestUtils.getComponent(view, "promptLibraryButton");
        Assertions.assertNotNull(promptLibraryButton);
        JmixButton clearButton = UiTestUtils.getComponent(view, "clearButton");
        Assertions.assertNotNull(clearButton);

        // band content resolves by id too, through HasSubParts
        Span contextSpan = UiTestUtils.getComponent(view, "contextSpan");
        Assertions.assertEquals("Replying in: Support ticket #4711", contextSpan.getText());

        AiMessageInput slotsComposer = UiTestUtils.getComponent(view, "slotsComposer");
        Assertions.assertSame(promptLibraryButton, slotsComposer.getPrefixComponent());
        Assertions.assertSame(clearButton, slotsComposer.getSuffixComponent());
    }

    @Test
    void test_nestedI18nOverridesOnlyThePlaceholder() {
        MessageInputView view = navigateToMessageInputView();

        AiMessageInput i18nComposer = UiTestUtils.getComponent(view, "i18nComposer");
        Assertions.assertEquals("Ask about this ticket", i18nComposer.getI18n().getPlaceholder());

        // not declared: the value from the add-on message bundle is kept
        Assertions.assertEquals("Send", i18nComposer.getI18n().getSend());
    }

    private MessageInputView navigateToMessageInputView() {
        viewNavigators.view(UiTestUtils.getCurrentView(), MessageInputView.class).navigate();
        return UiTestUtils.getCurrentView();
    }
}
