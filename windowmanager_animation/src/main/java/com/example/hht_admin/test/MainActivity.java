package com.example.hht_admin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * 6.0以上没有做权限申请的适配，需自己手动开启。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //点击按钮，开启悬浮窗
    public void startFloatBar(View view) {
        Intent intent = new Intent(this,FloatBarService.class);
        startService(intent);
    }

	
	//点击按钮，悬浮窗的进入动画
    public void showFloatBar(View view) {

        FloatBarService.mFloatLayout.setVisibility(View.VISIBLE);
    }
}
