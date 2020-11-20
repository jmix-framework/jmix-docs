package sample.store;

import io.jmix.core.metamodel.model.StoreDescriptor;
import org.springframework.stereotype.Component;

// tag::custom-store[]
@Component("sample_InMemoryStoreDescriptor")
public class InMemoryStoreDescriptor implements StoreDescriptor {

    @Override
    public String getBeanName() {
        return "sample_InMemoryStore";
    }

    @Override
    public boolean isPersistent() {
        return false;
    }
}
// end::custom-store[]
