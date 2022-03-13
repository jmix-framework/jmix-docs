package ui.ex1.screen.data.sort;

import io.jmix.ui.model.BaseCollectionLoader;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.Sorter;
import io.jmix.ui.model.SorterFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

// tag::factory[]
@Primary
@Component("sample_SorterFactory")
public class CustomSorterFactory extends SorterFactory {

    @Override
    public Sorter createCollectionContainerSorter(CollectionContainer container,
                                                  @Nullable BaseCollectionLoader loader) {
        return new CustomCollectionContainerSorter(container, loader, beanFactory);
    }
}
// end::factory[]