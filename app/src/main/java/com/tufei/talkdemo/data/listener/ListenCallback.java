package com.tufei.talkdemo.data.listener;


/**
 * @author tufei
 * @date 2017/10/19.
 */

public interface ListenCallback {

    /**
     * 听写成功
     *
     * @param text 语音听写获得的文本
     */
    void onListenSuccess(String text);


    /**
     * 语音听写失败
     * @param errorMsg
     */
    void onListenError(String errorMsg);
}
