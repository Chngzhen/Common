package pfu.common.gex;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pfu.common.gex.aspect.GlobalExceptionInterceptor;
import pfu.common.gex.property.Global;

/**
 * 全局异常处理自动配置类。
 *
 * @author chengzhen
 * @since 2021-05-07
 */
@Configuration
@EnableConfigurationProperties(Global.class)
public class AutoConfiguration {

    private final Global properties;
    public AutoConfiguration(Global properties) {
        this.properties = properties;
    }

    @Bean
    public GlobalExceptionInterceptor globalExceptionHandlingAdvice() {
        return new GlobalExceptionInterceptor(properties);
    }

    @Bean
    @ConditionalOnBean(GlobalExceptionInterceptor.class)
    public DefaultPointcutAdvisor globalExceptionHandlingAdvisor(GlobalExceptionInterceptor advice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(properties.getPointcut());

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(advice);
        return advisor;
    }

}
