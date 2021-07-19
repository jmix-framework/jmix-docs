package search.ex1.screen.search;

import io.jmix.core.Messages;
import io.jmix.search.searching.SearchResult;
import io.jmix.searchui.component.SearchField;
import io.jmix.searchui.screen.result.SearchResultsScreen;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.ComponentsHelper;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import static io.jmix.ui.Notifications.NotificationType.HUMANIZED;
import static io.jmix.ui.screen.UiControllerUtils.getScreenContext;

@UiController("sample_SearchScreenHandler1")
@UiDescriptor("search-screen-handler1.xml")
public class SearchScreenHandler1 extends Screen {

    // tag::my-search-field[]
    @Autowired
    SearchField mySearchField;

    // end::my-search-field[]

    // tag::screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::screen-builders[]

    // tag::init[]
    @Subscribe
    public void onInit(InitEvent initEvent) {
        mySearchField.setSearchCompletedHandler(event -> {
            SearchResult searchResult = event.getSearchResult();
            screenBuilders.screen(this)
                    .withScreenClass(SearchResultsScreen.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .build()
                    .setSearchResult(searchResult)
                    .show();
        });
    }
    // end::init[]

}