package com.example.user.mvptest.mvpbase.register.contract;

import com.example.user.mvptest.mvpbase.base.BasePasenter;
import com.example.user.mvptest.mvpbase.base.BaseView;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterContract 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 16:55
 * 邮箱:814326663@qq.com
 */
public interface RegisterContract {

    interface View extends BaseView<Prestener>{
        String getMobile();
        String getSmsCode();
        String getPassword();
        String getConfirmPwd();
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

    interface Prestener extends BasePasenter{
        void register(String mobile,String firstPassword,String secondPassword,String SMSCode);
        void sendSmsCode(String mobile);
    }
}
