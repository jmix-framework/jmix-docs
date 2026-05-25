package audit.ex1.screen.user;

import io.jmix.ui.screen.*;
import audit.ex1.entity.User;

@UiController("ex1_User.edit")
@UiDescriptor("user-edit.xml")
@EditedEntityContainer("userDc")
public class UserEdit extends StandardEditor<User> {
}