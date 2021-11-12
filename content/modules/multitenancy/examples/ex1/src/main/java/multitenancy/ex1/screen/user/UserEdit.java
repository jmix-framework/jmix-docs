package multitenancy.ex1.screen.user;

import com.google.common.base.Strings;
import io.jmix.multitenancy.core.TenantProvider;
import io.jmix.multitenancyui.MultitenancyUiSupport;
import io.jmix.ui.component.HasValue;
import multitenancy.ex1.entity.User;
import io.jmix.core.EntityStates;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Objects;
import java.util.TimeZone;

@UiController("mtex1_User.edit")
@UiDescriptor("user-edit.xml")
@EditedEntityContainer("userDc")
@Route(value = "users/edit", parentPrefix = "users")
public class UserEdit extends StandardEditor<User> {

    @Autowired
    private EntityStates entityStates;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordField passwordField;

    @Autowired
    private TextField<String> usernameField;

    @Autowired
    private PasswordField confirmPasswordField;

    @Autowired
    private Notifications notifications;

    @Autowired
    private MessageBundle messageBundle;

    @Autowired
    private ComboBox<String> timeZoneField;

    // tag::tenant-1[]
    @Autowired
    private ComboBox<String> tenantField;

    @Autowired
    private TenantProvider tenantProvider;

    @Autowired
    private MultitenancyUiSupport multitenancyUiSupport;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        String currentTenantId = tenantProvider.getCurrentUserTenantId();
        if (!currentTenantId.equals(TenantProvider.NO_TENANT)
                && Strings.isNullOrEmpty(tenantField.getValue())) {
            tenantField.setEditable(false);
            tenantField.setValue(currentTenantId);
        }
    }

    @Subscribe("tenantField")
    public void onTenantFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        usernameField.setValue(
                multitenancyUiSupport.getUsernameByTenant(
                        usernameField.getValue(), event.getValue()));
    }
    // end::tenant-1[]


    // tag::tenant-2[]

    @Subscribe
    public void onInit(InitEvent event) {
        tenantField.setOptionsList(multitenancyUiSupport.getTenantOptions());
        // ...
        // end::tenant-2[]

        timeZoneField.setOptionsList(Arrays.asList(TimeZone.getAvailableIDs()));
    }

    // tag::tenant-3[]

    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        tenantField.setEditable(true);
        // ...
        // end::tenant-3[]
        usernameField.setEditable(true);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
    }

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messageBundle.getMessage("passwordsDoNotMatch"))
                        .show();
                event.preventCommit();
            }
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }
}