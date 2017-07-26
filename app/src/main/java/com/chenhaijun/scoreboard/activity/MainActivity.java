package com.chenhaijun.scoreboard.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhaijun.scoreboard.R;
import com.chenhaijun.scoreboard.adapter.ProjectListAdapter;
import com.chenhaijun.scoreboard.base.BaseActivity;
import com.chenhaijun.scoreboard.bean.ProjectListBean;
import com.chenhaijun.scoreboard.constant.Constant;
import com.chenhaijun.scoreboard.listener.onMyClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{

    private TextView                mTv_add;
    private RecyclerView            mRecyclerView;
    private ProjectListAdapter      mProjectListAdapter;
    private List<ProjectListBean>   mProjectList = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        mTv_add = (TextView) findViewById(R.id.tv_activity_main_add);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_activity_main_list);
    }

    @Override
    protected void initListener() {
        mTv_add.setOnClickListener(this);//添加项目

        if (mProjectListAdapter != null) {
            mProjectListAdapter.setOnMyClickListener(new onMyClickListener() {
                @Override
                public void onMyClick(int position) {
                    Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void initData() {
        mProjectListAdapter = new ProjectListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mProjectListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_main_add:
                startActivityForResult(new Intent(mActivity, AddProjectActivity.class), Constant.INTENT_REQUEST_CODE.ADD_PROJECT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.INTENT_REQUEST_CODE.ADD_PROJECT && resultCode == RESULT_OK) {
            mProjectList.add((ProjectListBean) data.getExtras().getSerializable(Constant.INTENT_DATA.PROJECT_INFO));
            mProjectListAdapter.setData(mProjectList);
        }
    }
}
