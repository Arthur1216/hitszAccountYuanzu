package com.example.jumptonext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jumptonext.gesturelock.view.GestureLockViewGroup;
import com.example.jumptonext.gesturelock.view.listener.GestureEventListener;
import com.example.jumptonext.gesturelock.view.listener.GestureUnmatchedExceedListener;

public class LoginActivity extends AppCompatActivity {
    private GestureLockViewGroup mGestureLockViewGroup;
    private boolean isReset = false;
    private Button fgGestureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp;
        sp = getSharedPreferences("data", Context.MODE_PRIVATE); //从data.xml文件中读取是否开启手势密码
        //判断是否开启手势解锁
        int hadOpenGesture = sp.getInt("hadOpenGesture", 0);
        if (hadOpenGesture == 1){
            initGesture();
        }
        else //未开启手势解锁，转文字密码
        {
            //第一次使用，设置文本密码
            sp = getSharedPreferences("data", Context.MODE_PRIVATE);
            boolean hadSetPsw = sp.getBoolean("hadSetPsw", false);

            if (!hadSetPsw){
                Intent intent = new Intent(LoginActivity.this, SetPassword.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(LoginActivity.this, TextPassword.class);
                startActivity(intent);
            }
            finish();
        }
        //点击“忘记手势密码？”按钮跳转文本密码登录
        fgGestureBtn = findViewById(R.id.fgtGestureBtn);
        fgGestureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, TextPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initGesture() {
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gestureLock);
        gestureEventListener();
        gestureRetryLimitListener();
    }

    private void gestureEventListener() {
        mGestureLockViewGroup.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (!matched) {
                    Toast.makeText(LoginActivity.this, "手势密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    if (isReset) {
                        isReset = false;
                        Toast.makeText(LoginActivity.this, "清除成功!", Toast.LENGTH_SHORT).show();
                        resetGesturePattern();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, GeneralActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
    private void gestureRetryLimitListener() {
        mGestureLockViewGroup.setGestureUnmatchedExceedListener(3, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(LoginActivity.this, "错误次数过多，请稍后再试!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setGestureWhenNoSet() {
        if (!mGestureLockViewGroup.isSetPassword()){
        }
    }

    private void resetGesturePattern() {
        mGestureLockViewGroup.removePassword();
        setGestureWhenNoSet();
        mGestureLockViewGroup.resetView();
    }
}