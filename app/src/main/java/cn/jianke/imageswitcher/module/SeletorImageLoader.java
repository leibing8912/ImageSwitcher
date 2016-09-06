package cn.jianke.imageswitcher.module;

import android.content.Context;
import android.widget.ImageView;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.utils.ImageLoader;

/**
 * @className: SeletorImageLoader
 * @classDescription: 图片选择器图片加载
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class SeletorImageLoader implements InterfaceImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        ImageLoader.getInstance().load(context,imageView,path,
                context.getResources().getDrawable(R.mipmap.imageselector_photo));
    }
}
