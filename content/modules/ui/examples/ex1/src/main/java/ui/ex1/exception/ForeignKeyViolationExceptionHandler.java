package ui.ex1.exception;

import io.jmix.ui.Notifications;
import io.jmix.ui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

// tag::foreign-key-violation-exception-handler[]
@Component("uiex1_ForeignKeyViolationExceptionHandler")
public class ForeignKeyViolationExceptionHandler extends AbstractUiExceptionHandler {

    public ForeignKeyViolationExceptionHandler() {
        super("java.sql.SQLIntegrityConstraintViolationException");
    }
    // end::foreign-key-violation-exception-handler[]
    protected void doHandle(String className, String message, @Nullable Throwable throwable, UiContext context) {
        context.getNotifications().create(Notifications.NotificationType.ERROR)
                .withCaption("Cannot delete object")
                .withDescription("There are references from other objects")
                .show();
    }
    // tag::foreign-key-violation-exception-handler[]
    /*...*/
}
// end::foreign-key-violation-exception-handler[]