package com.company.demo.codeblock;

import com.company.demo.AiChatEx1Application;
import com.company.demo.view.codeblock.CodeBlockView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.aichat.component.codeblock.AiCodeBlock;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * UI integration test that keeps the snippets included by the aiCodeBlock
 * documentation page correct.
 */
@UiTest
@SpringBootTest(classes = {AiChatEx1Application.class, FlowuiTestAssistConfiguration.class})
@ActiveProfiles("test")
public class CodeBlockViewUiTest {

    @Autowired
    ViewNavigators viewNavigators;

    @Test
    void test_declaredBlocksAreLoaded() {
        CodeBlockView view = navigateToCodeBlockView();

        AiCodeBlock basicsBlock = UiTestUtils.getComponent(view, "basicsBlock");
        Assertions.assertEquals("sql", basicsBlock.getLanguage());
        Assertions.assertTrue(basicsBlock.getCode().startsWith("select c.name"));

        // the 'code' attribute is a resourceString, so msg:// resolves
        AiCodeBlock bundleBlock = UiTestUtils.getComponent(view, "bundleBlock");
        Assertions.assertEquals("./gradlew -Pvaadin.productionMode=true bootJar",
                bundleBlock.getCode());

        // no language attribute: getLanguage() is empty, never null
        AiCodeBlock noLanguageBlock = UiTestUtils.getComponent(view, "noLanguageBlock");
        Assertions.assertEquals("", noLanguageBlock.getLanguage());
        Assertions.assertFalse(noLanguageBlock.getCode().isEmpty());

        AiCodeBlock highlightOffBlock = UiTestUtils.getComponent(view, "highlightOffBlock");
        Assertions.assertFalse(highlightOffBlock.isHighlight());

        AiCodeBlock actionsDisabledBlock = UiTestUtils.getComponent(view, "actionsDisabledBlock");
        Assertions.assertFalse(actionsDisabledBlock.isCopyActionEnabled());
        Assertions.assertFalse(actionsDisabledBlock.isWrapActionEnabled());
    }

    @Test
    void test_blockCreatedInControllerIsLocalized() {
        CodeBlockView view = navigateToCodeBlockView();

        VerticalLayout programmaticBox = UiTestUtils.getComponent(view, "programmaticBox");
        AiCodeBlock codeBlock = programmaticBox.getChildren()
                .filter(AiCodeBlock.class::isInstance)
                .map(AiCodeBlock.class::cast)
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals("java", codeBlock.getLanguage());
        Assertions.assertTrue(codeBlock.getCode().contains("notifications.show"));

        // created through UiComponents, so it localizes itself from the bundle
        Assertions.assertEquals("Copy", codeBlock.getI18n().getCopy());
    }

    @Test
    void test_nestedI18nOverridesOnlyDeclaredLabels() {
        CodeBlockView view = navigateToCodeBlockView();

        AiCodeBlock i18nBlock = UiTestUtils.getComponent(view, "i18nBlock");

        // declared in <aichat:i18n>
        Assertions.assertEquals("Copy the compose file", i18nBlock.getI18n().getCopy());
        Assertions.assertEquals("Compose file copied", i18nBlock.getI18n().getCopied());

        // not declared: the value from the add-on message bundle is kept
        Assertions.assertEquals("Wrap lines", i18nBlock.getI18n().getWrapOn());
        Assertions.assertEquals("Disable wrapping", i18nBlock.getI18n().getWrapOff());
    }

    private CodeBlockView navigateToCodeBlockView() {
        viewNavigators.view(UiTestUtils.getCurrentView(), CodeBlockView.class).navigate();
        return UiTestUtils.getCurrentView();
    }
}
