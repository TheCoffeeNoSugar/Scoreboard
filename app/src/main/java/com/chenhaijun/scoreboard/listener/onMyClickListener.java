package com.chenhaijun.scoreboard.listener;

import com.chenhaijun.scoreboard.bean.ProjectListBean;

/**
 * Created by Haijun Chen on 2017/7/25.
 */

public interface OnMyClickListener {
    void onMyClick(int position, ProjectListBean projectListBean);
    void onMyLongClick(int position);
}
