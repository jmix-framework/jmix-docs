package datamodel.ex1.screen.model;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Model;

@UiController("sample_Model.browse")
@UiDescriptor("model-browse.xml")
@LookupComponent("modelsTable")
public class ModelBrowse extends StandardLookup<Model> {
}