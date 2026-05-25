package com.company.onboarding.view.component.listmenu;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.menu.provider.MenuConfigListMenuItemProvider;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Route(value = "list-menu-view", layout = MainView.class)
@ViewController("ListMenuView")
@ViewDescriptor("list-menu-view.xml")
public class ListMenuView extends StandardView {
    // tag::injects[]
    @ViewComponent
    private VerticalLayout navigation;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ApplicationContext applicationContext;

    // end::injects[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::create-listMenu[]
        JmixListMenu listMenu = uiComponents.create(JmixListMenu.class);

        MenuConfigListMenuItemProvider itemProvider =
                applicationContext.getBean(MenuConfigListMenuItemProvider.class);
        listMenu.setMenuItemProvider(itemProvider);
        navigation.add(listMenu);

        itemProvider.load();
        // end::create-listMenu[]
        // tag::onInit[]
    }
// end::onInit[]
}