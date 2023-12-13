package com.company.demo.app;

import com.company.demo.entity.Customer;
import io.jmix.email.EmailException;
import io.jmix.email.EmailInfo;
import io.jmix.email.Emailer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class NotificationServiceTest {

    @MockBean
    Emailer emailer;

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
