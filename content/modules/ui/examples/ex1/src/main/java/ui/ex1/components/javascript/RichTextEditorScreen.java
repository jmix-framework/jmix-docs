package ui.ex1.components.javascript;

import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.JavaScriptComponent;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

// tag::rich-text-editor-screen[]
@UiController("sample_RichTextEditorScreen")
@UiDescriptor("rich-text-editor-screen.xml")
public class RichTextEditorScreen extends Screen {
    @Autowired
    private JavaScriptComponent quill;

    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        QuillState state = new QuillState();
        state.options = ParamsMap.of("theme", "snow",
                "placeholder", "Compose an epic...");

        quill.setState(state);

        quill.addFunction("valueChanged", javaScriptCallbackEvent -> {
            String value = javaScriptCallbackEvent.getArguments().getString(0);
            notifications.create()
                    .withCaption(value)
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
        });
    }

    class QuillState {
        public Map<String, Object> options;
    }
}
// end::rich-text-editor-screen[]