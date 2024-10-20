package com.company.onboarding.model;

import com.vaadin.flow.shared.Registration;
import io.jmix.pivottableflowui.kit.data.JmixPivotTableItems;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

// tag::shape-list[]
public class ShapeListPivotTableItems implements JmixPivotTableItems<Shape> {

    private List<Shape> items;

    public ShapeListPivotTableItems(List<Shape> items) {
        this.items = items;
    }

    @Override
    public Collection<Shape> getItems() {
        return items;
    }

    @Override
    public Shape getItem(Object itemId) {
        return null;
    }

    @Nullable
    @Override
    public Object getItemValue(Shape item, String propertyPath) {
        if ("shape".equals(propertyPath)) {
            return item.getShape();
        } else if ("color".equals(propertyPath)) {
            return item.getColor();
        } else if ("size".equals(propertyPath)) {
            return item.getSize();
        }
        return "";
    }

    @Nullable
    @Override
    public Object getItemId(Shape item) {
        return item.getId();
    }

    @Override
    public void setItemValue(Shape item, String propertyPath, @Nullable Object value) {
    }

    @Override
    public Shape getItem(String stringId) {
        return items.stream().filter(i -> i.getId().toString().equals(stringId)).findFirst().orElse(null);
    }

    @Override
    public void updateItem(Shape item) {
    }

    @Override
    public boolean containsItem(Shape item) {
        return false;
    }

    @Override
    public Registration addItemsChangeListener(Consumer listener) {
        return null;
    }
}
// end::shape-list[]
