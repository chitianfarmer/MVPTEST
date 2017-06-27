package com.example.user.mvptest.mvp.login.pasenter;

/**
 * 项目名称：MVPTEST
 * 类描述：LoginInteractor 描述:网络请求回调接口
 * 创建人：songlijie
 * 创建时间：2017/6/12 10:05
 * 邮箱:814326663@qq.com
 */
public interface LoginInteractor {
    interface onLoginFinishedListener{
        void onUserNameError();
        void onUserNameNotAllow();
        void onPasswordError();
        void onPasswordNotAllow();
        void onSuccess(String response);
        void onFailed(String message);
    }
    void login(String userName,String passWord,onLoginFinishedListener listener);
}
