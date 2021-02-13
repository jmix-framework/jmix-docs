package audit.ex1.screen.user;

import io.jmix.ui.screen.*;
import audit.ex1.entity.User;

@UiController("ex1_User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
public class UserBrowse extends StandardLookup<User> {
}