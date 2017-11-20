package com.tufei.talkdemo.data;

import android.content.Context;
import android.support.annotation.NonNull;

import static com.tufei.talkdemo.utils.Preconditions.checkNotNull;


/**
 * @author tufei
 * @date 2017/11/20.
 */

public class Injection {
    public static ListenModel provideListenModel(@NonNull Context context) {
        checkNotNull(context);
        return new ListenModel(context);
    }

    public static TextUnderstandModel provideTextUnderstandModel(@NonNull Context context) {
        checkNotNull(context);
        return new TextUnderstandModel(context);
    }

    public static SpeakModel provideSpeakModel(@NonNull Context context) {
        checkNotNull(context);
        return new SpeakModel(context);
    }
}
