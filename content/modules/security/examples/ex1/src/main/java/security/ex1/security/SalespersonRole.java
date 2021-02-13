package security.ex1.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.*;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import security.ex1.entity.Customer;
import security.ex1.entity.CustomerDetail;
import security.ex1.entity.Order;

import java.util.function.Predicate;

@ResourceRole(name = "Salesperson", code = "salesperson")
public interface SalespersonRole {

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();

    @EntityPolicy(entityClass = Customer.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Customer.class, attributes = {"name", "region", "details"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Customer.browse", "sample_Customer.edit"})
    @MenuPolicy(menuIds = {"sample_Customer.browse"})
    @JpqlRowLevelPolicy(entityClass = Customer.class, where = "{E}.region = (select u.region from sample_User u where u.username = :session$username)")
    void customer();

    @EntityPolicy(entityClass = CustomerDetail.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = CustomerDetail.class, attributes = {"content"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_CustomerDetail.edit"})
    void customerDetail();

    @PredicateRowLevelPolicy(entityClass = CustomerDetail.class, actions = {RowLevelPolicyAction.READ})
    static Predicate<CustomerDetail> customerDetailNotConfidential() {
        return customerDetail -> !Boolean.TRUE.equals(customerDetail.getConfidential());
    }

    @EntityPolicy(entityClass = Order.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Order.class, attributes = {"date", "amount", "customer"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sample_Order.browse", "sample_Order.edit"})
    @MenuPolicy(menuIds = {"sample_Order.browse"})
    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.createdBy = :session$username")
    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.customer.region = (select u.region from sample_User u where u.username = :session$username)")
    void order();
}
