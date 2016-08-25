package cn.jianke.imageswitcher.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * @className: CustomGlideModule
 * @classDescription: 自定义Glide配置
 * @author: leibing
 * @createTime: 2016/8/15
 */
public class CustomGlideModule implements GlideModule{

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 设置格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        // 缓存到data目录下最大50M
        // 缓存目录为程序内部缓存目录
        // /data/data/your_package_name/image_manager_disk_cache/
        // (不能被其它应用访问)且缓存最大为250MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                DiskCache.Factory.DEFAULT_DISK_CACHE_DIR,
                DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));
        // 缓存到外部磁盘SD卡上,字节
        // builder.setDiskCache(new ExternalCacheDiskCacheFactory(
        // context,DiskCache.Factory.DEFAULT_DISK_CACHE_DIR,
        // DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));
        // 设置内存缓存大小
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
