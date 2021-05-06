package pfu.common.jwt.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pfu.common.base.exception.LocalException;
import pfu.common.jwt.bean.JwtUser;
import pfu.common.jwt.property.JwtProperties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * JWT控制层抽象类。
 *
 * @author chngzhen
 * @since 2021-05-06
 */
public abstract class JwtController {

    private final Logger log = LoggerFactory.getLogger(JwtController.class);

    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtProperties properties;

    protected JwtUser getJwtUser() {
        String jwtUserHeader = request.getHeader(properties.getHeader().getJwtUser());
        assert null != jwtUserHeader && !"".equals(jwtUserHeader.trim());

        try {
            String str = URLDecoder.decode(jwtUserHeader, StandardCharsets.UTF_8);
            log.info("Jwt User: {}", str);
            return JSON.parseObject(str, properties.getJwtUserImpl());
        } catch (Exception e) {
            throw new LocalException("解析权限header失败");
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public JwtProperties getProperties() {
        return properties;
    }

    public void setProperties(JwtProperties properties) {
        this.properties = properties;
    }
}
