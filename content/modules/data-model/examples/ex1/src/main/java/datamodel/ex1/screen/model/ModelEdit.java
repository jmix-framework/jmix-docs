package datamodel.ex1.screen.model;

import datamodel.ex1.entity.Model;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("sample_Model.edit")
@UiDescriptor("model-edit.xml")
@EditedEntityContainer("modelDc")
public class ModelEdit extends StandardEditor<Model> {
}