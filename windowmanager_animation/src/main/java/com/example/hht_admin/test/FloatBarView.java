package com.example.hht_admin.test;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;


public class FloatBarView extends FrameLayout {

    //点击控件的放大倍数的阈值
    private final float THRESHOLD=0.1f;
    //是否多点触控
    private boolean isMoreTouch = false;

    private boolean test = false;

    private TextView tv1,tv2;

    private ImageView iv1,iv2,iv3;

    public interface CallBack{

        public void updateViewPosition(float xInView,float xInScrenn,float yInView,float yInScreen);
    }

    private CallBack callBack;


    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;


    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    public FloatBarView(Context context,CallBack cb) {
        super(context);
        init(cb);
    }

    public FloatBarView(Context context, AttributeSet attrs,CallBack cb) {
        super(context, attrs);
        init(cb);
    }

    public FloatBarView(Context context, AttributeSet attrs, int defStyleAttr,CallBack cb) {
        super(context, attrs, defStyleAttr);
        init(cb);
    }

    private void init(CallBack cb) {
        LayoutInflater.from(getContext()).inflate(R.layout.float_layout, this, true);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        callBack = cb;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN: {
                isMoreTouch = true;
            }
            break;
            case MotionEvent.ACTION_POINTER_UP: {
                isMoreTouch = false;
            }
            break;
            case MotionEvent.ACTION_DOWN: {
                isMoreTouch = false;
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                if (isMoreTouch) {
                    startScaleAnimation();
                } else {
                    xInScreen = event.getRawX();
                    yInScreen = event.getRawY() - getStatusBarHeight();
                    // 手指移动的时候更新小悬浮窗的位置
                    callBack.updateViewPosition(xInView,xInScreen,yInView,yInScreen);
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                isMoreTouch = false;
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if ((xDownInScreen == xInScreen && yDownInScreen == yInScreen) || (int) (yInScreen - yInView) < 30) {
                    //执行相应的处理
                }
            }
            break;
        }
        return true;
    }


    private void startScaleAnimation() {
        ObjectAnimator animator=ObjectAnimator.ofFloat(this,"xxx",0,1).setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //progress-->[0,1]
                float progress= (float) animation.getAnimatedValue();

//                    setScaleX(1+progress*THRESHOLD*2);
                    setScaleY(1-progress*THRESHOLD*2);
                 if(!test) {
                     setScaleY(1-progress*THRESHOLD*2);
                     iv1.setVisibility(GONE);
                 }else{
                     setScaleY(1);
                     iv1.setVisibility(VISIBLE);
                 }

                test =!test;
            }
        });

        animator.start();
    }



    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }




}
