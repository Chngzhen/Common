package pfu.common.global.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pfu.global")
public class GlobalProperties {

    private CallbackImpl callback = new CallbackImpl();

    public CallbackImpl getCallback() {
        return callback;
    }

    public void setCallback(CallbackImpl callback) {
        this.callback = callback;
    }
}
