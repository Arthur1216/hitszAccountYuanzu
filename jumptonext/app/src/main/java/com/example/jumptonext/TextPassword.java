package com.example.jumptonext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jumptonext.util.tools;

public class TextPassword extends AppCompatActivity {

    private Button loginButton;
    private EditText inputPassword;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_text_password);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginByPassword();
            }
        });
    }

    private void loginByPassword(){
        inputPassword = findViewById(R.id.inputPassword);
        String inputPasswordStr = inputPassword.getText().toString();
        String pswMd5 = tools.getMd5(inputPasswordStr); //输入的密码转为md5
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        String password = sp.getString("password", "0");
        if (pswMd5.equals(password)) {
            Intent intent1 = new Intent(TextPassword.this, GeneralActivity.class);
            startActivity(intent1);
            finish();
        } else {
            Toast t = Toast.makeText(TextPassword.this, "密码错误!", Toast.LENGTH_LONG);
            t.show();
        }
    }
}
