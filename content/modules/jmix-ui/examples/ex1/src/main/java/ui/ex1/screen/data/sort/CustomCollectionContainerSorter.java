package ui.ex1.screen.data.sort;

import io.jmix.core.Sort;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.ui.model.BaseCollectionLoader;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.impl.CollectionContainerSorter;
import io.jmix.ui.model.impl.EntityValuesComparator;
import org.springframework.beans.factory.BeanFactory;
import ui.ex1.entity.Order;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Objects;

// tag::sorter[]
public class CustomCollectionContainerSorter extends CollectionContainerSorter {

    public CustomCollectionContainerSorter(CollectionContainer container,
                                           @Nullable BaseCollectionLoader loader,
                                           BeanFactory beanFactory) {
        super(container, loader, beanFactory);
    }

    @Override
    protected Comparator<?> createComparator(Sort sort, MetaClass metaClass) {
        MetaPropertyPath metaPropertyPath = Objects.requireNonNull(
                metaClass.getPropertyPath(sort.getOrders().get(0).getProperty()));

        if (metaPropertyPath.getMetaClass().getJavaClass().equals(Order.class)
                && "num".equals(metaPropertyPath.toPathString())) {
            boolean isAsc = sort.getOrders().get(0).getDirection() == Sort.Direction.ASC;
            return Comparator.comparing((Order e) ->
                            e.getNum() == null ? null : Integer.valueOf(e.getNum()),
                    new EntityValuesComparator<>(isAsc, metaClass, beanFactory));
        }
        return super.createComparator(sort, metaClass);
    }
}
// end::sorter[]