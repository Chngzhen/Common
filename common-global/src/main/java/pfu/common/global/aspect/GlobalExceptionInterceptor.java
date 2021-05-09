package pfu.common.global.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import pfu.common.base.entity.ResponseJson;
import pfu.common.base.exception.LocalException;
import pfu.common.global.property.Global;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 全局异常拦截器。
 *
 * @author chngzhen
 * @since 2021-05-07
 */
public class GlobalExceptionInterceptor implements MethodInterceptor {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionInterceptor.class);

    private Validator validator;

    public GlobalExceptionInterceptor(Global properties) {
        if (null != properties.getValidator()) {
            try {
                this.validator = Validation
                        .byProvider(properties.getValidatorProvider())
                        .configure()
                        .buildValidatorFactory()
                        .getValidator();
            } catch (Exception e) {
                log.error("找不到校验实现[{}]", properties.getValidator());
            }
        }
    }

    @Override
    public Object invoke(@NonNull MethodInvocation invocation) {
        Object result;

        try {
            validParameters(invocation.getArguments());

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
        if (null == this.validator) {
            return;
        }

        for (Object arg : args) {
            Set<ConstraintViolation<Object>> validation = this.validator.validate(arg);
            if (null != validation && !validation.isEmpty()) {
                throw new LocalException(validation.iterator().next().getMessage());
            }
        }
    }

}
