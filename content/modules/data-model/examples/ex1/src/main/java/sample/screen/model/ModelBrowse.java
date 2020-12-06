package sample.screen.model;

import io.jmix.ui.screen.*;
import sample.entity.Model;

@UiController("sample_Model.browse")
@UiDescriptor("model-browse.xml")
@LookupComponent("modelsTable")
public class ModelBrowse extends StandardLookup<Model> {
}