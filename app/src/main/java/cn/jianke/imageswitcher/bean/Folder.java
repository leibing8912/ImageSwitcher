package cn.jianke.imageswitcher.bean;

import java.util.List;

/**
 * @className: Folder
 * @classDescription: 文件类
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class Folder {
    // 文件夹名称
    public String name;
    // 文件夹路径
    public String path;
    // 图片
    public Image cover;
    // 图片集合
    public List<Image> images;

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}