package search.ex1.screen.search;

import io.jmix.search.searching.SearchResult;
import io.jmix.searchui.component.SearchField;
import io.jmix.searchui.screen.result.SearchResultsScreen;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_SearchScreenHandler2")
@UiDescriptor("search-screen-handler2.xml")
public class SearchScreenHandler2 extends Screen {

    // tag::my-search-field[]
    @Autowired
    SearchField mySearchField;

    // end::my-search-field[]

    // tag::screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::screen-builders[]

    // tag::handler[]
    @Install(to = "mySearchField", subject = "searchCompletedHandler")
    public void mySearchFieldSearchCompletedHandler(
            SearchField.SearchCompletedEvent event) {
        SearchResult searchResult = event.getSearchResult();
        screenBuilders.screen(this)
                .withScreenClass(SearchResultsScreen.class)
                .withOpenMode(OpenMode.DIALOG)
                .build()
                .setSearchResult(searchResult)
                .show();
    }
    // end::handler[]
}
