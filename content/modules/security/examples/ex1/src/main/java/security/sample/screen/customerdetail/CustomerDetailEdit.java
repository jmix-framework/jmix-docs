package security.sample.screen.customerdetail;

import io.jmix.ui.screen.*;
import security.sample.entity.CustomerDetail;

@UiController("sample_CustomerDetail.edit")
@UiDescriptor("customer-detail-edit.xml")
@EditedEntityContainer("customerDetailDc")
public class CustomerDetailEdit extends StandardEditor<CustomerDetail> {
}