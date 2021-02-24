package security.ex1.security.permissions;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.ex1.entity.Customer;
import security.ex1.entity.CustomerDetail;

// tag::role[]
@ResourceRole(
        name = "Customers: non-confidential info only, cannot delete",
        code = "customer-nonconfidential-access")
public interface CustomerNonConfidentialAccessRole {

    // tag::entity-policy[]
    @EntityPolicy(
            entityClass = Customer.class,
            actions = {EntityPolicyAction.READ,
                    EntityPolicyAction.CREATE,
                    EntityPolicyAction.UPDATE})
    // end::entity-policy[]
    // tag::entity-attribute-policy[]
    @EntityAttributePolicy(
            entityClass = Customer.class,
            attributes = {"name", "region", "details"},
            action = EntityAttributePolicyAction.MODIFY)
    // end::entity-attribute-policy[]
    // tag::screen-policy[]
    @ScreenPolicy(
            screenIds = {"sample_Customer.browse", "sample_Customer.edit"})
    // end::screen-policy[]
    // tag::menu-policy[]
    @MenuPolicy(
            menuIds = {"sample_Customer.browse"})
    // end::menu-policy[]
    // tag::method[]
    void customer();
    // end::method[]

    @EntityPolicy(
            entityClass = CustomerDetail.class,
            actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(
            entityClass = CustomerDetail.class,
            attributes = {"content"},
            action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(
            screenIds = {"sample_CustomerDetail.edit"})
    void customerDetail();

    @MenuPolicy(
            menuIds = {"application"})
    void commonMenus();
}
// end::role[]
