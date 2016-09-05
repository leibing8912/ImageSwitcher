#ImageSwitcher
一个基于Glide封装图片加载库、基于ViewFlipper方式实现封装图片浏览器对话框、广告轮播器的框架.

### 功能

###### 图片加载

* 可加载本地和远程图片文件,本地需采用File访问,远程只需Url即可.可支持多种参数选择加载图片

###### 图片浏览器对话框、广告轮播器

* 实现自动轮播图片
* 长按可切换自动轮播与停止
* 可定制自动轮播时间
* 可定制指示器
* 支持本地与远程加载图片资源
* 支持缩略图选择

### Usage

本人已将该项目放入Maven jcenter仓库,简化了该项目的使用,大家如需集成,请往下看:


* 添加依赖代码,代码如下:

gradle:
```java
dependencies {
    compile 'cn.jianke.imageswitcher:app:1.0.1'
}
```
maven:
```java
<dependency>
  <groupId>cn.jianke.imageswitcher</groupId>
  <artifactId>app</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

lvy:
```java
<dependency org='cn.jianke.imageswitcher' name='app' rev='1.0.1'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```

* 调用代码如下:

图片加载库加载图片:

```java
ImageLoader.getInstance().load(xxx);
```
MainActivity.class:

```java
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
        setContentView(cn.jianke.imageswitcher.R.layout.activity_main);
        // onClick
        findViewById(R.id.btn_open_window).setOnClickListener(this);
        findViewById(R.id.turn_to_others).setOnClickListener(this);
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
            case R.id.turn_to_others:
                // 跳转广告轮播页面
                Intent intent = new Intent();
                intent.setClass(this, AdsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
```

AdsActivity.class:

```java
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

```
以上注释已经非常清晰,如有不明白之处请联系.


* 邮箱:leibing1989@126.com
* QQ:872721111


### License
Copyright 2016 leibing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.