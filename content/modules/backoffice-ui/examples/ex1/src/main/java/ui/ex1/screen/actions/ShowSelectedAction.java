package ui.ex1.screen.actions;

import io.jmix.core.MetadataTools;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.ActionType;
import io.jmix.ui.action.ItemTrackingAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.ComponentsHelper;
import io.jmix.ui.meta.PropertyType;
import io.jmix.ui.meta.StudioPropertiesItem;
import io.jmix.ui.screen.Screen;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

// tag::showSelected-start[]
@ActionType("showSelected")
public class ShowSelectedAction extends ItemTrackingAction {

    @Autowired
    private MetadataTools metadataTools;

    private String description;

    public ShowSelectedAction(String id) {
        super(id);
        setCaption("Show Selected");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void actionPerform(Component component) {
        if (getTarget() != null) {
            Object selected = getTarget().getSingleSelected();
            if (selected != null) {
                Notifications notifications = ComponentsHelper.getScreenContext(target).getNotifications();
                notifications.create()
                        .withType(Notifications.NotificationType.TRAY)
                        .withDescription(description)
                        .withCaption(metadataTools.getInstanceName(selected))
                        .show();
            }
        }
    }
    // end::showSelected-start[]
    // tag::studio-properties[]
    private String contentType = "PLAIN";
    private Class<? extends Screen> dialogClass;
    private List<Integer> columnNumbers = new ArrayList<>();

    @StudioPropertiesItem(name = "ctype", type = PropertyType.ENUMERATION, description = "Email content type", // <1>
            defaultValue = "PLAIN", options = {"PLAIN", "HTML"}
    )
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @StudioPropertiesItem(type = PropertyType.SCREEN_CLASS_NAME, required = true) // <2>
    public void setDialogClass(Class<? extends Screen> dialogClass) {
        this.dialogClass = dialogClass;
    }

    @StudioPropertiesItem(type = PropertyType.STRING) // <3>
    public void setColumnNumbers(List<Integer> columnNumbers) {
        this.columnNumbers = columnNumbers;
    }
    // end::studio-properties[]
    // tag::showSelected-end[]
}
// end::showSelected-end[]
