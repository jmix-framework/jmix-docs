package sample;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.EntityValues;
import io.jmix.core.security.Authenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.entity.GeoPoint;
import sample.entity.Metric;
import sample.entity.OperationResult;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EntitiesTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    Metadata metadata;
    @Autowired
    MetadataTools metadataTools;
    @Autowired
    Authenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator.begin();
    }

    @AfterEach
    void tearDown() {
        authenticator.end();
    }

    @Test
    void testCustomStore() {
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

    @Test
    void testSimpleDto() {
        OperationResult operationResult = metadata.create(OperationResult.class);
        assertNotNull(EntityValues.getId(operationResult));
    }

    @Test
    void testInstanceName() {
        GeoPoint geoPoint = metadata.create(GeoPoint.class);
        geoPoint.setLatitude(53.20076);
        geoPoint.setLongitude(50.098603);

        String instanceName = metadataTools.getInstanceName(geoPoint);
        assertEquals("Latitude: 53.20076, Longitude: 50.098603", instanceName);
    }
}
