package security.ex1.security.finegrained;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import security.ex1.entity.Customer;
import security.ex1.entity.Order;

@RowLevelRole(name = "Can work with Customer and Order of their region", code = "SameRegionOnlyRole")
public interface SameRegionOnlyRole {

    @JpqlRowLevelPolicy(entityClass = Customer.class, where = "{E}.region = (select u.region from sample_User u where u.username = :session$username)")
    void customer();

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.customer.region = (select u.region from sample_User u where u.username = :session$username)")
    void order();
}
