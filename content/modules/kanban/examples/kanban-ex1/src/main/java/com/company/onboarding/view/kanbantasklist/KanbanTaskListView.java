package com.company.onboarding.view.kanbantasklist;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "kanban-task-list-view", layout = MainView.class)
@ViewController("KanbanTask.list")
@ViewDescriptor("kanban-task-list-view.xml")
public class KanbanTaskListView extends StandardView {
}