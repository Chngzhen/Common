package pfu.common.jwt.property;

public class Header {

    private String jwtUser = "Authorization-User";

    public String getJwtUser() {
        return jwtUser;
    }

    public void setJwtUser(String jwtUser) {
        this.jwtUser = jwtUser;
    }
}
