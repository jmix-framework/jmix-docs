package ui.ex1.exception;

import io.jmix.ui.Notifications;
import io.jmix.ui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component("uiex1_ZeroAccountBalanceExceptionHandler")
public class ZeroAccountBalanceExceptionHandler  extends AbstractUiExceptionHandler {

    public ZeroAccountBalanceExceptionHandler() {
        super(ZeroAccountBalanceException.class.getName());
    }
    // tag::do-handle[]
    @Override
    protected void doHandle(String className, String message,
                            @Nullable Throwable throwable, UiContext context) {
        if (throwable != null) {
            context.getDialogs().createExceptionDialog()
                    .withThrowable(throwable)
                    .withCaption("Error")
                    .withMessage(message)
                    .show();
        } else {
            context.getNotifications().create(Notifications.NotificationType.ERROR)
                    .withCaption("Error")
                    .withDescription(message)
                    .show();
        }
    }
    // end::do-handle[]
}