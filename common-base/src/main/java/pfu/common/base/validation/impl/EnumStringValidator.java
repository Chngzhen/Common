package pfu.common.base.validation.impl;

import pfu.common.base.validation.EnumString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chngzhen@outlook.com
 * @since 2020/5/28
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {

    private String[] enums;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        enums = constraintAnnotation.enums();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return false;
        }

        boolean match = false;
        for (String obj : enums) {
            match = value.equals(obj);
            if (match) {
                break;
            }
        }
        return match;
    }

}
