package com.example.jumptonext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jumptonext.util.tools;

public class SetPassword extends AppCompatActivity {
    private Button okBtn;
    private EditText inputPassword1;
    private EditText inputPassword2;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//禁用actionBar
        setContentView(R.layout.activity_set_password);
        okBtn = findViewById(R.id.okButton);
        inputPassword1 = findViewById(R.id.inputPassword1);
        inputPassword2 = findViewById(R.id.inputPassword2);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPsw();
            }
        });
    }

    private void resetPsw(){
        String inputPasswordStr1 = inputPassword1.getText().toString();
        String inputPasswordStr2 = inputPassword2.getText().toString();
        String pswMd5;//将密码转为md5进行存储，提高安全性

        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        boolean hadSetPsw = sp.getBoolean("hadSetPsw", false);

        if(inputPasswordStr1.equals(inputPasswordStr2)){
            if (isSafePsw(inputPasswordStr1)) {
                editor = sp.edit();
                pswMd5 = tools.getMd5(inputPasswordStr1);
                if (!hadSetPsw) { //未设置密码，即第一次使用应用
                    editor.putBoolean("hadSetPsw", true);
                    editor.putString("password", pswMd5);
                    editor.apply();
                    Intent intent = new Intent(SetPassword.this, GeneralActivity.class);
                    startActivity(intent);
                } else {
                    editor.putString("password", pswMd5);
                    editor.apply();
                    Toast.makeText(SetPassword.this, "密码修改成功", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        }else{
            Toast t = Toast.makeText(SetPassword.this,"两次密码不一致!",Toast.LENGTH_LONG);
            t.show();
        }
    }

    private boolean isSafePsw(String str){
        if (str.length() < 6){
            Toast.makeText(SetPassword.this, "密码长度应大于等于6个字符", Toast.LENGTH_SHORT).show();
            return false;
        }else if (str.length() > 16){
            Toast.makeText(SetPassword.this, "密码长度应小于等于16个字符", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}