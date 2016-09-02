package cn.jianke.imageswitcher.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.imageswitcher.R;

/**
 * @className: MainActivity
 * @classDescription: 图片选择器首页
 * @author: leibing
 * @createTime: 2016/08/25
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
            default:
                break;
        }
    }
}
