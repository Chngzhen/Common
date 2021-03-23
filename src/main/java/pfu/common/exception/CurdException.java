package pfu.common.exception;

/**
 * 数据库操作异常。
 * <p></p>
 * 该异常常见于数据入库、更新操作。由于删除时允许数据库不存在目标数据，通常不用抛出该异常。
 *
 * @author chngzhen@outlook.com
 * @since 2020-02-29
 */
public class CurdException extends LocalException {

    public CurdException() {
        this("数据库操作异常");
    }

    public CurdException(String msg) {
        this(RESULT_CODE_DATABASE_CURD, msg);
    }

    public CurdException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
