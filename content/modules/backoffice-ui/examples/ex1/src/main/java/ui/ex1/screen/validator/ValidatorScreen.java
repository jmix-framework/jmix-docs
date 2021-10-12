package ui.ex1.screen.validator;

import io.jmix.ui.component.*;
import io.jmix.ui.component.validation.*;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

@UiController("sample_ValidatorScreen")
@UiDescriptor("validator-screen.xml")
public class ValidatorScreen extends Screen {
    // tag::inject-numberField[]
    @Autowired
    protected TextField numberField;

    // end::inject-numberField[]
    // tag::inject-numberField2[]
    @Autowired
    protected TextField numberField2;

    // end::inject-numberField2[]
    // tag::inject-numberField3[]
    @Autowired
    protected TextField numberField3;

    // end::inject-numberField3[]
    // tag::inject-numberField4[]
    @Autowired
    protected TextField numberField4;

    // end::inject-numberField4[]
    // tag::inject-numberField5[]
    @Autowired
    protected TextField numberField5;

    // end::inject-numberField5[]
    // tag::inject-dateTimeField[]
    @Autowired
    protected DateField dateTimeField;

    // end::inject-dateTimeField[]
    // tag::inject-localTimeField[]
    @Autowired
    protected TimeField localTimeField;

    // end::inject-localTimeField[]
    // tag::inject-numberField8[]
    @Autowired
    protected TextField numberField8;

    // end::inject-numberField8[]
    // tag::inject-numberField9[]
    @Autowired
    protected TextField numberField9;

    // end::inject-numberField9[]
    // tag::inject-numberField10[]
    @Autowired
    protected TextField numberField10;

    // end::inject-numberField10[]
    // tag::inject-numberField11[]
    @Autowired
    protected TextField numberField11;

    // end::inject-numberField11[]
    // tag::inject-numberField12[]
    @Autowired
    protected TextField numberField12;

    // end::inject-numberField12[]
    // tag::inject-numberField13[]
    @Autowired
    protected TextField numberField13;

    // end::inject-numberField13[]
    // tag::inject-numberField14[]
    @Autowired
    protected TextField numberField14;

    // end::inject-numberField14[]
    // tag::inject-dateField[]
    @Autowired
    protected DateField dateField;

    // end::inject-dateField[]
    // tag::inject-dateField16[]
    @Autowired
    protected DateField dateField16;

    // end::inject-dateField16[]
    // tag::inject-numberField17[]
    @Autowired
    protected TextField numberField17;

    // end::inject-numberField17[]
    // tag::inject-numberField18[]
    @Autowired
    protected TextField numberField18;

    // end::inject-numberField18[]
    // tag::inject-numberField19[]
    @Autowired
    protected TextField numberField19;

    // end::inject-numberField19[]
    // tag::inject-numberField20[]
    @Autowired
    protected TextField numberField20;

    // end::inject-numberField20[]
    // tag::inject-twinColumn[]
    @Autowired
    protected TwinColumn twinColumn;

    // end::inject-twinColumn[]
    // tag::inject-numberField23[]
    @Autowired
    protected TextField numberField23;

    // end::inject-numberField23[]
    // tag::inject-zipField[]
    @Autowired
    protected TextField<String> zipField;

    // end::inject-zipField[]
    @Autowired
    protected TextField numberField22;
    // tag::inject-application-context[]
    @Autowired
    protected ApplicationContext applicationContext;

    // end::inject-application-context[]
    // tag::inject-textField[]
    @Autowired
    protected TextField textField;

    // end::inject-textField[]

    @Subscribe("validateBtn1")
    protected void onValidateBtn1Click(Button.ClickEvent event) {
        numberField.validate();
    }
    // tag::decimal-max-validator[]
    @Subscribe("addValidBtn1")
    protected void onAddValidBtn1Click(Button.ClickEvent event) {
        DecimalMaxValidator maxValidator = applicationContext
                .getBean(DecimalMaxValidator.class, new BigDecimal(1000));
        numberField.addValidator(maxValidator);
    }
    // end::decimal-max-validator[]
    @Subscribe("validateBtn2")
    protected void onValidateBtn2Click(Button.ClickEvent event) {
        numberField2.validate();
    }

    // tag::decimal-min-validator[]
    @Subscribe("addValidBtn2")
    protected void onAddValidBtn2Click(Button.ClickEvent event) {
        DecimalMinValidator minValidator = applicationContext
                .getBean(DecimalMinValidator.class, new BigDecimal(100));
        numberField2.addValidator(minValidator);
    }
    // end::decimal-min-validator[]
    @Subscribe("validateBtn3")
    protected void onValidateBtn3Click(Button.ClickEvent event) {
        numberField3.validate();
    }

    // tag::digits-validator[]
    @Subscribe("addValidBtn3")
    protected void onAddValidBtn3Click(Button.ClickEvent event) {
        DigitsValidator digitsValidator = applicationContext
                .getBean(DigitsValidator.class, 3, 2);
        numberField3.addValidator(digitsValidator);
    }
    // end::digits-validator[]
    @Subscribe("validateBtn4")
    protected void onValidateBtn4Click(Button.ClickEvent event) {
        numberField4.validate();
    }

