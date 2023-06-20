package testing.ex1.app.customer;

import io.jmix.email.EmailException;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import testing.ex1.entity.Customer;

@Component
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private Emailer emailer;

    public boolean notifyCustomerAboutOrderDelivered(Customer customer) {
        EmailInfo emailInfo = EmailInfoBuilder.create(
                        customer.getEmail(),
                        "Order Delivered",
                        "Your order has been delivered"
                )
                .build();

        try {
            emailer.sendEmail(emailInfo);
        }
        catch (EmailException e) {
            log.error("Failed to send email", e);
            return false;
        }
        return true;
    }
}
