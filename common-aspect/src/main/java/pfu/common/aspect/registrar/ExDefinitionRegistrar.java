package pfu.common.aspect.registrar;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import pfu.common.aspect.GlobalExceptionHandlingConfig;
import pfu.common.aspect.annotation.EnableGlobalExceptionHandling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chngzhen
 * @since 2021/4/7
 */
public class ExDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableGlobalExceptionHandling.class.getName());
        assert attributes != null;
        String pointcut = getPointcut(attributes);
        boolean enableArgumentsValidation = getEnableArgumentsValidation(attributes);

        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(GlobalExceptionHandlingConfig.class);
        definition.addPropertyValue("pointcutExpression", pointcut);
        definition.addPropertyValue("enableArgumentsValidation", enableArgumentsValidation);
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        String beanName = StringUtils.uncapitalize(GlobalExceptionHandlingConfig.class.getSimpleName());
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

    private boolean getEnableArgumentsValidation(Map<String, Object> attributes) {
        return (boolean) attributes.get("enableArgumentsValidation");
    }

}
