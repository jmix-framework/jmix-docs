package com.company.onboarding.view.user;

import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "users-list-events", layout = MainView.class)
@ViewController("UserListEventsView")
@ViewDescriptor("user-list-events-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListEventsView extends StandardListView<User> {

    private static final Logger log = LoggerFactory.getLogger(UserListEventsView.class);

    // tag::users-loader[]
    @ViewComponent
    private CollectionLoader<User> usersDl;

    // end::users-loader[]

    // tag::notifications-bean[]
    @Autowired
    private Notifications notifications;

    // end::notifications-bean[]

    // tag::before-show-event[]
    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        usersDl.load();
    }
    // end::before-show-event[]

    // tag::ready-event[]
    @Subscribe
    public void onReady(final ReadyEvent event) {
        notifications.show("Just opened");
    }
    // end::ready-event[]

    // tag::attach-event[]
    @Subscribe
    public void onAttachEvent(final AttachEvent event) {
        log.debug("View is attached");
    }
    // end::attach-event[]

    // tag::before-close-event[]
    @Subscribe
    public void onBeforeClose(BeforeCloseEvent event) {
        if (!isLicenseAgreementAccepted()) {
            CloseAction action = event.getCloseAction();
            if (action instanceof NavigateCloseAction navigateCloseAction) {
                BeforeLeaveEvent beforeLeaveEvent = navigateCloseAction.getBeforeLeaveEvent();
                beforeLeaveEvent.postpone();
            }

            event.preventClose();
        }
    }
    // end::before-close-event[]

    private boolean isLicenseAgreementAccepted() {
        return true;
    }

    // tag::after-close-event[]
    @Subscribe
    public void onAfterClose(final AfterCloseEvent event) {
        notifications.show("View is closed");
    }
    // end::after-close-event[]

    // tag::detach-event[]
    @Subscribe
    public void onDetachEvent(final DetachEvent event) {
        log.debug("View is detached");
    }
    // end::detach-event[]

    // tag::query-parameters-change-event[]
    @ViewComponent
    private Span messageLabel;

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    @Subscribe
    public void onQueryParametersChange(final QueryParametersChangeEvent event) {
        event.getQueryParameters()
                .getSingleParameter("message")
                .ifPresent(this::setMessage);

    }
    // end::query-parameters-change-event[]
}