package pfu.common.base.entity;

import java.util.List;

/**
 * Http表格响应内容包装类。
 *
 * @author chngzhen@outlook.com
 * @since 2019-08-15
 */
public class DataTable<T> extends ResponseJson<List<T>> {

	/**
	 * 过滤后的记录总数。默认：0。
	 */
	private long total;

	/**
	 * 分页数量。
	 */
	private long pages;

	/**
	 * 初始化成功响应内容。
	 *
	 * @param total 条件过滤后的数据总数
	 * @param data 查询数据
	 */
	public static <T> DataTable<T> success(long total, int pages, List<T> data) {
		DataTable<T> dataTable = new DataTable<>();
		dataTable.data = data;
		dataTable.total = total;
		dataTable.pages = pages;
		return dataTable;
	}

	public long getTotal() {
		return total;
	}

	public long getPages() {
		return pages;
	}
}
