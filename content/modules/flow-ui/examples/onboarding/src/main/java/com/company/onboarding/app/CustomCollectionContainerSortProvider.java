package com.company.onboarding.app;

import com.company.onboarding.entity.Department;
import io.jmix.flowui.model.CollectionContainerSortContext;
import io.jmix.flowui.model.CollectionContainerSortProvider;
import io.jmix.flowui.model.Sorter;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// tag::custom-collection-container-sort-provider[]
@Component
@Order(100)
public class CustomCollectionContainerSortProvider implements CollectionContainerSortProvider {

    @Autowired
    private BeanFactory beanFactory;

    @Nullable
    @Override
    public Sorter getSorter(CollectionContainerSortContext context) {
        if (supports(context)) {
            return new CustomCollectionContainerSorter(context.container(), context.loader(), beanFactory);
        }

        return null;
    }

    private boolean supports(CollectionContainerSortContext context) {
        return context.container().getEntityMetaClass().getJavaClass().equals(Department.class);
    }
}
// end::custom-collection-container-sort-provider[]
