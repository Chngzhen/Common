package pfu.common.base.exception;

/**
 * 本地异常基类。
 *
 * @author chngzhen@outlook.com
 * @since 2019-08-30
 */
public class LocalException extends RuntimeException {

	/**
	 * 默认错误码。
	 */
	protected int code;

	public LocalException() {
		super("内部异常");
	}

	public LocalException(String msg) {
		super(msg);
	}

	public LocalException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
