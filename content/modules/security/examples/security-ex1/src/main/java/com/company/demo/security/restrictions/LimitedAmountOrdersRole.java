package com.company.demo.security.restrictions;

import com.company.demo.entity.Order;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

// tag::role[]
@RowLevelRole( // <1>
        name = "Can see Orders with amount < 1000", // <2>
        code = "limited-amount-orders")             // <3>
public interface LimitedAmountOrdersRole {

    // end::role[]

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.amount < 1000")

    // tag::role[]
    // ...
    void order(); // <4>
    // end::role[]
}
