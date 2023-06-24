package com.company.demo;

import com.company.demo.entity.Status;
import io.jmix.core.Messages;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessagesTest {

    @Autowired
    private Messages messages;

    @Autowired
    private SystemAuthenticator authenticator;

    @Test
    void test() {
        authenticator.withSystem(() -> {
            // tag::using-messages-1[]
            String message1 = messages.getMessage("com.company.demo/someNotification"); // <1>

            String message2 = messages.getMessage("com.company.demo", "someNotification"); // <2>

            String message3 = messages.getMessage(getClass(), "someNotification"); // <3>
            // end::using-messages-1[]

            assertEquals("Something has happened", message1);
            assertEquals("Something has happened", message2);
            assertEquals("Something has happened", message3);

            return null;
        });
    }

    @Test
    void testMessageWithoutGroup() {
        authenticator.withSystem(() -> {
            // tag::using-messages-3[]
            String message = messages.getMessage("messageWithoutGroup");
            // end::using-messages-3[]

            assertEquals("Message without a group", message);

            return null;
        });
    }

    @Test
    void testEnum() {
        authenticator.withSystem(() -> {
            // tag::using-messages-enum[]
            String message = messages.getMessage(Status.ACTIVE);
            // end::using-messages-enum[]

            assertEquals("Active", message);

            return null;
        });
    }
}
