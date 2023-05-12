package testing.ex1.listener;

import testing.ex1.entity.Order;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderEventListenerTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from ORDER");
    }

    @Test
    void test() {
        Order order1 = dataManager.create(Order.class);
        order1.setDate(LocalDate.now());
        Order savedOrder = dataManager.save(order1);

        assertTrue(Integer.parseInt(savedOrder.getNumber()) > 0);
    }
}
