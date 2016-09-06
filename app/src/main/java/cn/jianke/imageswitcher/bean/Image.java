package cn.jianke.imageswitcher.bean;

/**
 * @className: Image
 * @classDescription: 图片类
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class Image {
    // 图片路径
    public String path;
    // 图片名称
    public String name;
    // 时间
    public long time;

    /**
     * Constructor
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param path 图片路径
     * @param name 图片名称
     * @param time 时间
     * @return
     */
    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}