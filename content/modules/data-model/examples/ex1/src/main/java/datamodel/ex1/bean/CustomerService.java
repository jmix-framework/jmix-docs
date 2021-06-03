package datamodel.ex1.bean;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Metadata;
import io.jmix.core.SaveContext;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.datatype.Enumeration;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.core.metamodel.model.Range;
import io.jmix.data.PersistenceHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import datamodel.ex1.entity.Customer;
import datamodel.ex1.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomerService {

    // tag::data-manager[]
    @Autowired
    private DataManager dataManager;

    // end::data-manager[]

    // tag::hard-delete[]
    public void hardDeleteCustomer(Customer customer) {
        dataManager.save(
                new SaveContext()
                        .removing(customer)
                        .setHint(PersistenceHints.SOFT_DELETION, false)
        );
    }
    // end::hard-delete[]

    // tag::load-hard-deleted[]
    public Customer loadHardDeletedCustomer(Id<Customer> customerId) {
        return dataManager.load(customerId).hint(PersistenceHints.SOFT_DELETION, false).one();
    }
    // end::load-hard-deleted[]

    // tag::hard-delete-em[]
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void hardRemoveCustomerByEM(Customer customer) {
        entityManager.setProperty(PersistenceHints.SOFT_DELETION, false);
        entityManager.remove(customer);
    }
    // end::hard-delete-em[]

    // tag::metadata[]
    @Autowired
    private Metadata metadata;

    public void printOrderProperties() {
        MetaClass metaClass = metadata.getClass(Order.class); // <1>
        for (MetaProperty metaProperty : metaClass.getProperties()) { // <2>

            String propertyName = metaProperty.getName(); // <3>
            MetaProperty.Type propertyType = metaProperty.getType(); // <4>
            Class<?> javaType = metaProperty.getJavaType(); // <5>
            Range propertyRange = metaProperty.getRange(); // <6>

            String info = "name: " + propertyName +
                    "\n type: " + propertyType +
                    "\n Java type: " + javaType +
                    "\n range: " + propertyRange;

            if (propertyRange.isClass()) { // <7>
                MetaClass refMetaClass = propertyRange.asClass(); // <8>
                Range.Cardinality cardinality = propertyRange.getCardinality(); // <9>
                info += "\n reference to: " + refMetaClass;
                info += "\n cardinality: " + cardinality;

            } else if (propertyRange.isEnum()) { // <10>
                Enumeration<?> enumeration = propertyRange.asEnumeration(); // <11>
                info += "\n enum: " + enumeration;

            } else if (propertyRange.isDatatype()) { // <12>
                Datatype<Object> propertyDatatype = propertyRange.asDatatype(); // <13>
                info += "\n data type: " + propertyDatatype;
            }

            System.out.println(info);
        }
    }
    // end::metadata[]
}
