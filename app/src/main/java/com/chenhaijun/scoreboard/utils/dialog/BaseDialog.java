package com.chenhaijun.scoreboard.utils.dialog;

import android.app.Dialog;
import android.content.Context;

import com.chenhaijun.scoreboard.utils.dialog.listener.OnClickCancelListener;
import com.chenhaijun.scoreboard.utils.dialog.listener.OnClickConfirmListener;
import com.chenhaijun.scoreboard.utils.dialog.listener.OnClickInfoListener;

/**
 * Created by Haijun Chen on 2017/7/26.
 */

public class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 确认按钮监听器
     */
    public OnClickConfirmListener mOnClickConfirmListener;

    /**
     * 取消按钮监听器
     */
    public OnClickCancelListener mOnClickCancelListener;

    public BaseDialog setOnClickCancelListener(OnClickCancelListener onClickCancelListener) {
        mOnClickCancelListener = onClickCancelListener;
        return this;
    }

    public BaseDialog setOnClickConfirmListener(OnClickConfirmListener onClickConfirmListener) {
        mOnClickConfirmListener = onClickConfirmListener;
        return this;
    }

    /**
     * 点击展示的信息监听器
     */
    public OnClickInfoListener mOnClickInfoListener;

    public BaseDialog setOnClickInfoListener(OnClickInfoListener onClickInfoListener) {
        mOnClickInfoListener = onClickInfoListener;
        return this;
    }

}
