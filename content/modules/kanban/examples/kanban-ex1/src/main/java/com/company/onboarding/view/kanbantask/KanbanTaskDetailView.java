package com.company.onboarding.view.kanbantask;

import com.company.onboarding.entity.KanbanTask;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "kanbanTasks/:id", layout = MainView.class)
@ViewController("KanbanTask.detail")
@ViewDescriptor("kanban-task-detail-view.xml")
@EditedEntityContainer("kanbanTaskDc")
public class KanbanTaskDetailView extends StandardDetailView<KanbanTask> {
}