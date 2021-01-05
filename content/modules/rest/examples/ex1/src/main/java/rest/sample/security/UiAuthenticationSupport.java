package rest.sample.security;

import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinServletResponse;
import io.jmix.core.security.ClientDetails;
import io.jmix.ui.event.AppInitializedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UiAuthenticationSupport {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SessionAuthenticationStrategy authenticationStrategy;

    @EventListener
    public void onAppInitializedEvent(AppInitializedEvent event) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("admin", "admin");
        authToken.setDetails(ClientDetails.builder().locale(Locale.ENGLISH).build());

        Authentication authentication = authenticationManager.authenticate(authToken);

        authenticationStrategy.onAuthentication(authentication, VaadinServletRequest.getCurrent(), VaadinServletResponse.getCurrent());
    }
}
