package security.sample.security.finegrained;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.Role;
import security.sample.entity.Order;

@Role(name = "Can see only Orders created by them", code = "OrdersCreatedByMeOnlyRole")
public interface OrdersCreatedByMeOnlyRole {

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.createdBy = :session$username")
    void order();
}
