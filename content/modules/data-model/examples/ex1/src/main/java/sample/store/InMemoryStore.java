package sample.store;

import io.jmix.core.DataStore;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.entity.EntityValues;
import io.jmix.core.entity.KeyValueEntity;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// tag::custom-store[]
@Component("sample_InMemoryStore")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InMemoryStore implements DataStore {

    private String name;

    private Map<Class<?>, Map<Object, Object>> entities = new ConcurrentHashMap<>();

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
    public <E> E load(LoadContext<E> context) {
        Map<Object, Object> instances = entities.get(context.getEntityMetaClass().getJavaClass());
        if (instances == null)
            return null;
        else
            return (E) instances.get(context.getId());
    }

    @Override
    public <E> List<E> loadList(LoadContext<E> context) {
        Map<Object, Object> instances = entities.get(context.getEntityMetaClass().getJavaClass());
        if (instances == null)
            return Collections.emptyList();
        else
            return new ArrayList(instances.values());
    }

    @Override
    public long getCount(LoadContext<?> context) {
        Map<Object, Object> instances = entities.get(context.getEntityMetaClass().getJavaClass());
        if (instances == null)
            return 0;
        else
            return instances.size();
    }

    @Override
    public Set<?> save(SaveContext context) {
        Set<Object> result = new HashSet<>();
        for (Object entity : context.getEntitiesToSave()) {
            Map<Object, Object> instances = entities.computeIfAbsent(
                    entity.getClass(),
                    aClass -> new ConcurrentHashMap<>()
            );
            instances.put(EntityValues.getId(entity), entity);
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
}
// end::custom-store[]