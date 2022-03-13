package ui.ex2.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.ScreenPolicy;

// tag::anonymous-role[]
@ResourceRole(name = "AnonymousRole", code = AnonymousRole.CODE)
public interface AnonymousRole {

    String CODE = "anonymous-role";

    @ScreenPolicy(screenIds = {"MyAnonymousScreen"})
    void screens();
}
// end::anonymous-role[]