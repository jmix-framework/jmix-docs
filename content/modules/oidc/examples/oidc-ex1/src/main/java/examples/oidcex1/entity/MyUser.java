package examples.oidcex1.entity;
//tag::my-user[]
import io.jmix.oidc.user.DefaultJmixOidcUser;

public class MyUser extends DefaultJmixOidcUser {

    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
//end::my-user[]