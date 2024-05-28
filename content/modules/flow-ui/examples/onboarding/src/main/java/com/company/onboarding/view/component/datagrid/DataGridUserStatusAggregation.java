package com.company.onboarding.view.component.datagrid;

import com.company.onboarding.entity.OnboardingStatus;
import io.jmix.flowui.data.aggregation.AggregationStrategy;
import org.apache.commons.collections4.CollectionUtils;
import io.jmix.core.Messages;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

// tag::aggregatable-grid[]
public class DataGridUserStatusAggregation implements AggregationStrategy<OnboardingStatus, String> {

    @Autowired
    public Messages messages;

    @Override
    public String aggregate(Collection<OnboardingStatus> propertyValues) {
        OnboardingStatus mostFrequent = null;
        long max = 0;

        if (CollectionUtils.isNotEmpty(propertyValues)) {
            for (OnboardingStatus status : OnboardingStatus.values()) {
                long current = propertyValues.stream()
                        .filter(status :: equals)
                        .count();

                if (current > max) {
                    mostFrequent = status;
                    max = current;
                }
            }
        }

        if (mostFrequent != null) {
            String key = OnboardingStatus.class.getSimpleName() + "." + mostFrequent.name();
            return String.format("%s: %d/%d", messages.getMessage(OnboardingStatus.class, key), max, propertyValues.size());
        }

        return null;
    }

    @Override
    public Class<String> getResultClass() {
        return String.class;
    }
}
// end::aggregatable-grid[]