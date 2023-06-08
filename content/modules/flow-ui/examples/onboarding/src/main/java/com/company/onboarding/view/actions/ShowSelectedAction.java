package com.company.onboarding.view.actions;

import com.vaadin.flow.component.Component;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.ActionType;
import io.jmix.flowui.action.list.ItemTrackingAction;
import org.springframework.beans.factory.annotation.Autowired;

@ActionType("showSelected")
public class ShowSelectedAction<E> extends ItemTrackingAction<E> {

    @Autowired
    private MetadataTools metadataTools;

    @Autowired
    private Notifications notifications;

    public ShowSelectedAction(String id) {
        super(id);
        setText("Show Selected");
    }

    @Override
    public void actionPerform(Component component) {
        if (getTarget() != null) {
            E selected = getTarget().getSingleSelectedItem();
            if (selected != null) {
                notifications.create(metadataTools.getInstanceName(selected)).show();
            }
        }
    }
}
