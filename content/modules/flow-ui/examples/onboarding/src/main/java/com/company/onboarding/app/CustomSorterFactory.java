package com.company.onboarding.app;

import io.jmix.flowui.model.BaseCollectionLoader;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.Sorter;
import io.jmix.flowui.model.SorterFactory;
import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class CustomSorterFactory extends SorterFactory {

    @Override
    public Sorter createCollectionContainerSorter(CollectionContainer container,
                                                  @Nullable BaseCollectionLoader loader) {
        return new CustomCollectionContainerSorter(container, loader, beanFactory);
    }
}
