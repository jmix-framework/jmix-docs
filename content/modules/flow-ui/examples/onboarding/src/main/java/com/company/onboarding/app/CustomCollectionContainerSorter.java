package com.company.onboarding.app;

import com.company.onboarding.entity.Department;
import io.jmix.core.Sort;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.flowui.model.BaseCollectionLoader;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.impl.CollectionContainerSorter;
import io.jmix.flowui.model.impl.EntityValuesComparator;
import org.springframework.beans.factory.BeanFactory;

import java.util.Comparator;
import java.util.Objects;

public class CustomCollectionContainerSorter extends CollectionContainerSorter {

    public CustomCollectionContainerSorter(CollectionContainer<?> container,
                                           BaseCollectionLoader loader,
                                           BeanFactory beanFactory) {
        super(container, loader, beanFactory);
    }

    @Override
    protected Comparator<?> createComparator(Sort sort, MetaClass metaClass) {
        MetaPropertyPath metaPropertyPath = Objects.requireNonNull(
                metaClass.getPropertyPath(sort.getOrders().get(0).getProperty()));

        if (metaPropertyPath.getMetaClass().getJavaClass().equals(Department.class)
                && "num".equals(metaPropertyPath.toPathString())) {
            boolean isAsc = sort.getOrders().get(0).getDirection() == Sort.Direction.ASC;
            return Comparator.comparing((Department e) ->
                            e.getNum() == null ? null : Integer.valueOf(e.getNum()),
                    new EntityValuesComparator<>(isAsc, metaClass, beanFactory));
        }
        return super.createComparator(sort, metaClass);
    }
}
