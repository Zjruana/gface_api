package cn.gface.face.api.utils;

public class MyFilter {
    /**
     * 去除时间的.0
     *
     * @param time
     * @return
     */
    public static String filterTime(String time) {
        boolean contains = time.contains(".0");
        if (contains) {
            time = time.replace(".0", "");
        }
        return time;
    }
}