    // tag::double-max-validator[]
    @Subscribe("addValidBtn4")
    protected void onAddValidBtn4Click(Button.ClickEvent event) {
        DoubleMaxValidator maxValidator = applicationContext
                .getBean(DoubleMaxValidator.class,new Double(1000));
        numberField4.addValidator(maxValidator);
    }
    // end::double-max-validator[]
    @Subscribe("validateBtn5")
    protected void onValidateBtn5Click(Button.ClickEvent event) {
        numberField5.validate();
    }
    // tag::double-min-validator[]
    @Subscribe("addValidBtn5")
    protected void onAddValidBtn5Click(Button.ClickEvent event) {
        DoubleMinValidator minValidator = applicationContext
                .getBean(DoubleMinValidator.class,new Double(100));
        numberField5.addValidator(minValidator);
    }
    // end::double-min-validator[]
    @Subscribe("validateBtn6")
    protected void onValidateBtn6Click(Button.ClickEvent event) {
        dateTimeField.validate();
    }
    // tag::future-or-present-validator[]
    @Subscribe("addValidBtn6")
    protected void onAddValidBtn6Click(Button.ClickEvent event) {
        FutureOrPresentValidator futureOrPresentValidator = applicationContext
                .getBean(FutureOrPresentValidator.class);
        dateTimeField.addValidator(futureOrPresentValidator);
    }
    // end::future-or-present-validator[]
    @Subscribe("validateBtn7")
    protected void onValidateBtn7Click(Button.ClickEvent event) {
        localTimeField.validate();
    }
    // tag::future-validator[]
    @Subscribe("addValidBtn7")
    protected void onAddValidBtn7Click(Button.ClickEvent event) {
        FutureValidator futureValidator = applicationContext
                .getBean(FutureValidator.class);
        localTimeField.addValidator(futureValidator);
    }
    // end::future-validator[]
    @Subscribe("validateBtn8")
    protected void onValidateBtn8Click(Button.ClickEvent event) {
        numberField8.validate();
    }
    // tag::max-validator[]
    @Subscribe("addValidBtn8")
    protected void onAddValidBtn8Click(Button.ClickEvent event) {
        MaxValidator maxValidator = applicationContext
                .getBean(MaxValidator.class, 20500);
        numberField8.addValidator(maxValidator);
    }
    // end::max-validator[]
    @Subscribe("validateBtn9")
    protected void onValidateBtn9Click(Button.ClickEvent event) {
        numberField9.validate();
    }
    // tag::min-validator[]
    @Subscribe("addValidBtn9")
    protected void onAddValidBtn9Click(Button.ClickEvent event) {
        MinValidator minValidator = applicationContext
                .getBean(MinValidator.class,30);
        numberField9.addValidator(minValidator);
    }
    // end::min-validator[]
    @Subscribe("validateBtn10")
    protected void onValidateBtn10Click(Button.ClickEvent event) {
        numberField10.validate();
    }
    // tag::negative-or-zero-validator[]
    @Subscribe("addValidBtn10")
    protected void onAddValidBtn10Click(Button.ClickEvent event) {
        NegativeOrZeroValidator negativeOrZeroValidator = applicationContext
                .getBean(NegativeOrZeroValidator.class);
        numberField10.addValidator(negativeOrZeroValidator);
    }
    // end::negative-or-zero-validator[]
    @Subscribe("validateBtn11")
    protected void onValidateBtn11Click(Button.ClickEvent event) {
        numberField11.validate();
    }
    // tag::negative-validator[]
    @Subscribe("addValidBtn11")
    protected void onAddValidBtn11Click(Button.ClickEvent event) {
        NegativeValidator negativeValidator = applicationContext
                .getBean(NegativeValidator.class);
        numberField11.addValidator(negativeValidator);
    }
    // end::negative-validator[]
    @Subscribe("validateBtn12")
    protected void onValidateBtn12Click(Button.ClickEvent event) {
        numberField12.validate();
    }

    // tag::not-blank-validator[]
    @Subscribe("addValidBtn12")
    protected void onAddValidBtn12Click(Button.ClickEvent event) {
        NotBlankValidator notBlankValidator = applicationContext
                .getBean(NotBlankValidator.class);
        numberField11.addValidator(notBlankValidator);
    }
    // end::not-blank-validator[]
    @Subscribe("validateBtn13")
    protected void onValidateBtn13Click(Button.ClickEvent event) {
        numberField13.validate();
    }
    // tag::not-empty-validator[]
    @Subscribe("addValidBtn13")
    protected void onAddValidBtn13Click(Button.ClickEvent event) {
        NotEmptyValidator notEmptyValidator = applicationContext
                .getBean(NotEmptyValidator.class);
        numberField13.addValidator(notEmptyValidator);
    }
    // end::not-empty-validator[]
    @Subscribe("validateBtn14")
    protected void onValidateBtn14Click(Button.ClickEvent event) {
        numberField14.validate();
    }
    // tag::not-null-validator[]
    @Subscribe("addValidBtn14")
    protected void onAddValidBtn14Click(Button.ClickEvent event) {
        NotNullValidator notNullValidator = applicationContext
                .getBean(NotNullValidator.class);
        numberField14.addValidator(notNullValidator);
    }
    // end::not-null-validator[]
    @Subscribe("validateBtn15")
    protected void onValidateBtn15Click(Button.ClickEvent event) {
        dateField.validate();
    }
    // tag::past-or-present-validator[]
    @Subscribe("addValidBtn15")
    protected void onAddValidBtn15Click(Button.ClickEvent event) {
        PastOrPresentValidator pastOrPresentValidator = applicationContext
                .getBean(PastOrPresentValidator.class);
        dateField.addValidator(pastOrPresentValidator);
    }
    // end::past-or-present-validator[]
    @Subscribe("validateBtn16")
    protected void onValidateBtn16Click(Button.ClickEvent event) {
        dateField16.validate();
    }

