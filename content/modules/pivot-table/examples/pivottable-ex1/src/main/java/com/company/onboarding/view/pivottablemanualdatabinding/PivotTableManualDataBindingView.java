package com.company.onboarding.view.pivottablemanualdatabinding;


import com.company.onboarding.entity.Day;
import com.company.onboarding.entity.Sex;
import com.company.onboarding.entity.Time;
import com.company.onboarding.entity.TipInfo;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Route(value = "pivot-table-manual-data-binding-view", layout = MainView.class)
@ViewController(id = "PivotTableManualDataBindingView")
@ViewDescriptor(path = "pivot-table-manual-data-binding-view.xml")
public class PivotTableManualDataBindingView extends StandardView {

    // tag::manual-create-items[]
    @Autowired
    protected DataManager dataManager;
    @ViewComponent
    private CollectionContainer<TipInfo> tipsDc;

    @Subscribe
    protected void onInit(final InitEvent event) {
        List<TipInfo> items = new ArrayList<>();
        items.add(createTipInfo(16.99, 1.01, Sex.FEMALE, false, Day.FRIDAY, Time.DINNER, 2));
        items.add(createTipInfo(10.34, 1.66, Sex.FEMALE, true, Day.THURSDAY, Time.LUNCH, 3));
        items.add(createTipInfo(21.01, 3.5, Sex.MALE, true, Day.FRIDAY, Time.LUNCH, 3));
        items.add(createTipInfo(23.68, 3.31, Sex.FEMALE, false, Day.MONDAY, Time.DINNER, 2));
        items.add(createTipInfo(24.59, 3.61, Sex.MALE, false, Day.TUESDAY, Time.LUNCH, 4));
        tipsDc.setItems(items);
    }

    private TipInfo createTipInfo(double totalBill, double tip, Sex sex, Boolean smoker, Day day, Time time, int size) {
        TipInfo tips = dataManager.create(TipInfo.class);
        tips.setTotalBill(new BigDecimal(totalBill));
        tips.setTip(new BigDecimal(tip));
        tips.setSex(sex);
        tips.setSmoker(smoker);
        tips.setDay(day);
        tips.setTime(time);
        tips.setSize(size);
        return tips;
    }
    // end::manual-create-items[]
}