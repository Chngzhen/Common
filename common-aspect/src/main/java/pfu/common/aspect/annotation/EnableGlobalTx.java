package pfu.common.aspect.annotation;

import org.springframework.context.annotation.Import;
import pfu.common.aspect.registrar.TxDefinitionRegistrar;

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
@Import(TxDefinitionRegistrar.class)
public @interface EnableGlobalTx {

    /**
     * 切点。默认：任意 service 包下所有类的所有方法。
     */
    String[] pointcut() default {"* *..service..*.*(..)"};

    /**
     * 超时时间。单位：秒。
     */
    int txMethodTimeOut() default 10;

}
