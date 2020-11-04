package com.example.jumptonext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private ImageButton revert_btn;
    private Button mdf_psw_btn;
    private Switch swh_gesture;
    private Button reset_gesture_btn;
    private Switch swh_fingerprint;
    private Button clear_btn; //发布app时删除
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static FingerprintManagerCompat fingerprintManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sp.edit();

        revert_btn = findViewById(R.id.setting_revert_btn);
        mdf_psw_btn = findViewById(R.id.mdf_psw_btn);
        swh_gesture = findViewById(R.id.swh_gesture);
        swh_fingerprint = findViewById(R.id.swh_fingerprint);
        reset_gesture_btn = findViewById(R.id.reset_gesture_btn);
        //clear_btn = findViewById(R.id.clear_data_btn); //发布app时删除

        revert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mdf_psw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改文本密码
                Intent intent = new Intent(SettingActivity.this, SetPassword.class);
                startActivity(intent);
            }
        });

        //获取手势密码开关状态
        if (sp != null) {
            boolean gesture_flag = sp.getBoolean("gesture_flag", false);
            swh_gesture.setChecked(gesture_flag);
        }
        //切换手势密码开关状态
        swh_gesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int hadOpenGesture = sp.getInt("hadOpenGesture", -1); //从未开启手势密码，则返回默认值-1
                if (isChecked){
                    //从未开启手势密码则转到设置手势密码界面，在那里将hadOpenGesture设为1，防止用户不设置密码直接返回而产生bug
                    if (hadOpenGesture == -1){
                        Intent intent = new Intent(SettingActivity.this, ResetGesture.class);
                        editor.putBoolean("gesture_flag", true);
                        editor.apply();
                        startActivity(intent);
                    }
                    //若手势密码关闭，则开启
                    else if (hadOpenGesture == 0){
                        editor.putInt("hadOpenGesture", 1);
                        editor.putBoolean("gesture_flag", true);
                        editor.apply();
                        Toast.makeText(SettingActivity.this, "手势密码开启", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SettingActivity.this, "手势密码已开启", Toast.LENGTH_SHORT).show();
                    }
                }else{//手势密码开启，则关闭
                    editor.putInt("hadOpenGesture", 0);
                    editor.putBoolean("gesture_flag", false);
                    editor.apply();
                    Toast.makeText(SettingActivity.this,"手势密码关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //重设手势密码，需要已开启手势密码
        reset_gesture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                boolean gesture_flag = sp.getBoolean("gesture_flag", false);
                if (gesture_flag) {
                    Intent intent = new Intent(SettingActivity.this, ResetGesture.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("message", "resetGesture");
                    intent.putExtras(bundle); //向ResetGesture发送消息表明这是一个重置请求
                    startActivity(intent);
                }else{
                    Toast.makeText(SettingActivity.this, "尚未开启手势密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //获取指纹登录开启状态
        if (sp != null) {
            boolean fingerprint_flag = sp.getBoolean("fingerprint_flag", false);
            swh_fingerprint.setChecked(fingerprint_flag);
        }
        //切换指纹登录状态
        swh_fingerprint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //开启指纹登录时，检查设备是否支持指纹，是否录入指纹
                if (isChecked){
                    if (supportFingerprint()){
                        //支持指纹，则设置已开启指纹，开启switch
                        editor.putBoolean("hadOpenFingerprint", true);
                        editor.putBoolean("fingerprint_flag", true);
                        editor.apply();
                        Toast.makeText(SettingActivity.this, "指纹登录已开启", Toast.LENGTH_SHORT).show();
                    }else{
                        //不支持指纹，关闭switch
                        editor.putBoolean("fingerprint_flag", false);
                        editor.apply();
                        swh_fingerprint.setChecked(false);
                    }
                }else{
                    //关闭指纹登录
                    editor.putBoolean("hadOpenFingerprint", false);
                    editor.putBoolean("fingerprint_flag", false);
                    editor.apply();
                    Toast.makeText(SettingActivity.this, "指纹登录已关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*
        //清除SharedPreferences数据，用于虚拟机测试，发布app时删除
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                //这里清除存储的手势密码，SharedPreferences详见GesturePreference.java
                sp = getSharedPreferences("com.oden.gesturelock.filename", Context.MODE_PRIVATE);
                editor = sp.edit();
                editor.clear();
                editor.commit();
            }
        });
        */
    }
    //判断设备是否支持指纹
    private boolean supportFingerprint(){
        fingerprintManagerCompat = FingerprintManagerCompat.from(this);

        // 是否支持指纹验证
        if (fingerprintManagerCompat == null || !fingerprintManagerCompat.isHardwareDetected() || Build.VERSION.SDK_INT < 23) {
            Toast.makeText(SettingActivity.this, "设备不支持指纹验证！", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 是否录入了指纹
        if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
            Toast.makeText(SettingActivity.this, "尚未录入指纹！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}