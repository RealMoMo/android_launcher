package com.example.systemshareddemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //传文字
    public void share_txt(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my Share text.");
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    //传文本文件
    public void share_txtfile(View view) {
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "eguan.txt";
        //由文件得到uri
        Uri fileUri = Uri.fromFile(new File(filePath));
        Log.d("share", "uri:" + fileUri);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    //传单图
    public void share_image(View view) {
        String imagePath = Environment.getExternalStorageDirectory() + File.separator + "head.jpg";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
        
    }

    //传多图
    public void share_moreimage(View view) {

        ArrayList<Uri> uriList = new ArrayList<>();

        String path = Environment.getExternalStorageDirectory() + File.separator;
        uriList.add(Uri.fromFile(new File(path+"head.jpg")));
        uriList.add(Uri.fromFile(new File(path+"test.jpg")));


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    //传zip
    public void share_zip(View view) {
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "shenzhenmetro.zip";
        //由文件得到uri
        Uri fileUri = Uri.fromFile(new File(filePath));
        Log.d("share", "uri:" + fileUri);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));

    }

}
