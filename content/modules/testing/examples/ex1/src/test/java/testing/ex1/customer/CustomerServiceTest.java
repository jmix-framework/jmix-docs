package testing.ex1.customer;

import io.jmix.core.DataManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import testing.ex1.app.customer.CustomerService;
import testing.ex1.entity.Customer;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    DataManager dataManager;

    // tag::cleanup-customer-table[]

    @Autowired // <1>
    DataSource dataSource;

    @AfterEach// <2>
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbc, "CUSTOMER"); // <3>
    }
    // end::cleanup-customer-table[]

    // tag::find-by-email-found[]
    @Test
    void given_customerWithEmailExists_when_findByEmail_then_customerFound() {

        // given
        Customer customer = dataManager.create(Customer.class);
        customer.setEmail("customer@test.com");
        dataManager.save(customer); // <1>

        // when
        Optional<Customer> foundCustomer = customerService.findByEmail("customer@test.com");  // <2>

        // then
        assertThat(foundCustomer)
                .isPresent();
    }

    // end::find-by-email-found[]
    @Test
    void given_customerWithEmailDoesNotExist_when_findByEmail_then_nothingFound() {

        // given
        Customer customer = dataManager.create(Customer.class);
        customer.setEmail("customer@test.com");
        dataManager.save(customer);

        // when
        Optional<Customer> foundCustomer = customerService.findByEmail("other-customer@company.com");

        // then
        assertThat(foundCustomer)
                .isNotPresent();
    }
}
