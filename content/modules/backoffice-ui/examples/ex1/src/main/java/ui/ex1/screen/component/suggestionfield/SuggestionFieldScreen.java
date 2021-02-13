package ui.ex1.screen.component.suggestionfield;

import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.QueryUtils;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.core.querycondition.JpqlCondition;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Country;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Hobby;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UiController("suggestionField-screen")
@UiDescriptor("suggestionfield-screen.xml")
public class SuggestionFieldScreen extends Screen {
    // tag::enum-search-executor[]
    @Autowired
    private Messages messages;

    @Install(to = "enumField", subject = "searchExecutor")
    private List enumFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return Stream.of(Hobby.values())
                .filter(status -> StringUtils.containsIgnoreCase(messages.getMessage(status), searchString))
                .collect(Collectors.toList());
    }
    // end::enum-search-executor[]
    // tag::string-search-executor[]
    @Install(to = "stringField", subject = "searchExecutor")
    private List stringFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return Stream.of("John", "Andy", "Dora", "Martin", "Peter", "George")
                .filter(str -> StringUtils.containsIgnoreCase(str, searchString))
                .collect(Collectors.toList());
    }
    // end::string-search-executor[]
    // tag::entity-search-executor[]
    @Autowired
    private DataManager dataManager;

    @Install(to = "entityField", subject = "searchExecutor")
    private List entityFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Country.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }
    // end::entity-search-executor[]
    // tag::escape-for-like[]
    @Install(to = "entitySuggestionField", subject = "searchExecutor")
    private List entitySuggestionFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        searchString = QueryUtils.escapeForLike(searchString);
        return dataManager.load(Customer.class)
                .condition(JpqlCondition.createWithParameters("e.firstName like :name order by e.firstName escape '\\'",
                        null, ParamsMap.of("name", "%" + searchString + "%")))
                .list();
    }
    // end::escape-for-like[]
    // tag::option-style-provider[]
    @Install(to = "customerSuggestionField", subject = "optionStyleProvider")
    private String customerSuggestionFieldOptionStyleProvider(Customer customer) {
        if (customer != null) {
            switch (customer.getLevel()) {
                case SILVER:
                    return "silver-level";
                case GOLD:
                    return "gold-level";
                case PLATINUM:
                    return "platinum-level";
                case DIAMOND:
                    return "diamond-level";
            }
        }
        return null;
    }
    // end::option-style-provider[]
    @Install(to = "customerSuggestionField", subject = "searchExecutor")
    private List customerSuggestionFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Customer.class)
                .query("e.firstName like ?1 order by e.firstName", "(?i)%" + searchString + "%")
                .list();
    }
}