package com.example.user.mvptest.mvpbase.login.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.mvptest.R;
import com.example.user.mvptest.config.ActivityUtils;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;
import com.example.user.mvptest.mvpbase.login.contract.LoginContract;
import com.example.user.mvptest.mvpbase.login.presenter.LoginPresenter;
import com.example.user.mvptest.mvpbase.main.activity.Main2Activity;
import com.example.user.mvptest.mvpbase.register.activity.Register2Activity;
import com.example.user.utils.util.LogUtils;

/**
 * 项目名称：MVPTEST
 * 类描述：Login2Activity 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 16:49
 * 邮箱:814326663@qq.com
 */
public class Login2Activity extends BaseActivity implements LoginContract.View ,View.OnClickListener{
    private ProgressDialog progressBar;
    private EditText username;
    private EditText password;
    private LoginContract.Prestener presenter;
    private Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.add(this);
        setContentView(R.layout.activity_login);
        getData();
        initView();
        initData();
        setListener();
    }

    private void getData() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.log_in);
        progressBar = new ProgressDialog(this);
        progressBar.setMessage(getString(R.string.log_in));
    }

    private void setListener() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    private void initData() {
        presenter = new LoginPresenter(this);
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
    }


    @Override
    public String getUserName() {
        return username.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public void showLoginProgress() {
        if (progressBar!=null){
            progressBar.show();
        }
    }

    @Override
    public void hideLoginProgress() {
        if (progressBar!=null){
            progressBar.dismiss();
        }
    }

    @Override
    public void setUserNameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setUserNameNotAllow() {
        username.setError(getString(R.string.username_incorrect));
    }

    @Override
    public void setPassowrdError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void setPassworNotAllow() {
        password.setError(getString(R.string.password_incorrect));
    }

    @Override
    public void loginSuccess(String result) {
        LogUtils.d("登陆成功:"+result);
        toast(getString(R.string.login_success));
        startActivity(new Intent(this, Main2Activity.class));
        finish();
    }

    @Override
    public void loginFailed(String errorMsg) {
        LogUtils.d("登录失败:"+errorMsg);
        toast(getString(R.string.login_failed));
    }

    @Override
    public void setPresenter(LoginContract.Prestener presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                presenter.login(getUserName(), getPassword());
                break;
            case R.id.button2:
                startActivityForResult(new Intent(Login2Activity.this, Register2Activity.class), 200);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
        ActivityUtils.remove(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    String mobile = data.getStringExtra("mobile");
                    if (!TextUtils.isEmpty(mobile)) {
                        username.setText(mobile);
                        username.setSelection(username.getText().length());
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
