package com.company.onboarding.view.facets;

import com.company.onboarding.entity.Department;

import com.company.onboarding.view.main.MainView;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.sun.jna.platform.win32.Advapi32Util;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.facet.UrlQueryParametersFacet;
import io.jmix.flowui.facet.urlqueryparameters.AbstractUrlQueryParametersBinder;
import io.jmix.flowui.view.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Route(value = "url-query-parameters", layout = MainView.class)
@ViewController("UrlQueryParametersView")
@ViewDescriptor("url-query-parameters-view.xml")
@LookupComponent("departmentsTable")
@DialogMode(width = "50em", height = "37.5em")
public class UrlQueryParametersView extends StandardListView<Department> {

    // tag::custom-binding[]
    private static final String DETAILS_OPENED_URL_PARAM = "detailsOpened";
    private static final String TEXT_URL_PARAM = "text";

    @ViewComponent
    private UrlQueryParametersFacet urlQueryParameters;

    @ViewComponent
    private JmixDetails sampleDetails;

    @ViewComponent
    private TypedTextField<String> sampleTextField;

    private class SampleUrlQueryParametersBinder extends AbstractUrlQueryParametersBinder { // <1>

        public SampleUrlQueryParametersBinder() { // <2>
            sampleDetails.addOpenedChangeListener(event -> {
                boolean opened = event.isOpened();
                QueryParameters qp = new QueryParameters(ImmutableMap.of(DETAILS_OPENED_URL_PARAM,
                        opened ? Collections.singletonList("1") : Collections.emptyList()));
                fireQueryParametersChanged(new UrlQueryParametersFacet.UrlQueryParametersChangeEvent(this, qp));
            });

            sampleTextField.addValueChangeListener(event -> {
                String text = event.getValue();
                QueryParameters qp = new QueryParameters(ImmutableMap.of(TEXT_URL_PARAM,
                        text != null ? Collections.singletonList(text) : Collections.emptyList()));
                fireQueryParametersChanged(new UrlQueryParametersFacet.UrlQueryParametersChangeEvent(this, qp));
            });
        }

        @Override
        public void updateState(QueryParameters queryParameters) { // <3>
            List<String> detailsOpenedStrings = queryParameters.getParameters().get(DETAILS_OPENED_URL_PARAM);
            if (detailsOpenedStrings != null) {
                sampleDetails.setOpened("1".equals(detailsOpenedStrings.get(0)));
            }

            List<String> textStrings = queryParameters.getParameters().get(TEXT_URL_PARAM);
            if (textStrings != null && !textStrings.isEmpty()) {
                sampleTextField.setValue(textStrings.get(0));
            }
        }
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        urlQueryParameters.registerBinder(new SampleUrlQueryParametersBinder()); // <4>
    }
// end::custom-binding[]
}
