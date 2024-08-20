package com.company.onboarding.view.kanban;


import com.company.onboarding.entity.KanbanTask;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.kanbanflowui.component.Kanban;
import io.jmix.kanbanflowui.component.data.ContainerKanbanItems;

import java.util.UUID;

@Route(value = "kanban-view", layout = MainView.class)
@ViewController("KanbanView")
@ViewDescriptor("kanban-view.xml")
public class KanbanView extends StandardView {

    // tag::kanban[]
    @ViewComponent
    private Kanban<KanbanTask> programmaticallyKanban;

    // end::kanban[]
    // tag::tasksDc[]
    @ViewComponent
    private CollectionContainer<KanbanTask> kanbanTasksDc;

    // end::tasksDc[]

    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        programmaticallyKanban.setItems(new ContainerKanbanItems<>(kanbanTasksDc, UUID.class));
        // end::setItems[]
        // tag::onInit[]
    }
    // end::onInit[]
}