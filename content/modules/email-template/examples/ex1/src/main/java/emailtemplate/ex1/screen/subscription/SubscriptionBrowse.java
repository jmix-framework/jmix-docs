package emailtemplate.ex1.screen.subscription;

import emailtemplate.ex1.listener.SubscriptionEventListener;
import io.jmix.emailtemplates.EmailTemplates;
import io.jmix.emailtemplates.entity.EmailTemplate;
import io.jmix.emailtemplates.exception.TemplateNotFoundException;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.ItemTrackingAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import emailtemplate.ex1.entity.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

@UiController("sample_Subscription.browse")
@UiDescriptor("subscription-browse.xml")
@LookupComponent("subscriptionsTable")
public class SubscriptionBrowse extends StandardLookup<Subscription> {

    private static final Logger log =
            LoggerFactory.getLogger(SubscriptionEventListener.class);
    @Autowired
    protected GroupTable<Subscription> subscriptionsTable;
    @Autowired
    protected Button bTempBtn;

    @Inject
    EmailTemplates emailTemplates;

    public static final String CREATED_TEMPLATE_CODE = "subscription_created";

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        Action buildTemplateAction = new ItemTrackingAction("buildTemplateAction").
                withHandler(actionPerformedEvent -> onBuildTemplateClick());
        subscriptionsTable.addAction(buildTemplateAction);
        bTempBtn.setAction(buildTemplateAction);
    }

    protected void onBuildTemplateClick() {
        Subscription subscription = subscriptionsTable.getSingleSelected();
        if (subscription != null) {
            try {
                // tag::build-template[]
                EmailTemplate newTemplate = emailTemplates.buildFromTemplate(CREATED_TEMPLATE_CODE)
                        .setSubject("Test subject")
                        .setTo("address@haulmont.com")
                        .setBodyParameter("subscription", subscription)
                        .setBodyParameter("customer", subscription.getCustomer())
                        .build();
                // end::build-template[]
            } catch (TemplateNotFoundException e) {
                log.info(e.getMessage());
            }
        }
    }
}