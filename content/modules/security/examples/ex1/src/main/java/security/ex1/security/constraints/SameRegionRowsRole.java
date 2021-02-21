package security.ex1.security.constraints;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import security.ex1.entity.Customer;
import security.ex1.entity.Order;

// tag::role[]
@RowLevelRole(
        name = "Can see Customers and Orders of their region",
        code = "same-region-rows")
public interface SameRegionRowsRole {

    @JpqlRowLevelPolicy(
            entityClass = Customer.class,
            where = "{E}.region = :current_user_region")
    void customer();

    @JpqlRowLevelPolicy(
            entityClass = Order.class,
            where = "{E}.customer.region = :current_user_region")
    void order();
}
// end::role[]