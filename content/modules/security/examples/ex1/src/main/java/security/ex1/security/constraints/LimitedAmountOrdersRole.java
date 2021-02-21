package security.ex1.security.constraints;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import security.ex1.entity.Order;

// tag::role-1[]
@RowLevelRole( // <1>
        name = "Can see Orders with amount < 1000", // <2>
        code = "limited-amount-orders")             // <3>
public interface LimitedAmountOrdersRole {

    // end::role-1[]

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.amount < 1000")

    // tag::role-2[]
    // ...
    void order(); // <4>
    // end::role-2[]
}
