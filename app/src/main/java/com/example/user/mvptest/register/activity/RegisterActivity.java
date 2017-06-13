package com.example.user.mvptest.register.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.ActivityUtils;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;
import com.example.user.mvptest.register.model.RegisterPastenerImpl;
import com.example.user.mvptest.register.pasenter.RegisterPasenter;
import com.example.user.mvptest.register.view.RegisterView;
import com.example.user.utils.sys.SetTelCountTimer;
import com.example.user.utils.util.LogUtils;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterActivity 描述:注册页面
 * 创建人：songlijie
 * 创建时间：2017/6/12 11:45
 * 邮箱:814326663@qq.com
 */
public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {
    private EditText mobile, firstPwd, secondPwd, smsCode;
    private Button registerButton, smsCodeButton;
    private RegisterPasenter pasenter;
    private ProgressDialog dialog;
    private SetTelCountTimer setTelCountTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.add(this);
        setContentView(R.layout.activity_register);
        getData();
        initView();
        initData();
        setListener();
    }

    private void getData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.getting_smscode));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.register);
    }

    private void setListener() {
        registerButton.setOnClickListener(this);
        smsCodeButton.setOnClickListener(this);
    }

    private void initData() {
        pasenter = new RegisterPastenerImpl(this);//中间层pastener数据绑定view
        setTelCountTimer = new SetTelCountTimer(smsCodeButton);
    }

    private void initView() {
        mobile = (EditText) findViewById(R.id.username);
        firstPwd = (EditText) findViewById(R.id.password);
        secondPwd = (EditText) findViewById(R.id.password_config);
        smsCode = (EditText) findViewById(R.id.smscode);
        registerButton = (Button) findViewById(R.id.btn_register);
        smsCodeButton = (Button) findViewById(R.id.btn_smsCode);
    }

    @Override
    public void onClick(View v) {
        String Moblie = mobile.getText().toString().trim();
        String firstPassword = firstPwd.getText().toString().trim();
        String secondPassword = secondPwd.getText().toString().trim();
        String SmsCode = smsCode.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_smsCode:
                setTelCountTimer.start();
                pasenter.sendSmsCode(Moblie);
                break;
            case R.id.btn_register:
                pasenter.register(Moblie, firstPassword, secondPassword, SmsCode);
                break;
        }
    }

    @Override
    public void showRegisterProgress() {
        if (dialog != null) {
            dialog.setMessage(getString(R.string.registering));
            dialog.show();
        }
    }

    @Override
    public void hideRegisterProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void setMobileError() {
        mobile.setError(getString(R.string.username_error));
        if (setTelCountTimer!=null){
            setTelCountTimer.onFinish();
        }
    }

    @Override
    public void setMobileNotAllow() {
        mobile.setError(getString(R.string.username_incorrect));
        if (setTelCountTimer!=null){
            setTelCountTimer.onFinish();
        }
    }

    @Override
    public void setFirstPasswordError() {
        firstPwd.setError(getString(R.string.password_error));
    }

    @Override
    public void setFirstPasswordNotAllow() {
        firstPwd.setError(getString(R.string.password_incorrect));
    }

    @Override
    public void setSecondPasswordError() {
        secondPwd.setError(getString(R.string.password_error));
    }

    @Override
    public void setSecondPasswordNotAllow() {
        secondPwd.setError(getString(R.string.password_incorrect));
    }

    @Override
    public void passwordNotEquals() {
        toast(getString(R.string.password_not_equals));
        firstPwd.setError(getString(R.string.password_not_equals));
        secondPwd.setError(getString(R.string.password_not_equals));
    }

    @Override
    public void setSMSCodeError() {
        smsCode.setError(getString(R.string.sms_input));
    }

    @Override
    public void showSmsCodeProgress() {
        if (dialog != null) {
            dialog.show();
        }
    }

    @Override
    public void hideSmsCodeProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void sendSuccess(String result) {
        LogUtils.d("发送验证码成功:"+result);
        toast( getString(R.string.smscode_send_success));
    }

    @Override
    public void sendFailed(String errorMsg) {
        LogUtils.d("发送验证码失败:"+errorMsg);
        setTelCountTimer.onFinish();
        toast( getString(R.string.smscode_send_failed));
    }

    @Override
    public void registerSuccess(String result) {
        LogUtils.d("注册成功:"+result);
        toast( getString(R.string.register_success));
        setResult(RESULT_OK, new Intent().putExtra("mobile", result));
        finish();
    }

    @Override
    public void registerFailed(String errorMsg) {
        LogUtils.d("注册失败:"+errorMsg);
        toast(getString(R.string.register_failed)+","+errorMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pasenter.onDestory();
        ActivityUtils.remove(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
