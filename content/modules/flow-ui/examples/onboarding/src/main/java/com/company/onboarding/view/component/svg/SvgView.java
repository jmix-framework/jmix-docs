package com.company.onboarding.view.component.svg;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.Svg;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "svg-view", layout = MainView.class)
@ViewController(id = "SvgView")
@ViewDescriptor(path = "svg-view.xml")
public class SvgView extends StandardView {

    // tag::programmatic[]
    @ViewComponent
    private Svg dotSvg;

    @Subscribe
    public void onInit(final InitEvent event) {
        dotSvg.setSvg("""
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" width="16" height="16">
                    <circle cx="8" cy="8" r="6" fill="currentColor"/>
                </svg>
                """);
    }
    // end::programmatic[]
}
