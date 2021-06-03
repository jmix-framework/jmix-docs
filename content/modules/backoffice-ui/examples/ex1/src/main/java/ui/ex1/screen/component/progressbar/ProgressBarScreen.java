package ui.ex1.screen.component.progressbar;

import io.jmix.ui.component.ProgressBar;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.BackgroundTaskHandler;
import io.jmix.ui.executor.BackgroundWorker;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@UiController("sample_ProgressBarScreen")
@UiDescriptor("progress-bar-screen.xml")
public class ProgressBarScreen extends Screen {
    // tag::background-tasks[]
    @Autowired
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
    // end::background-tasks[]
}