package com.example.user.mvptest.mvpbase.login.contract;

import com.example.user.mvptest.mvpbase.base.BasePasenter;
import com.example.user.mvptest.mvpbase.base.BaseView;

/**
 * 项目名称：MVPTEST
 * 类描述：LoginContract 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 16:39
 * 邮箱:814326663@qq.com
 */
public interface LoginContract {
    interface View extends BaseView<LoginContract.Prestener>{
        String getUserName();
        String getPassword();
        void showLoginProgress();
        void hideLoginProgress();
        void setUserNameError();
        void setUserNameNotAllow();
        void setPassowrdError();
        void setPassworNotAllow();
        void loginSuccess(String result);
        void loginFailed(String errorMsg);
    }

    interface Prestener extends BasePasenter{
        void login(String userName,String password);
    }
}
