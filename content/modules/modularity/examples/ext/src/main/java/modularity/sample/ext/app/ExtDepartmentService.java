package modularity.sample.ext.app;

import modularity.sample.base.app.DepartmentService;

// tag::class[]
public class ExtDepartmentService extends DepartmentService {

    @Override
    public void sayHello() {
        super.sayHello();
        System.out.println("Hello from ext");
    }
}
// end::class[]
