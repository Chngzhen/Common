package pfu.common.aspect.annotation;

import org.springframework.context.annotation.Import;
import pfu.common.aspect.registrar.ExDefinitionRegistrar;

import java.lang.annotation.*;

/**
 * 允许使用全局异常处理。
 *
 * @author chngzhen
 * @since 2021/4/7
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ExDefinitionRegistrar.class)
public @interface EnableGlobalExceptionHandling {

    /**
     * 切点。默认：任意 controller 包下所有类的所有方法。
     */
    String[] pointcut() default {"pfu.common.base.entity.ResponseJson *..controller..*.*(..)"};

    /**
     * 允许参数校验。默认：true。
     */
    boolean enableArgumentsValidation() default true;

}
