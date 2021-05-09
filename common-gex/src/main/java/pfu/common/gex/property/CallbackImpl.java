package pfu.common.gex.property;

import pfu.common.gex.callback.Callback;
import pfu.common.gex.callback.DefaultCallback;

public class CallbackImpl {

    private Class<? extends Callback> exception = DefaultCallback.class;

    public Class<? extends Callback> getException() {
        return exception;
    }

    public void setException(Class<? extends Callback> exception) {
        this.exception = exception;
    }
}
