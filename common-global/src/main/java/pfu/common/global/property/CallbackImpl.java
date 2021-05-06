package pfu.common.global.property;

import pfu.common.global.callback.Callback;
import pfu.common.global.callback.DefaultCallback;

public class CallbackImpl {

    private Class<? extends Callback> exception = DefaultCallback.class;

    public Class<? extends Callback> getException() {
        return exception;
    }

    public void setException(Class<? extends Callback> exception) {
        this.exception = exception;
    }
}
