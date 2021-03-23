package pfu.common.dao;

import java.util.List;

/**
 * 基础 DAO。
 * <p></p>
 * 基于单表或比较简单的操作。若涉及到多表查询，请自定义新的接口。
 *
 * @param <E> 实体类
 * @param <Q> 查询类
 *
 * @author chngzhen@outlook.com
 * @since 2019-12-19
 */
public interface BaseDao<E, Q> {

    /**
     * 插入数据。
     *
     * @param entity 实体对象
     * @return {@code 1} 表示成功，{@code 0} 表示失败
     */
    long insert(E entity);

    /**
     * 条件删除数据。
     *
     * @param query 删除条件
     * @return 删除的数据条数
     */
    long delete(Q query);

    /**
     * 根据ID批量删除数据。
     *
     * @param ids ID集合
     * @return {@code 0} 表示失败
     */
    int deleteIn(List<Integer> ids);

    /**
     * 条件查询数据。
     *
     * @param query 查询条件
     * @return 查询结果
     */
    List<E> select(Q query);

    /**
     * 条件查询数据。
     *
     * @param query 查询条件
     * @return 查询结果
     */
    E selectOne(Q query);

    /**
     * 条件统计。
     *
     * @param query 统计条件
     * @return 统计结果
     */
    long count(Q query);

    /**
     * 更新数据。
     *
     * @param entity 实体对象
     * @return {@code 1} 表示成功，{@code 0} 表示失败
     */
    long update(E entity);

    /**
     * 修改数据。注意：该接口不会忽略空值和空字符串。
     *
     * @param entity 实体对象
     * @see #update(Object)
     */
    long updateWithoutIgnoreNull(E entity);

}
