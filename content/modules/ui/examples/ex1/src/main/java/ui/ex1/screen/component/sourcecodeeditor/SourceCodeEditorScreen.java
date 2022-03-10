package ui.ex1.screen.component.sourcecodeeditor;

import io.jmix.ui.component.SourceCodeEditor;
import io.jmix.ui.component.autocomplete.AutoCompleteSupport;
import io.jmix.ui.component.autocomplete.Suggestion;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UiController("sample_SourceCodeEditorScreen")
@UiDescriptor("source-code-editor-screen.xml")
public class SourceCodeEditorScreen extends Screen {

    // tag::keyword[]
    private static String[] KEYWORDS = {
            "byte",
            "short",
            //...

    // end::keyword[]
            "int",
            "long"
    };

    // tag::mode[]
    @Install(to = "sourceCodeEditor", subject = "mode")
    private String sourceCodeEditorMode() {
        return SourceCodeEditor.Mode.Java.getId();
    }
    // end::mode[]

    // tag::suggester[]
    @Install(to = "sourceCodeEditor", subject = "suggester")
    private List<Suggestion> sourceCodeEditorSuggester(AutoCompleteSupport source, String text, int cursorPosition) {
        return Stream.of(KEYWORDS)
                .map(keyword -> new Suggestion(source, keyword, keyword, null, -1, -1))
                .collect(Collectors.toList());
    }
    // end::suggester[]
}