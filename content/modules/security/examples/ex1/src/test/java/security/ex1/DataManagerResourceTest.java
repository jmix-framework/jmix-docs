package security.ex1;

import io.jmix.core.AccessConstraintsRegistry;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.constraint.RowLevelConstraint;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import security.ex1.entity.Customer;
import security.ex1.entity.CustomerDetail;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataManagerResourceTest {

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    UnconstrainedDataManager unconstrainedDataManager;

    @Autowired
    DataManager dataManager;

    @Autowired
    AccessConstraintsRegistry accessConstraintsRegistry;

    @Autowired
    DataSource dataSource;

    private UUID customerId;

    private static final Logger log = LoggerFactory.getLogger(DataManagerResourceTest.class);

    @BeforeEach
    void setUp() {
        Customer customer = dataManager.create(Customer.class);
        customer.setName("test-customer-1");
        customer.setConfidentialInfo("111");
        authenticator.withSystem(() -> dataManager.save(customer));
        customerId = customer.getId();
    }

    @AfterEach
    void tearDown() {
        new JdbcTemplate(dataSource).update("delete from SAMPLE_CUSTOMER where ID=?", customerId);
    }

    @Test
    void dmAppliesCrudConstraints() {
        authenticator.withUser(
                "johnson",
                () -> {
                    try {
                        dataManager.remove(Id.of(customerId, Customer.class));
                        fail();
                    } catch (Exception e) {
                        log.info("Cannot remove Customer: " + e);
                    }
                    return null;
                }
        );
    }

    @Test
    void unconstrainedDmDoesNotApplyCrudConstraints() {
        authenticator.withUser(
                "johnson",
                () -> {
                    unconstrainedDataManager.remove(Id.of(customerId, Customer.class));
                    return null;
                }
        );
    }

    @Test
    void anyDmDoesNotApplyAttributeConstraints() {
        List<Customer> customers = authenticator.withUser(
                "johnson",
                () -> unconstrainedDataManager.load(Customer.class).all().list()
        );
        for (Customer customer : customers) {
            assertNotNull(customer.getConfidentialInfo());
        }

        customers = authenticator.withUser(
                "johnson",
                () -> dataManager.load(Customer.class).all().list()
        );
        for (Customer customer : customers) {
            assertNotNull(customer.getConfidentialInfo());
        }
    }

    @Test
    void dmDoesNotApplyAttributeConstraintsForLoadValues() {
        List<KeyValueEntity> customers = authenticator.withUser(
                "butler",
                () -> dataManager.loadValues("select c.id, c.name, c.confidentialInfo from sample_Customer c")
                        .properties("id", "name", "confidentialInfo")
                        .list()
        );
        for (KeyValueEntity customer : customers) {
            assertNotNull(customer.getValue("confidentialInfo"));
        }
    }
}
