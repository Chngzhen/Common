package pfu.common.base.validation.impl;

import pfu.common.base.validation.Range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chngzhen@outlook.com
 * @since 2020/5/28
 */
public class RangeValidator implements ConstraintValidator<Range, Integer> {

    private Integer min;
    private Integer max;

    @Override
    public void initialize(Range constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        boolean match = true;
        if (null != value) {
            if (null != min) {
                match = value >= min;
            }
            if (match && null != max) {
                match = value <= max;
            }
        }
        return match;
    }

}
