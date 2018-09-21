package com.horen.base.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by HOREN on 2017/12/19.
 */

public class EditTextUtils {

    private EditText[] editTexts;
    private EdittextInputLinstener edittextInputLinstener;

    /**
     * 监听多个Edittext输入
     *
     * @param editTexts1
     */
    public EditTextUtils addEdittexts(EditText... editTexts1) {
        EditText[] clone = editTexts1.clone();
        editTexts = clone;
        for (EditText editText : clone) {
            editText.addTextChangedListener(new NewTextWatcher());
        }
        return this;
    }

    public EditTextUtils addEdittextInputLinstener(EdittextInputLinstener edittextInputLinstener) {
        this.edittextInputLinstener = edittextInputLinstener;
        return this;
    }


    class NewTextWatcher implements TextWatcher {


        public NewTextWatcher() {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean isAllInput = true; // 默认全部输入了文字
            for (EditText editText : editTexts) { // 判断所有的edittext是否都输入了字
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    isAllInput = false; // 只要有一个edittext内容为空，表示没有全部输入内容
                    break; // 已有空的Edittext，跳出循环
                }
            }

            if (isAllInput) {
                edittextInputLinstener.onSuccess();
            } else {
                edittextInputLinstener.onError();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    }

    public interface EdittextInputLinstener {
        void onSuccess();

        void onError();
    }
}
