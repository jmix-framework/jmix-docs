package ui.ex1.screen.component.singleselectlist;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.SelectList;
import io.jmix.ui.component.SingleSelectList;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Hobby;

import java.util.*;

@UiController("singleSelectList-screen")
@UiDescriptor("singleselectlist-screen.xml")
public class SingleSelectListScreen extends Screen {
    // tag::reward-points-list[]
    @Autowired
    private SingleSelectList<Integer> rewardPointsList;
    // end::reward-points-list[]
    // tag::raiting-list[]
    @Autowired
    private SingleSelectList<Integer> ratingList;
    // end::raiting-list[]
    @Autowired
    private Notifications notifications;
    // tag::onInit-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit-start[]
        // tag::set-options-list[]
        List<Integer> list = new ArrayList<>();
        list.add(1000);
        list.add(2000);
        list.add(3000);
        list.add(4000);
        list.add(5000);
        list.add(6000);
        list.add(7000);
        rewardPointsList.setOptionsList(list);
        // end::set-options-list[]
        // tag::set-options-map[]
        Map<String,Integer> map = new LinkedHashMap<>();
        map.put("Poor",2);
        map.put("Average",3);
        map.put("Good",4);
        map.put("Excellent",5);
        ratingList.setOptionsMap(map);
        // end::set-options-map[]
        // tag::onInit-end[]
    }
    // end::onInit-end[]
    // tag::double-click[]
    @Subscribe("hobbyList") // <1>
    public void onHobbyListDoubleClick(SelectList.DoubleClickEvent<Hobby> event) {
        notifications.create()
                .withCaption("Double clicked on the " + event.getItem() + " hobby") // <2>
                .show();
    }
    // end::double-click[]

}