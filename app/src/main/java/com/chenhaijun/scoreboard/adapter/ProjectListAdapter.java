package com.chenhaijun.scoreboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenhaijun.scoreboard.R;
import com.chenhaijun.scoreboard.bean.ProjectListBean;
import com.chenhaijun.scoreboard.listener.OnMyClickListener;
import com.chenhaijun.scoreboard.utils.DialogManager;
import com.chenhaijun.scoreboard.utils.dialog.OptionAlertDialog;
import com.chenhaijun.scoreboard.utils.dialog.listener.OnClickConfirmListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haijun Chen on 2017/7/25.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.MyViewHolder> {

    private Context                 mContext;
    private List<ProjectListBean>   mData = new ArrayList<>();
    private OnMyClickListener       mOnMyClickListener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MyViewHolder(View.inflate(mContext, R.layout.activity_project_list, null));
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
                    if (mOnMyClickListener != null) {
                        mOnMyClickListener.onMyClick(position, mData.get(position));
                    }
                }
            });
            holder.mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnMyClickListener != null) {
                        OptionAlertDialog optionAlertDialog = DialogManager.createOptionAlertDialog(mContext,
                                mContext.getString(R.string.common_dialog_alert_title),
                                mContext.getString(R.string.common_dialog_alert_delete));

                        optionAlertDialog.show();
                        optionAlertDialog.setOnClickConfirmListener(new OnClickConfirmListener() {
                            @Override
                            public void onClickConfirm() {
                                mOnMyClickListener.onMyLongClick(position);
                            }
                        });
                    }
                    return true;
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

    public void deleteItem(int position) {
        if (mData != null) {
            mData.remove(position);
            notifyItemChanged(position);
        }
    }

    public void setOnMyClickListener(OnMyClickListener onMyClickListener) {
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
