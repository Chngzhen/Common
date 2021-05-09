package pfu.common.gtx;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;
import pfu.common.gtx.property.Rule;
import pfu.common.gtx.property.Transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局事务自动配置类。当YML配置了pfu.transaction.pointcut且引入了spring-tx，本配置才会生效。
 *
 * @author chngzhen
 * @since 2021/4/7
 */
@Configuration
@EnableConfigurationProperties(Transaction.class)
@ConditionalOnProperty(prefix = Transaction.PREFIX, name = "pointcut")
public class AutoConfiguration {

    private final Transaction properties;
    public AutoConfiguration(Transaction properties) {
        this.properties = properties;
    }

    @Primary
    @Bean
    @ConditionalOnBean(PlatformTransactionManager.class)
    public TransactionInterceptor globalTxAdvice(PlatformTransactionManager platformTransactionManager) {
        RuleBasedTransactionAttribute readOnlyRule = new RuleBasedTransactionAttribute();
        readOnlyRule.setReadOnly(true);
        readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        RuleBasedTransactionAttribute requireRule = new RuleBasedTransactionAttribute();
        requireRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requireRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requireRule.setTimeout(properties.getTimeout());

        Map<String, TransactionAttribute> nameMap = new HashMap<>();

        Rule rule = properties.getRule();
        if (null != rule.getReadOnly() && rule.getReadOnly().length > 0) {
            for (String item : rule.getReadOnly()) {
                nameMap.put(item, readOnlyRule);
            }
        }

        if (null != rule.getRequired() && rule.getRequired().length > 0) {
            for (String item : rule.getRequired()) {
                nameMap.put(item, requireRule);
            }
        }

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(nameMap);
        return new TransactionInterceptor(platformTransactionManager, source);

    }

    /**
     * 设置切面=切点pointcut+通知TxAdvice
     *
     * @return Advisor
     */
    @Bean
    @ConditionalOnBean(name = "globalTxAdvice")
    public Advisor txAdviceAdvisor(TransactionInterceptor advice) {
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(this.properties.getPointcut());
        return new DefaultPointcutAdvisor(pc, advice);
    }

}
