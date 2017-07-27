package com.chenhaijun.scoreboard.activity;

import android.view.animation.AccelerateDecelerateInterpolator;

import com.chenhaijun.scoreboard.R;
import com.chenhaijun.scoreboard.base.BaseActivity;
import com.chenhaijun.scoreboard.view.piegraph.PieGraph;
import com.chenhaijun.scoreboard.view.piegraph.PieSlice;
import com.nineoldandroids.animation.Animator;

import java.util.List;

/**
 * Created by Haijun Chen on 2017/7/26.
 */

public class ProjectDetail extends BaseActivity {

    private static final int ANIMATION_DURATION = 1000;
    private PieGraph mPieGraph_month;
    private PieGraph mPieGraph_total;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_project_detail);

        mPieGraph_month = (PieGraph) findViewById(R.id.pg_activity_project_detail_month);
        mPieGraph_total = (PieGraph) findViewById(R.id.pg_activity_project_detail_total);
    }

    @Override
    protected void initData() {
        super.initData();

        //21天目标
        PieSlice mPs_month_current = new PieSlice();
        mPs_month_current.setColor(getResources().getColor(R.color.red));
        mPs_month_current.setValue(0);
        mPieGraph_month.addSlice(mPs_month_current);

        PieSlice mPs_month_remain = new PieSlice();
        mPs_month_remain.setColor(getResources().getColor(R.color.white));
        mPs_month_remain.setValue(0);
        mPieGraph_month.addSlice(mPs_month_remain);

        //总目标
        PieSlice mPs_total_current = new PieSlice();
        mPs_total_current.setColor(getResources().getColor(R.color.yellow));
        mPs_total_current.setValue(0);
        mPieGraph_total.addSlice(mPs_total_current);

        PieSlice mPs_total_remain = new PieSlice();
        mPs_total_remain.setColor(getResources().getColor(R.color.white));
        mPs_total_remain.setValue(0);
        mPieGraph_total.addSlice(mPs_total_remain);

        showPieGraphData();
    }

    public void showPieGraphData() {
        if (!mPieGraph_month.isAnimating()) {
            mPieGraph_month.setTitle("58");
            mPieGraph_month.setTotal("100");
            mPieGraph_month.setTitleColor(getResources().getColor(R.color.red));
            List<PieSlice> pieSliceList = mPieGraph_month.getSlices();
            if (pieSliceList != null) {
                pieSliceList.get(0).setGoalValue(58);
                pieSliceList.get(1).setGoalValue(100 - 58);
            }
            mPieGraph_month.setDuration(ANIMATION_DURATION);
            mPieGraph_month.setInterpolator(new AccelerateDecelerateInterpolator());
            mPieGraph_month.setAnimationListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mPieGraph_month.animateToGoalValues();
        }

        if (!mPieGraph_total.isAnimating()) {
            mPieGraph_total.setTitle("350");
            mPieGraph_total.setTotal("1000");
            mPieGraph_total.setTitleColor(getResources().getColor(R.color.yellow));
            List<PieSlice> pieSliceList = mPieGraph_total.getSlices();
            if (pieSliceList != null) {
                pieSliceList.get(0).setGoalValue(350);
                pieSliceList.get(1).setGoalValue(1000 - 350);
            }
            mPieGraph_total.setDuration(ANIMATION_DURATION);
            mPieGraph_total.setInterpolator(new AccelerateDecelerateInterpolator());
            mPieGraph_total.setAnimationListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mPieGraph_total.animateToGoalValues();
        }
    }
}
