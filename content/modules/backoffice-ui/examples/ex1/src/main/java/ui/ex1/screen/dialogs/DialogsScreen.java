package ui.ex1.screen.dialogs;

import com.google.common.base.Strings;
import io.jmix.core.DataManager;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.app.inputdialog.DialogActions;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.*;
import io.jmix.ui.component.inputdialog.InputDialogAction;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.BackgroundTaskHandler;
import io.jmix.ui.executor.BackgroundWorker;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Status;

import java.util.List;
import java.util.concurrent.TimeUnit;

@UiController("dialogsScreen")
@UiDescriptor("dialogs-screen.xml")
public class DialogsScreen extends Screen {
    private static final int ITERATIONS = 5;
    // tag::unsafe-html[]
    protected static final String UNSAFE_HTML = "<i>Jackdaws </i><u>love</u> " +
            "<font size=\"javascript:alert(1)\" " +
            "color=\"moccasin\">my</font> " +
            "<font size=\"7\">big</font> <sup>sphinx</sup> " +
            "<font face=\"Verdana\">of</font> <span style=\"background-color: " +
            "red;\">quartz</span><svg/onload=alert(\"XSS\")>";

    // end::unsafe-html[]
    // tag::inject-dialogs[]
    @Autowired
    private Dialogs dialogs;

    // end::inject-dialogs[]
    // tag::inject-notifications[]
    @Autowired
    private Notifications notifications;

    // end::inject-notifications[]
    // tag::inject-uiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::inject-uiComponents[]
    @Autowired
    private DataManager dataManager;
    @Autowired
    private BackgroundWorker backgroundWorker;

    // tag::create-message-dialog[]
    @Subscribe("msgDialogBtn")
    public void onMsgDialogBtnClick(Button.ClickEvent event) {
        dialogs.createMessageDialog()
                .withCaption("Success")
                .withMessage("Your invitation successfully send")
                .show();
    }
    // end::create-message-dialog[]
    // tag::create-message-dialog-sanitize[]
    @Subscribe("msgDialogOnBtn")
    public void onMsgDialogOnBtnClick(Button.ClickEvent event) {
        dialogs.createMessageDialog()
                .withCaption("MessageDialog with Sanitizer")
                .withMessage(UNSAFE_HTML)
                .withContentMode(ContentMode.HTML)
                .withHtmlSanitizer(true)
                .show();
    }

