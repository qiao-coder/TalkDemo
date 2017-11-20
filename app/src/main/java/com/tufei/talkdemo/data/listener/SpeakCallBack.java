package com.tufei.talkdemo.data.listener;

/**
 * @author tufei
 * @date 2017/10/19.
 */

public interface SpeakCallBack {

    void onSpeakSuccess();

    void onSpeakError(String errorMsg);
}
