package com.company.onboarding.view.component.progressbar;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.BackgroundTaskHandler;
import io.jmix.flowui.backgroundtask.BackgroundWorker;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Route(value = "ProgressBarView", layout = MainView.class)
@ViewController("ProgressBarView")
@ViewDescriptor("progress-bar-view.xml")
public class ProgressBarView extends StandardView {
    // tag::background-tasks[]
    @ViewComponent
    protected ProgressBar progressBar; // <1>
    @Autowired
    protected BackgroundWorker backgroundWorker;

    protected BackgroundTaskHandler<Void> taskHandler;

    private static final int ITERATIONS = 6;

    @Subscribe
    protected void onInit(InitEvent event) {  // <2>
        taskHandler = backgroundWorker.handle(createBackgroundTask());
        taskHandler.execute();
    }

    protected BackgroundTask<Integer, Void> createBackgroundTask () { // <3>
        return new BackgroundTask<>(100, TimeUnit.SECONDS) {
            @Override
            public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                for (int i=1; i< ITERATIONS; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    taskLifeCycle.publish(i);
                }
                return null;
            }

            @Override
            public void progress (List<Integer> changes) {
                double lastValue = changes.get(changes.size() - 1);
                double value = lastValue/ITERATIONS;
                progressBar.setValue(value); // <4>
            }
        };
    }
    // end::background-tasks[]
}
