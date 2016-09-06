package cn.jianke.imageswitcher.module.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.widget.CycleViewPager;

/**
 * @className: AdsActivity
 * @classDescription: 广告页面
 * @author: leibing
 * @createTime: 2016/09/05
 */
public class AdsActivity extends AppCompatActivity {
    // 广告轮播图
    private CycleViewPager adsCvp;
    // 图片url列表
    private List<String> imageUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        // findView
        adsCvp = (CycleViewPager) findViewById(R.id.cvp_ads);
        // 初始化图片url列表
        imageUrlList = new ArrayList<>();
        // 模拟数据
        imageUrlList.add("http://g.hiphotos.baidu.com/imgad/pic/item/f603918fa0ec08fa9f0b7dd85eee3d6d55fbda42.jpg");
        imageUrlList.add("http://g.hiphotos.baidu.com/imgad/pic/item/4a36acaf2edda3cc6a22d65f06e93901203f928e.jpg");
        imageUrlList.add("http://g.hiphotos.baidu.com/imgad/pic/item/023b5bb5c9ea15cec0e68e76b1003af33a87b241.jpg");
        imageUrlList.add("http://f.hiphotos.baidu.com/imgad/pic/item/5366d0160924ab18ead18f4832fae6cd7a890b8d.jpg");
        // onClick
        findViewById(R.id.btn_show_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 显示广告
                showAds(imageUrlList);
            }
        });
    }

    /**
     * 显示广告
     * @author leibing
     * @createTime 2016/09/05
     * @lastModify 2016/09/05
     * @param imageUrlList 数据源
     * @return
     */
    private void showAds(List<String> imageUrlList) {
        if (imageUrlList != null && imageUrlList.size() != 0){
            adsCvp.loadRemoteImage(imageUrlList, this);
            adsCvp.startAutoRotation(0);
        }
    }

}
