package com.example.hht_admin.test;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class FloatBarService extends Service implements View.OnClickListener, FloatBarView.CallBack {
    //定义浮动窗口布局
    public static FrameLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;
    //移除浮动窗的按钮
    Button removeBtn;

    //点击该图片，悬浮窗执行退出动画
    ImageView iv1;

    Animation mTopOutAnimation;




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
        wmParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
       // wmParams.x = 0;
        wmParams.x=0;
        wmParams.y = 0;

		//最重要：设置windowmanager的view进出动画（同时Mainifest声明权限）
        wmParams.windowAnimations = R.style.anim_view;


  /*       // 设置悬浮窗口长宽数据
        wmParams.width = 120;
        wmParams.height = 140;*/

     /* LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout=(LinearLayout) inflater.inflate(R.layout.float_layout, null);*/

        mFloatLayout = new FloatBarView(getApplicationContext(),this);
        //浮动窗口的移除按钮
        removeBtn = (Button) mFloatLayout.findViewById(R.id.fb_btn);
        iv1 = (ImageView) mFloatLayout.findViewById(R.id.iv1);
        removeBtn.setOnClickListener(this);
        iv1.setOnClickListener(this);

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);


        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));


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
                //移除悬浮窗
                removeSmallWindow();
                //同时关闭服务s
                stopSelf();
            }break;
            case R.id.iv1:{

                mFloatLayout.setVisibility(View.GONE);

            }break;
        }
    }


    @Override
    public void updateViewPosition(float xInView, float xInScrenn, float yInView, float yInScreen) {
        //直接固定x坐标
        wmParams.x = 0;
        wmParams.y = (int) (yInScreen - yInView);
        if(wmParams.y > 30 ) {
            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
        }
    }
}
