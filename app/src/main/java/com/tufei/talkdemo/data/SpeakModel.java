package com.tufei.talkdemo.data;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.tufei.talkdemo.data.listener.SpeakCallBack;
import com.tufei.talkdemo.utils.LogUtil;

import static com.tufei.talkdemo.utils.Preconditions.checkNotNull;

/**
 * 讯飞语音合成功能
 * @author tufei
 * @date 2017/11/19.
 */

public class SpeakModel {
    private static final String TAG = "SpeakModel";
    private Context mContext;
    private SpeechSynthesizer mSpeaker;
    private SpeakCallBack mSpeakCallBack;
    /**
     * 默认发音人
     */
    private String voicer = "xiaoyan";
    private String mText;

    public SpeakModel(Context context) {
        mContext = context;
    }

    public void startSpeak(String text, @NonNull SpeakCallBack speakCallBack) {
        mSpeakCallBack = checkNotNull(speakCallBack);
        mText = text;
        mSpeaker = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            LogUtil.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                mSpeakCallBack.onSpeakError("初始化失败：InitListener init() code = " + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                initParams();
                mSpeaker.startSpeaking(mText, mTtsListener);
            }
        }
    };

    private void initParams() {
        //根据合成引擎设置相应参数
        mSpeaker.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置在线合成发音人
        mSpeaker.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        mSpeaker.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mSpeaker.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mSpeaker.setParameter(SpeechConstant.VOLUME, "50");
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                mSpeakCallBack.onSpeakSuccess();
            } else {
                LogUtil.d(TAG, error.getPlainDescription(true));
                mSpeakCallBack.onSpeakError(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
}
