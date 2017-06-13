package com.example.user.mvptest.register.model;

import com.example.user.mvptest.register.pasenter.RegisterInterator;
import com.example.user.mvptest.register.pasenter.RegisterPasenter;
import com.example.user.mvptest.register.view.RegisterView;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterPastenerImpl 描述:中间层数据接口实现model与view的绑定
 * 创建人：songlijie
 * 创建时间：2017/6/12 11:40
 * 邮箱:814326663@qq.com
 */
public class RegisterPastenerImpl implements RegisterPasenter,RegisterInterator.onRegisterFinishedListener {
    private RegisterView registerView;
    private RegisterInterator interator;

    public RegisterPastenerImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.interator = new RegisterInteractorImpl();//网络请求回调
    }

    @Override
    public void setMobileError() {
        if (registerView!=null){
            registerView.setMobileError();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void setMobileNotAllow() {
        if (registerView!=null){
            registerView.setMobileNotAllow();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void setFirstPasswordError() {
        if (registerView!=null){
            registerView.setFirstPasswordError();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void setFirstPasswordNotAllow() {
        if (registerView!=null){
            registerView.setFirstPasswordNotAllow();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void setSecondPasswordError() {
        if (registerView!=null){
            registerView.setSecondPasswordError();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void passwordNotEquals() {
        if (registerView!=null){
            registerView.passwordNotEquals();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void setSecondPasswordNotAllow() {
        if (registerView!=null){
            registerView.setSecondPasswordNotAllow();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void setSMSCodeError() {
        if (registerView!=null){
            registerView.setSMSCodeError();
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void sendSuccess(String reuslt) {
        if (registerView!=null){
            registerView.sendSuccess(reuslt);
            registerView.hideSmsCodeProgress();
        }
    }

    @Override
    public void sendFailed(String errorMsg) {
        if (registerView!=null){
            registerView.sendFailed(errorMsg);
            registerView.hideSmsCodeProgress();
        }
    }

    @Override
    public void registerSuccess(String moblie) {
        if (registerView!=null){
            registerView.registerSuccess(moblie);
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void registerFailed(String errorMsg) {
        if (registerView!=null){
            registerView.registerFailed(errorMsg);
            registerView.hideRegisterProgress();
        }
    }

    @Override
    public void register(String mobile, String firstPassword, String secondPassword, String SMSCode) {
        registerView.showRegisterProgress();
        interator.register(mobile,firstPassword,secondPassword,SMSCode,this);
    }

    @Override
    public void sendSmsCode(String mobile) {
        registerView.showSmsCodeProgress();
        interator.sendSmsCode(mobile,this);
    }

    @Override
    public void onDestory() {
        registerView = null;
    }
}
