package pfu.common.base.entity;

/**
 * Http响应内容包装类。
 *
 * @author chngzhen@outlook.com
 * @since 2019-08-15
 */
public class ResponseJson<T> {

	private static final int CODE_SUCCESS = 0;
	private static final int CODE_GENERIC_FAILURE = 1;

	/**
	 * 结果码：{@code 0} - 成功，其他表示失败。
	 */
	protected int code = CODE_SUCCESS;

	/**
	 * 结果描述。
	 */
	protected String msg = "操作成功";

	/**
	 * 响应的数据。当且仅当 {@link #code} 为 {@code 0} 时有值。
	 */
	protected T data;


	public static <T> ResponseJson<T> successWith(T data, String msg) {
		ResponseJson<T> responseJson = new ResponseJson<>();
		responseJson.data = data;
		responseJson.msg = msg;
		return responseJson;
	}

	/**
	 * 初始化成功响应内容。
	 *
	 * @param data 响应数据
	 */
	public static <T> ResponseJson<T> successWithData(T data) {
		ResponseJson<T> responseJson = new ResponseJson<>();
		responseJson.data = data;
		return responseJson;
	}

	public static ResponseJson<Object> successWithMsg(String msg) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		responseJson.msg = msg;
		return responseJson;
	}

	/**
	 * 初始化错误响应内容。
	 *
	 * @param code 错误码
	 * @param msg 错误描述
	 */
	public static ResponseJson<Object> failureWith(int code, String msg) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		responseJson.code = code;
		responseJson.msg = msg;
		return responseJson;
	}

	public static ResponseJson<Object> failureWithMsg(String msg) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		responseJson.code = CODE_GENERIC_FAILURE;
		responseJson.msg = msg;
		return responseJson;
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
				"\"code\": " + code +
				", \"msg\": \"" + msg + '"' +
				", \"data\": " + data +
			'}';
	}
}
