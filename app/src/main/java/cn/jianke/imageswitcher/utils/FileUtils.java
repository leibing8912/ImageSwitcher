package cn.jianke.imageswitcher.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @className: FileUtils
 * @classDescription: 文件工具类
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class FileUtils {
    // 时间格式
    private final static String PATTERN = "yyyyMMddHHmmss";

    /**
     * 创建临时文件
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param context 上下文
     * @param filePath 文件路径
     * @return
     */
    public static File createTmpFile(Context context, String filePath) {
        // sd卡路径
        String externalStorageState = Environment.getExternalStorageState();
        // 当前时间字符串
        String timeStamp = new SimpleDateFormat(PATTERN, Locale.CHINA).format(new Date());

        File dir = new File(Environment.getExternalStorageDirectory() + filePath);
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return new File(dir, timeStamp + ".jpg");
        } else {
            File cacheDir = context.getCacheDir();
            return new File(cacheDir, timeStamp + ".jpg");
        }
    }

    /**
     * 创建文件
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param filePath 文件路径
     * @return
     */
    public static void createFile(String filePath) {
        // sd卡路径
        String externalStorageState = Environment.getExternalStorageState();

        File dir = new File(Environment.getExternalStorageDirectory() + filePath);
        File cropFile = new File(Environment.getExternalStorageDirectory() + filePath + "/crop");

        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!cropFile.exists()) {
                cropFile.mkdirs();
            }
            File file = new File(dir, ".nomedia");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}