package com.company.onboarding.formatters;

import io.jmix.flowui.kit.component.formatter.Formatter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

// tag::CurrencyFormatter[]
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencyFormatter implements Formatter<BigDecimal> {

    @Override
    public String apply(BigDecimal value) {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(value);
    }
}
// end::CurrencyFormatter[]