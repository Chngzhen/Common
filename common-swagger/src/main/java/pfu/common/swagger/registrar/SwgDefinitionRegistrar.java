package pfu.common.swagger.registrar;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import pfu.common.swagger.SwaggerConfiguration;
import pfu.common.swagger.annotation.EnableSwagger;

import java.util.Map;

/**
 * @author chngzhen
 * @since 2021/4/7
 */
public class SwgDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableSwagger.class.getName());
        assert attributes != null;

        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(SwaggerConfiguration.class);
        definition.addPropertyValue("title", attributes.get("title"));
        definition.addPropertyValue("contact", attributes.get("contact"));
        definition.addPropertyValue("contactUrl", attributes.get("contactUrl"));
        definition.addPropertyValue("contactEmail", attributes.get("contactEmail"));
        definition.addPropertyValue("version", attributes.get("version"));
        definition.addPropertyValue("description", attributes.get("description"));
        definition.addPropertyValue("basePackage", attributes.get("basePackage"));
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        String beanName = StringUtils.uncapitalize(SwaggerConfiguration.class.getSimpleName());
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

}
