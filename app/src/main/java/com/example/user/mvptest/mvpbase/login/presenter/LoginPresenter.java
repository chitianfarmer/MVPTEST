package com.example.user.mvptest.mvpbase.login.presenter;


import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.config.Configs;
import com.example.user.mvptest.http.HttpUtils;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.StringCallback;
import com.example.user.mvptest.mvpbase.login.contract.LoginContract;
import com.example.user.utils.sys.Validator;

import okhttp3.Call;

/**
 * 项目名称：MVPTEST
 * 类描述：LoginPresenter 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 16:43
 * 邮箱:814326663@qq.com
 */
public class LoginPresenter implements LoginContract.Prestener{
    private LoginContract.View loginView;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        this.loginView.setPresenter(this);
    }

    @Override
    public void login(final String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            loginView.setUserNameError();
            return;
        }
        if (!Validator.isMobile(userName)) {
            loginView.setUserNameNotAllow();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loginView.setPassowrdError();
            return;
        }
        if (!Validator.isPassword(password)) {
            loginView.setPassworNotAllow();
            return;
        }
        loginView.showLoginProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginView.hideLoginProgress();
                loginView.loginSuccess(userName);
            }
        },3000);

//
//
//        HttpUtils.login(Configs.URL_LOGIN, userName, password, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                loginView.hideLoginProgress();
//                loginView.loginFailed(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                loginView.hideLoginProgress();
//                JSONObject jsonObject = JSONObject.parseObject(response);
//                int code = jsonObject.getIntValue("code");
//                switch (code){
//                    case 1:
//                        JSONObject user = jsonObject.getJSONObject("user");
//                        loginView.loginSuccess(user.toJSONString());
//                        break;
//                    default:
//                        loginView.loginFailed("登录失败");
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestory() {
        loginView = null;
    }
}
