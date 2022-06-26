package ui.ex2.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

// tag::anonymous-role[]
@ResourceRole(name = "AnonymousRole", code = AnonymousRole.CODE)
public interface AnonymousRole {

    String CODE = "anonymous-role";

    @MenuPolicy(menuIds = {"anonymousScreen"})
    @ScreenPolicy(screenIds = {"MainScreenSideMenu", "MyAnonymousScreen"})
    void screens();

    @MenuPolicy(menuIds = {"application"})
    void commonMenus();
}
// end::anonymous-role[]