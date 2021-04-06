package pfu.common.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() {}

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static String formatToString(Date date, String fmt) {
        if (null == date) return null;
        try {
            return new SimpleDateFormat(fmt).format(date);
        }catch (Exception e) {
            return null;
        }
    }

    public static Date formatToDate(String strDate, String fmt) {
        if (null == strDate || "".equals(strDate)) return null;
        try {
            return new SimpleDateFormat(fmt).parse(strDate);
        }catch (Exception e) {
            return null;
        }
    }


}
