package com.company.onboarding.view.layout.sidepanellayout;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.sidepanellayout.SidePanelLayout;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "side-panel-layout-resizable-view", layout = MainView.class)
@ViewController(id = "SidePanelLayoutResizableView")
@ViewDescriptor(path = "side-panel-layout-resizable-view.xml")
public class SidePanelLayoutResizableView extends StandardView {

    @ViewComponent
    private SidePanelLayout sidePanelLayout;

    @Subscribe(id = "toggleButton", subject = "clickListener")
    public void onToggleButtonClick(final ClickEvent<JmixButton> event) {
        sidePanelLayout.toggleSidePanel();
    }
}