    @Subscribe("msgDialogOffBtn")
    public void onMsgDialogOffBtnClick(Button.ClickEvent event) {
        dialogs.createMessageDialog()
                .withCaption("MessageDialog without Sanitizer")
                .withMessage(UNSAFE_HTML)
                .withContentMode(ContentMode.HTML)
                .withHtmlSanitizer(false)
                .show();
    }
    // end::create-message-dialog-sanitize[]
    // tag::create-message-dialog-customize[]
    @Subscribe("showDialogBtn")
    public void onShowDialogBtnClick(Button.ClickEvent event) {
        dialogs.createMessageDialog()
                .withCaption("Information")
                .withMessage("<i>Message<i/>")
                .withContentMode(ContentMode.HTML)
                .withCloseOnClickOutside(true)
                .withWidth("100px")
                .withHeight("300px")
                .show();
    }
    // end::create-message-dialog-customize[]
    // tag::create-option-dialog[]
    @Subscribe("optDialogBtn")
    public void onOptDialogBtnClick(Button.ClickEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to add a customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY)
                                .withHandler(e -> {
                            doSomething();
                        }),
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::create-option-dialog[]
    private void doSomething(){
        notifications.create().withCaption("Hello!").show();
    }
    // tag::create-input-dialog[]
    @Subscribe("inputDialogBtn")
    public void onInputDialogBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption("Get values")
                .withParameters(
                        InputParameter.dateTimeParameter("deliveryTime")
                                .withCaption("Delivery Time")
                                .withRequired(true),// <1>
                        InputParameter.doubleParameter("amount")
                                .withCaption("Amount")
                                .withDefaultValue(1.0),// <2>
                        InputParameter.entityParameter("customer", Customer.class)
                                .withCaption("Customer"),// <3>
                        InputParameter.enumParameter("status", Status.class)
                                .withCaption("Status")// <4>
                )
                .withActions(DialogActions.OK_CANCEL)// <5>
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {// <6>
                        String name = closeEvent.getValue("name");// <7>
                        Double quantity = closeEvent.getValue("quantity");
                        Customer customer = closeEvent.getValue("customer");
                        Status status = closeEvent.getValue("status");
                        // process entered values...
                    }
                })
                .show();
    }
    // end::create-input-dialog[]
    // tag::create-input-dialog-params[]
    @Subscribe("inpDlgParamsBtn")
    public void onInpDlgParamsBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption("Enter some values")
                .withParameters(
                        InputParameter.stringParameter("name").withCaption("Name"),
                        InputParameter.parameter("customer")// <1>
                                .withField(() -> {
                                    EntityComboBox<Customer> field = uiComponents.create(
                                            EntityComboBox.of(Customer.class));
                                    field.setOptionsList(dataManager.load(Customer.class).all().list());
                                    field.setCaption("Customer");// <2>
                                    field.setWidthFull();
                                    return field;
                                })
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String name = closeEvent.getValue("name");
                        Customer customer = closeEvent.getValue("customer");// <3>
                        // process entered values...
                    }
                })
                .show();
    }
    // end::create-input-dialog-params[]
    // tag::create-input-dialog-actions[]
    @Subscribe("inpDlgActionsBtn")
    public void onInpDlgActionsBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption("Enter some values")
                .withParameters(
                        InputParameter.stringParameter("name").withCaption("Name")
                )
                .withActions(// <1>
                        InputDialogAction.action("confirm")
                                .withCaption("Confirm")
                                .withPrimary(true)
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    String name = dialog.getValue("name");// <2>
                                    dialog.closeWithDefaultAction();// <3>
                                    // process entered values...
                                }),
                        InputDialogAction.action("refuse")
                                .withCaption("Refuse")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }
    // end::create-input-dialog-actions[]
    // tag::create-input-dialog-validator[]
    @Subscribe("inpDlgValidBtn")
    public void onInpDlgValidBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption("Enter some values")
                .withParameters(
                        InputParameter.stringParameter("name").withCaption("Name"),
                        InputParameter.entityParameter("customer", Customer.class).withCaption("Customer")
                )
                .withValidator(context -> {// <1>
                    String name = context.getValue("name");// <2>
                    Customer customer = context.getValue("customer");
                    if (Strings.isNullOrEmpty(name) && customer == null) {
                        return ValidationErrors.of("Enter name or select a customer");
                    }
                    return ValidationErrors.none();
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String name = closeEvent.getValue("name");
                        Customer customer = closeEvent.getValue("customer");
                        // process entered values...
                    }
                })
                .show();
    }
    // end::create-input-dialog-validator[]
    // tag::create-exception-dialog[]
    @Subscribe("expDlgBtn")
    public void onExpDlgBtnClick(Button.ClickEvent event) {
        try {
            int d = 0;
            int a = 42 / d;
        }
        catch (ArithmeticException e) {
            dialogs.createExceptionDialog()
                    .withCaption("Alert")
                    .withMessage("Division by zero")
                    .withThrowable(e.fillInStackTrace())
                    .show();
        }
    }
    // end::create-exception-dialog[]
    @Subscribe("backgroundDlgBtn")
    public void onBackgroundDlgBtnClick(Button.ClickEvent event) {
        BackgroundTask<Integer, Void> task = new BackgroundTask<Integer, Void>(300, this) {
            @Override
            public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception{
                for(int i = 1; i <= ITERATIONS; i++) {
                    TimeUnit.SECONDS.sleep(2);
                    taskLifeCycle.publish(i);
                }
                return null;
            }

            @Override
            public void progress(List<Integer> changes){
                double lastValue = changes.get(changes.size() - 1);
                //progressBar.setValue((lastValue / ITERATIONS));
            }
        };

        BackgroundTaskHandler taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();
        dialogs.createBackgroundWorkDialog(this,task)
                .withCaption("Please wait")
                .withMessage("Data loading continues")
                .withShowProgressInPercentage(true)
                .withCancelAllowed(true)
                .show();
    }
    // tag::create-simple-notification[]
    @Subscribe("simple")
    public void onSimpleClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Upload successful!")
                .show();
    }
    // end::create-simple-notification[]
    // tag::create-simple-notification-desc[]
    @Subscribe("withDescription")
    public void onWithDescriptionClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Upload complete!")
                .withDescription("Your file was uploaded successfully")
                .show();
    }
    // end::create-simple-notification-desc[]
    // tag::create-simple-notification-content[]
    @Subscribe("withContent")
    public void onWithContentClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("<i>Upload complete!</i>")
                .withContentMode(ContentMode.HTML)
                .show();
    }
    // end::create-simple-notification-content[]
    // tag::create-simple-notification-sanitize[]
    @Subscribe("showNotificationOnBtn")
    public void onShowNotificationOnBtnClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Notification with Sanitizer")
                .withDescription(UNSAFE_HTML)
                .withContentMode(ContentMode.HTML)
                .withHtmlSanitizer(true)
                .show();
    }

    @Subscribe("showNotificationOffBtn")
    public void onShowNotificationOffBtnClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Notification without Sanitizer")
                .withDescription(UNSAFE_HTML)
                .withContentMode(ContentMode.HTML)
                .withHtmlSanitizer(false)
                .show();
    }
    // end::create-simple-notification-sanitize[]
    // tag::create-simple-notification-tray[]
    @Subscribe("withType")
    public void onWithTypeClick(Button.ClickEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Upload complete!")
                .withDescription("Your file was uploaded successfully")
                .show();
    }
    // end::create-simple-notification-tray[]
    @Subscribe("humanized")
    public void onHumanizedClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Humanized notification!")
                .withType(Notifications.NotificationType.HUMANIZED)
                .withDescription("Description text goes here")
                .show();
    }

    @Subscribe("warning")
    public void onWarningClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Warning notification")
                .withType(Notifications.NotificationType.WARNING)
                .withDescription("Description text goes here")
                .show();
    }

    @Subscribe("error")
    public void onErrorClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Error notification")
                .withType(Notifications.NotificationType.ERROR)
                .withDescription("Description text goes here")
                .show();
    }

    @Subscribe("system")
    public void onSystemClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("System notification!")
                .withType(Notifications.NotificationType.SYSTEM)
                .withDescription("Description text goes here")
                .show();
    }

    @Subscribe("tray")
    public void onTrayClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Tray notification")
                .withType(Notifications.NotificationType.TRAY)
                .withDescription("Description text goes here")
                .show();
    }

    @Subscribe("position")
    public void onPositionClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("Tray notification")
                .withType(Notifications.NotificationType.TRAY)
                .withDescription("Description text goes here")
                .withPosition(Notifications.Position.DEFAULT)
                .show();
    }

}