package pfu.common.aspect.registrar;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import pfu.common.aspect.TransactionConfig;
import pfu.common.aspect.annotation.EnableGlobalTx;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chngzhen
 * @since 2021/4/7
 */
public class TxDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableGlobalTx.class.getName());
        assert attributes != null;
        String pointcut = getPointcut(attributes);
        int txMethodTimeOut = getTxMethodTimeOut(attributes);

        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(TransactionConfig.class);
        definition.addPropertyValue("pointcut", pointcut);
        definition.addPropertyValue("txMethodTimeOut", txMethodTimeOut);
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        String beanName = StringUtils.uncapitalize(TransactionConfig.class.getSimpleName());
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    private String getPointcut(Map<String, Object> attributes) {
        Set<String> pointcut = new HashSet<>();
        for (String pc : (String[]) attributes.get("pointcut")) {
            if (StringUtils.hasText(pc)) {
                pointcut.add("execution(" + pc + " )");
            }
        }
        return StringUtils.collectionToDelimitedString(pointcut, "||");
    }

    private int getTxMethodTimeOut(Map<String, Object> attributes) {
        return (int) attributes.get("txMethodTimeOut");
    }

}
