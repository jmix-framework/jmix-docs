package com.company.onboarding.view.component.horizontalmenu;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.horizontalmenu.HorizontalMenu;
import io.jmix.flowui.component.horizontalmenu.MenuConfigHorizontalMenuItemProvider;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Route(value = "horizontal-menu-view", layout = MainView.class)
@ViewController("HorizontalMenuView")
@ViewDescriptor("horizontal-menu-view.xml")
public class HorizontalMenuView extends StandardView {

    // tag::horizontalMenu[]
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ApplicationContext applicationContext;
    @ViewComponent
    private VerticalLayout navigation;

    @Subscribe
    public void onInit(final InitEvent event) {
        HorizontalMenu horizontalMenu = uiComponents.create(HorizontalMenu.class);

        MenuConfigHorizontalMenuItemProvider itemProvider =
                applicationContext.getBean(MenuConfigHorizontalMenuItemProvider.class);
        horizontalMenu.setMenuItemProvider(itemProvider);
        navigation.add(horizontalMenu);

        itemProvider.load();
    }
    // end::horizontalMenu[]
}