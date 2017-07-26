package com.chenhaijun.scoreboard.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by Haijun Chen on 2017/7/25.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{

    protected BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        init();
        initView();
        initListener();
        initData();
    }

    protected void init() {

    }

    protected abstract void initView();

    protected void initListener() {

    }

    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
