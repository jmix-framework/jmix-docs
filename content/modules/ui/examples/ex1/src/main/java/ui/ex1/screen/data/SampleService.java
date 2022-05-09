package ui.ex1.screen.data;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Component("uiex1_SampleService")
public class SampleService {

    public Set<Object> saveEntities(Collection<Object> entities, Collection<Object> entitiesToRemove) {
        return Collections.emptySet();
    }
}
