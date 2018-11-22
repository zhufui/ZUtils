package com.zf.zutils.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zf.library.KeyboardUtil;
import com.zf.zutils.R;
import com.zf.zutils.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 操作软键盘界面
 */
public class KeyboardAct extends BaseActivity {
    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.btPwd)
    Button btPwd;

    @Override
    protected int getLayout() {
        return R.layout.act_keyboard;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.bt, R.id.bt1, R.id.btPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //显示软键盘
            case R.id.bt:
                KeyboardUtil.showSoftInput(mContext, et);
                break;
            //隐藏软键盘
            case R.id.bt1:
                KeyboardUtil.hideSoftInput(mContext, et);
                break;
            //显示/隐藏密码
            case R.id.btPwd:
                TransformationMethod transformationMethod = etPwd.getTransformationMethod();
                if (transformationMethod instanceof HideReturnsTransformationMethod) {
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    setSelection();
                } else if (transformationMethod instanceof PasswordTransformationMethod) {
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    setSelection();
                }
                break;
        }
    }

    private void setSelection() {
        String text = etPwd.getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        etPwd.setSelection(text.length());
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, KeyboardAct.class);
        context.startActivity(intent);
    }

}
