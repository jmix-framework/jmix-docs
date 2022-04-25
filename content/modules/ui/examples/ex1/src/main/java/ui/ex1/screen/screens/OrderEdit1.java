package ui.ex1.screen.screens;

import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiScreenProperties;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.*;
import io.jmix.ui.util.UnknownOperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Order;

import java.util.regex.Pattern;


@UiController("uiex1_Order1.edit")
@UiDescriptor("order-edit1.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit1 extends StandardEditor<Order> {

    // tag::before-commit1[]
    @Autowired
    private Notifications notifications;

    // end::before-commit1[]

    // tag::before-close1[]
    @Autowired
    private CheckBox checkBox;

    // end::before-close1[]

    // tag::validate[]
    private Pattern pattern = Pattern.compile("\\s");

    @Override
    protected void validateAdditionalRules(ValidationErrors errors) {
        if (getEditedEntity().getNum() != null) {
            if (pattern.matcher(getEditedEntity().getNum()).find()) {
                errors.add("Number cannot contain whitespaces");
            }
        }
        super.validateAdditionalRules(errors);
    }
    // end::validate[]

    // tag::init-entity[]
    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        event.getEntity().setRating(10);
    }
    // end::init-entity[]

    // tag::before-commit2[]
    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (getEditedEntity().getAmount() == null) {
            notifications.create().withCaption("Enter amount").show();
            event.preventCommit();
        }
    }
    // end::before-commit2[]

    // tag::after-commit[]
    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        notifications.create()
                .withCaption("Saved!")
                .show();
    }
    // end::after-commit[]

    // tag::before-close2[]
    @Subscribe
    protected void onBeforeClose(BeforeCloseEvent event) {
        if (!checkBox.isChecked()) {
            notifications.create().withCaption("Select checkbox").show();
            event.preventWindowClose();
        }
    }
    // end::before-close2[]

    // tag::commit[]
    @Override
    protected void commit(Action.ActionPerformedEvent event) {
        commitChanges().then(() -> {
            commitActionPerformed = true; // <1>
            // ... // <2>
        });
    }
    // end::commit[]


    @Override
    // tag::prevent-unsaved[]
    protected void preventUnsavedChanges(BeforeCloseEvent event) {
        CloseAction action = event.getCloseAction();

        if (action instanceof ChangeTrackerCloseAction
                && ((ChangeTrackerCloseAction) action).isCheckForUnsavedChanges()
                && hasUnsavedChanges()) {
            ScreenValidation screenValidation = getApplicationContext().getBean(ScreenValidation.class);

            UnknownOperationResult result = new UnknownOperationResult();

            if (getApplicationContext().getBean(UiScreenProperties.class).isUseSaveConfirmation()) {
                screenValidation.showSaveConfirmationDialog(this, action) // <1>
                        .onCommit(() -> result.resume(closeWithCommit()))
                        .onDiscard(() -> result.resume(closeWithDiscard()))
                        .onCancel(result::fail);
            } else {
                screenValidation.showUnsavedChangesDialog(this, action) // <2>
                        .onDiscard(() -> result.resume(closeWithDiscard()))
                        .onCancel(result::fail);
            }

            event.preventWindowClose(result);
        }
    }
    // end::prevent-unsaved[]

}