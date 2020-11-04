package com.example.jumptonext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumptonext.gesturelock.view.GestureLockViewGroup;
import com.example.jumptonext.gesturelock.view.listener.GestureEventListener;
import com.example.jumptonext.gesturelock.view.listener.GesturePasswordSettingListener;
import com.example.jumptonext.gesturelock.view.listener.GestureUnmatchedExceedListener;

import java.io.OutputStream;

public class ResetGesture extends AppCompatActivity {
    private GestureLockViewGroup mGestureLockViewGroup;
    private TextView tv_state;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_gesture);
        initGesture();

        //从MainActivity获取数据，判断是否为重置密码请求
        Bundle bundle = getIntent().getExtras();
        String string = null;
        if (bundle != null)
            string = bundle.getString("message");

        if (string != null){
            if (string.equals("resetGesture"))
                resetGesturePattern();
        }else {
            setGestureWhenNoSet();
        }
    }

    private void initGesture() {
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gestureLock);
        tv_state = findViewById(R.id.tv_state);
        gesturePasswordSettingListener();
    }

    private void gesturePasswordSettingListener() {
        mGestureLockViewGroup.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {
                if (len > 3) {
                    tv_state.setTextColor(Color.WHITE);
                    tv_state.setText("再次绘制手势密码");
                    return true;
                } else {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("最少连接4个点，请重新输入!");
                    return false;
                }
            }

            @Override
            public void onSuccess() {
                tv_state.setTextColor(Color.WHITE);
                Toast.makeText(ResetGesture.this, "密码设置成功!", Toast.LENGTH_SHORT).show();
                sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                editor = sp.edit();
                editor.putInt("hadOpenGesture", 1);
                editor.apply();
                finish();
            }

            @Override
            public void onFail() {
                tv_state.setTextColor(Color.RED);
                tv_state.setText("与上一次绘制不一致，请重新绘制");
            }
        });
    }


    private void setGestureWhenNoSet() {
        if (!mGestureLockViewGroup.isSetPassword()){
            tv_state.setTextColor(Color.WHITE);
            tv_state.setText("绘制手势密码");
        }
    }

    private void resetGesturePattern() {
        mGestureLockViewGroup.removePassword();
        setGestureWhenNoSet();
    }
}