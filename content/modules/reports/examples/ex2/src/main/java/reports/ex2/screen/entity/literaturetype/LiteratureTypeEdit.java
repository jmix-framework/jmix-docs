package reports.ex2.screen.entity.literaturetype;

import io.jmix.ui.screen.*;
import reports.ex2.entity.LiteratureType;

@UiController("jmxrpr_LiteratureType.edit")
@UiDescriptor("literature-type-edit.xml")
@EditedEntityContainer("literatureTypeDc")
public class LiteratureTypeEdit extends StandardEditor<LiteratureType> {
}