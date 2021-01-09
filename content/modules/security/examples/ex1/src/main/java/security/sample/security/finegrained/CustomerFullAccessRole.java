package security.sample.security.finegrained;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.Role;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.sample.entity.Customer;
import security.sample.entity.CustomerDetail;

@Role(name = "Customer full access", code = "CustomerFullAccessRole")
public interface CustomerFullAccessRole {

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();

    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Customer.browse", "sample_Customer.edit"})
    @MenuPolicy(menuIds = {"sample_Customer.browse"})
    void customer();

    @EntityPolicy(entityClass = CustomerDetail.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = CustomerDetail.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_CustomerDetail.edit"})
    void customerDetail();
}
