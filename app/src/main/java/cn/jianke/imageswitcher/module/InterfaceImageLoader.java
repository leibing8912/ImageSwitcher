package cn.jianke.imageswitcher.module;

import android.content.Context;
import android.widget.ImageView;
import java.io.Serializable;

/**
 * @interfaceName:ImageLoader
 * @interfaceDescription:图片加载接口
 * @author: leibing
 * @createTime: 2016/09/06
 */
public interface InterfaceImageLoader extends Serializable {
    void displayImage(Context context, String path, ImageView imageView);
}