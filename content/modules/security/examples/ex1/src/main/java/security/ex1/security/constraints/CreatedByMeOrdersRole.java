package security.ex1.security.constraints;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import security.ex1.entity.Order;

@RowLevelRole(
        name = "Can see only Orders created by themselves",
        code = "orders-created-by-themselves")
public interface CreatedByMeOrdersRole {

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.createdBy = :session$username")
    void order();
}
