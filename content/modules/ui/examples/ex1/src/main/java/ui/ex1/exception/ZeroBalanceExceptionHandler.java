package ui.ex1.exception;

import io.jmix.ui.Notifications;
import io.jmix.ui.exception.AbstractUiExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

// tag::zero-balance-exception-handler[]
@Component("uiex1_ZeroBalanceExceptionHandler")
public class ZeroBalanceExceptionHandler extends AbstractUiExceptionHandler {

    // end::zero-balance-exception-handler[]
    // tag::constructor[]
    public ZeroBalanceExceptionHandler() {
        super(ZeroBalanceException.class.getName());
    }
    // end::constructor[]
    // tag::do-handle[]
    @Override
    protected void doHandle(String className, String message,
                            @Nullable Throwable throwable, UiContext context) {
        context.getNotifications().create(Notifications.NotificationType.ERROR)
                .withCaption("Error")
                .withDescription(message)
                .show();
    }
    // end::do-handle[]

    // tag::can-handle[]
    /*...*/

    @Override
    protected boolean canHandle(String className, String message,
                                @Nullable Throwable throwable) {
        return StringUtils.containsIgnoreCase(message,
                "Insufficient funds in your account");
    }
    // end::can-handle[]
    // tag::zero-balance-exception-handler[]
}
// end::zero-balance-exception-handler[]