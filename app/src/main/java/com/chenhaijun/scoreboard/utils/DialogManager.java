package com.chenhaijun.scoreboard.utils;

import android.content.Context;

import com.chenhaijun.scoreboard.utils.dialog.OptionAlertDialog;

/**
 * Created by Haijun Chen on 2017/7/26.
 */

public class DialogManager {

    public static OptionAlertDialog createOptionAlertDialog(Context context, String title, CharSequence info, String cancelText, String confirmText) {
        OptionAlertDialog dialog = new OptionAlertDialog(context)
                .setTitle(title)
                .setInfo(info)
                .setCancelText(cancelText)
                .setConfirmText(confirmText);
        return dialog;
    }

    public static OptionAlertDialog createOptionAlertDialog(Context context, String title, CharSequence info) {
        return createOptionAlertDialog(context, title, info, null, null);
    }

}
