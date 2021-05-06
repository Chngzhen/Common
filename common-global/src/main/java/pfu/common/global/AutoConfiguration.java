package pfu.common.global;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pfu.common.global.aspect.ExceptionHandler;
import pfu.common.global.callback.Callback;
import pfu.common.global.property.GlobalProperties;

import java.lang.reflect.InvocationTargetException;

@Configuration
@EnableConfigurationProperties(GlobalProperties.class)
public class AutoConfiguration {

    private final GlobalProperties properties;
    public AutoConfiguration(GlobalProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ExceptionHandler exceptionHandler() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends Callback> clazz = properties.getCallback().getException();
        return new ExceptionHandler(clazz.getDeclaredConstructor().newInstance());
    }

}
