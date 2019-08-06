package com.mb.smart.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.smart.R;
import com.mb.smart.utils.ProgressDialogHelper;
import com.mb.smart.utils.ProjectHelper;

/**
 * Created by cgy on 2018/4/19 0019.
 */

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{
    private EditText etTel;
    private EditText etCode;
    private EditText etPwd;
    private TextView tvSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_forget_pwd);
        int type = getIntent().getIntExtra("type",0);
        setTitle(type==0?"忘记密码":"修改密码");
        initView();
    }

    private void setTitle(String title) {
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ImageView imgLeft = findViewById(R.id.btn_left);
        imgLeft.setVisibility(View.VISIBLE);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
    }
    private void initView() {
        etTel = (EditText) findViewById(R.id.et_tel);
        etCode = (EditText) findViewById(R.id.et_code);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        tvSend = (TextView) findViewById(R.id.tv_send);
        tvSend.setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_send){
            if (TextUtils.isEmpty(etTel.getText().toString().trim()) ||
                    !ProjectHelper.isMobiPhoneNum(etTel.getText().toString().trim())) {
                showToast(getString(R.string.input_correct_tel));
            }else {
                new TimeCount(60000, 1000).start();
                getValidCode();
            }
        }else if (id == R.id.tv_confirm){
            doModify();
        }
    }

    private void doModify() {
        String mobile = etTel.getText().toString().trim();
        final String password = etPwd.getText().toString().trim();
        final String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            showToast(getString(R.string.input_correct_tel));
            return;
        }else if (TextUtils.isEmpty(code)){
            showToast(getString(R.string.input_valid_code));
            return;
        }else if (TextUtils.isEmpty(password)){
            showToast(getString(R.string.input_password));
            return;
        }else if (!ProjectHelper.isMobiPhoneNum(mobile)) {
            showToast(getString(R.string.tel_error));
            return;
        }else if (!ProjectHelper.isPwdValid(password)) {
            showToast(getString(R.string.password_error));
            return;
        }
//        AVUser.resetPasswordBySmsCodeInBackground(code, password, new UpdatePasswordCallback() {
//            @Override
//            public void done(AVException e) {
//                ProgressDialogHelper.dismissProgressDialog();
//                if (e == null) {
//                    showToast(getString(R.string.modify_success));
//                    finish();
//                } else {
//                    ProjectHelper.showErrorMessage(e.getMessage());
//                }
//            }
//        });
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tvSend.setText(R.string.get_valid_code);
            tvSend.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tvSend.setClickable(false);
            tvSend.setText(millisUntilFinished / 1000 + getString(R.string.reget_after_seconds));
        }
    }

    public void getValidCode() {
        String mobile = etTel.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            showToast(getString(R.string.input_correct_tel));
            return;
        }
        if (!ProjectHelper.isMobiPhoneNum(mobile)) {
            showToast(getString(R.string.tel_error));
            return;
        }
        ProgressDialogHelper.showProgressDialog(this,"发送中...");
//        AVUser.requestPasswordResetBySmsCodeInBackground(mobile, new RequestMobileCodeCallback() {
//            @Override
//            public void done(AVException e) {
//                ProgressDialogHelper.dismissProgressDialog();
//                if (e == null) {
//                    showToast(getString(R.string.send_success));
//                } else {
//                    ProjectHelper.showErrorMessage(e.getMessage());
//                }
//            }
//        });
    }
}
