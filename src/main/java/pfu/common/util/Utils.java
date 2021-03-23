package pfu.common.util;

import pfu.common.exception.LocalException;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共工具类。
 *
 * @author chngzhen@outlook.com
 * @since 2020-03-15
 */
public class Utils {

    /**
     * 将源对象数组的属性值拷贝到目标类型的对象数组中。
     *
     * @param sources 源对象数组
     * @param targetClass 目标对象数组的元素类型
     * @param <T> 目标对象数组的元素类型
     * @return {@link T} 类型的对象数组
     */
    public static <T> List<T> convert(List<?> sources, Class<T> targetClass) {
        if (null != sources && 0 < sources.size()) {
            Class<?> sourceClass = null;
            BeanCopier copier = null;

            List<T> results = new ArrayList<>(sources.size());
            for (Object source : sources) {
                if (null == sourceClass) {
                    sourceClass = source.getClass();
                    copier = BeanCopier.create(source.getClass(), targetClass, false);
                }
                results.add(Utils.copyProperties(copier, source, targetClass));
            }
            return results;
        }
        return new ArrayList<>();
    }

    /**
     * 将源对象的属性值拷贝到目标对象中。
     *
     * @param copier 拷贝器
     * @param source 源对象
     * @param targetClass 目标对象数组的元素类型
     */
    public static <T> T copyProperties(BeanCopier copier, Object source, Class<T> targetClass) throws LocalException {
        try {
            T entity = targetClass.newInstance();
            copier.copy(source, entity, null);
            return entity;
        }catch (Exception e) {
            throw new LocalException("对象拷贝异常");
        }
    }

    /**
     * 将源对象的属性值拷贝到目标对象中。
     *
     * @param source 源对象
     * @param targetClass 目标对象数组的元素类型
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) throws LocalException {
        BeanCopier copier = BeanCopier.create(source.getClass(), targetClass, false);
        return Utils.copyProperties(copier, source, targetClass);
    }

    public static void copyProperties(Object source, Object target) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }

}
