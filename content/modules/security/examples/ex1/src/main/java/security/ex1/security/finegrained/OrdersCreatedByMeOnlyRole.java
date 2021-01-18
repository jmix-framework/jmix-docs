package security.ex1.security.finegrained;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.Role;
import security.ex1.entity.Order;

@Role(name = "Can see only Orders created by them", code = "OrdersCreatedByMeOnlyRole")
public interface OrdersCreatedByMeOnlyRole {

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.createdBy = :session$username")
    void order();
}
