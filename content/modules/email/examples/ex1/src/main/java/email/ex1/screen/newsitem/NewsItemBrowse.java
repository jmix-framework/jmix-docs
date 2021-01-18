package email.ex1.screen.newsitem;

import io.jmix.ui.screen.*;
import email.ex1.entity.NewsItem;

@UiController("sample_NewsItem.browse")
@UiDescriptor("news-item-browse.xml")
@LookupComponent("newsItemsTable")
public class NewsItemBrowse extends StandardLookup<NewsItem> {

}
