package pfu.common.exception;

/**
 * 登录异常。
 * <p></p>
 * 例如，验证码错误、账号或密码错误、Session失效等。该类异常应当注销当前用户的登录状态。
 *
 * @author chngzhen@outlook.com
 * @since 2020/6/4
 */
public class LoginException extends LocalException {

    public LoginException() {
        this("登录异常，请重新登录");
    }

    public LoginException(String msg) {
        this(RC_LOGIN, msg);
    }

    public LoginException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
