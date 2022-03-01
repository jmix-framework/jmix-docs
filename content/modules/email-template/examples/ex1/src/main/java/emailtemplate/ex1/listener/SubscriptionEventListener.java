package emailtemplate.ex1.listener;

import emailtemplate.ex1.entity.Subscription;
import io.jmix.core.event.EntitySavingEvent;
import io.jmix.email.EmailException;
import io.jmix.emailtemplates.EmailTemplates;
import io.jmix.emailtemplates.exception.ReportParameterTypeChangedException;
import io.jmix.emailtemplates.exception.TemplateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

// tag::subscription-event-listener[]
@Component
public class SubscriptionEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(SubscriptionEventListener.class);

    @Inject
    EmailTemplates emailTemplates;  // <1>

    public static final String CREATED_TEMPLATE_CODE = "subscription_created";

    @EventListener
    void onSubscriptionSaving(EntitySavingEvent<Subscription> event) {
        if (event.isNewEntity()) {
            try {
                emailTemplates.buildFromTemplate(CREATED_TEMPLATE_CODE)
                        .setTo(event.getEntity().getCustomer().getEmail()) // <2>
                        .setBodyParameter("subscription", event.getEntity()) // <3>
                        .setBodyParameter("customer", event.getEntity().getCustomer()) // <4>
                        .sendEmail(); // <5>
            } catch (TemplateNotFoundException | EmailException |
                    ReportParameterTypeChangedException e) {
                log.info(e.getMessage());
            }
        }
    }
}
// end::subscription-event-listener[]
