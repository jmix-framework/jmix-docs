package ui.ex1.screen.component.sourcecodeeditor;

import io.jmix.ui.component.SourceCodeEditor;
import io.jmix.ui.component.autocomplete.AutoCompleteSupport;
import io.jmix.ui.component.autocomplete.Suggestion;
import io.jmix.ui.screen.*;
import ui.ex1.entity.KeyWord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UiController("sample_SourceCodeEditorScreen")
@UiDescriptor("source-code-editor-screen.xml")
public class SourceCodeEditorScreen extends Screen {

    // tag::mode[]
    @Install(to = "sourceCodeEditor", subject = "mode")
    private String sourceCodeEditorMode() {
        return SourceCodeEditor.Mode.Java.getId();
    }
    // end::mode[]

    // tag::suggester[]
    @Install(to = "sourceCodeEditor", subject = "suggester")
    private List<Suggestion> sourceCodeEditorSuggester(AutoCompleteSupport source, String text, int cursorPosition) {
        List<String> keywords = Stream.of(KeyWord.values())
                .map(KeyWord::getId)
                .collect(Collectors.toList());
        List<Suggestion> suggestions = new ArrayList<>();
        for (String keyword : keywords) {
            suggestions.add(new Suggestion(source, keyword, keyword, null, -1, -1));
        }
        return suggestions;
    }
    // end::suggester[]
}