package testing.ex1.customer;

import io.jmix.email.EmailException;
import io.jmix.email.EmailInfo;
import io.jmix.email.Emailer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import testing.ex1.app.customer.NotificationService;
import testing.ex1.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;


@SpringBootTest
class NotificationServiceWithTestConfigurationTest {


    @Autowired
    NotificationService notificationService;

    @TestConfiguration // <1>
    public static class EmailerTestConfiguration {
        @Bean
        public Emailer emailer() throws EmailException { // <2>
            Emailer emailer = mock(Emailer.class); // <3>

            doThrow(EmailException.class).when(emailer) // <4>
                    .sendEmail(any(EmailInfo.class));

            return emailer;
        }
    }

    @Test
    void given_emailNotDelivered_when_sendNotification_then_noSuccess() throws EmailException {

        // given:
        Customer customer = new Customer();
        customer.setEmail("customer@company.com");

        // when:
        boolean success = notificationService.notifyCustomerAboutOrderDelivered(customer);

        // then:
        assertThat(success).isFalse();
    }

}