    // tag::past-validator[]
    @Subscribe("addValidBtn16")
    protected void onAddValidBtn16Click(Button.ClickEvent event) {
        PastValidator pastValidator = applicationContext
                .getBean(PastValidator.class);
        dateField16.addValidator(pastValidator);
    }
    // end::past-validator[]
    @Subscribe("validateBtn17")
    protected void onValidateBtn17Click(Button.ClickEvent event) {
        numberField17.validate();
    }
    // tag::positive-or-zero-validator[]
    @Subscribe("addValidBtn17")
    protected void onAddValidBtn17Click(Button.ClickEvent event) {
        PositiveOrZeroValidator positiveOrZeroValidator = applicationContext
                .getBean(PositiveOrZeroValidator.class);
        numberField17.addValidator(positiveOrZeroValidator);
    }
    // end::positive-or-zero-validator[]
    @Subscribe("validateBtn18")
    protected void onValidateBtn18Click(Button.ClickEvent event) {
        numberField18.validate();
    }
    // tag::positive-validator[]
    @Subscribe("addValidBtn18")
    protected void onAddValidBtn18Click(Button.ClickEvent event) {
        PositiveValidator positiveValidator = applicationContext
                .getBean(PositiveValidator.class);
        numberField18.addValidator(positiveValidator);
    }
    // end::positive-validator[]
    @Subscribe("validateBtn19")
    protected void onValidateBtn19Click(Button.ClickEvent event) {
        numberField19.validate();
    }
    // tag::regexp-validator[]
    @Subscribe("addValidBtn19")
    protected void onAddValidBtn19Click(Button.ClickEvent event) {
        RegexpValidator regexpValidator = applicationContext
                .getBean(RegexpValidator.class);
        numberField19.addValidator(regexpValidator);
    }
    // end::regexp-validator[]
    @Subscribe("validateBtn20")
    protected void onValidateBtn20Click(Button.ClickEvent event) {
        numberField20.validate();
    }
    // tag::size-validator[]
    @Subscribe("addValidBtn20")
    protected void onAddValidBtn20Click(Button.ClickEvent event) {
        SizeValidator sizeValidator = applicationContext
                .getBean(SizeValidator.class);
        numberField20.addValidator(sizeValidator);
    }
    // end::size-validator[]
    @Subscribe("validateBtn21")
    protected void onValidateBtn21Click(Button.ClickEvent event) {
        twinColumn.validate();
    }

    @Subscribe("addValidBtn21")
    protected void onAddValidBtn21Click(Button.ClickEvent event) {
        SizeValidator sizeValidator = applicationContext
                .getBean(SizeValidator.class);
        twinColumn.addValidator(sizeValidator);
    }

    @Subscribe("validateBtn23")
    protected void onValidateBtn23Click(Button.ClickEvent event) {
        numberField23.validate();
    }
    // tag::email-validator[]
    @Subscribe("addValidBtn23")
    protected void onAddValidBtn23Click(Button.ClickEvent event) {
        EmailValidator emailValidator = applicationContext
                .getBean(EmailValidator.class);
        numberField23.addValidator(emailValidator);
    }
    // end::email-validator[]
    @Subscribe("validateBtn24")
    protected void onValidateBtn24Click(Button.ClickEvent event) {
        zipField.validate();
    }

    @Subscribe("addValidBtn24")
    protected void onAddValidBtn24Click(Button.ClickEvent event) {
        // tag::add-validator[]
        zipField.addValidator(value -> {
            if (value != null && value.length() != 6)
                throw new ValidationException("Zip must be of 6 characters length");
        });
        // end::add-validator[]
    }

    @Subscribe("validateBtn22")
    protected void onValidateBtn22Click(Button.ClickEvent event) {
        numberField22.validate();
    }

    @Subscribe("addValidBtn25")
    protected void onAddValidBtn25Click(Button.ClickEvent event) {
        // tag::get-validator[]
        PositiveValidator validator = applicationContext.getBean(PositiveValidator.class);
        // end::get-validator[]
        // tag::add-predefined-validator[]
        textField.addValidator(applicationContext.getBean(PositiveValidator.class));
        // end::add-predefined-validator[]
    }

}