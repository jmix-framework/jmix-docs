package com.company.supersetsample.view.samples.customguesttokenprovider;

import com.company.supersetsample.app.CustomGuestTokenProvider;
import com.company.supersetsample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.supersetflowui.SupersetGuestTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

@Route(value = "custom-guest-token-provider-view", layout = MainView.class)
@ViewController("CustomGuestTokenProviderView")
@ViewDescriptor("custom-guest-token-provider-view.xml")
public class CustomGuestTokenProviderView extends StandardView {
    // tag::custom-guest-token-provider[]
    @Autowired
    private CustomGuestTokenProvider customGuestTokenProvider;

    @Install(to = "dashboard", subject = "guestTokenProvider")
    private void dashboardGuestTokenProvider(SupersetGuestTokenProvider.FetchGuestTokenContext context,
                                             Consumer<String> callback) {
        customGuestTokenProvider.fetchGuestToken(context, callback);
    }
    // end::custom-guest-token-provider[]
}