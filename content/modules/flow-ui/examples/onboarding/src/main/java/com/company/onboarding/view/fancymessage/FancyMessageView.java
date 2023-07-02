package com.company.onboarding.view.fancymessage;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

import java.util.List;

@Route(value = "FancyMessageView", layout = MainView.class)
@ViewController("FancyMessageView")
@ViewDescriptor("fancy-message-view.xml")
public class FancyMessageView extends StandardView {

    // tag::message[]
    @ViewComponent
    private H1 messageLabel;

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    // end::message[]

    // tag::QueryParametersChangeEvent[]
    @Subscribe
    public void onQueryParametersChange(final QueryParametersChangeEvent event) {
        List<String> messageParams = event.getQueryParameters().getParameters().get("message");
        if (messageParams != null && !messageParams.isEmpty())
            setMessage(messageParams.get(0));
    }
    // end::QueryParametersChangeEvent[]
}