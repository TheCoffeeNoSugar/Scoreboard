package com.chenhaijun.scoreboard.utils.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenhaijun.scoreboard.R;

/**
 * Created by Haijun Chen on 2017/7/26.
 */

public class OptionAlertDialog extends BaseDialog{

    private String       title;
    private CharSequence info;
    private String       cancelText;
    private String       confirmText;

    private TextView mTv_title;
    private TextView       mTv_info;
    private Button mBtn_confirm;
    private Button         mBtn_cancel;
    private View contentView;
    private RelativeLayout mContentLayout;

    public OptionAlertDialog(Context context) {
        super(context, R.style.style_dialog_alert);
        init();
    }

    public OptionAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_option_alert);
        setCancelable(false);
        mTv_title = (TextView) findViewById(R.id.tv_dialog_alert_option_title);
        mTv_info = (TextView) findViewById(R.id.tv_dialog_alert_option_info);
        mBtn_confirm = (Button) findViewById(R.id.btn_dialog_alert_option_confirm);
        mBtn_cancel = (Button) findViewById(R.id.btn_dialog_alert_option_cancel);
        mContentLayout = (RelativeLayout) findViewById(R.id.rl_dialog_alert_option_content);
        mTv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickInfoListener != null) {
                    mOnClickInfoListener.onClickInfo();
                }
            }
        });
        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnClickCancelListener != null) {
                    mOnClickCancelListener.onClickCancel();
                }
            }
        });
        mBtn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnClickConfirmListener != null) {
                    mOnClickConfirmListener.onClickConfirm();
                }
            }
        });

    }

    public String getTitle() {
        return title;
    }

    public OptionAlertDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTv_title.setText(title);
        }
        this.title = title;
        return this;
    }

    public String getInfo() {
        return info.toString();
    }

    public OptionAlertDialog setInfo(CharSequence info) {
        if (!TextUtils.isEmpty(info)) {
            mTv_info.setText(info);
        }
        this.info = info;
        return this;
    }

    public String getCancelText() {
        return cancelText;
    }

    public OptionAlertDialog setCancelText(String cancelText) {
        if (!TextUtils.isEmpty(cancelText)) {
            mBtn_cancel.setText(cancelText);
        }
        this.cancelText = cancelText;
        return this;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public OptionAlertDialog setConfirmText(String confirmText) {
        if (!TextUtils.isEmpty(confirmText)) {
            mBtn_confirm.setText(confirmText);
        }
        this.confirmText = confirmText;
        return this;
    }

    public OptionAlertDialog setDialogContentView(View contentView) {
        this.contentView = contentView;
        if (contentView != null) {
            mTv_info.setVisibility(View.GONE);
            mContentLayout.addView(contentView);
        }
        return this;
    }

}
