package com.example.customdialogactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startDialogActivity(View view) {

        Intent intent = new Intent(this,CustomDialogActivity.class);
        startActivity(intent);
    }
}
