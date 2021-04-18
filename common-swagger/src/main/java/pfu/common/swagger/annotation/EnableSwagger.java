package pfu.common.swagger.annotation;

import org.springframework.context.annotation.Import;
import pfu.common.swagger.SwaggerConfiguration;
import pfu.common.swagger.registrar.SwgDefinitionRegistrar;

import java.lang.annotation.*;

/**
 * 允许使用全局事务。
 *
 * @author chngzhen
 * @since 2021/4/7
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SwgDefinitionRegistrar.class)
public @interface EnableSwagger {

    String title() default "Swagger文档";

    String contact() default "";

    String contactUrl() default "";

    String contactEmail() default "";

    String version() default "1.0.0";

    String description() default "接口文档";

    String basePackage();

}
