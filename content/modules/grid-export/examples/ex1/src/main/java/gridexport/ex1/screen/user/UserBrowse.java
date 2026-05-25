package gridexport.ex1.screen.user;

import io.jmix.ui.screen.*;
import gridexport.ex1.entity.User;

@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
public class UserBrowse extends StandardLookup<User> {
}