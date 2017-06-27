package com.example.user.mvptest.mvp.register.view;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterView 描述:注册的页面view实现展示接口回调接口
 * 创建人：songlijie
 * 创建时间：2017/6/12 11:20
 * 邮箱:814326663@qq.com
 */
public interface RegisterView {
    void showRegisterProgress();

    void hideRegisterProgress();

    void setMobileError();

    void setMobileNotAllow();

    void setFirstPasswordError();

    void setFirstPasswordNotAllow();

    void setSecondPasswordError();

    void setSecondPasswordNotAllow();

    void passwordNotEquals();

    void setSMSCodeError();

    void showSmsCodeProgress();

    void hideSmsCodeProgress();

    void sendSuccess(String result);

    void sendFailed(String errorMsg);

    void registerSuccess(String result);
    void registerFailed(String errorMsg);
}
