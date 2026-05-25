package webdav.ex1.screen.contract;

import io.jmix.ui.screen.*;
import webdav.ex1.entity.Contract;

@UiController("sample_Contract.edit")
@UiDescriptor("contract-edit.xml")
@EditedEntityContainer("contractDc")
public class ContractEdit extends StandardEditor<Contract> {
}