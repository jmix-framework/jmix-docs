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
    protected ProgressBar progressBar;
    @Autowired
    protected BackgroundWorker backgroundWorker;

    private static final int ITERATIONS = 6;

    @Subscribe
    protected void onInit(InitEvent event) {
        BackgroundTask<Integer, Void> task = new BackgroundTask<Integer, Void>(100) {
            @Override
            public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                for (int i = 1; i <= ITERATIONS; i++) {
                    TimeUnit.SECONDS.sleep(1); // <1>
                    taskLifeCycle.publish(i);
                }
                return null;
            }
            @Override
            public void progress(List<Integer> changes) {
                double lastValue = changes.get(changes.size() - 1);
                progressBar.setValue(lastValue / ITERATIONS); // <2>
            }
        };
        BackgroundTaskHandler taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();
    }

}
// end::background-tasks[]