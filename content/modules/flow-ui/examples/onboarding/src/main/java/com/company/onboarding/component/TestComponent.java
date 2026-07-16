package com.company.onboarding.component;

import com.company.onboarding.entity.Product;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import io.jmix.flowui.model.CollectionContainer;

import java.util.List;
import java.util.Set;

@Tag("test-component")
public class TestComponent extends Component {

    public void setStringsList(List<String> stringsList) {
    }

    public void setStringsSet(Set<String> stringsSet) {
    }

    public void setStringsArray(String[] stringsArray) {
    }

    public void setStrings(String... strings) {
    }

    public void setDataContainer(CollectionContainer<Product> dataContainer) {
    }
}
