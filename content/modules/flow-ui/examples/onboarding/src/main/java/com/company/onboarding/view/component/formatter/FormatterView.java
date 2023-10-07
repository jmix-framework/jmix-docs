package com.company.onboarding.view.component.formatter;


import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.formatter.CollectionFormatter;
import io.jmix.flowui.component.formatter.DateFormatter;
import io.jmix.flowui.component.formatter.NumberFormatter;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.component.valuepicker.JmixMultiValuePicker;
import io.jmix.flowui.component.valuepicker.JmixValuePicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Route(value = "FormatterView", layout = MainView.class)
@ViewController("FormatterView")
@ViewDescriptor("formatter-view.xml")
public class FormatterView extends StandardView {
    // tag::valuePicker[]
    @ViewComponent
    private JmixValuePicker<LocalDateTime> valuePicker;

    // end::valuePicker[]
    // tag::valueNumberPicker[]
    @ViewComponent
    private JmixValuePicker<BigDecimal> valueNumberPicker;

    // end::valueNumberPicker[]
    // tag::applicationContext[]
    @Autowired
    private ApplicationContext applicationContext;

    // end::applicationContext[]
    @ViewComponent
    private JmixValuePicker<BigDecimal> numberPicker;
    // tag::entityPicker[]
    @ViewComponent
    private EntityPicker<Department> entityPicker;

    // end::entityPicker[]
    @ViewComponent
    private JmixMultiValuePicker<String> stringsValuesPicker;

    @Subscribe("valuePicker.generate")
    public void onValuePickerGenerate(final ActionPerformedEvent event) {
        valuePicker.setValue(LocalDateTime.now());
    }

    @Subscribe("valueNumberPicker.generate")
    public void onValueNumberPickerGenerate(final ActionPerformedEvent event) {
        valueNumberPicker.setValue(new BigDecimal(3));
    }

    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::setFormatter[]
        NumberFormatter formatter = applicationContext.getBean(NumberFormatter.class);
        formatter.setFormat("#,##0.00");
        valueNumberPicker.setFormatter(formatter);
        // end::setFormatter[]
        // tag::setDateFormatter[]
        DateFormatter dateFormatter = applicationContext.getBean(DateFormatter.class);
        dateFormatter.setFormat("h:mm a");
        valuePicker.setFormatter(dateFormatter);
        // end::setDateFormatter[]
        // tag::customFormatter[]
        entityPicker.setFormatter(value -> value.getName() + " department");
        // end::customFormatter[]
        CollectionFormatter collectionFormatter = applicationContext.getBean(CollectionFormatter.class);
        stringsValuesPicker.setFormatter(collectionFormatter);
        // tag::onInit[]
    }
    // end::onInit[]
    @Subscribe("numberPicker.generate")
    public void onNumberPickerGenerate(final ActionPerformedEvent event) {
        numberPicker.setValue(new BigDecimal(100));
    }

}