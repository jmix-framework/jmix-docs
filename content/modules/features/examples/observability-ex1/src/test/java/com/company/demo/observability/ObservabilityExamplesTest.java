package com.company.demo.observability;

import com.company.demo.entity.*;
import com.company.demo.performance.VisitService;
import com.company.demo.sales.OrderService;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the examples of the observability pages: the N+1 examples really perform one
 * DataManager load per visit, and the custom observation produces its timer.
 */
@SpringBootTest(properties = "jmix.core.data-observation-enabled=true")
@ExtendWith(AuthenticatedAsAdmin.class)
public class ObservabilityExamplesTest {

    private static final int VISITS = 5;

    @Autowired
    DataManager dataManager;

    @Autowired
    VisitService visitService;

    @Autowired
    OrderService orderService;

    @Autowired
    MeterRegistry meterRegistry;

    @BeforeEach
    void createVisits() {
        SaveContext saveContext = new SaveContext();
        for (int i = 0; i < VISITS; i++) {
            Owner owner = dataManager.create(Owner.class);
            owner.setName("Owner " + i);
            Pet pet = dataManager.create(Pet.class);
            pet.setName("Pet " + i);
            pet.setOwner(owner);
            Visit visit = dataManager.create(Visit.class);
            visit.setPet(pet);
            visit.setVisitStart(LocalDateTime.now().minusDays(i));
            saveContext.saving(owner, pet, visit);
        }
        dataManager.save(saveContext);
    }

    @AfterEach
    void removeData() {
        dataManager.remove(dataManager.load(Visit.class).all().list());
        dataManager.remove(dataManager.load(Pet.class).all().list());
        dataManager.remove(dataManager.load(Owner.class).all().list());
        dataManager.remove(dataManager.load(Order.class).all().list());
    }

    @Test
    void loadInLoopPerformsOneLoadPerVisit() {
        long before = loadCount("Owner");

        List<Owner> owners = visitService.loadOwnersOfLatestVisits();

        assertThat(owners).hasSize(VISITS);
        assertThat(loadCount("Owner") - before).isEqualTo(VISITS);
    }

    @Test
    void lazyLoadingPerformsOneLoadPerVisit() {
        long before = loadCount("Pet");

        List<String> petNames = visitService.getPetNamesOfLatestVisits();

        assertThat(petNames).hasSize(VISITS).allMatch(name -> name.startsWith("Pet "));
        assertThat(loadCount("Pet") - before).isEqualTo(VISITS);
    }

    @Test
    void confirmOrderRecordsObservation() {
        Order order = dataManager.create(Order.class);
        order.setNumber("A-1");
        order.setChannel(SalesChannel.WEB);

        orderService.confirmOrder(order);

        Timer timer = meterRegistry.find("sales.order.confirm").tag("order.channel", "W").timer();
        assertThat(timer).isNotNull();
        assertThat(timer.count()).isEqualTo(1);
        assertThat(dataManager.load(Order.class).all().list()).hasSize(1);
    }

    private long loadCount(String entityName) {
        Timer timer = meterRegistry.find("jmix.data.load")
                .tag("method", "load")
                .tag("entity.name", entityName)
                .timer();
        return timer == null ? 0 : timer.count();
    }
}
