package ui.ex1.screen.validator;

import io.jmix.ui.component.ValidationException;
import io.jmix.ui.component.validation.Validator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// tag::zip-validator[]
@Component("ui_ZipValidator")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ZipValidator implements Validator<String> {
    @Override
    public void accept(String value) throws ValidationException {
        if (value != null && value.length() != 6)
            throw new ValidationException("Zip must be of 6 characters length");
    }
}
// end::zip-validator[]