package emailtemplate.ex1.screen.user;

import io.jmix.ui.screen.*;
import emailtemplate.ex1.entity.User;

@UiController("User.edit")
@UiDescriptor("user-edit.xml")
@EditedEntityContainer("userDc")
public class UserEdit extends StandardEditor<User> {
}