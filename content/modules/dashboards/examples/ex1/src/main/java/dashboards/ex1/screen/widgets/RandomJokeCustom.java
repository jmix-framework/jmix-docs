package dashboards.ex1.screen.widgets;

import io.jmix.dashboardsui.annotation.DashboardWidget;
import io.jmix.dashboardsui.annotation.WidgetParam;
import io.jmix.dashboardsui.event.DashboardEvent;
import io.jmix.dashboardsui.widget.RefreshableWidget;
import io.jmix.ui.WindowParam;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@UiController("sample_RandomJokeCustom")
@UiDescriptor("random-joke-custom.xml")
@DashboardWidget(name = "Random Joke Custom")
public class RandomJokeCustom extends ScreenFragment implements RefreshableWidget {

    // tag::dash-param[]
    @Autowired
    private TextArea<String> randomJoke;

    @WindowParam(name = "font-size")
    @WidgetParam
    private String fontSize;

    @Subscribe
    private void onAfterInit(AfterInitEvent event) {
        if(fontSize!=null) {
            randomJoke.addStyleName(fontSize);
        }
        randomJoke.setValue(getNewJoke());
    }
    // end::dash-param[]

    @Override
    public void refresh(DashboardEvent dashboardEvent) {
        randomJoke.setValue(getNewJoke());
    }

    private String getNewJoke() {
        String host = "icanhazdadjoke.com";
        String url = "https://" + host;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "text/plain");
        headers.set("User-Agent", "CubaDashboardDemoApp");
        headers.set("Cache-Control", "no-cache");
        headers.set("Host", host);
        HttpEntity<String> request = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();

    }
}
