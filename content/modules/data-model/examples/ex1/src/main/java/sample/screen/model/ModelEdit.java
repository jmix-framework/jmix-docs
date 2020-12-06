package sample.screen.model;

import io.jmix.ui.screen.*;
import sample.entity.Model;

@UiController("sample_Model.edit")
@UiDescriptor("model-edit.xml")
@EditedEntityContainer("modelDc")
public class ModelEdit extends StandardEditor<Model> {
}