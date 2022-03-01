package emailtemplate.ex1.screen.subscription;

import io.jmix.ui.screen.*;
import emailtemplate.ex1.entity.Subscription;

@UiController("sample_Subscription.edit")
@UiDescriptor("subscription-edit.xml")
@EditedEntityContainer("subscriptionDc")
public class SubscriptionEdit extends StandardEditor<Subscription> {
}