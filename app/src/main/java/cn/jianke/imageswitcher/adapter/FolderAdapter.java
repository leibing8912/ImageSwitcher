package cn.jianke.imageswitcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.bean.Folder;
import cn.jianke.imageswitcher.module.ImageConfig;

/**
 * @className: FolderAdapter
 * @classDescription:文件夹适配器
 * @author: leibing
 * @createTime: 2016/09/06
 */
public class FolderAdapter extends BaseAdapter {
    // TAG
    private final static String TAG = "FolderAdapter";
    // 上下文
    private Context context;
    // 布局
    private LayoutInflater mLayoutInflater;
    // 文件夹列表
    private List<Folder> folderList = new ArrayList<>();
    // 最后选中
    private int lastSelected = 0;
    // 图片配置
    private ImageConfig imageConfig;

    /**
     * Constructor
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param context 上下文
     * @param imageConfig 图片配置
     * @return
     */
    public FolderAdapter(Context context, ImageConfig imageConfig) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.imageConfig = imageConfig;
    }

    /**
     * 设置数据源
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param folders 文件夹列表
     * @return
     */
    public void setData(List<Folder> folders) {
        if (folders != null && folders.size() > 0) {
            folderList.addAll(folders);
        } else {
            folderList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return folderList.size() + 1;
    }

    @Override
    public Folder getItem(int position) {
        if (position == 0)
            return null;
        return folderList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.imageselector_item_folder, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder != null) {
            if (position == 0) {
                holder.mFolderNameText.setText(R.string.all_folder);
                holder.mImageNumTextTv.setText("" + getTotalImageSize() + (context.getResources().getText(R.string.sheet)));

                if (folderList.size() > 0) {
                    Folder folder = folderList.get(0);

                    imageConfig.getInterfaceImageLoader().displayImage(context,
                            folder.cover.path, holder.mFolderImageIv);

                }
            } else {

                Folder folder = getItem(position);
                holder.mFolderNameText.setText(folder.name);
                holder.mImageNumTextTv.setText("" + folder.images.size() + (context.getResources().getText(R.string.sheet)));

                imageConfig.getInterfaceImageLoader().displayImage(context, folder.cover.path, holder.mFolderImageIv);

            }

            if (lastSelected == position) {
                holder.mIndicatorIv.setVisibility(View.VISIBLE);
            } else {
                holder.mIndicatorIv.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    class ViewHolder {
        ImageView mFolderImageIv;
        TextView mFolderNameText;
        TextView mImageNumTextTv;
        ImageView mIndicatorIv;

        ViewHolder(View itemView) {
            mFolderImageIv = (ImageView) itemView.findViewById(R.id.iv_folder_image);
            mFolderNameText = (TextView) itemView.findViewById(R.id.tv_folder_name_text);
            mImageNumTextTv = (TextView) itemView.findViewById(R.id.tv_image_num_text);
            mIndicatorIv = (ImageView) itemView.findViewById(R.id.iv_indicator);
            itemView.setTag(this);
        }
    }

    /**
     * 获取选择位置
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param
     * @return
     */
    public int getSelectIndex() {
        return lastSelected;
    }

    /**
     * 获取总图片大小
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param
     * @return
     */
    private int getTotalImageSize() {
        int result = 0;
        if (folderList != null && folderList.size() > 0) {
            for (Folder folder : folderList) {
                result += folder.images.size();
            }
        }
        return result;
    }

    /**
     * 设置选择位置
     * @author leibing
     * @createTime 2016/09/06
     * @lastModify 2016/09/06
     * @param position 图片位置
     * @return
     */
    public void setSelectIndex(int position) {
        if (lastSelected == position)
            return;
        lastSelected = position;
        notifyDataSetChanged();
    }
}