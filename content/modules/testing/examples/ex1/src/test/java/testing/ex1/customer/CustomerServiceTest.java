package testing.ex1.customer;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import testing.ex1.app.customer.CustomerService;
import testing.ex1.entity.Customer;
import testing.ex1.entity.Order;
import testing.ex1.entity.OrderLine;
import testing.ex1.entity.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @AfterEach // <2>
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("DELETE FROM CUSTOMER"); // <3>
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

        assertThat(dataManager.load(Customer.class).all().list())
                .hasSize(1);

        // when
        Optional<Customer> foundCustomer = customerService.findByEmail("other-customer@company.com");

        // then
        assertThat(foundCustomer)
                .isNotPresent();
    }

    // ...
    // end::order-line-event-listener-test[]

}
