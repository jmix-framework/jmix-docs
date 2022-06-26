package security.ex1.service;

import io.jmix.core.session.SessionData;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// tag::session-data[]
@Component
public class CustomerService {

    @Autowired
    private ObjectProvider<SessionData> sessionDataProvider;

    public void saveSessionValue(String value) {
        sessionDataProvider.getObject().setAttribute("my-attribute", value);
    }
// end::session-data[]
}
