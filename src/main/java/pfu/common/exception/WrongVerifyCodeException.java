package pfu.common.exception;

/**
 * 错误的验证码异常。
 *
 * @author chngzhen@outlook.com
 * @since 2020/12/13
 */
public class WrongVerifyCodeException extends LoginException {

    public WrongVerifyCodeException() {
        this("验证码错误");
    }

    public WrongVerifyCodeException(String msg) {
        super(RC_LOGIN_WRONG_VERIFY_CODE, msg);
    }

}
