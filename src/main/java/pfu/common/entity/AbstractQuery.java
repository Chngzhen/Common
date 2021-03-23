package pfu.common.entity;

import java.io.Serializable;

/**
 * 抽象实体类。
 * <p></p>
 * 该类主要用于扩展公共映射字段。
 *
 * @author chngzhen@outlook.com
 * @since 2020-02-29
 */
public abstract class AbstractQuery {

    /**
     * 模糊搜索
     */
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * 排斥主键。查询时，排除该主键标识的数据。
     */
    private Serializable pk;

    public Serializable getPk() {
        return pk;
    }

    public void setPk(Serializable pk) {
        this.pk = pk;
    }


    /**
     * 过滤条件。
     * <p></p>
     * 为避免前端注入，该属性不提供Setter。
     */
    private StringBuilder filters;

    public void addFilter(String filter) {
        if (null == filter || "".equals(filter.trim())) return;
        if (null == this.filters) {
            this.filters = new StringBuilder(20);
        }
        this.filters.append(" AND ").append(filter);
    }

    public String getFilters() {
        return null == this.filters ? null : this.filters.toString();
    }

    /**
     * 排序串。注意，无需order by前缀。
     * <p></p>
     * 为避免前端注入，该属性不提供Setter。
     */
    private StringBuilder orderBy;

    public void addOrder(String order) {
        if (null == order || "".equals(order.trim())) return;
        if (null == this.orderBy) {
            this.orderBy = new StringBuilder(20);
        }
        this.orderBy.append(",").append(order);
    }

    public String getOrderBy() {
        if (null == this.orderBy) {
            String defOrderBy = defOrderBy();
            return null == defOrderBy || 0 == defOrderBy.length() ? "" : (" ORDER BY " + defOrderBy);
        }
        return " ORDER BY " + this.orderBy.substring(1);
    }

    /**
     * 默认排序方式。
     */
    protected String defOrderBy() {
        return null;
    }

}
