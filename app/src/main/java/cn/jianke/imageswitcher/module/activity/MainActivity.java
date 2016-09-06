package cn.jianke.imageswitcher.module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.module.ImageConfig;
import cn.jianke.imageswitcher.module.ImageSelector;
import cn.jianke.imageswitcher.module.SeletorImageLoader;
import cn.jianke.imageswitcher.widget.PictureShowDialog;

/**
 * @className: MainActivity
 * @classDescription: 图片选择器首页
 * @author: leibing
 * @createTime: 2016/08/25
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    // 请求码
    public static final int REQUEST_CODE = 1000;
    // 图片浏览器对话框
    private PictureShowDialog mPictureShowDialog;
    // 图片url列表
    private List<String> imageUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // onClick
        findViewById(R.id.btn_open_window).setOnClickListener(this);
        findViewById(R.id.btn_turn_to_others).setOnClickListener(this);
        findViewById(R.id.btn_image_selector).setOnClickListener(this);
        // 初始化图片url列表
        imageUrlList = new ArrayList<>();
        // 模拟数据
        imageUrlList.add("http://g.hiphotos.baidu.com/imgad/pic/item/f603918fa0ec08fa9f0b7dd85eee3d6d55fbda42.jpg");
        imageUrlList.add("http://g.hiphotos.baidu.com/imgad/pic/item/4a36acaf2edda3cc6a22d65f06e93901203f928e.jpg");
        imageUrlList.add("http://g.hiphotos.baidu.com/imgad/pic/item/023b5bb5c9ea15cec0e68e76b1003af33a87b241.jpg");
        imageUrlList.add("http://f.hiphotos.baidu.com/imgad/pic/item/5366d0160924ab18ead18f4832fae6cd7a890b8d.jpg");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_open_window:
                // 打开图片浏览器
                if (imageUrlList == null || imageUrlList.size() == 0){
                    Toast.makeText(MainActivity.this,
                            "图片url列表为空，请检查！",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 初始化Dialog
                mPictureShowDialog = new PictureShowDialog(MainActivity.this);
                // 添加数据源
                mPictureShowDialog.loadRemoteImage(imageUrlList, MainActivity.this);
                // 设定自动轮播
                mPictureShowDialog.startAutoRotation(PictureShowDialog.AUTO_ROTATION_TIME);
                // 显示对话框
                mPictureShowDialog.show();
                break;
            case R.id.btn_turn_to_others:
                // 跳转广告轮播页面
                Intent intent = new Intent();
                intent.setClass(this, AdsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_image_selector:
                // 图片选择器
                ImageConfig imageConfig
                        = new ImageConfig.Builder(new SeletorImageLoader())
                        // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                        .steepToolBarColor(getResources().getColor(R.color.blue))
                        // 标题的背景颜色 （默认黑色）
                        .titleBgColor(getResources().getColor(R.color.blue))
                        // 提交按钮字体的颜色  （默认白色）
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        // 标题颜色 （默认白色）
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）  (单选 为 singleSelect)
//                        .singleSelect()
//                        .crop()
                        // 多选时的最大数量   （默认 9 张）
                        .mutiSelectMaxSize(9)
                        // 已选择的图片路径
//                        .pathList(path)
                        // 拍照后存放的图片路径（默认 /temp/picture）
                        .filePath("/ImageSelector/Pictures")
                        // 开启拍照功能 （默认开启）
                        .showCamera()
                        .requestCode(REQUEST_CODE)
                        .build();
                ImageSelector.open(MainActivity.this, imageConfig);   // 开启图片选择器
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            for (String path : pathList) {
                Log.v(TAG, path);
            }
        }
    }
}
