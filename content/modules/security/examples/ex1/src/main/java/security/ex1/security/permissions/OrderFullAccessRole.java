package security.ex1.security.permissions;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.ex1.entity.Order;

@ResourceRole(
        name = "Order full access",
        code = "order-full-access")
public interface OrderFullAccessRole {

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();

    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Order.browse", "sample_Order.edit"})
    @MenuPolicy(menuIds = {"sample_Order.browse"})
    void order();
}
