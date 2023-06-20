package testing.ex1.customer;

import io.jmix.email.EmailException;
import io.jmix.email.EmailInfo;
import io.jmix.email.Emailer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import testing.ex1.app.customer.NotificationService;
import testing.ex1.entity.Customer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationServiceTest {


    // tag::emailer-mock-bean[]
    @MockBean
    Emailer emailer;
    // end::emailer-mock-bean[]

    @Autowired
    NotificationService notificationService;

    @Test
    void given_emailDelivered_when_sendNotification_then_success() throws EmailException {

        // given:
        Customer customer = new Customer();
        customer.setEmail("customer@company.com");

        // when:
        boolean success = notificationService.notifyCustomerAboutOrderDelivered(customer);

        // then:
        assertThat(success).isTrue();
    }


    @Test
    void given_emailNotDelivered_when_sendNotification_then_noSuccess() throws EmailException {

        // given:
        doThrow(EmailException.class).when(emailer)
                .sendEmail(any(EmailInfo.class));

        // and:
        Customer customer = new Customer();
        customer.setEmail("customer@company.com");

        // when:
        boolean success = notificationService.notifyCustomerAboutOrderDelivered(customer);

        // then:
        assertThat(success).isFalse();
    }

}
