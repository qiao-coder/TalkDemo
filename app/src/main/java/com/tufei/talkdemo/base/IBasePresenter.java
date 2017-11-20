package com.tufei.talkdemo.base;

/**
 * Created by tufei on 2017/6/29.
 */

public interface IBasePresenter<T extends IBaseView> {

    void onAttachView(T view);

    void onDetachView();
}
