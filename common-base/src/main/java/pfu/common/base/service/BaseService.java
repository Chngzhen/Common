package pfu.common.base.service;

import java.util.List;

/**
 * 业务逻辑层基础接口。
 *
 * @param <E> 实体类
 * @param <Q> 查询类
 * @author chngzhen@outlook.com
 * @since 2020-02-29
 */
public interface BaseService<E, Q> {

    /**
     * 保存数据
     *
     * @param entity 实体对象
     */
    void save(E entity);

    /**
     * 删除数据。
     *
     * @param ids 主键集合。
     */
    void deleteIn(List<Integer> ids);

    /**
     * 修改数据。注意：主键不能为空。
     *
     * @param entity 实体对象
     */
    void updateById(E entity);

    /**
     * 条件查询单条数据。
     *
     * @param query 查询条件
     * @return 若结果存在多个，则返回第一个。
     */
    E get(Q query);

    /**
     * <p>条件查询。</p>
     *
     * @param query 查询条件
     * @return 查询结果。若无数据，则返回空数组。
     */
    List<E> list(Q query);

    /**
     * <p>判断数据是否唯一。</p>
     *
     * @param query 实体对象
     * @return {@code true}表示唯一。默认返回{@code false}。
     */
    boolean isUnique(Q query);

}
