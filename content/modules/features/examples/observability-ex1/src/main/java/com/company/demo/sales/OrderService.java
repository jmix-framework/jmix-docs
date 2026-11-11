package com.company.demo.sales;

import com.company.demo.entity.Order;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // tag::observation-registry[]
    @Autowired
    private ObservationRegistry observationRegistry;
    // end::observation-registry[]

    @Autowired
    private OrderProcessor orderProcessor;

    // tag::custom-span[]
    public void confirmOrder(Order order) {
        Observation.createNotStarted("sales.order.confirm", observationRegistry)
                .contextualName("confirm order")
                .lowCardinalityKeyValue("order.channel", order.getChannel().getId())
                .observe(() -> orderProcessor.process(order)); // <1>
    }
    // end::custom-span[]
}
