package cn.jianke.imageswitcher.utils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @className: TimeUtils
 * @classDescription: 时间工具类
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class TimeUtils {
    // TAG
    private final static String TAG = "TimeUtils";
    // 时间格式
    private final static String PATTERN = "yyyy-MM-dd";

    /**
     * 时间转字符串格式
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param timeMillis 时间毫秒级别
     * @param pattern 时间格式
     * @return
     */
    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        return simpleDateFormat.format(new Date(timeMillis));
    }

    /**
     * 照片日期转字符串格式
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param time 时间毫秒级别
     * @return
     */
    public static String formatPhotoDate(long time) {
        return timeFormat(time, PATTERN);
    }

    /**
     * 照片日期转字符串格式
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param filePath 文件路径
     * @return
     */
    public static String formatPhotoDate(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }
}