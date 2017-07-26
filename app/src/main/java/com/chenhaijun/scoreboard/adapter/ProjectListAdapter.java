package com.chenhaijun.scoreboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenhaijun.scoreboard.R;
import com.chenhaijun.scoreboard.bean.ProjectListBean;
import com.chenhaijun.scoreboard.listener.onMyClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haijun Chen on 2017/7/25.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.MyViewHolder> {

    private List<ProjectListBean> mData = new ArrayList<>();
    private onMyClickListener mOnMyClickListener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(parent.getContext(), R.layout.activity_project_list, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (mData != null && mData.size() > 0) {
            holder.mTv_project_name.setText(mData.get(position).getProject_nama());
            holder.mTv_project_score.setText(mData.get(position).getProject_total_score());
            holder.mPb_project_progress.setProgress(mData.get(position).getProject_progress());

            holder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMyClickListener.onMyClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void setData(List<ProjectListBean> data){
        mData.clear();
        if (mData != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setOnMyClickListener(onMyClickListener onMyClickListener) {
        mOnMyClickListener = onMyClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private View        mRootView;
        private TextView    mTv_project_name;
        private TextView    mTv_project_score;
        private ProgressBar mPb_project_progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRootView = itemView;
            mTv_project_name = (TextView) itemView.findViewById(R.id.tv_activity_main_project_name);
            mTv_project_score = (TextView) itemView.findViewById(R.id.tv_activity_main_project_score);
            mPb_project_progress = (ProgressBar) itemView.findViewById(R.id.pb_activity_main_project_progress);
        }
    }
}
