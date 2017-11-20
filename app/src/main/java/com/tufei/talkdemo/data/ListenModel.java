package com.tufei.talkdemo.data;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.tufei.talkdemo.data.bean.QuestionBean;
import com.tufei.talkdemo.data.listener.ListenCallback;
import com.tufei.talkdemo.utils.LogUtil;

import java.util.List;

import static com.tufei.talkdemo.utils.Preconditions.checkNotNull;

/**
 * 讯飞语音听写功能
 *
 * @author tufei
 * @date 2017/4/5
 */

public class ListenModel {
    private static final String TAG = "ListenModel";
    private final Context mContext;
    private SpeechRecognizer mListener;
    /**
     * 听到的话转换成的文字
     */
    private String question;
    private ListenCallback mListenCallback;

    public ListenModel(Context context) {
        mContext = context;
    }

    /**
     * 开启语音听写
     *
     * @param listenCallback
     */
    public void startListen(@NonNull ListenCallback listenCallback) {
        mListenCallback = checkNotNull(listenCallback);
        //将问题文本置空
        question = "";
        initParameter();
        if (mListener.isListening()) {
            stopListen();
        }
        mListener.startListening(mRecognizerListener);
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            LogUtil.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                LogUtil.w(TAG, "初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 初始化相关参数
     */
    public void initParameter() {
        //创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mListener = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        // 清空参数
        mListener.setParameter(SpeechConstant.PARAMS, null);
        //应用领域
        mListener.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置语言
        mListener.setParameter(SpeechConstant.LANGUAGE, "zn_ch");
        //设置语言区域
        mListener.setParameter(SpeechConstant.ACCENT, "mandarin");
        //设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理  值范围为[1000, 10000]
        mListener.setParameter(SpeechConstant.VAD_BOS, "50000");
        //设置通过麦克风输入语音
        mListener.setParameter(SpeechConstant.AUDIO_SOURCE, "1");
        //音频格式
        mListener.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
    }

    /**
     * 停止语音听写
     */
    public void stopListen() {
        if (mListener != null) {
            mListener.cancel();
        }
    }


    /**
     * 是否正在进行语音听写
     *
     * @return
     */
    public boolean isListening() {
        if (mListener != null) {
            return mListener.isListening();
        }
        return false;
    }


    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            LogUtil.v(TAG, "i = " + i + ",bytes = " + bytes);
        }

        @Override
        public void onBeginOfSpeech() {
            LogUtil.d(TAG, "开始录音");
        }


        @Override
        public void onEndOfSpeech() {
            LogUtil.d(TAG, "结束录音");
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            LogUtil.d(TAG, recognizerResult.getResultString());
            String json = recognizerResult.getResultString();
            Gson gson = new Gson();
            QuestionBean questionBean = gson.fromJson(json, QuestionBean.class);
            List<QuestionBean.WsBean> words = questionBean.getWs();

            for (QuestionBean.WsBean wsBean : words) {
                List<QuestionBean.WsBean.CwBean> cw = wsBean.getCw();
                String w = cw.get(0).getW();
                question = question + w;
            }

            if (!isLast) {
                return;
            }

            if (!TextUtils.isEmpty(question)) {
                LogUtil.d(TAG, "text = " + question);
                mListenCallback.onListenSuccess(question);
            } else {
                mListenCallback.onListenError("语音听写获得的文本为空！");
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            LogUtil.d(TAG, "errorMsg = " + speechError.getErrorDescription() + "errorCode = " + speechError.getErrorCode());
            mListenCallback.onListenError("errorMsg = " + speechError.getErrorDescription() + "errorCode = " + speechError.getErrorCode());
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

}
