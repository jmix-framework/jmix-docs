package security.ex1.security;

import io.jmix.securityui.password.PasswordValidationContext;
import io.jmix.securityui.password.PasswordValidationException;
import io.jmix.securityui.password.PasswordValidator;
import org.springframework.stereotype.Component;
import security.ex1.entity.User;

@Component
public class MyPasswordValidator implements PasswordValidator<User> {

    @Override
    public void validate(PasswordValidationContext<User> context) throws PasswordValidationException {
        if (context.getPassword().length() < 3)
            throw new PasswordValidationException("Password is too short, must be >= 3 characters");
    }
}
