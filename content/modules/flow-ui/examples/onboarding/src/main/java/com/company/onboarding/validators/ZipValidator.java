package com.company.onboarding.validators;

import io.jmix.flowui.component.validation.Validator;
import io.jmix.flowui.exception.ValidationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// tag::ZipValidator[]
@Component("ZipValidator")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ZipValidator implements Validator<String> {
    @Override
    public void accept(String value) throws ValidationException {
        if (value != null && value.length() != 6)
            throw new ValidationException("Zip must be of 6 characters length");
    }
}
// end::ZipValidator[]
