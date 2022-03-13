package ui.ex1.screen.screens.mixins;

import io.jmix.ui.model.DataLoader;
import java.util.Set;

// tag::state[]
public class DeclarativeLoaderParametersState {

    private Set<DataLoader> loadersToLoadBeforeShow;

    public DeclarativeLoaderParametersState(Set<DataLoader> loadersToLoadBeforeShow) {
        this.loadersToLoadBeforeShow = loadersToLoadBeforeShow;
    }

    public Set<DataLoader> getLoadersToLoadBeforeShow() {
        return loadersToLoadBeforeShow;
    }
}
// end::state[]
