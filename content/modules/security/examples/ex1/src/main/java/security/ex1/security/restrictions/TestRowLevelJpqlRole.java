package security.ex1.security.restrictions;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import security.ex1.entity.Customer;
import security.ex1.entity.Order;

@RowLevelRole(
        name = "Can see Orders with amount < 1000, Can see Customers of their region",
        code = "test-row-level-jpql-role")
public interface TestRowLevelJpqlRole {

    @JpqlRowLevelPolicy(
            entityClass = Customer.class,
            where = "{E}.region = :current_user_region")
    void customer();

    @JpqlRowLevelPolicy(
            entityClass = Order.class,
            where = "{E}.amount < 1000")
    void order();
}
