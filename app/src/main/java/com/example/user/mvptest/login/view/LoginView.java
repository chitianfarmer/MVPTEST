package com.example.user.mvptest.login.view;

/**
 * 项目名称：MVPTEST
 * 类描述：LoginView 描述: 登录view
 * 创建人：songlijie
 * 创建时间：2017/6/12 10:00
 * 邮箱:814326663@qq.com
 */
public interface LoginView {

    void showLoginProgress();

    void hideLoginProgress();

    void setUserNameError();

    void setUserNameNotAllow();

    void setPassowrdError();

    void setPassworNotAllow();

    void loginSuccess(String result);
    void loginFailed(String errorMsg);
}
