package cn.jianke.imageswitcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.bean.Image;
import cn.jianke.imageswitcher.module.ImageConfig;

/**
 * @className: ImageAdapter
 * @classDescription: 图片适配器
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class ImageAdapter extends BaseAdapter {
    // TAG
    private final static String TAG = "ImageAdapter";
    // 上下文
    private Context context;
    // 布局
    private LayoutInflater mLayoutInflater;
    // 图片列表
    private List<Image> imageList;
    // 照相类型
    private static final int TYPE_CAMERA = 0;
    // 正常类型
    private static final int TYPE_NORMAL = 1;
    // 是否显示照相
    private boolean showCamera = true;
    // 是否显示选择指示器
    private boolean showSelectIndicator = true;
    // 选择的图片列表
    private List<Image> selectedImageList = new ArrayList<>();
    // item大小
    private int mItemSize;
    // GridView 布局
    private GridView.LayoutParams mItemLayoutParams;
    // 图片配置
    private ImageConfig imageConfig;

    /**
     * Constructor
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param context 上下文
     * @param imageList 图片列表
     * @param imageConfig 图片配置
     * @return
     */
    public ImageAdapter(Context context, List<Image> imageList, ImageConfig imageConfig) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.imageList = imageList;
        this.imageConfig = imageConfig;
        mItemLayoutParams = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置选择列表
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param resultList 结果列表
     * @return
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for (String filePath : resultList) {
            Image image = getImageByPath(filePath);
            if (image != null) {
                selectedImageList.add(image);
            }
        }
        if (selectedImageList.size() > 0) {
            notifyDataSetChanged();
        }
    }

    /**
     * 通过路径寻找图片
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param filePath 文件路径
     * @return
     */
    private Image getImageByPath(String filePath) {
        if (imageList != null && imageList.size() > 0) {
            for (Image image : imageList) {
                if (image.path.equalsIgnoreCase(filePath)) {
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置Item大小
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param columnWidth 列宽度
     * @return
     */
    public void setItemSize(int columnWidth) {
        if (mItemSize == columnWidth) {
            return;
        }
        mItemSize = columnWidth;
        mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return showCamera ? imageList.size() + 1 : imageList.size();
    }

    @Override
    public Image getItem(int position) {
        if (showCamera) {
            if (position == 0) {
                return null;
            }
            return imageList.get(position - 1);
        } else {
            return imageList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);

        if (type == TYPE_CAMERA) {
            convertView = mLayoutInflater.inflate(R.layout.imageselector_item_camera, parent, false);
            convertView.setTag(null);
        } else if (type == TYPE_NORMAL) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.imageselector_item_image, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
                if (holder == null) {
                    convertView = mLayoutInflater.inflate(R.layout.imageselector_item_image, parent, false);
                    holder = new ViewHolder(convertView);
                }
            }

            if (showSelectIndicator) {
                holder.mPhotoCheckIv.setVisibility(View.VISIBLE);
                if (selectedImageList.contains(getItem(position))) {
                    holder.mPhotoCheckIv.setImageResource(R.mipmap.imageselector_select_checked);
                    holder.mPhotoMaskVw.setVisibility(View.VISIBLE);
                } else {
                    holder.mPhotoCheckIv.setImageResource(R.mipmap.imageselector_select_uncheck);
                    holder.mPhotoMaskVw.setVisibility(View.GONE);
                }
            } else {
                holder.mPhotoCheckIv.setVisibility(View.GONE);
            }

            if (mItemSize > 0) {
                imageConfig.getInterfaceImageLoader().displayImage(context, getItem(position).path,
                        holder.mPhotoImageIv);
            }
        }

        GridView.LayoutParams layoutParams = (GridView.LayoutParams) convertView.getLayoutParams();
        if (layoutParams.height != mItemSize) {
            convertView.setLayoutParams(mItemLayoutParams);
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView mPhotoImageIv;
        private View mPhotoMaskVw;
        private ImageView mPhotoCheckIv;

        ViewHolder(View itemView) {
            mPhotoImageIv = (ImageView) itemView.findViewById(R.id.iv_photo_image);
            mPhotoMaskVw = itemView.findViewById(R.id.vw_photo_mask);
            mPhotoCheckIv = (ImageView) itemView.findViewById(R.id.iv_photo_check);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera && position == 0) {
            return TYPE_CAMERA;
        }
        return TYPE_NORMAL;
    }

    /**
     * 设置显示选择指示器
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param showSelectIndicator 显示选择指示器
     * @return
     */
    public void setShowSelectIndicator(boolean showSelectIndicator) {
        this.showSelectIndicator = showSelectIndicator;
    }

    /**
     * 是否显示照相
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param showCamera 显示照相
     * @return
     */
    public void setShowCamera(boolean showCamera) {
        if (this.showCamera == showCamera)
            return;
        this.showCamera = showCamera;
        notifyDataSetChanged();
    }

    /**
     * 选择图片
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param image
     * @return
     */
    public void select(Image image) {
        if (selectedImageList.contains(image)) {
            selectedImageList.remove(image);
        } else {
            selectedImageList.add(image);
        }
        notifyDataSetChanged();
    }

    /**
     * 是否显示照相
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param
     * @return
     */
    public boolean isShowCamera() {
        return showCamera;
    }
}