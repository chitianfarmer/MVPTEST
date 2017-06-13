package com.example.user.mvptest.login.model;

import com.example.user.mvptest.login.pasenter.LoginInteractor;
import com.example.user.mvptest.login.pasenter.LoginPasenter;
import com.example.user.mvptest.login.view.LoginView;

/**
 * 项目名称：MVPTEST
 * 类描述：LoginPasenterImplement 描述:登录中间层实现
 * 创建人：songlijie
 * 创建时间：2017/6/12 10:42
 * 邮箱:814326663@qq.com
 */
public class LoginPasenterImplement implements LoginPasenter, LoginInteractor.onLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPasenterImplement(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImplement();//网络请求实现类
    }

    @Override
    public void InputUserInfo(String userName, String password) {
        loginView.showLoginProgress();
        loginInteractor.login(userName, password, this);
    }

    @Override
    public void onDestory() {
        loginView = null;
    }

    @Override
    public void onUserNameError() {
        if (loginView!=null){
            loginView.setUserNameError();
            loginView.hideLoginProgress();
        }
    }

    @Override
    public void onUserNameNotAllow() {
        if (loginView!=null){
            loginView.setUserNameNotAllow();
            loginView.hideLoginProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView!=null){
            loginView.setPassowrdError();
            loginView.hideLoginProgress();
        }
    }

    @Override
    public void onPasswordNotAllow() {
        if (loginView!=null){
            loginView.setPassworNotAllow();
            loginView.hideLoginProgress();
        }
    }

    @Override
    public void onSuccess(String response) {
            if (loginView!=null){
                loginView.loginSuccess(response);
                loginView.hideLoginProgress();
            }
    }

    @Override
    public void onFailed(String message) {
        if (loginView!=null){
            if (loginView!=null){
                loginView.loginFailed(message);
            }
            loginView.hideLoginProgress();
        }
    }
}
