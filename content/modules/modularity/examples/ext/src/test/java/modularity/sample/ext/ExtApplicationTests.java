package modularity.sample.ext;

import io.jmix.core.*;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.security.SystemAuthenticator;
import modularity.sample.base.entity.Department;
import modularity.sample.base.entity.Employee;
import modularity.sample.ext.entity.ExtDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExtApplicationTests {

    @Autowired
    Metadata metadata;
    @Autowired
    ExtendedEntities extendedEntities;
    @Autowired
    DataManager dataManager;
    @Autowired
    SystemAuthenticator authenticator;

    // tag::module-prop[]
    @Autowired
    Environment environment;
    @Autowired
    JmixModules jmixModules;

    void checkProperties() {
        String envProp = environment.getProperty("jmix.ui.menu-config");
        // modularity/sample/ext/menu.xml

        List<String> moduleProps = jmixModules.getPropertyValues("jmix.ui.menu-config");
        // [io/jmix/ui/menu.xml, modularity/sample/base/menu.xml, modularity/sample/ext/menu.xml]
    }
    // end::module-prop[]

    // tag::bean-selector[]
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    JmixModulesAwareBeanSelector beanSelector;

    BigDecimal calculate() {
        Map<String, AmountCalculator> calculators = applicationContext.getBeansOfType(AmountCalculator.class);
        AmountCalculator calculator = beanSelector.selectFrom(calculators.values());
        return calculator.calculate();
    }
    // end::bean-selector[]

    @Test
    void testModuleProperties() {
        String envProp = environment.getProperty("jmix.ui.menu-config");
        assertEquals("modularity/sample/ext/menu.xml", envProp);

        List<String> moduleProps = jmixModules.getPropertyValues("jmix.ui.menu-config");
        assertEquals(Arrays.asList("io/jmix/ui/menu.xml",
                        "io/jmix/securityui/menu.xml",
                        "modularity/sample/base/menu.xml",
                        "modularity/sample/ext/menu.xml"),
                moduleProps);
    }

    @Test
    void testExtDepartment() {
        MetaClass baseMetaClass = metadata.getClass(Department.class);
        assertEquals("ExtDepartment", baseMetaClass.getName());

        MetaClass originalMetaClass = extendedEntities.getOriginalMetaClass(baseMetaClass);
        assertEquals("base_Department", originalMetaClass.getName());

        authenticator.runWithSystem(() -> {
            ExtDepartment department1 = dataManager.create(ExtDepartment.class);
            department1.setName("Dept 1");
            department1.setDescription("some department");

            Employee employee1 = dataManager.create(Employee.class);
            UUID employeeId = employee1.getId();
            employee1.setLastName("Smith");
            employee1.setDepartment(department1);

            dataManager.save(department1, employee1);

            // tag::ext-ref[]
            Employee employee = dataManager.load(Employee.class).id(employeeId).one();

            ExtDepartment department = (ExtDepartment) employee.getDepartment();
            String description = department.getDescription();
            // end::ext-ref[]

            assertEquals(department1, department);
        });

    }

    @Test
    void contextLoads() {
    }
}
