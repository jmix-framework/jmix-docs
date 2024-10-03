package com.company.demo.listener;

import com.company.demo.entity.KanbanTask;
import com.company.demo.entity.TaskStatus;
import com.company.demo.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.SaveContext;
import io.jmix.core.security.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DemoDataInitializer {

    private static final Random random = new Random();
    private final FileStorage fileStorage;
    private static final String PICTURE_ROOT_PATH = "com/company/demo/demo/";

    @Autowired
    private DataManager dataManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DemoDataInitializer(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @EventListener
    @Authenticated
    public void onApplicationStarted(ApplicationStartedEvent event) {
        if (!dataManager.load(KanbanTask.class).all().maxResults(1).list().isEmpty()) {
            return;
        }
        List<User> users = initUsers();
        initTasks(users);
    }

    private List<User> initUsers(){
        User user;
        ArrayList<User> list = new ArrayList<>();

        user = dataManager.create(User.class);
        user.setUsername("alice");
        user.setPassword(createPassword());
        user.setFirstName("Alice");
        user.setLastName("Brown");
        user.setPicture(uploadPicture(PICTURE_ROOT_PATH, "alice.png"));
        list.add(dataManager.save(user));

        user = dataManager.create(User.class);
        user.setUsername("james");
        user.setPassword(createPassword());
        user.setFirstName("James");
        user.setLastName("Wilson");
        user.setPicture(uploadPicture(PICTURE_ROOT_PATH, "james.png"));
        list.add(dataManager.save(user));

        user = dataManager.create(User.class);
        user.setUsername("mary");
        user.setPassword(createPassword());
        user.setFirstName("Mary");
        user.setLastName("Jones");
        user.setPicture(uploadPicture(PICTURE_ROOT_PATH, "mary.png"));
        list.add(dataManager.save(user));

        return list;
    }

    private FileRef uploadPicture(String path, String fileName) {
        ClassPathResource resource = new ClassPathResource(path + fileName);
        try (InputStream stream = resource.getInputStream()) {
            return fileStorage.saveStream(fileName, stream);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read resource: " + path + fileName, e);
        }
    }

    private String createPassword() {
        return passwordEncoder.encode("1");
    }

    private void initTasks(List<User> users) {
        KanbanTask task1 = createKanbanTask(TaskStatus.TODO, "Create documentation", getRandomUser(users));
        KanbanTask task2 = createKanbanTask(TaskStatus.TODO, "Create samples", getRandomUser(users));
        KanbanTask task3 = createKanbanTask(TaskStatus.IN_PROGRESS, "Conduct a demo", getRandomUser(users));
        KanbanTask task4 = createKanbanTask(TaskStatus.IN_PROGRESS, "Prepare a speech", getRandomUser(users));
        KanbanTask task5 = createKanbanTask(TaskStatus.VERIFICATION, "Create onboarding guide", getRandomUser(users));
        KanbanTask task6 = createKanbanTask(TaskStatus.DONE, "Conduct a presentation", getRandomUser(users));

        SaveContext saveContext = new SaveContext();
        saveContext.saving(task1, task2, task3, task4, task5, task6);
        dataManager.save(saveContext);
    }

    private KanbanTask createKanbanTask(TaskStatus status, String text, User assignee) {
        KanbanTask task = dataManager.create(KanbanTask.class);

        task.setStatus(status);
        task.setText(text);
        task.setAssignee(assignee);

        return task;
    }

    private User getRandomUser(List<User> users) {
        User user;
        do {
            user = users.get(random.nextInt(users.size()));
        } while ("admin".equals(user.getUsername()));

        return user;
    }
}