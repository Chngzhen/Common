package pfu.common.base.exception;

/**
 * 本地异常基类。
 *
 * @author chngzhen@outlook.com
 * @since 2019-08-30
 */
public class LocalException extends RuntimeException {

	/**
	 * 其他。
	 */
	public static final int RESULT_CODE_OTHER = 1000;

	/**
	 * 不合法的请求参数。通常用于请求参数校验失败。
	 */
	public static final int RESULT_CODE_INVALID_PARAM = 1001;

	/**
	 * 数据不存在。通常用于数据库查询结果为空时。
	 */
	public static final int RESULT_CODE_DATA_NOT_FOUND = 1101;

	/**
	 * 数据库操作异常。通常用于数据库增删改不符合预期时。
	 */
	public static final int RESULT_CODE_DATABASE_CURD = 1102;

	public static final int RESULT_CODE_PARAM_MISSING = 1103;

	public static final int RESULT_CODE_DATA_COPY = 1201;

	public static final int RC_LOGIN = 1300;

	public static final int RC_LOGIN_WRONG_VERIFY_CODE = 1401;

	public static final int RC_LOGIN_WRONG_ACCOUNT_OR_PASSWORD = 1402;

	public static final int RC_LOGIN_STATUS_MISSING = 1403;

	/**
	 * 默认错误码。
	 */
	protected int code = RESULT_CODE_OTHER;

	public LocalException() {
		super("内部异常");
	}

	public LocalException(String msg) {
		super(msg);
	}

	public int getCode() {
		return code;
	}
}
