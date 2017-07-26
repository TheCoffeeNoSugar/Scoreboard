package com.chenhaijun.scoreboard.view;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Haijun Chen on 2017/7/26.
 */

public abstract class MyTextWatcher implements TextWatcher {

    private Activity        mActivity;
    private List<EditText>  mEditTextList;
    private List<Boolean>   emptyList = new ArrayList<>();

    public MyTextWatcher(Activity activity, EditText... editTexts) {
        this(activity, Arrays.asList(editTexts));
    }

    public MyTextWatcher(Activity activity, List<EditText> editTextList) {
        mActivity = activity;
        mEditTextList = editTextList;

        if (isEditTextListValid()) {
            for (EditText edittext : mEditTextList) {
                if (edittext != null) {
                    edittext.addTextChangedListener(this);
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkEditTextsInput();
    }

    private boolean isEditTextListValid() {
        return mEditTextList != null && !mEditTextList.isEmpty();
    }

    public void checkEditTextsInput() {
        emptyList.clear();
        if (isEditTextListValid()) {
            for (EditText editText : mEditTextList) {
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    emptyList.add(Boolean.TRUE);
                }
            }
        }
        onInputStatusChanged(isAllInput());
    }

    public boolean isAllInput() {
        for (Boolean empty : emptyList) {
            if (empty) {
                return false;
            }
        }
        return true;
    }

    public abstract void onInputStatusChanged(boolean isAllInput);

    public void onDestroy() {
        if (isEditTextListValid()) {
            for (EditText edittext : mEditTextList) {
                if (edittext != null) {
                    edittext.removeTextChangedListener(this);
                }
            }
        }
    }
}
