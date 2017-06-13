package com.example.user.mvptest.register.pasenter;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterInterator 描述:网络请求回调接口
 * 创建人：songlijie
 * 创建时间：2017/6/12 11:25
 * 邮箱:814326663@qq.com
 */
public interface RegisterInterator {
    interface onRegisterFinishedListener{
        void setMobileError();
        void setMobileNotAllow();
        void setFirstPasswordError();
        void setFirstPasswordNotAllow();
        void setSecondPasswordError();
        void setSecondPasswordNotAllow();
        void passwordNotEquals();
        void setSMSCodeError();
        void sendSuccess(String reuslt);
        void sendFailed(String errorMsg);
        void registerSuccess(String result);
        void registerFailed(String errorMsg);
    }
    void register(String mobile,String firstPassword,String secondPassword,String SMSCode,onRegisterFinishedListener listener);
    void sendSmsCode(String mobile,onRegisterFinishedListener listener);
}
