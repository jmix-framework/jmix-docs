package ui.ex1.screen.component.combobox;

import io.jmix.core.Metadata;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Hobby;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UiController("comboBox-screen")
@UiDescriptor("combobox-screen.xml")
public class ComboBoxScreen extends Screen {

    // tag::options-list[]
    @Autowired
    private ComboBox<String> maritalStatusField;
    // end::options-list[]
    // tag::options-map[]
    @Autowired
    private ComboBox<Integer> ratingField;

    // end::options-map[]
    // tag::options-map-2[]
    // tag::options-list-2[]

    @Autowired
    private ComboBox<Hobby> hobbyPagingComboBox;
    @Autowired
    private Metadata metadata;

    @Subscribe
    public void onInit(InitEvent event) {
        // end::options-map-2[]
        List<String> list = new ArrayList<>();
        list.add("Married");
        list.add("Widowed");
        list.add("Separated");
        list.add("Divorced");
        list.add("Single");
        maritalStatusField.setOptionsList(list);
        // end::options-list-2[]
        // tag::options-map-3[]
        Map<String,Integer> map = new LinkedHashMap<>();
        map.put("Poor",2);
        map.put("Average",3);
        map.put("Good",4);
        map.put("Excellent",5);
        ratingField.setOptionsMap(map);
        // end::options-map-3[]
        hobbyPagingComboBox.setPopupWidth("50%");
    }



}