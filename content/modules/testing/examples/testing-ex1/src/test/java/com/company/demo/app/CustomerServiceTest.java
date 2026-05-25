package com.company.demo.app;

import com.company.demo.entity.Customer;
import com.company.demo.entity.User;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


// tag::authenticated-as-admin[]
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
public class CustomerServiceTest {
    // ...
    // end::authenticated-as-admin[]

    // tag::find-by-email-as-user[]
    private final String USERNAME = "userWithoutPermissions";

    // end::find-by-email-as-user[]

    @Autowired
    CustomerService customerService;

    @Autowired
    DataManager dataManager;
    @Autowired
    SystemAuthenticator systemAuthenticator;

    // tag::cleanup-customer-table[]

    @Autowired // <1>
    DataSource dataSource;

    @AfterEach // <2>
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

    // tag::find-by-email-as-user[]
    @Test
    void given_noPermissionsToReadCustomerData_when_findByEmail_then_nothingFound() {

        // given
        Customer customer = dataManager.create(Customer.class);
        customer.setEmail("customer@test.com");
        dataManager.save(customer);

        // and
        User userWithoutPermissions = dataManager.create(User.class);
        userWithoutPermissions.setUsername(USERNAME);
        dataManager.save(userWithoutPermissions); // <1>

        // when
        Optional<Customer> foundCustomer = systemAuthenticator.withUser( // <2>
                USERNAME,
                () -> customerService.findByEmail("customer@test.com") // <3>
        );

        // then
        assertThat(foundCustomer)
                .isNotPresent();
    }
    // end::find-by-email-as-user[]

    @AfterEach // <2>
    void removeUser() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTableWhere(jdbc, "USER_", "username = ?", USERNAME); // <3>
    }
}
