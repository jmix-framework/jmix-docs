package com.company.onboarding.view.exception;


import com.company.onboarding.exception.MyException;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "ExceptionSampleView", layout = MainView.class)
@ViewController("ExceptionSampleView")
@ViewDescriptor("exception-sample-view.xml")
public class ExceptionSampleView extends StandardView {

    @Subscribe(id = "myExceptionBtn", subject = "clickListener")
    public void onMyExceptionBtnClick(final ClickEvent<JmixButton> event) {
        try {
            throw new MyException("Some error");
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }
}