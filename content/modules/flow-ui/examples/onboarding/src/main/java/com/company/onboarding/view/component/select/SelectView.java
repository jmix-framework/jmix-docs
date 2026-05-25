package com.company.onboarding.view.component.select;


import com.company.onboarding.entity.DayOfWeek;
import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@Route(value = "SelectView", layout = MainView.class)
@ViewController("SelectView")
@ViewDescriptor("select-view.xml")
public class SelectView extends StandardView {
    @ViewComponent
    private JmixSelect<Department> select;
    // tag::items-list[]
    @ViewComponent
    private JmixSelect<Integer> selectItems;

    // end::items-list[]
    // tag::items-map[]
    @ViewComponent
    private JmixSelect<Integer> selectMaps;

    // end::items-map[]
    // tag::items-enum[]
    @ViewComponent
    private JmixSelect<DayOfWeek> selectEnum;

    // end::items-enum[]
    @ViewComponent
    private JmixSelect<String> sizeSelect;
    // tag::autowired[]
    @Autowired
    private UiComponents uiComponents;

    // end::autowired[]
    @Autowired
    private Notifications notifications;

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::items-list[]
        selectItems.setItems(1, 2, 3, 4, 5);
        // end::items-list[]
        // tag::items-map[]
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(2, "Poor");
        map.put(3, "Average");
        map.put(4, "Good");
        map.put(5, "Excellent");
        ComponentUtils.setItemsMap(selectMaps, map);
        // end::items-map[]
        // tag::items-enum[]
        selectEnum.setItems(DayOfWeek.class);
        // end::items-enum[]
        sizeSelect.setItems("XS", "S", "M", "L", "XL");
        // tag::onInit[]
    }
// end::onInit[]

    // tag::itemEnabledProvider[]
    @Install(to = "select", subject = "itemEnabledProvider")
    private boolean selectItemEnabledProvider(final Department department) {
        return department == null || department.getHrManager() != null;
    }
    // end::itemEnabledProvider[]

    // tag::emptySelectionAllowed[]
    @Install(to = "sizeSelect", subject = "itemLabelGenerator")
    private String sizeSelectItemLabelGenerator(final String t) {
        return t != null ? t : sizeSelect.getEmptySelectionCaption();
    }

    // end::emptySelectionAllowed[]
    // tag::itemLabelGenerator[]
    @Install(to = "customItemsSelect", subject = "itemLabelGenerator")
    private String customItemsSelectItemLabelGenerator(final Department t) {
        return t.getHrManager() != null
                ? t.getName() + "[Manager: " + t.getHrManager().getFirstName() +
                " " + t.getHrManager().getLastName() + "]"
                : t.getName();
    }

    // end::itemLabelGenerator[]
    // tag::renderer[]
    @Supply(to = "selectWithRenderer", subject = "renderer")
    private ComponentRenderer<Button, Department> selectWithRendererRenderer() {
        return new ComponentRenderer<>(department -> {
            Button button = uiComponents.create(Button.class);
            button.setText(department.getName());
            button.setIcon(VaadinIcon.DESKTOP.create());
            return button;
        });
    }
    // end::renderer[]
// tag::textRenderer[]
    @Install(to = "departmentSelect", subject = "textRenderer")
    private String departmentSelectTextRenderer(final Department t) {
        return t.getName() + ", number #" + t.getNum();
    }
    // end::textRenderer[]
}