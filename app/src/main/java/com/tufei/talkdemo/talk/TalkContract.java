package com.tufei.talkdemo.talk;

import com.tufei.talkdemo.base.IBasePresenter;
import com.tufei.talkdemo.base.IBaseView;
import com.tufei.talkdemo.deprecated.TalkPresenter;
import com.tufei.talkdemo.deprecated.TalkView;

/**
 * 契约类：指定了View和Presenter之间的契约。
 * View和Presenter暴露的方法，都放在这里。
 * 这是建议的做法，源自google，
 * 以前很多人的写View和Presenter的接口，都是分成两个类，类似{@link TalkView}和{@link TalkPresenter}这样。
 * 而Contract的优点在于，减少了接口类文件的数目，而且View和Presenter放在一起，一目了然。
 *
 * @author tufei
 * @date 2017/11/1
 */

public interface TalkContract {

    interface View extends IBaseView {

        void showToast(String tip);

        void showRobotSay(String text);

        void showPeopleSay(String text);
    }

    interface Presenter extends IBasePresenter<View> {

        void startTalk();
    }
}
