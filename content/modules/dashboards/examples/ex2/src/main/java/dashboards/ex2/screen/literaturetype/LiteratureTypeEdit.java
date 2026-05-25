package dashboards.ex2.screen.literaturetype;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.LiteratureType;

@UiController("sample_LiteratureType.edit")
@UiDescriptor("literature-type-edit.xml")
@EditedEntityContainer("literatureTypeDc")
public class LiteratureTypeEdit extends StandardEditor<LiteratureType> {
}