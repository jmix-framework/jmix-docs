package security.sample.security.finegrained;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.Role;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.sample.entity.Order;

@Role(name = "Order full access", code = "OrderFullAccessRole")
public interface OrderFullAccessRole {

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();

    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Order.browse", "sample_Order.edit"})
    @MenuPolicy(menuIds = {"sample_Order.browse"})
    void order();
}
