package ui.ex1.screen.component.datagrid;

import io.jmix.ui.component.data.aggregation.AggregationStrategy;
import org.apache.commons.collections4.CollectionUtils;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Hobby;

import java.util.Collection;
// tag::aggregation-strategy[]
public class CustomerHobbyAggregation implements AggregationStrategy<Hobby, String> {
    @Override
    public String aggregate(Collection<Hobby> propertyValues) {
        Hobby mostFrequent = null;
        long max = 0;
        if (CollectionUtils.isNotEmpty(propertyValues)) {
            for (Hobby hobby : Hobby.values()) {
                long current = propertyValues.stream()
                        .filter(customerHobby -> customerHobby.equals(hobby))
                        .count();

                if (current > max) {
                    mostFrequent = hobby;
                    max = current;
                }
            }
        }

        if (mostFrequent != null) {
            return String.format("%s: %d/%d",
                    mostFrequent.name(), max, propertyValues.size());
        }

        return null;
    }

    @Override
    public Class<String> getResultClass() {
        return String.class;
    }
}
// end::aggregation-strategy[]
