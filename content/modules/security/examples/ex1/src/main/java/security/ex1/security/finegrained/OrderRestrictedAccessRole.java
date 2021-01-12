package security.ex1.security.finegrained;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.*;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.ex1.entity.Order;

@Role(name = "Cannot delete orders", code = "OrderRestrictedAccessRole")
public interface OrderRestrictedAccessRole {

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();

    @EntityPolicy(entityClass = Order.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Order.class, attributes = {"date", "amount", "customer"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Order.browse", "sample_Order.edit"})
    @MenuPolicy(menuIds = {"sample_Order.browse"})
    void order();
}
