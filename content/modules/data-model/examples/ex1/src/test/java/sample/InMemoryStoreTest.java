package sample;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.entity.Metric;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InMemoryStoreTest {

    @Autowired
    DataManager dataManager;

    @Test
    void test() {
        // tag::custom-store[]
        Metric metric = dataManager.create(Metric.class);
        metric.setName("test");
        metric.setValue(10.0);
        dataManager.save(metric);

        Metric metric1 = dataManager.load(Id.of(metric)).one();
        // end::custom-store[]
        assertEquals(metric, metric1);
        assertNotSame(metric, metric1);
    }
}
