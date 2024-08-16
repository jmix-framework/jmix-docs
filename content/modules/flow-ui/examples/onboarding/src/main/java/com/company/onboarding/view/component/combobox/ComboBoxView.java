package com.company.onboarding.view.component.combobox;


import com.company.onboarding.entity.DayOfWeek;
import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;


import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import com.vaadin.flow.component.combobox.ComboBoxBase.CustomValueSetEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Stream;

@Route(value = "ComboBoxView", layout = MainView.class)
@ViewController("ComboBoxView")
@ViewDescriptor("combo-box-view.xml")
public class ComboBoxView extends StandardView {
    // tag::durationComboBox[]
    @ViewComponent
    private JmixComboBox<Integer> durationComboBox;

    // end::durationComboBox[]
    // tag::colorComboBox[]
    @ViewComponent
    private JmixComboBox<String> colorComboBox;

    // end::colorComboBox[]
    // tag::setItemsMap[]
    @ViewComponent
    private JmixComboBox<Integer> ratingComboBox;

    // end::setItemsMap[]
    // tag::setItemsEnum[]
    @ViewComponent
    private JmixComboBox<OnboardingStatus> enumComboBox;

    // end::setItemsEnum[]
    // tag::setFiltering[]
    @ViewComponent
    private JmixComboBox<String> colorDropDown;

    // end::setFiltering[]
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private MetadataTools metadataTools;
    // tag::load-users[]
    @Autowired
    protected DataManager dataManager;

    protected Collection<User> users;

    // end::load-users[]

    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        durationComboBox.setItems(1,2,3,4,5);
        // end::setItems[]
        // tag::setColorItems[]
        colorComboBox.setItems("White", "Red", "Blue", "Grey");
        // end::setColorItems[]
        // tag::setFiltering[]
        List<String> itemsList = List.of("White", "Red", "Blue", "Grey");
        colorDropDown.setItems(getStartsWithFilter(), itemsList);

        // end::setFiltering[]
        // tag::setItemsMap[]
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(2, "Poor");
        map.put(3, "Average");
        map.put(4, "Good");
        map.put(5, "Excellent");
        ComponentUtils.setItemsMap(ratingComboBox, map);
        // end::setItemsMap[]
        // tag::setItemsEnum[]
        enumComboBox.setItems(OnboardingStatus.class);
        // end::setItemsEnum[]
        // tag::load-users[]
        users = dataManager.load(User.class).all().list();
        // end::load-users[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::setFiltering[]
    protected ComboBox.ItemFilter<String> getStartsWithFilter() {
        return (color, filterString) ->
                color.toLowerCase().startsWith(filterString.toLowerCase());
    }
    // end::setFiltering[]
    // tag::CustomValueSetEvent[]

    @Subscribe("colorComboBox")
    public void onColorComboBoxCustomValueSet(CustomValueSetEvent<ComboBox<String>> event) {
        colorComboBox.setValue(event.getDetail());
    }
    // end::CustomValueSetEvent[]
    // tag::itemLabelGenerator[]
    @Install(to = "colorComboBox", subject = "itemLabelGenerator")
    private String colorComboBoxItemLabelGenerator(String item) {
        return item.toUpperCase();
    }

    // end::itemLabelGenerator[]
    // tag::renderer[]
    @Supply(to = "daysComboBox", subject = "renderer")
    private Renderer<DayOfWeek> daysComboBoxRenderer() {
        return new ComponentRenderer<>(day -> {
            HorizontalLayout layout = uiComponents.create(HorizontalLayout.class);
            layout.setPadding(false);

            String dayValue = metadataTools.format(day);
            H4 label = new H4(dayValue);

            JmixButton button = uiComponents.create(JmixButton.class);
            button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);
            Icon icon = switch (day) {
                case MONDAY -> VaadinIcon.BRIEFCASE.create();
                case TUESDAY -> VaadinIcon.LINE_CHART.create();
                case WEDNESDAY -> VaadinIcon.TROPHY.create();
                case THURSDAY -> VaadinIcon.GROUP.create();
                case FRIDAY -> VaadinIcon.CASH.create();
                case SATURDAY -> VaadinIcon.GLASS.create();
                case SUNDAY -> VaadinIcon.BED.create();
            };
            button.setIcon(icon);
            layout.add(button, label);
            return layout;
        });
    }

    // end::renderer[]
    // tag::itemsFetchCallback[]

    @Install(to = "programmaticComboBox", subject = "itemsFetchCallback")
    private Stream<User> programmaticComboBoxItemsFetchCallback(Query<User, String> query) {
        String enteredValue = query.getFilter()
                .orElse("");

        return users.stream()
                .filter(user -> user.getDisplayName() != null &&
                        user.getDisplayName().toLowerCase().contains(enteredValue.toLowerCase()))
                .skip(query.getOffset())
                .limit(query.getLimit());
    }
    // end::itemsFetchCallback[]
    @Supply(to = "programmaticComboBox", subject = "renderer")
    private Renderer<User> programmaticComboBoxRenderer() {
        return new TextRenderer<>(User::getDisplayName);
    }
}