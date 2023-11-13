package com.company.demo.listener;

import com.company.demo.entity.Product;
import com.company.demo.entity.ProductPart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.core.event.EntityLoadingEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ProductEntityEventListener {

    private ObjectMapper objectMapper = new ObjectMapper();

    @EventListener
    void onProductSaving(EntitySavingEvent<Product> event) {
        Product product = event.getEntity();
        try {
            String json = objectMapper.writeValueAsString(product.getPartsList());
            product.setParts(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error writing JSON", e);
        }
    }

    @EventListener
    void onProductLoading(EntityLoadingEvent<Product> event) {
        Product product = event.getEntity();
        try {
            ProductPart[] productPartsArray = objectMapper.readValue(product.getParts(), ProductPart[].class);
            product.setPartsList(productPartsArray == null ? null : Arrays.asList(productPartsArray));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error reading JSON", e);
        }
    }
}
