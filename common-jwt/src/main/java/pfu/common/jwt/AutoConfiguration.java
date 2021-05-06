package pfu.common.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pfu.common.jwt.property.JwtProperties;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class AutoConfiguration {

    private final JwtProperties properties;
    public AutoConfiguration(JwtProperties properties) {
        this.properties = properties;
    }

}
