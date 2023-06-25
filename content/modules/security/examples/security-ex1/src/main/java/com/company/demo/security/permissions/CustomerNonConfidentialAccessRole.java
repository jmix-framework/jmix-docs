package com.company.demo.security.permissions;

import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerDetail;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

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
    // tag::view-policy[]
    @ViewPolicy(
            viewIds = {"Customer.list"})
    // end::view-policy[]
    // tag::menu-policy[]
    @MenuPolicy(
            menuIds = {"Customer.list"})
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
    @ViewPolicy(
            viewIds = {"CustomerDetail.detail"})
    void customerDetail();
}
// end::role[]
