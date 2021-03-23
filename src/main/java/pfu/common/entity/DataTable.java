package pfu.common.entity;

import java.util.List;

/**
 * Http表格响应内容包装类。
 *
 * @author chngzhen@outlook.com
 * @since 2019-08-15
 */
public class DataTable extends ResponseJson<List<Object>> {

	/**
	 * 过滤后的记录总数。默认：0。
	 */
	private long count;

	/**
	 * 初始化成功响应内容。
	 *
	 * @param count 条件过滤后的数据总数
	 * @param data 查询数据
	 */
	public void succ(long count, List<Object> data) {
		super.successWithData(data);
		this.count = count;
	}

	public long getCount() {
		return count;
	}
}
