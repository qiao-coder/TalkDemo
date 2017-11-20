package com.tufei.talkdemo.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author tufei
 * @date 2017/11/1
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 将TAG定义在BaseFragment里面
     */
    protected String TAG = getClass().getSimpleName();
    protected View mRootView;
    private List<IBasePresenter> presenterList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResourceId(), container, false);
        initView();
        attachPresenter();
        return mRootView;
    }

    /**
     * 设置布局文件对应的Id
     *
     * @return
     */
    @LayoutRes
    protected abstract int setLayoutResourceId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 抽象方法，主要提醒自己记得绑定Presenter,使用{@link #attachPresenter(? extends IBasePresenter)}方法来绑定
     */
    protected abstract void attachPresenter();

    /**
     * presenter绑定view  在fragment结束时{@link #onDestroyView()},解除绑定
     *
     * @param t
     * @param <T>
     */
    public <T extends IBasePresenter> void attachPresenter(T t) {
        if (presenterList == null) {
            presenterList = new ArrayList<>();
        }
        presenterList.add(t);
        t.onAttachView((IBaseView) this);
    }

    /**
     * presenter解除绑定的view，用于主动解除绑定
     *
     * @param t
     * @param <T>
     */
    public <T extends IBasePresenter> void detachPresenter(T t) {
        t.onDetachView();
        presenterList.remove(t);
    }

    @Override
    public void onDestroyView() {
        //将presenter里绑定的view置空
        if (presenterList != null) {
            Iterator<IBasePresenter> iterator = presenterList.iterator();
            if (iterator.hasNext()) {
                IBasePresenter presenter = iterator.next();
                presenter.onDetachView();
                iterator.remove();
            }
        }
        super.onDestroyView();
    }
}
