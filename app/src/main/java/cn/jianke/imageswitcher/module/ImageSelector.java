package cn.jianke.imageswitcher.module;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.module.activity.ImageSelectorActivity;
import cn.jianke.imageswitcher.utils.Utils;

/**
 * @className: ImageSelector
 * @classDescription: 图片选择
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class ImageSelector {
    // 图片请求码
    public static final int IMAGE_REQUEST_CODE = 1002;
    // 图片剪切码
    public static final int IMAGE_CROP_CODE = 1003;
    // 图片配置
    private static ImageConfig mImageConfig;

    /**
     * 获取图片配置
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param
     * @return
     */
    public static ImageConfig getImageConfig() {
        return mImageConfig;
    }

    /**
     * 开启图片选择器
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param activity
     * @param config 图片配置
     * @return
     */
    public static void open(Activity activity, ImageConfig config) {
        if (config == null) {
            return;
        }
        // 初始化图片配置
        mImageConfig = config;

        if (config.getInterfaceImageLoader() == null) {
            Toast.makeText(activity, R.string.open_camera_fail, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.existSDCard()) {
            Toast.makeText(activity, R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(activity, ImageSelectorActivity.class);
        activity.startActivityForResult(intent, mImageConfig.getRequestCode());
    }

    /**
     * 开启图片选择器
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param fragment
     * @param config 图片配置
     * @return
     */
    public static void open(Fragment fragment, ImageConfig config) {
        if (config == null) {
            return;
        }
        // 初始化图片配置
        mImageConfig = config;

        if (config.getInterfaceImageLoader() == null) {
            Toast.makeText(fragment.getActivity(), R.string.open_camera_fail, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.existSDCard()) {
            Toast.makeText(fragment.getActivity(), R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(fragment.getActivity(), ImageSelectorActivity.class);
        fragment.startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }
}
