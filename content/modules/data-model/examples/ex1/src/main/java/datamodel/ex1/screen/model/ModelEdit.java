package datamodel.ex1.screen.model;

import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Model;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_Model.edit")
@UiDescriptor("model-edit.xml")
@EditedEntityContainer("modelDc")
public class ModelEdit extends StandardEditor<Model> {

    // tag::conversion-error[]
    @Autowired
    private TextField<Integer> productionYearField;

    @Subscribe
    public void onInit(InitEvent event) {
        productionYearField.setConversionErrorMessage("Incorrect year format");
    }
    // end::conversion-error[]


}