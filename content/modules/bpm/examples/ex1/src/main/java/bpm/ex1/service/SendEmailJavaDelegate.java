package bpm.ex1.service;

import bpm.ex1.entity.User;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.common.engine.api.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// tag::java-delegate[]
@Component("smpl_SendEmailJavaDelegate")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SendEmailJavaDelegate implements JavaDelegate {

    private Expression addressee; // <1>
    private Expression emailSubject;
    private Expression emailBody;

    @Autowired
    private Emailer emailer;

    @Override
    public void execute(DelegateExecution execution) { // <2>
        User addresseeValue = (User) addressee.getValue(execution); // <3>
        String emailSubjectValue = (String) emailSubject.getValue(execution);
        String emailBodyValue = (String) emailBody.getValue(execution);
        EmailInfo emailInfo = EmailInfoBuilder.create() // <4>
                .setAddresses(addresseeValue.getEmail())
                .setSubject(emailSubjectValue)
                .setFrom(null)
                .setBody(emailBodyValue)
                .build();
        emailer.sendEmailAsync(emailInfo); // <5>
    }
}
// end::java-delegate[]
