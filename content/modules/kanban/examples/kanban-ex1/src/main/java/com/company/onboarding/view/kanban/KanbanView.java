package com.company.onboarding.view.kanban;


import com.company.onboarding.entity.KanbanTask;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.kanbanflowui.component.Kanban;
import io.jmix.kanbanflowui.component.data.ContainerKanbanItems;
import io.jmix.kanbanflowui.kit.component.JmixKanban;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Route(value = "kanban-view", layout = MainView.class)
@ViewController("KanbanView")
@ViewDescriptor("kanban-view.xml")
public class KanbanView extends StandardView {

    // tag::kanban[]
    @ViewComponent
    private Kanban<KanbanTask> programmaticallyKanban;

    // end::kanban[]
    // tag::programmaticPropertiesMappingKanban[]
    @ViewComponent
    private Kanban<KanbanTask> programmaticPropertiesMappingKanban;

    // end::programmaticPropertiesMappingKanban[]
    // tag::tasksDc[]
    @ViewComponent
    private CollectionContainer<KanbanTask> kanbanTasksDc;
    @Autowired
    private DataManager dataManager;

    // end::tasksDc[]

    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        programmaticallyKanban.setItems(new ContainerKanbanItems<>(kanbanTasksDc, UUID.class));
        // end::setItems[]
        // tag::programmaticPropertiesMapping[]
        JmixKanban.PropertiesMapping propertiesMapping = new JmixKanban.PropertiesMapping("id", "status", "text");
        propertiesMapping.setUserAvatar("assignee.picture");
        propertiesMapping.setUsername("assignee.username");
        programmaticPropertiesMappingKanban.setPropertiesMapping(propertiesMapping);
        // end::programmaticPropertiesMapping[]
        // tag::onInit[]
    }
    // end::onInit[]

    // tag::kanbanSaveDelegate[]
    @Install(to = "saveDelegateKanban", subject = "saveDelegate")
    private void kanbanSaveDelegate(final KanbanTask kanbanTask) {
        dataManager.save(kanbanTask);
    }
    // end::kanbanSaveDelegate[]
}