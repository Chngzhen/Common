package pfu.common.base.service.impl;

import pfu.common.base.dao.BaseDao;
import pfu.common.base.exception.LocalException;

import java.util.List;

/**
 * 业务逻辑层基础接口抽象类。
 * <p></p>
 * 基于Dao类的简单业务。若涉及到复杂的业务，请在新的接口实现。
 *
 * @param <E> 实体类
 * @param <Q> 查询类
 * @author chngzhen@outlook.com
 * @since 2020-02-29
 */
public abstract class BaseServiceImpl<E, Q> {

    protected BaseDao<E, Q> localDao;

    public void setLocalDao(BaseDao<E, Q> localDao) {
        this.localDao = localDao;
    }

    /**
     * <p>保存数据。</p>
     *
     * @param entity 实体对象
     */
    public void save(E entity) {
        long effectNum = localDao.insert(entity);
        if (0 == effectNum) {
            throw new LocalException("数据保存失败");
        }
    }

    /**
     * <p>删除数据。</p>
     *
     * @param ids 主键集合。
     */
    public void deleteIn(List<Integer> ids) {
        localDao.deleteIn(ids);
    }

    /**
     * <p>修改数据。注意：主键不能为空。</p>
     *
     * @param entity 实体对象
     */
    public void updateById(E entity) {
        long effectNum = localDao.update(entity);
        if (0 == effectNum) {
            throw new LocalException("数据更新失败");
        }
    }

    /**
     * 条件查询单条数据。
     *
     * @param query 查询条件
     * @return 若结果存在多个，则返回第一个。
     */
    public abstract E get(Q query);

    /**
     * <p>条件查询。</p>
     *
     * @param query 查询条件
     * @return 查询结果。若无数据，则返回空数组。
     */
    public List<E> list(Q query) {
        return localDao.select(query);
    }

    /**
     * <p>判断数据是否唯一。</p>
     *
     * @param query 实体对象
     * @return {@code true}表示唯一。默认返回{@code false}。
     */
    public boolean isUnique(Q query) {
        return 0 == localDao.count(query);
    }

}
