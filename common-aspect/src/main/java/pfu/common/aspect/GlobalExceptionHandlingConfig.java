package pfu.common.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import pfu.common.aspect.interceptor.GlobalExceptionInterceptor;

/**
 * @author chngzhen
 * @since 2021/4/7
 */
public class GlobalExceptionHandlingConfig implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlingConfig.class);

    /**
     * 超时时间
     */
    private boolean enableArgumentsValidation;

    /**
     * 切点表达式
     */
    private String pointcutExpression;

    public void setEnableArgumentsValidation(boolean enableArgumentsValidation) {
        this.enableArgumentsValidation = enableArgumentsValidation;
    }

    public boolean isEnableArgumentsValidation() {
        return enableArgumentsValidation;
    }

    public void setPointcutExpression(String pointcutExpression) {
        this.pointcutExpression = pointcutExpression;
    }

    public String getPointcutExpression() {
        return pointcutExpression;
    }

    @Bean
    public GlobalExceptionInterceptor globalExceptionHandlingAdvice() {
        return new GlobalExceptionInterceptor(isEnableArgumentsValidation());
    }

    @Bean
    @ConditionalOnBean(GlobalExceptionInterceptor.class)
    public DefaultPointcutAdvisor globalExceptionHandlingAdvisor(GlobalExceptionInterceptor advice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(getPointcutExpression());

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(advice);
        return advisor;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("启用全局异常处理：切面-{}，是否允许参数校验-{}", getPointcutExpression(), isEnableArgumentsValidation());
    }
}
