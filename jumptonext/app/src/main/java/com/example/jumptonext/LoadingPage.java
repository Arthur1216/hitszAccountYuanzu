package com.example.jumptonext;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
/*
加载界面，也是app的启动界面
在这里申请权限
 */
public class LoadingPage extends AppCompatActivity {
    public static final String loadingPageSuccess = "loadingPageSuccess";
    private Context mContext = this;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        boolean hadOpenFingerprint = sp.getBoolean("hadOpenFingerprint", false);
        if (hadOpenFingerprint){
            Intent intent = new Intent(LoadingPage.this, FingerPrint.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(LoadingPage.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}