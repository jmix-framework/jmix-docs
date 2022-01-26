package reports.ex2.screen.user;

import io.jmix.ui.screen.*;
import reports.ex2.entity.User;

@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
public class UserBrowse extends StandardLookup<User> {
}