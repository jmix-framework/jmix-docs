package com.company.onboarding.component;

// tag::js-component[]
import com.vaadin.flow.component.*;
import com.vaadin.flow.shared.Registration;

@Tag("div")
public class Slider extends Component implements HasSize {

    private static final String VALUE_PROPERTY = "value";
    private static final String MIN_PROPERTY = "min";
    private static final String MAX_PROPERTY = "max";

    public Slider() {
        getElement().setText("Slider is temporarily disabled");
    }

    public int getMin() {
        return getElement().getProperty(MIN_PROPERTY, 0);
    }

    public void setMin(int min) {
        getElement().setProperty(MIN_PROPERTY, min);
    }

    public int getMax() {
        return getElement().getProperty(MAX_PROPERTY, 100);
    }

    public void setMax(int max) {
        getElement().setProperty(MAX_PROPERTY, max);
    }

    @Synchronize("custom-slide-changed") // <4>
    public int getValue() {
        return getElement().getProperty(VALUE_PROPERTY, 0);
    }

    public void setValue(int value) {
        getElement().setProperty(VALUE_PROPERTY, value);
    }

    // tag::dom-event[]
    public Registration addValueChangeListener(ComponentEventListener<SlideChangedEvent> listener) {
        return addListener(SlideChangedEvent.class, listener);
    }

    @DomEvent("custom-slide-changed") // <1>
    public static class SlideChangedEvent extends ComponentEvent<Slider> {

        protected int value;

        public SlideChangedEvent(Slider source, boolean fromClient,
                                 @EventData("event.detail.value") int value) { // <2>
            super(source, fromClient);
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    // end::dom-event[]
}
// end::js-component[]
