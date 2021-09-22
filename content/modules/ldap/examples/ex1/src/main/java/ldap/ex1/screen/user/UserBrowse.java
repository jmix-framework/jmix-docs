package ldap.ex1.screen.user;

import com.google.common.base.Strings;
import io.jmix.core.DataManager;
import io.jmix.core.QueryUtils;
import io.jmix.core.security.UserRepository;
import io.jmix.ui.component.SuggestionField;
import ldap.ex1.entity.User;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@UiController("ldap_User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private SuggestionField suggestionField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        initUserSuggestionField();
    }

    protected void initUserSuggestionField() {
        suggestionField.setSearchExecutor((searchString, searchParams) ->
                userRepository.getByUsernameLike(searchString).stream()
                        .map(UserDetails::getUsername)
                        .collect(Collectors.toList()));
        suggestionField.setEnterPressHandler(enterPressEvent -> {
            if (!Strings.isNullOrEmpty(enterPressEvent.getText())) {
                try {
                    //check if such user exists
                    userRepository.loadUserByUsername(enterPressEvent.getText());
                    suggestionField.clear();
                } catch (UsernameNotFoundException e) {
                    //ignore me
                }
            }
        });
    }
}