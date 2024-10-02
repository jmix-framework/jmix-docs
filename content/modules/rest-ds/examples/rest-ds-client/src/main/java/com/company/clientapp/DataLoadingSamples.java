package com.company.clientapp;

import com.company.clientapp.entity.Customer;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataLoadingSamples {

    private final DataManager dataManager;

    public DataLoadingSamples(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // tag::by-condition[]
    List<Customer> loadByCondition(String lastName) {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.equal("lastName", lastName))
                .list();
    }
    // end::by-condition[]

// tag::by-query[]
List<Customer> loadByQuery(String lastName) {
    String query = """
    {
      "property": "lastName",
      "operator": "=",
      "value": "%s"
    }
    """.formatted(lastName);

    return dataManager.load(Customer.class)
            .query(query)
            .list();
}
// end::by-query[]

    // tag::by-ids[]
    List<Customer> loadByIdentifiers(UUID id1, UUID id2, UUID id3) {
        return dataManager.load(Customer.class)
                .ids(id1, id2, id3)
                .list();
    }
    // end::by-ids[]
}

