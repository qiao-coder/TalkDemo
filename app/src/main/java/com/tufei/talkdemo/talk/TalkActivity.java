package com.tufei.talkdemo.talk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tufei.talkdemo.R;
import com.tufei.talkdemo.base.BaseActivity;
import com.tufei.talkdemo.data.Injection;

/**
 * @author tufei
 * @date 2017/11/1
 */

public class TalkActivity extends BaseActivity implements TalkContract.View, View.OnClickListener {

    private static final int PermissionCode = 0;
    private static final String[] needPermission =
            new String[]{Manifest.permission.RECORD_AUDIO};
    private TalkContract.Presenter mTalkPresenter;
    private EditText mEtPeopleSay;
    private EditText mEtRobotSay;
    private Button mBtnStart;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_talk;
    }

    @Override
    protected void initView() {
        mEtPeopleSay = findViewById(R.id.et_people_say);
        mEtRobotSay = findViewById(R.id.et_robot_say);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //未授权，提起权限申请
            ActivityCompat.requestPermissions(this, needPermission, PermissionCode);
        }
    }

    @Override
    protected void attachPresenter() {
        mTalkPresenter = new TalkPresenter(Injection.provideListenModel(getApplicationContext()),
                Injection.provideTextUnderstandModel(getApplicationContext()),
                Injection.provideSpeakModel(getApplicationContext()));
        attachPresenter(mTalkPresenter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionCode:
                for (int result : grantResults) {
                    if (result == -1) {
                        showToast("你禁止了录音权限的使用，无法使用语音交互功能！");
                        return;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mTalkPresenter.startTalk();
                break;
            default:
                break;
        }
    }

    @Override
    public void showPeopleSay(String text) {
        mEtPeopleSay.setText(text);
    }

    @Override
    public void showRobotSay(String text) {
        mEtRobotSay.setText(text);
    }

    @Override
    public void showToast(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

}
