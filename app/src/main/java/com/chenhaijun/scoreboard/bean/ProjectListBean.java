package com.chenhaijun.scoreboard.bean;

import java.io.Serializable;

/**
 * Created by Haijun Chen on 2017/7/25.
 */

public class ProjectListBean implements Serializable{
    public String   project_nama;
    public String   project_total_score;
    public String   project_month_score;
    public int      project_progress;

    public String getProject_nama() {
        return project_nama;
    }

    public void setProject_nama(String project_nama) {
        this.project_nama = project_nama;
    }

    public String getProject_total_score() {
        return project_total_score;
    }

    public void setProject_total_score(String project_total_score) {
        this.project_total_score = project_total_score;
    }

    public String getProject_month_score() {
        return project_month_score;
    }

    public void setProject_month_score(String project_month_score) {
        this.project_month_score = project_month_score;
    }

    public int getProject_progress() {
        return project_progress;
    }

    public void setProject_progress(int project_progress) {
        this.project_progress = project_progress;
    }
}
