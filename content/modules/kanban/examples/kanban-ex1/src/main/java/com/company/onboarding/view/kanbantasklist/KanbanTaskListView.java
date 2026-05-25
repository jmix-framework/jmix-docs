package com.company.onboarding.view.kanbantasklist;


import com.company.onboarding.entity.KanbanTask;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.view.*;
import io.jmix.kanbanflowui.component.Kanban;
import io.jmix.kanbanflowui.kit.component.event.KanbanTaskDoubleClickEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "kanban-task-list-view", layout = MainView.class)
@ViewController("KanbanTask.list")
@ViewDescriptor("kanban-task-list-view.xml")
public class KanbanTaskListView extends StandardView {

    // tag::dialogWindows[]
    @Autowired
    private DialogWindows dialogWindows;

    // end::dialogWindows[]
    // tag::kanban[]
    @ViewComponent
    private Kanban<KanbanTask> kanban;

    // end::kanban[]

    // tag::doubleClickHandler[]
    @Subscribe("kanban")
    public void onKanbanTaskDoubleClick(final KanbanTaskDoubleClickEvent<KanbanTask> event) {
        dialogWindows.detail(kanban)
                .open();
    }
    // end::doubleClickHandler[]
}