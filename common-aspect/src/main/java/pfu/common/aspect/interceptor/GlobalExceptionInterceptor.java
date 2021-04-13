package pfu.common.aspect.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pfu.common.base.entity.ResponseJson;
import pfu.common.base.exception.LocalException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 全局异常拦截器。
 */
public class GlobalExceptionInterceptor implements MethodInterceptor {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionInterceptor.class);

    private final Validator validator;
    private final boolean validArguments;

    public GlobalExceptionInterceptor() {
        this(true);
    }

    public GlobalExceptionInterceptor(boolean validArguments) {
        this.validator = Validation
                .byProvider(HibernateValidator.class)
                .configure()
                .failFast(false)
                .buildValidatorFactory()
                .getValidator();
        this.validArguments = validArguments;
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        Object result = null;

        try {
            if (this.validArguments) {
                validParameters(invocation.getArguments());
            }

            result = invocation.proceed();
        } catch (LocalException e) {
            // LocalException 异常会自定义异常信息。
            log.error(e.getMessage(), e);
            return ResponseJson.failureWithMsg(e.getMessage());
        } catch (Throwable t) {
            // 其他异常统一返回“内部异常”。
            log.error("内部异常", t);
            return ResponseJson.failureWithMsg("内部异常");
        }

        return result;
    }

    private void validParameters(Object... args) {
        for (Object arg : args) {
            Set<ConstraintViolation<Object>> validation = this.validator.validate(arg);
            if (null != validation && !validation.isEmpty()) {
                throw new LocalException(validation.iterator().next().getMessage());
            }
        }
    }

}
