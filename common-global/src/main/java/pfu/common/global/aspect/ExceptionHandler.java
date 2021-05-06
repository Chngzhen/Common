package pfu.common.global.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pfu.common.base.entity.ResponseJson;
import pfu.common.base.exception.LocalException;
import pfu.common.global.callback.Callback;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 异常处理器。
 *
 * @author chngzhen@outlook.com
 * @since 2020-03-16
 */
@Aspect
public class ExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    private final Validator validator;
    private final Callback callback;

    public ExceptionHandler(Callback callback) {
        this.callback = callback;
        this.validator = Validation
                .byProvider(HibernateValidator.class)
                .configure()
                .failFast(false)
                .buildValidatorFactory()
                .getValidator();
    }

    /**
     * API接口切面。
     */
    @Pointcut("execution(public pfu.common.base.entity.ResponseJson pfu..controller.*.*(..))")
    public void apiCut() {
        // 无任何逻辑，仅用作各类通知的可复用切面。
    }

    /**
     * 切入API接口，校验参数并处理异常。
     *
     * @param pjp 切入点
     * @return 接口的返回值
     */
    @Around("apiCut()")
    public Object api(ProceedingJoinPoint pjp) {
        Object obj;

        try {
            validParameters(pjp.getArgs());
            obj = pjp.proceed();
        } catch (LocalException e) {
            // LocalException 异常会自定义异常信息。
            log.error(e.getMessage(), e);
            callback.run();
            return ResponseJson.failureWithMsg(e.getMessage());
        } catch (Throwable t) {
            // 其他异常统一返回“内部异常”。
            log.error("内部异常", t);
            callback.run();
            return ResponseJson.failureWithMsg("内部异常");
        }

        callback.run();
        return obj;
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
