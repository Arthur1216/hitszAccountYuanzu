package com.example.jumptonext;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

public class FingerPrint extends AppCompatActivity{

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        startVerification();
    }

    private void startVerification(){
        FingerprintManagerUtil.startFingerprinterVerification(this,
                new FingerprintManagerUtil.FingerprintListenerAdapter() {

                    @Override
                    public void onAuthenticationStart() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FingerPrint.this)

                                .setTitle("指纹验证")
                                .setIcon(R.drawable.ic_fp_40px)
                                .setMessage("指纹验证测试")
                                .setCancelable(false)
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FingerprintManagerUtil.cancel();
                                        noFingerPrint();
                                    }
                                });
                        alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onNonsupport() {
                        Log.i("MainActivity", "onNonsupport");
                        Toast.makeText(FingerPrint.this, "不支持指纹验证", Toast.LENGTH_SHORT).show();
                        noFingerPrint();
                    }

                    @Override
                    public void onEnrollFailed() {
                        Log.i("MainActivity", "onEnrollFailed");
                        Toast.makeText(FingerPrint.this, "没有录入指纹", Toast.LENGTH_SHORT).show();
                        noFingerPrint();
                    }

                    @Override
                    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                        alertDialog.dismiss();
                        Log.i("MainActivity", "onAuthenticationSucceeded result = [" + result + "]");
                        Toast.makeText(FingerPrint.this, "验证成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FingerPrint.this, GeneralActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        Log.i("MainActivity", "onAuthenticationFailed");
                        Toast.makeText(FingerPrint.this, "验证失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationError(int errMsgId, CharSequence errString) {
                        Log.i("MainActivity", "onAuthenticationError errMsgId = [" + errMsgId + "], errString = [" + errString + "]");
                        //Toast.makeText(FingerPrint.this, "提示: " + errString, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                        Log.i("MainActivity", "onAuthenticationHelp helpMsgId = [" + helpMsgId + "], helpString = [" + helpString + "]");
                        //Toast.makeText(FingerPrint.this, "提示: " + helpString, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //不支持指纹或没有录入指纹或取消指纹验证，跳转手势密码界面
    private void noFingerPrint(){
        Intent intent = new Intent(FingerPrint.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}