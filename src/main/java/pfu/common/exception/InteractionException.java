package pfu.common.exception;

/**
 * 交互异常，指需要展示给用户的异常信息。
 *
 * @author chngzhen@outlook.com
 * @since 2020-02-29
 */
public class InteractionException extends LocalException {

    public InteractionException() {
        this("操作异常");
    }

    public InteractionException(String msg) {
        this(RESULT_CODE_DATABASE_CURD, msg);
    }

    public InteractionException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
