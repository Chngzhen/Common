package pfu.common.base.util;

import pfu.common.base.entity.OperationSystem;

public class OSUtils {

    private static String os = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return os.contains(OperationSystem.WINDOWS.toString().toLowerCase());
    }

}
