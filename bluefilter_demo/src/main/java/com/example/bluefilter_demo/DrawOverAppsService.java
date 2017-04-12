package com.example.bluefilter_demo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class DrawOverAppsService extends Service {

    public static final String TAG = "DrawOverAppsService";

    private View mOverlayView;

    private int lastDegree;

    private WindowManager.LayoutParams params;
    private WindowManager wm;

    //private ScreenFilter binder = new ScreenFilter();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                PixelFormat.TRANSLUCENT);

        // An alpha value to apply to this entire window.
        // An alpha of 1.0 means fully opaque and 0.0 means fully transparent
        params.alpha = 0.1F;

        // When FLAG_DIM_BEHIND is set, this is the amount of dimming to apply.
        // Range is from 1.0 for completely opaque to 0.0 for no dim.
        params.dimAmount = 0.5F;

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mOverlayView = inflater.inflate(R.layout.fiter_layout, null);

        wm.addView(mOverlayView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

         lastDegree = intent.getIntExtra("degree",lastDegree);
        params.alpha = (float) (lastDegree*3 / 1000.0);
//        params.dimAmount = (float) (lastDegree / 100.0);
        mOverlayView.setBackgroundColor(Color.rgb(223,218, (int) (255-255*Math.sqrt(lastDegree*1.0/100))));
        wm.updateViewLayout(mOverlayView, params);
        //粘性服务
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.removeView(mOverlayView);
    }

    /*class ScreenFilter extends Binder {

        public void upDateFilterOption(int degree) {
            params.alpha = (float) (degree / 100.0);
            params.dimAmount = (float) (degree / 100.0);

            wm.updateViewLayout(mOverlayView, params);
        }
    }*/
}
