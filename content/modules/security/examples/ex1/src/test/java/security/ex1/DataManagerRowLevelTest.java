package security.ex1;

import io.jmix.core.AccessConstraintsRegistry;
import io.jmix.core.DataManager;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.constraint.RowLevelConstraint;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import security.ex1.entity.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataManagerRowLevelTest {

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    UnconstrainedDataManager unconstrainedDataManager;

    @Autowired
    DataManager dataManager;

    @Autowired
    AccessConstraintsRegistry accessConstraintsRegistry;

    @Test
    void unconstrainedDmDoesNotApplyRowLevelRestrictions() {
        List<Customer> customers = authenticator.withUser(
                "butler",
                () -> unconstrainedDataManager.load(Customer.class).all().list()
        );
        assertTrue(customers.stream().anyMatch(customer -> customer.getRegion().equals("US")));
        assertTrue(customers.stream().anyMatch(customer -> customer.getRegion().equals("UK")));
    }

    @Test
    void unconstrainedDmWithExplicitRowLevelRestrictions() {
        List<Customer> customers = authenticator.withUser(
                "butler",
                () -> unconstrainedDataManager.load(Customer.class)
                        .all()
                        .accessConstraints(accessConstraintsRegistry.getConstraintsOfType(RowLevelConstraint.class))
                        .list()
        );
        assertTrue(customers.stream().anyMatch(customer -> customer.getRegion().equals("US")));
        assertFalse(customers.stream().anyMatch(customer -> customer.getRegion().equals("UK")));
    }

    @Test
    void dmAppliesRowLevelRestrictions() {
        List<Customer> customers = authenticator.withUser(
                "butler",
                () -> dataManager.load(Customer.class).all().list()
        );
        assertTrue(customers.stream().anyMatch(customer -> customer.getRegion().equals("US")));
        assertFalse(customers.stream().anyMatch(customer -> customer.getRegion().equals("UK")));
    }

    // todo: should pass when https://github.com/Haulmont/jmix-core/issues/165 is fixed
    @Test
    void dmAppliesRowLevelRestrictionsForLoadValues() {
        List<KeyValueEntity> customers = authenticator.withUser(
                "butler",
                () -> dataManager.loadValues("select c.id, c.name, c.region from sample_Customer c")
                        .properties("id", "name", "region")
                        .list()
        );
        assertTrue(customers.stream().anyMatch(customer -> customer.getValue("region").equals("US")));
        assertFalse(customers.stream().anyMatch(customer -> customer.getValue("region").equals("UK")));
    }
}
