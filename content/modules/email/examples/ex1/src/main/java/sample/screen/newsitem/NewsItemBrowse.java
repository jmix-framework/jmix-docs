package sample.screen.newsitem;

import io.jmix.email.EmailAttachment;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import sample.entity.NewsItem;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

@UiController("sample_NewsItem.browse")
@UiDescriptor("news-item-browse.xml")
@LookupComponent("newsItemsTable")
public class NewsItemBrowse extends StandardLookup<NewsItem> {

}
