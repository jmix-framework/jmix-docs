package com.company.kanbanex1.view.step;

import com.company.kanbanex1.entity.Step;
import com.company.kanbanex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "steps/:id", layout = MainView.class)
@ViewController("Step.detail")
@ViewDescriptor("step-detail-view.xml")
@EditedEntityContainer("stepDc")
public class StepDetailView extends StandardDetailView<Step> {
}