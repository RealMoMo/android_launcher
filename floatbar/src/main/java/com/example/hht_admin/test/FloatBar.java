package com.example.hht_admin.test;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.Field;


/**
 * Created by realmo on 2017/4/10.
 */
public class FloatBar extends Service implements View.OnClickListener {
    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;

    Button removeBtn;


    int latestParamsY=0;//记录拖动前的坐标Y值


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

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
       // Log.i(TAG, "oncreat");

        createFloatView();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private void createFloatView()
    {
        wmParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
       // wmParams.x = 0;
        wmParams.x=0;
        wmParams.y = 0;


  /*       // 设置悬浮窗口长宽数据
        wmParams.width = 120;
        wmParams.height = 140;*/

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //浮动窗口的移除按钮
        removeBtn = (Button) mFloatLayout.findViewById(R.id.fb_btn);
        removeBtn.setOnClickListener(this);

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);


        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //设置监听浮动窗口的触摸移动
        mFloatLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                        xInView = event.getX();
                        yInView = event.getY();
                        xDownInScreen = event.getRawX();
                        yDownInScreen = event.getRawY() - getStatusBarHeight();
                        xInScreen = event.getRawX();
                        yInScreen = event.getRawY() - getStatusBarHeight();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        xInScreen = event.getRawX();
                        yInScreen = event.getRawY() - getStatusBarHeight();
                        // 手指移动的时候更新小悬浮窗的位置
                        updateViewPosition();
                        break;
                    case MotionEvent.ACTION_UP:
                        // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                        if ((xDownInScreen == xInScreen && yDownInScreen == yInScreen) || (int) (yInScreen - yInView) < 30) {
                           // openBigWindow();
//                            displayHidden();
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(mFloatLayout != null)
        {
            //移除悬浮窗口
            mWindowManager.removeView(mFloatLayout);
        }
    }


    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        //直接固定x坐标
        wmParams.x = 0;
        wmParams.y = (int) (yInScreen - yInView);
        if(wmParams.y > 30 ) {
            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
        }

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


    /**
     * 将小悬浮窗从屏幕上移除。
     *
     */
    public  void removeSmallWindow() {
        if (mFloatLayout != null) {

            mWindowManager.removeView(mFloatLayout);
            mFloatLayout = null;
            wmParams = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fb_btn:{
                removeSmallWindow();
                //同时关闭服务s
                stopSelf();
            }break;
        }
    }
}
