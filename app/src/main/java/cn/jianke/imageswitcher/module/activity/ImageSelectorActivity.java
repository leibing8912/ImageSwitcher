package cn.jianke.imageswitcher.module.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.module.ImageConfig;
import cn.jianke.imageswitcher.module.ImageSelector;
import cn.jianke.imageswitcher.utils.Utils;

/**
 * @className: ImageSelectorActivity
 * @classDescription: 图片选择页面
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class ImageSelectorActivity extends FragmentActivity implements ImageSelectorFragment.Callback ,View.OnClickListener{
    // 选择返回结果
    public static final String EXTRA_RESULT = "select_result";
    // 图片路径集合
    private ArrayList<String> pathList = new ArrayList<>();
    // 图片配置
    private ImageConfig imageConfig;
    // 标题
    private TextView mTitleTextTv;
    // 提交
    private TextView mSubmitTv;
    // 图片选择标题Bar布局
    private RelativeLayout mImageSelectorTitleBarRly;
    // 剪切图片路径
    private String cropImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageselector_activity);
        // 初始化图片配置
        imageConfig = ImageSelector.getImageConfig();
        // 掩藏标题Bar
        Utils.hideTitleBar(this, R.id.imageselector_activity_layout, imageConfig.getSteepToolBarColor());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, ImageSelectorFragment.class.getName(), null))
                .commit();
        // findView
        mSubmitTv = (TextView) super.findViewById(R.id.tv_submit);
        mTitleTextTv = (TextView) super.findViewById(R.id.tv_title_text);
        mImageSelectorTitleBarRly = (RelativeLayout) super.findViewById(R.id.rly_imageselector_title_bar);
        // 初始化
        init();
        // Onclick
        findViewById(R.id.ly_back).setOnClickListener(this);
        findViewById(R.id.tv_submit).setOnClickListener(this);
    }

    /**
     * 初始化
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param
     * @return
     */
    private void init() {
        // view颜色
        mSubmitTv.setTextColor(imageConfig.getTitleSubmitTextColor());
        mTitleTextTv.setTextColor(imageConfig.getTitleTextColor());
        mImageSelectorTitleBarRly.setBackgroundColor(imageConfig.getTitleBgColor());
        // 初始化图片路径列表
        pathList = imageConfig.getPathList();

        if (pathList == null || pathList.size() <= 0) {
            mSubmitTv.setText(R.string.finish);
            mSubmitTv.setEnabled(false);
        } else {
            mSubmitTv.setText((getResources().getText(R.string.finish)) +
                    "(" + pathList.size() + "/" + imageConfig.getMaxSize() + ")");
            mSubmitTv.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.tv_submit:
                if (pathList != null && pathList.size() > 0) {
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, pathList);
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageSelector.IMAGE_CROP_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            pathList.add(cropImagePath);
            intent.putStringArrayListExtra(EXTRA_RESULT, pathList);
            setResult(RESULT_OK, intent);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 图片剪切
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param imagePath 图片路径
     * @return
     */
    private void crop(String imagePath, int aspectX, int aspectY, int outputX, int outputY) {
        File file;
        if (Utils.existSDCard()) {
            file = new File(Environment.getExternalStorageDirectory() +
                    imageConfig.getFilePath(), Utils.getImageName());
        } else {
            file = new File(getCacheDir(), Utils.getImageName());
        }
        // 图片剪切路径
        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, ImageSelector.IMAGE_CROP_CODE);
    }

    @Override
    public void onSingleImageSelected(String path) {
        if (imageConfig.isCrop()) {
            crop(path, imageConfig.getAspectX(), imageConfig.getAspectY(),
                    imageConfig.getOutputX(), imageConfig.getOutputY());
        } else {
            Intent data = new Intent();
            pathList.add(path);
            data.putStringArrayListExtra(EXTRA_RESULT, pathList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onImageSelected(String path) {
        if (!pathList.contains(path)) {
            pathList.add(path);
        }
        if (pathList.size() > 0) {
            mSubmitTv.setText((getResources().getText(R.string.finish)) +
                    "(" + pathList.size() + "/" + imageConfig.getMaxSize() + ")");
            if (!mSubmitTv.isEnabled()) {
                mSubmitTv.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if (pathList.contains(path)) {
            pathList.remove(path);
            mSubmitTv.setText((getResources().getText(R.string.finish)) +
                    "(" + pathList.size() + "/" + imageConfig.getMaxSize() + ")");
        } else {
            mSubmitTv.setText((getResources().getText(R.string.finish)) +
                    "(" + pathList.size() + "/" + imageConfig.getMaxSize() + ")");
        }
        if (pathList.size() == 0) {
            mSubmitTv.setText(R.string.finish);
            mSubmitTv.setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            Intent data = new Intent();
            pathList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, pathList);
            setResult(RESULT_OK, data);
            finish();
        }
        if (imageFile != null) {
            if (imageConfig.isCrop()) {
                crop(imageFile.getAbsolutePath(), imageConfig.getAspectX(), imageConfig.getAspectY(),
                        imageConfig.getOutputX(), imageConfig.getOutputY());
            } else {
                Intent data = new Intent();
                pathList.add(imageFile.getAbsolutePath());
                data.putStringArrayListExtra(EXTRA_RESULT, pathList);
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }
}