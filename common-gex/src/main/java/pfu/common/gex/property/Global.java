package pfu.common.gex.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import pfu.common.gex.callback.Callback;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "pfu.global")
public class Global {

    /**
     * 回调。
     */
    private List<Callback> callbackImpl = new ArrayList<>();

    /**
     * 切面表达式。
     */
    private String pointcut = "execution(public pfu.common.base.entity.ResponseJson pfu..controller.*.*(..))";

    /**
     * 校验实现。若为null，则不校验参数。
     */
    private Class<?> validator = null;

    public List<Callback> getCallbackImpl() {
        return callbackImpl;
    }

    public void setCallbackImpl(List<Callback> callbackImpl) {
        this.callbackImpl = callbackImpl;
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public <V> Class<V> getValidatorProvider() {
        return (Class<V>) validator;
    }

    public Class<?> getValidator() {
        return validator;
    }

    public void setValidator(Class<?> validator) {
        this.validator = validator;
    }
}
