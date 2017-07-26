package com.chenhaijun.scoreboard.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenhaijun.scoreboard.R;
import com.chenhaijun.scoreboard.base.BaseActivity;
import com.chenhaijun.scoreboard.bean.ProjectListBean;
import com.chenhaijun.scoreboard.constant.Constant;
import com.chenhaijun.scoreboard.view.MyTextWatcher;

/**
 * Created by Haijun Chen on 2017/7/25.
 */

public class AddProjectActivity extends BaseActivity {

    private EditText            mEt_project_name;
    private EditText            mEt_total_score;
    private EditText            mEt_month_score;
    private TextView            mTv_submit;
    private MyTextWatcher       mTextWatcher;
    private ProjectListBean     mProjectListBean;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_project);

        mEt_project_name = (EditText) findViewById(R.id.et_activity_add_project_name);
        mEt_total_score = (EditText) findViewById(R.id.et_activity_add_project_total_score);
        mEt_month_score = (EditText) findViewById(R.id.et_activity_add_project_month_score);
        mTv_submit = (TextView) findViewById(R.id.tv_activity_main_add_submit);
        mTextWatcher = new MyTextWatcher(mActivity, mEt_project_name, mEt_total_score, mEt_month_score) {
            @Override
            public void onInputStatusChanged(boolean isAllInput) {
                mTv_submit.setEnabled(isAllInput);
                mTv_submit.setClickable(isAllInput);
            }
        };
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_activity_main_add_submit:
                mProjectListBean = new ProjectListBean();
                mProjectListBean.setProject_nama(mEt_project_name.getText().toString().trim());
                mProjectListBean.setProject_total_score(mEt_total_score.getText().toString().trim());
                mProjectListBean.setProject_month_score(mEt_month_score.getText().toString().trim());
                Intent mIntent = new Intent();
                mIntent.putExtra(Constant.INTENT_DATA.PROJECT_INFO, mProjectListBean);
                setResult(RESULT_OK, mIntent);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTextWatcher.onDestroy();
    }
}
