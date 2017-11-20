package com.tufei.talkdemo.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.tufei.talkdemo.data.bean.AnswerBean;
import com.tufei.talkdemo.data.listener.UnderstandCallback;
import com.tufei.talkdemo.utils.LogUtil;

import static com.tufei.talkdemo.utils.Preconditions.checkNotNull;

/**
 * 讯飞语义理解功能，根据问题，查找答案
 * @author tufei
 * @date 2017/11/19.
 */

public class TextUnderstandModel {
    private static final String TAG = "TextUnderstandModel";

    private Context mContext;
    private TextUnderstander mTextUnderstander;
    private UnderstandCallback mUnderstandCallback;

    public TextUnderstandModel(Context context) {
        mContext = context;
    }

    public void startTextUnderstand(String text, @NonNull UnderstandCallback understandCallback) {
        mUnderstandCallback = checkNotNull(understandCallback);
        mTextUnderstander = TextUnderstander.createTextUnderstander(mContext, mTextUdrInitListener);
        int ret = mTextUnderstander.understandText(text, mTextUnderstanderListener);
        if (ret != 0) {
            LogUtil.d(TAG, "语义理解失败,错误码:" + ret);
        }
    }

    /**
     * 初始化监听器（文本到语义）。
     */
    private InitListener mTextUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                LogUtil.d(TAG, "初始化失败,错误码：" + code);
            }
        }
    };

    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (result != null && !TextUtils.isEmpty(result.getResultString())) {
                String text = result.getResultString();
                LogUtil.d(TAG, text);
                AnswerBean answerBean = new Gson().fromJson(text, AnswerBean.class);
                if (answerBean.getRc() == 0) {
                    AnswerBean.Content content = answerBean.getAnswer();
                    String answer = content.getText();
                    mUnderstandCallback.onUnderstandSuccess(answer);
                } else {
                    mUnderstandCallback.onUnderstandError("语义理解返回文本为空");
                }
            } else {
                mUnderstandCallback.onUnderstandError("语义理解返回文本为空");
            }
        }

        @Override
        public void onError(SpeechError error) {
            // 文本语义不能使用回调错误码14002，请确认您下载sdk时是否勾选语义场景和私有语义的发布
            // 请到 aiui.xfyun.cn 配置语义，从1115前的SDK更新到1116以上版本SDK后，语义需要重新到 aiui.xfyun.cn 配置
            LogUtil.d(TAG, "onError Code：" + error.getErrorCode());
            mUnderstandCallback.onUnderstandError("onError Code：" + error.getErrorCode());
        }
    };
}


