package pfu.common.exception;

/**
 * 数据不存在异常。
 *
 * @author chngzhen@outlook.com
 * @since 2020-02-29
 */
public class DataNotFoundException extends CurdException {

    public DataNotFoundException() {
        this("数据不存在");
    }

    public DataNotFoundException(String msg) {
        super(RESULT_CODE_DATA_NOT_FOUND, msg);
    }

}
