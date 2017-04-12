package com.example.animationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 发广播弹出AlertDialog
 */
public class MainActivity extends AppCompatActivity {


    public MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    String[] items = { "皇马", "巴塞", "拜仁", "阿森纳" };
    boolean[] isChecks = { true, false, false, false };

    //dialog
    public void sendBroadCast(View view) {
        //发送广播
        //１.准备Intent
        Intent intent = new Intent();
        intent.setAction("com.test.momo");
        intent.putExtra("key", "A发送过来的广播");
        //2.发送广播
        sendBroadcast(intent);
    }

    public class MyReceiver extends BroadcastReceiver{

        public MyReceiver(Context context) {
            //注册广播
            //1.设置Intent过滤
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.test.momo");
            //2.注册
            context.registerReceiver(this, filter);

        }


        @Override
        public void onReceive(Context context, Intent intent) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("你最喜欢那支球队");
            builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(MainActivity.this, items[which],
                            Toast.LENGTH_SHORT).show();

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

            builder.show();
        }
    }
}
