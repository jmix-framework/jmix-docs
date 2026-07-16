package com.company.demo.listener;

import com.company.demo.entity.Product;
import com.company.demo.entity.ProductPart;
import io.jmix.core.event.EntityLoadingEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import java.util.Arrays;

@Component
public class ProductEntityEventListener {

    private JsonMapper objectMapper = new JsonMapper();

    @EventListener
    void onProductSaving(EntitySavingEvent<Product> event) {
        Product product = event.getEntity();
        try {
            String json = objectMapper.writeValueAsString(product.getPartsList());
            product.setParts(json);
        } catch (JacksonException e) {
            throw new RuntimeException("Error writing JSON", e);
        }
    }

    @EventListener
    void onProductLoading(EntityLoadingEvent<Product> event) {
        Product product = event.getEntity();
        try {
            ProductPart[] productPartsArray = objectMapper.readValue(product.getParts(), ProductPart[].class);
            product.setPartsList(productPartsArray == null ? null : Arrays.asList(productPartsArray));
        } catch (JacksonException e) {
            throw new RuntimeException("Error reading JSON", e);
        }
    }
}
