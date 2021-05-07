package pfu.common.global.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.Configuration;
import javax.validation.spi.ValidationProvider;

@ConfigurationProperties(prefix = "pfu.global")
public class GlobalProperties {

    /**
     * 回调。
     */
    private CallbackImpl callback = new CallbackImpl();

    /**
     * 切面表达式。
     */
    private String pointcut = "execution(public pfu.common.base.entity.ResponseJson pfu..controller.*.*(..))";

    /**
     * 校验实现。若为null，则不校验参数。
     */
    private Class<? extends ValidationProvider<? extends Configuration<?>>> validator = null;

    public CallbackImpl getCallback() {
        return callback;
    }

    public void setCallback(CallbackImpl callback) {
        this.callback = callback;
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public Class<? extends ValidationProvider<? extends Configuration<?>>> getValidator() {
        return validator;
    }

    public void setValidator(Class<? extends ValidationProvider<? extends Configuration<?>>> validator) {
        this.validator = validator;
    }
}
