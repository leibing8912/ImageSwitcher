package cn.jianke.imageswitcher.module;

import java.io.Serializable;
import java.util.ArrayList;
import cn.jianke.imageswitcher.utils.FileUtils;

/**
 * @className: ImageConfig
 * @classDescription: 图片配置
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class ImageConfig {
    // 是否多张图片选择
    private boolean mutiSelect;
    // 多张图片选择最大值
    private int maxSize;
    // 是否显示照相
    private boolean showCamera;
    // 是否可以剪切
    private boolean crop;
    private int aspectX;
    private int aspectY;
    private int outputX;
    private int outputY;
    // 图片加载
    private InterfaceImageLoader interfaceImageLoader;
    // 标题背景颜色
    private int titleBgColor;
    // 标题字体颜色
    private int titleTextColor;
    // 标题提交字体颜色
    private int titleSubmitTextColor;
    // steepToolBar颜色
    private int steepToolBarColor;
    // 文件路径
    private String filePath;
    // 图片路径集合
    private ArrayList<String> pathList;
    // 请求码
    private int requestCode;

    /**
     * Constructor
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param builder 构建对象
     * @return
     */
    private ImageConfig(final Builder builder) {
        this.maxSize = builder.maxSize;
        this.showCamera = builder.showCamera;
        this.interfaceImageLoader = builder.interfaceImageLoader;
        this.mutiSelect = builder.mutiSelect;
        this.pathList = builder.pathList;
        this.filePath = builder.filePath;
        this.crop = builder.crop;
        this.aspectX = builder.aspectX;
        this.aspectY = builder.aspectY;
        this.outputX = builder.outputX;
        this.outputY = builder.outputY;
        this.requestCode = builder.requestCode;
        this.titleBgColor = builder.titleBgColor;
        this.titleTextColor = builder.titleTextColor;
        this.titleSubmitTextColor = builder.titleSubmitTextColor;
        this.steepToolBarColor = builder.steepToolBarColor;
        // 创建文件
        FileUtils.createFile(this.filePath);
    }

    /**
     * @className: Builder
     * @classDescription: 构建配置类
     * @author: leibing
     * @createTime: 2016/09/06
     */
    public static class Builder implements Serializable {
        // 默认为多张图片选择
        private boolean mutiSelect = true;
        // 多张图片选择最大值默认为9
        private int maxSize = 9;
        //  默认不能照相
        private boolean showCamera = false;
        // 默认不能剪切
        private boolean crop = false;
        private int aspectX = 1;
        private int aspectY = 1;
        private int outputX = 500;
        private int outputY = 500;
        // 默认请求码为图片请求
        private int requestCode = ImageSelector.IMAGE_REQUEST_CODE;
        // 图片加载
        private InterfaceImageLoader interfaceImageLoader;
        // 默认文件路径
        private String filePath = "/temp/pictures";
        // 默认标题背景颜色为0XFF000000
        private int titleBgColor = 0XFF000000;
        // 默认标题字体颜色为0XFFFFFFFF
        private int titleTextColor = 0XFFFFFFFF;
        // 默认标题提交字体颜色为0XFFFFFFFF
        private int titleSubmitTextColor = 0XFFFFFFFF;
        // 默认steepToolBar颜色为0XFF000000
        private int steepToolBarColor = 0XFF000000;
        // 图片路径集合
        private ArrayList<String> pathList = new ArrayList<String>();

        /**
         * Constructor
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param interfaceImageLoader 图片加载
         * @return
         */
        public Builder(InterfaceImageLoader interfaceImageLoader) {
            this.interfaceImageLoader = interfaceImageLoader;
        }

        /**
         * 多张图片构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param
         * @return
         */
        public Builder mutiSelect() {
            this.mutiSelect = true;
            return this;
        }

        /**
         * 剪切构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param
         * @return
         */
        public Builder crop() {
            this.crop = true;
            return this;
        }

        /**
         * 剪切带参数构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param aspectX
         * @param aspectY
         * @param outputX
         * @param outputY
         * @return
         */
        public Builder crop(int aspectX, int aspectY, int outputX, int outputY) {
            this.crop = true;
            this.aspectX = aspectX;
            this.aspectY = aspectY;
            this.outputX = outputX;
            this.outputY = outputY;
            return this;
        }

        /**
         * 请求码构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param requestCode 请求码
         * @return
         */
        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * 文件构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param filePath 文件路径
         * @return
         */
        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        /**
         * 图片集合列表构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param pathList 图片路径集合
         * @return
         */
        public Builder pathList(ArrayList<String> pathList) {
            this.pathList = pathList;
            return this;
        }

        /**
         * 标题背景颜色构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param titleBgColor 标题背景颜色
         * @return
         */
        public Builder titleBgColor(int titleBgColor) {
            this.titleBgColor = titleBgColor;
            return this;
        }

        /**
         * 标题字体颜色构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param titleTextColor 标题字体颜色
         * @return
         */
        public Builder titleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        /**
         * 标题提交字体颜色构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param titleSubmitTextColor 标题提交字体颜色
         * @return
         */
        public Builder titleSubmitTextColor(int titleSubmitTextColor) {
            this.titleSubmitTextColor = titleSubmitTextColor;
            return this;
        }

        /**
         * steepToolBar颜色构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param steepToolBarColor  steepToolBar颜色
         * @return
         */
        public Builder steepToolBarColor(int steepToolBarColor) {
            this.steepToolBarColor = steepToolBarColor;
            return this;
        }

        /**
         * 图片单选构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param
         * @return
         */
        public Builder singleSelect() {
            this.mutiSelect = false;
            return this;
        }

        /**
         * 图片多选最大值构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param maxSize 图片多选最大值
         * @return
         */
        public Builder mutiSelectMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        /**
         * 照相构建
         * @author leibing
         * @createTime 2016/09/06
         * @lastModify 2016/09/06
         * @param
         * @return
         */
        public Builder showCamera() {
            this.showCamera = true;
            return this;
        }

        public ImageConfig build() {
            return new ImageConfig(this);
        }
    }

    public boolean isCrop() {
        return crop;
    }

    public int getAspectX() {
        return aspectX;
    }

    public int getAspectY() {
        return aspectY;
    }

    public int getOutputX() {
        return outputX;
    }

    public int getOutputY() {
        return outputY;
    }

    public boolean isMutiSelect() {
        return mutiSelect;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    public InterfaceImageLoader getInterfaceImageLoader() {
        return interfaceImageLoader;
    }

    public int getTitleBgColor() {
        return titleBgColor;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public int getTitleSubmitTextColor() {
        return titleSubmitTextColor;
    }

    public int getSteepToolBarColor() {
        return steepToolBarColor;
    }

    public ArrayList<String> getPathList() {
        return pathList;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
