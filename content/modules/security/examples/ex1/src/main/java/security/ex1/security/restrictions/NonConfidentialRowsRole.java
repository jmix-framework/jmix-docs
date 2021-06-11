package security.ex1.security.restrictions;

import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import security.ex1.entity.CustomerDetail;

import java.util.function.Predicate;

// tag::role[]
@RowLevelRole(
        name = "Can see only non-confidential rows",
        code = "nonconfidential-rows")
public interface NonConfidentialRowsRole {

    @PredicateRowLevelPolicy(
            entityClass = CustomerDetail.class,
            actions = {RowLevelPolicyAction.READ})
    default Predicate<CustomerDetail> customerDetailNotConfidential() {
        return customerDetail -> !Boolean.TRUE.equals(customerDetail.getConfidential());
    }
}
// end::role[]
