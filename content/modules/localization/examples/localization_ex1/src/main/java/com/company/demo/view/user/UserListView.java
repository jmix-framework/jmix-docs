package com.company.demo.view.user;

import com.company.demo.entity.User;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "50em")
public class UserListView extends StandardListView<User> {

    //tag::inject-message-bundle[]
    @Autowired
    private MessageBundle messageBundle;
    //end::inject-message-bundle[]

    @ViewComponent
    private CollectionContainer<User> usersDc;

    @Subscribe
    public void onInit(final InitEvent event) {
        //tag::message-bundle-set-group[]
        messageBundle.setMessageGroup("some.group");
        //end::message-bundle-set-group[]

        //tag::message-bundle[]
        String someMessage = messageBundle.getMessage("someMessage");
        //end::message-bundle[]
    }

    @Subscribe("showUsername")
    public void onShowUsernameClick(final ClickEvent<JmixButton> event) {
        User user = usersDc.getItem();

        //tag::format-message[]
        String formattedMessage = messageBundle.formatMessage("userInfo", user.getUsername());
        //end::format-message[]

        System.out.println(formattedMessage);
    }


}