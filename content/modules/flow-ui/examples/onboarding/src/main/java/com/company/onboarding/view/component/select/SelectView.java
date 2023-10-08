package com.company.onboarding.view.component.select;


import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.view.*;

@Route(value = "SelectView", layout = MainView.class)
@ViewController("SelectView")
@ViewDescriptor("select-view.xml")
public class SelectView extends StandardView {
    @ViewComponent
    private JmixSelect<Department> select;

    @Subscribe
    public void onInit(final InitEvent event) {
    }



    @Subscribe("select")
    public void onSelectInvalidChange(final Select.InvalidChangeEvent event) {
        
    }

    @Subscribe("select")
    public void onSelectOpenedChange(final Select.OpenedChangeEvent event) {
        
    }

    // tag::itemEnabledProvider[]
    @Install(to = "select", subject = "itemEnabledProvider")
    private boolean selectItemEnabledProvider(final Department department) {
        if (department != null) {
            return department.getHrManager() != null;
        }
        return true;
    }
    // end::itemEnabledProvider[]
    @Install(to = "select", subject = "textRenderer")
    private String selectTextRenderer(final Department department) {
        return null;
    }

}