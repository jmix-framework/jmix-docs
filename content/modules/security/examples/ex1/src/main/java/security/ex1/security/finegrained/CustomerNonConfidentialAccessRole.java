package security.ex1.security.finegrained;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.ex1.entity.Customer;
import security.ex1.entity.CustomerDetail;

import java.util.function.Predicate;

@ResourceRole(name = "Can see only non-confidential info, cannot delete customers", code = "CustomerNonConfidentialAccessRole")
public interface CustomerNonConfidentialAccessRole {

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();

    @EntityPolicy(entityClass = Customer.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Customer.class, attributes = {"name", "region", "details"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Customer.browse", "sample_Customer.edit"})
    @MenuPolicy(menuIds = {"sample_Customer.browse"})
    void customer();

    @EntityPolicy(entityClass = CustomerDetail.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = CustomerDetail.class, attributes = {"content"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_CustomerDetail.edit"})
    void customerDetail();

    @PredicateRowLevelPolicy(entityClass = CustomerDetail.class, actions = {RowLevelPolicyAction.READ})
    static Predicate<CustomerDetail> customerDetailNotConfidential() {
        return customerDetail -> !Boolean.TRUE.equals(customerDetail.getConfidential());
    }
}
