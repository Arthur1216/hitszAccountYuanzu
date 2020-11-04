package com.example.jumptonext.gesturelock.view.listener;

public interface GesturePasswordSettingListener {
    boolean onFirstInputComplete(int len);
    void onSuccess();
    void onFail();
}
