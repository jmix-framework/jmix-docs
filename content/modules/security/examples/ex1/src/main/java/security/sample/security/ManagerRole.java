package security.sample.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.Role;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.sample.entity.Customer;
import security.sample.entity.CustomerDetail;
import security.sample.entity.Order;

@Role(name = "Manager", code = "manager")
public interface ManagerRole {

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();

    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Customer.browse", "sample_Customer.edit"})
    @MenuPolicy(menuIds = {"sample_Customer.browse"})
    @JpqlRowLevelPolicy(entityClass = Customer.class, where = "{E}.region = (select u.region from sample_User u where u.username = :session$username)")
    void customer();

    @EntityPolicy(entityClass = CustomerDetail.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = CustomerDetail.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_CustomerDetail.edit"})
    void customerDetail();

    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Order.browse", "sample_Order.edit"})
    @MenuPolicy(menuIds = {"sample_Order.browse"})
    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.customer.region = (select u.region from sample_User u where u.username = :session$username)")
    void order();
}
