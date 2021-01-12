package datamodel.ex1.screen.model;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Model;

@UiController("sample_Model.edit")
@UiDescriptor("model-edit.xml")
@EditedEntityContainer("modelDc")
public class ModelEdit extends StandardEditor<Model> {
}