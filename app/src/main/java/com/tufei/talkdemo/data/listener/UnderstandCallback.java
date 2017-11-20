package com.tufei.talkdemo.data.listener;

/**
 * @author tufei
 * @date 2017/11/19.
 */

public interface UnderstandCallback {
    void onUnderstandSuccess(String answer);
    void onUnderstandError(String errorMsg);
}
