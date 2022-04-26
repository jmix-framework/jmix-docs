package localization_demo.ex1.screen.user;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Table;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import localization_demo.ex1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {

    //tag::inject-message-bundle[]
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    //end::inject-message-bundle[]

    @Subscribe
    public void onInit(InitEvent event) {

        //tag::message-bundle-set-group[]
        messageBundle.setMessageGroup("additional_messages");
        //end::message-bundle-set-group[]

        //tag::message-bundle[]
        String someMessage = messageBundle.getMessage("someMessage");
        //end::message-bundle[]
    }

    @Subscribe("usersTable.username")
    public void onUsersTableUsernameClick(Table.Column.ClickEvent<User> event) {
        User user = event.getItem();

        //tag::format-message[]
        String formattedMessage = messageBundle.formatMessage("userInfo", user.getUsername());
        //end::format-message[]
        notifications.create()
                .withCaption(formattedMessage)
                .show();
    }
}