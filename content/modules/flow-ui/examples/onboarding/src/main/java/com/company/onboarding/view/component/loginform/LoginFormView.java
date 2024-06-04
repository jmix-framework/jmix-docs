package com.company.onboarding.view.component.loginform;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.AccessDeniedException;
import io.jmix.flowui.component.loginform.JmixLoginForm;
import io.jmix.flowui.kit.component.loginform.JmixLoginI18n;
import io.jmix.flowui.view.*;
import io.jmix.securityflowui.authentication.AuthDetails;
import io.jmix.securityflowui.authentication.LoginViewSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.util.Locale;

import static org.reflections.Reflections.log;

@Route(value = "LoginFormView", layout = MainView.class)
@ViewController("LoginFormView")
@ViewDescriptor("login-form-view.xml")
public class LoginFormView extends StandardView {

    @Autowired
    private LoginViewSupport loginViewSupport;
    @ViewComponent
    private JmixLoginForm login;

    @Autowired
    private MessageBundle messageBundle;


    // tag::exception-handling[]
    @Subscribe("login")
    public void onLogin(final AbstractLogin.LoginEvent event) {
        try {
            loginViewSupport.authenticate(
                    AuthDetails.of(event.getUsername(), event.getPassword())
                            .withLocale(login.getSelectedLocale())
                            .withRememberMe(login.isRememberMe())
            );
        }
        catch (final BadCredentialsException | DisabledException | LockedException | AccessDeniedException e) {
            if (e instanceof LockedException) {
                JmixLoginI18n loginI18n = createLoginI18n();
                loginI18n.getErrorMessage().setMessage(e.getMessage()); // <1>
                login.setI18n(loginI18n);
            } else {
                login.setI18n(createLoginI18n());
            }

            log.warn("Login failed for user '{}': {}", event.getUsername(), e.toString()); // <2>
            event.getSource().setError(true);
        }
    }
    // end::exception-handling[]

    private JmixLoginI18n createLoginI18n() {
        final JmixLoginI18n loginI18n = JmixLoginI18n.createDefault();

        final JmixLoginI18n.JmixForm form = new JmixLoginI18n.JmixForm();
        form.setTitle(messageBundle.getMessage("loginForm.headerTitle"));
        form.setUsername(messageBundle.getMessage("loginForm.username"));
        form.setPassword(messageBundle.getMessage("loginForm.password"));
        form.setSubmit(messageBundle.getMessage("loginForm.submit"));
        form.setForgotPassword(messageBundle.getMessage("loginForm.forgotPassword"));
        form.setRememberMe(messageBundle.getMessage("loginForm.rememberMe"));
        loginI18n.setForm(form);

        final LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setTitle(messageBundle.getMessage("loginForm.errorTitle"));
        errorMessage.setMessage(messageBundle.getMessage("loginForm.badCredentials"));
        errorMessage.setUsername(messageBundle.getMessage("loginForm.errorUsername"));
        errorMessage.setPassword(messageBundle.getMessage("loginForm.errorPassword"));
        loginI18n.setErrorMessage(errorMessage);

        return loginI18n;
    }
    // tag::localeItemLabelGenerator[]
    @Install(to = "login", subject = "localeItemLabelGenerator")
    private String loginLocaleItemLabelGenerator(final Locale locale) {
        return locale.getISO3Language().toUpperCase();
    }
    // end::localeItemLabelGenerator[]
}