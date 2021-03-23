package pfu.common.entity;

/**
 * Http响应内容包装类。
 *
 * @author chngzhen@outlook.com
 * @since 2019-08-15
 */
public class ResponseJson<T> {

	/**
	 * 结果码：{@code 0} - 成功，其他表示失败。默认：0。
	 */
	private int code;

	/**
	 * 结果描述。当 {@link #code} 不为 {@code 0} 时有值。
	 */
	private String msg = "操作成功";

	/**
	 * 响应的数据。当且仅当 {@link #code} 为 {@code 0} 时有值。
	 */
	private T data;


	/**
	 * 初始化错误响应内容。
	 *
	 * @param code 错误码
	 * @param msg 错误描述
	 */
	public void error(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 初始化成功响应内容。
	 *
	 * @param data 响应数据
	 */
	public void successWithData(T data) {
		this.data = data;
	}

	public void successWithMsg(String msg) {
		this.msg = msg;
	}

	public void successWith(T data, String msg) {
		this.data = data;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	@Override
	public String toString() {
		return '{' +
				"code: " + code +
				", msg: \"" + msg + '"' +
				", data: " + data +
			'}';
	}
}
