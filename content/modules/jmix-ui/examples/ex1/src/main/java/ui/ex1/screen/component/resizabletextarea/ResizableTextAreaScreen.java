package ui.ex1.screen.component.resizabletextarea;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ResizableTextArea;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_ResizableTextAreaScreen")
@UiDescriptor("resizable-text-area-screen.xml")
public class ResizableTextAreaScreen extends Screen {
    // tag::resize-listener[]
    @Autowired
    private Notifications notifications;

    @Subscribe("resizableTextArea")
    public void onResizableTextAreaResize(ResizableTextArea.ResizeEvent event) {
        notifications.create().
                withCaption("prevHeight:" + event.getPrevHeight()
                        + "; prevWidth:" + event.getPrevWidth()
                        + "; height:" + event.getHeight()
                        + "; width:" + event.getWidth())
                .show();
    }
    // end::resize-listener[]
}