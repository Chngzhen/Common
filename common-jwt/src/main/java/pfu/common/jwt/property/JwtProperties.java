package pfu.common.jwt.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import pfu.common.jwt.bean.JwtUser;

@ConfigurationProperties(prefix = "pfu.jwt")
public class JwtProperties {

    private Header header = new Header();

    private Class<? extends JwtUser> jwtUserImpl = JwtUser.class;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Class<? extends JwtUser> getJwtUserImpl() {
        return jwtUserImpl;
    }

    public void setJwtUserImpl(Class<? extends JwtUser> jwtUserImpl) {
        this.jwtUserImpl = jwtUserImpl;
    }
}