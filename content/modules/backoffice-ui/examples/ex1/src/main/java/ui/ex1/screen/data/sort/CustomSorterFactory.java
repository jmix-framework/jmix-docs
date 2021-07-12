package ui.ex1.screen.data.sort;

import io.jmix.ui.model.BaseCollectionLoader;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.Sorter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

// tag::factory[]
@Component("sample_CustomSorterFactory")
public class CustomSorterFactory {

    protected BeanFactory beanFactory;

    @Autowired
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Sorter createCustomCollectionContainerSorter(CollectionContainer container,
                                                        @Nullable BaseCollectionLoader loader) {
        return new CustomCollectionContainerSorter(container, loader, beanFactory);
    }
}
// end::factory[]