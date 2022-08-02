package modularity.sample.base.app;

import org.springframework.stereotype.Component;

// tag::class[]
@Component("base_DepartmentService")
public class DepartmentService {

    public void sayHello() {
        System.out.println("Hello from base");
    }
}
// end::class[]
