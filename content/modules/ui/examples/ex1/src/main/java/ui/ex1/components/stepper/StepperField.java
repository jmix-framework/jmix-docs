package ui.ex1.components.stepper;

import io.jmix.core.MessageTools;
import io.jmix.core.common.event.Subscription;
import io.jmix.ui.component.*;
import io.jmix.ui.component.data.ValueSource;
import io.jmix.ui.component.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;
// tag::component-start[]
@CompositeDescriptor("stepper-component.xml") // <1>
public class StepperField
        extends CompositeComponent<CssLayout> // <2>
        implements Field<Integer>, // <3>
        CompositeWithCaption, // <4>
        CompositeWithHtmlCaption,
        CompositeWithHtmlDescription,
        CompositeWithIcon,
        CompositeWithContextHelp {

    public static final String NAME = "stepperField"; // <5>

    private TextField<Integer> valueField; // <6>
    private Button upBtn;
    private Button downBtn;
    private int step = 1; // <7>
    private boolean editable = true;
    private Subscription parentEditableChangeListener;

    // end::component-start[]

    // tag::set-message-tools[]
    protected MessageTools messageTools;

    @Autowired
    public void setMessageTools(MessageTools messageTools) {
        this.messageTools = messageTools;
    }
    // end::set-message-tools[]
    @Override
    public void setCaption(@Nullable String caption) {
        Component.HasCaption hasCaption = (Component.HasCaption) ((CompositeComponent) this).getComposition();
        hasCaption.setCaption(messageTools.loadString(caption));
    }
    // tag::component-end[]
    public StepperField() {
        addCreateListener(this::onCreate); // <8>
    }

    private void onCreate(CreateEvent createEvent) {
        valueField = getInnerComponent("stepper_valueField");
        upBtn = getInnerComponent("stepper_upBtn");
        downBtn = getInnerComponent("stepper_downBtn");

        upBtn.addClickListener(clickEvent -> updateValue(step));
        downBtn.addClickListener(clickEvent -> updateValue(-step));
    }

    private void updateValue(int delta) {
        Integer value = getValue();
        setValue(value != null ? value + delta : delta);
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public boolean isRequired() {
        return valueField.isRequired();
    }

    @Override
    public void setRequired(boolean required) {
        valueField.setRequired(required);
        getComposition().setRequiredIndicatorVisible(required);
    }

    @Override
    public String getRequiredMessage() {
        return valueField.getRequiredMessage();
    }

    @Override
    public void setRequiredMessage(String msg) {
        valueField.setRequiredMessage(msg);
    }

    @Override
    public void setParent(Component parent) {
        if (getParent() instanceof EditableChangeNotifier
                && parentEditableChangeListener != null) {
            parentEditableChangeListener.remove();
            parentEditableChangeListener = null;
        }

        super.setParent(parent);

        if (parent instanceof EditableChangeNotifier) { // <9>
            parentEditableChangeListener = ((EditableChangeNotifier) parent).addEditableChangeListener(event -> {
                boolean parentEditable = event.getSource().isEditable();
                boolean finalEditable = parentEditable && isEditable();
                setEditableInternal(finalEditable);
            });

            Editable parentEditable = (Editable) parent;
            if (!parentEditable.isEditable()) {
                setEditableInternal(false);
            }
        }
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setEditable(boolean editable) {
        if (this.editable != editable) {
            setEditableInternal(editable);
        }
    }

    private void setEditableInternal(boolean editable) {
        valueField.setEditable(editable);
        upBtn.setEnabled(editable);
        downBtn.setEnabled(editable);
    }

    @Override
    public Integer getValue() {
        return valueField.getValue();
    }

    @Override
    public void setValue(Integer value) {
        valueField.setValue(value);
    }

    @Override
    public Subscription addValueChangeListener(Consumer<ValueChangeEvent<Integer>> listener) {
        return valueField.addValueChangeListener(listener);
    }

    @Override
    public boolean isValid() {
        return valueField.isValid();
    }

    @Override
    public void validate() throws ValidationException {
        valueField.validate();
    }

    @Override
    public void setValueSource(ValueSource<Integer> valueSource) {
        valueField.setValueSource(valueSource);
        getComposition().setRequiredIndicatorVisible(valueField.isRequired());
    }

    @Override
    public ValueSource<Integer> getValueSource() {
        return valueField.getValueSource();
    }

    @Override
    public void addValidator(Validator<? super Integer> validator) {
        valueField.addValidator(validator);
    }

    @Override
    public void removeValidator(Validator<Integer> validator) {
        valueField.removeValidator(validator);
    }

    @Override
    public Collection<Validator<Integer>> getValidators() {
        return valueField.getValidators();
    }
}
// end::component-end[]