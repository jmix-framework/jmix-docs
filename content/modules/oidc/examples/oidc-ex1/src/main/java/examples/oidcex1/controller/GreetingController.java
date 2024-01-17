package examples.oidcex1.controller;

//tag::code[]
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/authenticated/hello")
    public String authenticatedHello() {
        return "authenticated-hello";
    }

    @GetMapping("/anonymous/hi")
    public String anonymousHello() {
        return "anonymous-hi";
    }
}
//end::code[]