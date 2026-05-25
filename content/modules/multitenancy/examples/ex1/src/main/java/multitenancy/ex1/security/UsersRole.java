package multitenancy.ex1.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import multitenancy.ex1.entity.Customer;
import multitenancy.ex1.entity.Region;

// tag::mt-1[]
@ResourceRole(name = "Users", code = "users", scope = "UI")
public interface UsersRole {
    // ...

    // end::mt-1[]

    @MenuPolicy(menuIds = {"mtex1_Region.browse", "mtex1_Customer.browse"})
    @ScreenPolicy(screenIds = {"mtex1_Region.browse", "mtex1_Customer.browse", "mtex1_Customer.edit", "mtex1_Region.edit"}, screenClasses = {})
    void screens();

    // tag::mt-2[]
    @EntityAttributePolicy(
            entityClass = Customer.class, attributes = {"region", "name", "version", "id"},
            action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();
    // end::mt-2[]

    @EntityAttributePolicy(entityClass = Region.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Region.class, actions = EntityPolicyAction.ALL)
    void region();
}