package pfu.common.base.entity;

/**
 * 分页查询的请求参数类。
 *
 * @author chngzhen@outlook.com
 * @since 2020-03-14
 */
public class Pagination extends AbstractQuery {

    /**
     * 起始页。基于1，默认：1。
     */
    private Integer pageNo = 1;

    public void setPageNo(Integer pageNo) {
        this.pageNo = (null == pageNo || 0 >= pageNo) ? 1 : pageNo;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * 分页大小。默认：10。
     */
    private Integer pageSize = 10;

    public void setPageSize(Integer pageSize) {
        this.pageSize = (null == pageSize || 0 >= pageSize) ? 10 : pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

}
