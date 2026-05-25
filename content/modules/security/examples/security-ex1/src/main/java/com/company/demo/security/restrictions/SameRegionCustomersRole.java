package com.company.demo.security.restrictions;

import com.company.demo.entity.Customer;
import com.company.demo.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;

// tag::role[]
@RowLevelRole(
        name = "Can see Customers of their region",
        code = "same-region-customers-role")
public interface SameRegionCustomersRole {

    @PredicateRowLevelPolicy(
            entityClass = Customer.class,
            actions = {RowLevelPolicyAction.READ})
    default RowLevelBiPredicate<Customer, ApplicationContext> customerOfMyRegion() {
        return (customer, applicationContext) -> {
            CurrentAuthentication currentAuthentication = applicationContext.getBean(CurrentAuthentication.class);
            return customer.getRegion() != null
                    && customer.getRegion().equals(((User) currentAuthentication.getUser()).getRegion());
        };
    }
}
// end::role[]
