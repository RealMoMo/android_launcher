package com.example.bluefilter_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

/**
 * 模拟滤蓝光功能
 * 6.0以上需悬浮窗的权限申请
 * 截屏，会截取滤蓝光的布局（所以，最好硬件上调整屏幕色温）
 */
public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SharedPreferences shared;
    private SeekBar seekBar;
    private Intent intent;

    /*private DrawOverAppsService.ScreenFilter binder;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (DrawOverAppsService.ScreenFilter) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

        intent = new Intent(this,DrawOverAppsService.class);
        startService(intent);
        //bindService(intent,conn,BIND_AUTO_CREATE);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //binder.upDateFilterOption(progress);
        intent.putExtra("degree",progress);
        startService(intent);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


}
