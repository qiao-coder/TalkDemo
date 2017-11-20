package com.tufei.talkdemo.talk;

import android.support.annotation.NonNull;

import com.tufei.talkdemo.data.ListenModel;
import com.tufei.talkdemo.data.SpeakModel;
import com.tufei.talkdemo.data.TextUnderstandModel;
import com.tufei.talkdemo.data.listener.ListenCallback;
import com.tufei.talkdemo.data.listener.SpeakCallBack;
import com.tufei.talkdemo.data.listener.UnderstandCallback;

import static com.tufei.talkdemo.utils.Preconditions.checkNotNull;

/**
 * @author tufei
 * @date 2017/11/1.
 */

public class TalkPresenter implements TalkContract.Presenter {

    private TalkContract.View mView;
    private ListenModel mListenModel;
    private TextUnderstandModel mTextUnderstandModel;
    private SpeakModel mSpeakModel;


    /**
     * 1.使用@NonNull、@Nullable这些注解  是个好习惯
     * <p>
     * 2.很多人，习惯Model也像View、Presenter那样，提供一个接口。但这个我觉得是真没必要了。
     * 除非你像谷歌的demo那样，分本地数据、远程数据两种情况，才需要提供一个抽象接口TasksDataSource(详见谷歌的demo)。
     * 又或者，你的一个Model里，有多个回调接口，你可以提供一个Model接口，把这些回调接口放在一起。
     * 仁者见仁，智者见智吧。
     *
     * @param listenModel
     * @param textUnderstandModel
     * @param speakModel
     */
    public TalkPresenter(@NonNull ListenModel listenModel, @NonNull TextUnderstandModel textUnderstandModel, @NonNull SpeakModel speakModel) {
        mListenModel = checkNotNull(listenModel, "listenModel不可以为空！");
        mTextUnderstandModel = checkNotNull(textUnderstandModel, "textUnderstandModel不可以为空！");
        mSpeakModel = checkNotNull(speakModel, "speakModel不可以为空！");
    }

    @Override
    public void onAttachView(TalkContract.View view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void startTalk() {
        startListen();
    }

    private void startListen() {
        mListenModel.startListen(new ListenCallback() {
            @Override
            public void onListenSuccess(String question) {
                if (mView != null) {
                    mView.showPeopleSay(question);
                }
                startTextUnderstand(question);
            }

            @Override
            public void onListenError(String errorMsg) {
                if (mView != null) {
                    mView.showToast("语音听写失败，失败原因：" + errorMsg);
                }
            }
        });
    }

    private void startTextUnderstand(String text) {
        mTextUnderstandModel.startTextUnderstand(text, new UnderstandCallback() {
            @Override
            public void onUnderstandSuccess(String answer) {
                if (mView != null) {
                    mView.showRobotSay(answer);
                }
                startSpeak(answer);
            }

            @Override
            public void onUnderstandError(String errorMsg) {
                if (mView != null) {
                    mView.showToast("语音理解失败，失败原因：" + errorMsg);
                }
            }
        });
    }

    private void startSpeak(String text) {
        mSpeakModel.startSpeak(text, new SpeakCallBack() {
            @Override
            public void onSpeakSuccess() {

            }

            @Override
            public void onSpeakError(String errorMsg) {
                if (mView != null) {
                    mView.showToast("语音合成失败，失败原因：" + errorMsg);
                }
            }
        });
    }
}
