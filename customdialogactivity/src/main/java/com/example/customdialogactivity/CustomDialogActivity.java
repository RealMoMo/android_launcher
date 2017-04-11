package com.example.customdialogactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CustomDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);


        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        //另一种获取屏幕宽高的像素
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int width = getResources().getDisplayMetrics().widthPixels;
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值


        p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的0.5
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.7
        Log.d("realmo","after height:"+p.height+",width:"+p.width);

        //以下2个属性可以不设置，直接用系统默认的
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度(即非DialogActivity周围区域的颜色)

        getWindow().setAttributes(p);     //设置生效
        getWindow().setGravity(Gravity.RIGHT);       //设置靠右对齐
    }


    public void touchTest(View view) {
        Toast.makeText(this, "TouchTextView", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
