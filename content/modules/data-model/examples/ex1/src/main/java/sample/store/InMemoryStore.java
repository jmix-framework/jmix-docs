package sample.store;

import io.jmix.core.*;
import io.jmix.core.entity.EntityValues;
import io.jmix.core.entity.KeyValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// tag::custom-store[]
@Component("sample_InMemoryStore")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InMemoryStore implements DataStore {

    private String name;

    private Map<Class<?>, Map<Object, Object>> entities = new ConcurrentHashMap<>();

    @Autowired
    private MetadataTools metadataTools;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public Object load(LoadContext context) {
        Map<Object, Object> instances = entities.get(context.getEntityMetaClass().getJavaClass());
        return instances == null ? null : cloneInstance(instances.get(context.getId()));
    }

    @Override
    public List<Object> loadList(LoadContext context) {
        Map<Object, Object> instances = entities.get(context.getEntityMetaClass().getJavaClass());
        return instances == null ? Collections.emptyList() : cloneInstances(instances.values());
    }

    @Override
    public long getCount(LoadContext context) {
        Map<Object, Object> instances = entities.get(context.getEntityMetaClass().getJavaClass());
        return instances == null ? 0 : instances.size();
    }

    @Override
    public Set<?> save(SaveContext context) {
        Set<Object> result = new HashSet<>();
        for (Object entity : context.getEntitiesToSave()) {
            Map<Object, Object> instances = entities.computeIfAbsent(
                    entity.getClass(),
                    aClass -> new ConcurrentHashMap<>()
            );
            instances.put(EntityValues.getId(entity), cloneInstance(entity));
            result.add(entity);
        }
        for (Object entity : context.getEntitiesToRemove()) {
            Map<Object, Object> instances = entities.get(entity.getClass());
            if (instances != null) {
                instances.remove(EntityValues.getId(entity));
            }
            result.add(entity);
        }
        return result;
    }

    @Override
    public List<KeyValueEntity> loadValues(ValueLoadContext context) {
        return new ArrayList<>();
    }

    @Nullable
    private Object cloneInstance(@Nullable Object instance) {
        return instance == null ? null : metadataTools.deepCopy(instance);
    }

    private List<Object> cloneInstances(Collection<Object> instances) {
        return instances.stream().map(this::cloneInstance).collect(Collectors.toList());
    }
}
// end::custom-store[]