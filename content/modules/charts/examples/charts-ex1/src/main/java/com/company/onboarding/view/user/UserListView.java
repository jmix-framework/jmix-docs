package com.company.onboarding.view.user;

import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "50em", height = "37.5em")
public class UserListView extends StandardListView<User> {

    @ViewComponent
    private Chart chart;

    @Autowired
    private UiComponents uiComponents;

    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInit(final InitEvent event) {
        final List<com.company.onboarding.entity.User> inProgress = dataManager.load(com.company.onboarding.entity.User.class)
                .query("select u from User u where u.onboardingStatus = :onboardingStatus1")
                .parameter("onboardingStatus1", 10)
                .list();

        final List<com.company.onboarding.entity.User> notStarted = dataManager.load(com.company.onboarding.entity.User.class)
                .query("select u from User u where u.onboardingStatus = :onboardingStatus1")
                .parameter("onboardingStatus1", 20)
                .list();

        final List<com.company.onboarding.entity.User> completed = dataManager.load(com.company.onboarding.entity.User.class)
                .query("select u from User u where u.onboardingStatus = :onboardingStatus1")
                .parameter("onboardingStatus1", 30)
                .list();

        ListChartItems<MapDataItem> items = new ListChartItems<>(
                new MapDataItem(Map.of("category", "In Progress", "value", inProgress.size())),
                new MapDataItem(Map.of("category", "Not Started", "value", notStarted.size())),
                new MapDataItem(Map.of("category", "Completed", "value", completed.size()))
        );

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("category")
                                        .withValueField("value")
                        )
        );
    }

    @Supply(to = "usersDataGrid.picture", subject = "renderer")
    private Renderer<User> usersDataGridPictureRenderer() {
        return new ComponentRenderer<>(user -> {
            FileRef fileRef = user.getPicture();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource);
                image.setClassName("user-picture");

                return image;
            } else {
                return null;
            }
        });
    }
}