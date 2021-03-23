package pfu.common.validation;

import pfu.common.validation.impl.RangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author chngzhen@outlook.com
 * @since 2020/5/28
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
public @interface Range {

    int min();

    int max();

    String message() default "数据溢出可取值范围";

    /**
     * 解决 contains Constraint annotation, but does not contain a groups parameter 异常。
     *
     * @return 数组
     */
    Class<?>[] groups() default {};

    /**
     * 解决 contains Constraint annotation, but does not contain a payload parameter 异常。
     *
     * @return 数组
     */
    Class<? extends Payload>[] payload() default {};
}