package dynattr.ex1.screen.contract;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Contract;

@UiController("sample_Contract.edit")
@UiDescriptor("contract-edit.xml")
@EditedEntityContainer("contractDc")
public class ContractEdit extends StandardEditor<Contract> {
}