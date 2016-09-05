package cn.jianke.imageswitcher.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import java.io.File;
import java.util.List;
import cn.jianke.imageswitcher.R;
import cn.jianke.imageswitcher.utils.ImageLoader;

/**
 * @className: CycleViewPager
 * @classDescription: 自定义轮播图
 * @author: leibing
 * @createTime: 2016/09/05
 */
public class CycleViewPager extends FrameLayout implements GestureDetector.OnGestureListener{
    // 自动轮播默认时间
    public final static int AUTO_ROTATION_TIME = 6000;
    // 图片轮播器
    private ViewFlipper mCycleViewVf;
    // 图片指示器
    private LinearLayout mIndicatorsLy;
    // 指示器小圆点
    private ImageView[] mIndicators;
    // 手势检测
    private GestureDetector detector;
    // 指示器图片，被选择状态
    private int mIndicatorSelected = R.mipmap.btn_appraise_selected;
    // 指示器图片，未被选择状态
    private int mIndicatorUnselected = R.mipmap.btn_appraise_normal;
    // 指示器当前位置
    private int currentPosition = 0;
    // 图片资源数目
    private int pictureSize = 0;
    // 进出动画
    private Animation leftInAnimation;
    private Animation leftOutAnimation;
    private Animation rightInAnimation;
    private Animation rightOutAnimation;
    // Handler 用于自动轮播
    private Handler mHandler;
    // 自动轮播间隔时间
    private int autoRotationTime;
    // 是否停止自动轮播
    private boolean isStopAuto = false;
    // 是否自动轮播
    private boolean isAuto = false;
    // 执行自动轮播
    private Runnable mAutoRotationRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isStopAuto) {
                onFlingRight();
                mHandler.postDelayed(mAutoRotationRunnable, autoRotationTime);
            }
        }
    };

    public CycleViewPager(Context context) {
        super(context);
        // 初始化View
        initView(context);
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化View
        initView(context);
    }

    public CycleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化View
        initView(context);
    }

    /**
     * 初始化View
     * @author leibing
     * @createTime 2016/09/05
     * @lastModify 2016/09/05
     * @param context 上下文
     * @return
     */
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cycle_view, null);
        // FindView
        mCycleViewVf = (ViewFlipper) view.findViewById(R.id.vf_cycle_view);
        mIndicatorsLy = (LinearLayout) view.findViewById(R.id.ly_indicators);
        // 初始化手势
        detector = new GestureDetector(this);
        // 动画效果
        leftInAnimation = AnimationUtils.loadAnimation(context, R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(context, R.anim.left_out);
        rightInAnimation = AnimationUtils.loadAnimation(context, R.anim.right_in);
        rightOutAnimation = AnimationUtils.loadAnimation(context, R.anim.right_out);
        this.addView(view);
    }

    /**
     * 加载远程图片资源
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param remoteUrlList 远程图片url列表
     * @param context 上下文
     * @return
     */
    public void loadRemoteImage(List<String> remoteUrlList, Context context){
        if (mCycleViewVf == null)
            return;
        // 图片资源数目
        pictureSize = remoteUrlList.size();
        // 初始化指示器
        initIndicators(pictureSize,context);
        // 设置指示器
        if (pictureSize >=1)
            setIndicator(0);
        // 动态导入的方式为ViewFlipper加入子View
        for (int i=0;i<pictureSize;i++){
            ImageView imageView = new ImageView(context);
            // 加载图片
            ImageLoader.getInstance().load(context, imageView, remoteUrlList.get(i));
            mCycleViewVf.addView(imageView);
        }
    }

    /**
     * 加载远程图片资源
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param localUrlList 本地图片url列表
     * @param context 上下文
     * @return
     */
    public void loadLocalImage(List<File> localUrlList, Context context){
        if (mCycleViewVf == null)
            return;
        // 图片资源数目
        pictureSize = localUrlList.size();
        // 初始化指示器
        initIndicators(pictureSize,context);
        // 设置指示器
        if (pictureSize >=1)
            setIndicator(0);
        // 动态导入的方式为ViewFlipper加入子View
        for (int i=0;i<pictureSize;i++){
            ImageView imageView = new ImageView(context);
            // 加载图片
            ImageLoader.getInstance().load(context, imageView, localUrlList.get(i));
            mCycleViewVf.addView(imageView);
        }

    }

    /**
     * 初始化指示器
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param pictureSize  图片资源大小
     * @param context 上下文
     * @return
     */
    private void initIndicators(int pictureSize, Context context) {
        mIndicators = new ImageView[pictureSize];
        mIndicatorsLy.removeAllViews();
        for (int i = 0; i < mIndicators.length; i++) {
            mIndicators[i] = new ImageView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            mIndicators[i].setLayoutParams(lp);
            mIndicatorsLy.addView(mIndicators[i]);
        }
    }

    /**
     * 设置指示器
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param selectedPosition 默认指示器位置
     * @return
     */
    private void setIndicator(int selectedPosition) {
        if (mIndicators == null || mIndicators.length == 0)
            return;
        try {
            for (int i = 0; i < mIndicators.length; i++) {
                mIndicators[i]
                        .setBackgroundResource(mIndicatorUnselected);
            }
            if (mIndicators.length > selectedPosition)
                mIndicators[selectedPosition]
                        .setBackgroundResource(mIndicatorSelected);
        } catch (Exception e) {
        }
    }

    /**
     * 设置指示器图片被选中状态
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param mIndicatorSelected 指示器图片，被选择状态
     * @return
     */
    public void setIndicatorSelected(int mIndicatorSelected){
        this.mIndicatorSelected = mIndicatorSelected;
    }

    /**
     * 设置指示器图片未被选中状态
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param mIndicatorUnselected 指示器图片，未被选择状态
     * @return
     */
    public void setIndicatorUnselected(int mIndicatorUnselected){
        this.mIndicatorUnselected = mIndicatorUnselected;
    }

    /**
     * 启动自动轮播
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param time 自动轮播的间隔时间 单位为毫秒
     * @return
     */
    public void startAutoRotation(int time){
        isStopAuto = false;
        isAuto = true;
        if (time == 0)
            autoRotationTime = AUTO_ROTATION_TIME;
        else
            autoRotationTime = time;
        if (mHandler == null)
            mHandler = new Handler();
        else {
            if (mAutoRotationRunnable != null)
                mHandler.removeCallbacks(mAutoRotationRunnable);
        }
        mHandler.postDelayed(mAutoRotationRunnable, time);
    }

    /**
     *
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param
     * @return
     */
    public void stopAutoRotation(){
        if (mHandler != null && mAutoRotationRunnable != null){
            mHandler.removeCallbacks(mAutoRotationRunnable);
            mHandler = null;
            isStopAuto = true;
            isAuto = false;
        }
    }

    /**
     * 向右滑动
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param
     * @return
     */
    private void onFlingRight(){
        if (mCycleViewVf != null) {
            mCycleViewVf.setInAnimation(leftInAnimation);
            mCycleViewVf.setOutAnimation(leftOutAnimation);
            mCycleViewVf.showNext();
            currentPosition++;
            if (currentPosition >= pictureSize)
                currentPosition = currentPosition - pictureSize;
            setIndicator(currentPosition);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // touch事件交给手势处理
        return this.detector.onTouchEvent(event);
    }

    /**
     * 向左滑动
     * @author leibing
     * @createTime 2016/09/02
     * @lastModify 2016/09/02
     * @param
     * @return
     */
    private void onFlingLeft(){
        if (mCycleViewVf != null) {
            mCycleViewVf.setInAnimation(rightInAnimation);
            mCycleViewVf.setOutAnimation(rightOutAnimation);
            mCycleViewVf.showPrevious();
            currentPosition--;
            if (currentPosition < 0)
                currentPosition = currentPosition + pictureSize;
            setIndicator(currentPosition);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        if (!isAuto){
            startAutoRotation(AUTO_ROTATION_TIME);
        }else {
            stopAutoRotation();
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if(e1.getX()-e2.getX()>120){
            // 向右滑动
            onFlingRight();
            return true;
        }else if(e1.getX()-e2.getY()<-120){
            // 向左滑动
            onFlingLeft();
            return true;
        }
        return false;
    }
}
