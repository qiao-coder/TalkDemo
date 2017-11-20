package com.tufei.talkdemo.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author tufei
 * @date 2017/11/1
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 将TAG定义在BaseActivity里面
     */
    protected String TAG = getClass().getSimpleName();
    private List<IBasePresenter> presenterList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceId());
        initView();
        attachPresenter();
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
     * {#pr}
     * presenter绑定view  在activity结束时{@link #onDestroy()},解除绑定
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
        presenterList.remove(t);
        t.onDetachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //执行presenter里的onDetachView方法
        if (presenterList != null) {
            Iterator<IBasePresenter> iterator = presenterList.iterator();
            if (iterator.hasNext()) {
                IBasePresenter presenter = iterator.next();
                presenter.onDetachView();
                iterator.remove();
            }
        }
    }
}
