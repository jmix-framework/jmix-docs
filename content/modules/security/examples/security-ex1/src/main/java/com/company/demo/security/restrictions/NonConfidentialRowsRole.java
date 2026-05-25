package com.company.demo.security.restrictions;

import com.company.demo.entity.CustomerDetail;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.model.RowLevelPredicate;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

// tag::role[]
@RowLevelRole(
        name = "Can see only non-confidential rows",
        code = "nonconfidential-rows")
public interface NonConfidentialRowsRole {

    @PredicateRowLevelPolicy(
            entityClass = CustomerDetail.class,
            actions = {RowLevelPolicyAction.READ})
    default RowLevelPredicate<CustomerDetail> customerDetailNotConfidential() {
        return customerDetail -> !Boolean.TRUE.equals(customerDetail.getConfidential());
    }
}
// end::role[]
