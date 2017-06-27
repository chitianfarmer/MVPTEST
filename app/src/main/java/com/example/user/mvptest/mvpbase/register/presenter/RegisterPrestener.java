package com.example.user.mvptest.mvpbase.register.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.config.Configs;
import com.example.user.mvptest.http.HttpUtils;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.StringCallback;
import com.example.user.mvptest.mvpbase.register.contract.RegisterContract;
import com.example.user.utils.sys.Validator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import okhttp3.Call;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterPrestener 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 16:59
 * 邮箱:814326663@qq.com
 */
public class RegisterPrestener implements RegisterContract.Prestener {
    private RegisterContract.View registerView;

    public RegisterPrestener(RegisterContract.View registerView) {
        this.registerView = registerView;
        this.registerView.setPresenter(this);
    }

    @Override
    public void register(final String mobile, String firstPassword, String secondPassword, String SMSCode) {
        if (TextUtils.isEmpty(mobile)){
            registerView.setMobileError();
            return;
        }
        if (!Validator.isMobile(mobile)){
            registerView.setMobileNotAllow();
            return;
        }
        if (TextUtils.isEmpty(firstPassword)){
            registerView.setFirstPasswordError();
            return;
        }
        if (!Validator.isPassword(firstPassword)){
            registerView.setFirstPasswordNotAllow();
            return;
        }
        if (TextUtils.isEmpty(secondPassword)){
            registerView.setSecondPasswordError();
            return;
        }
        if (!Validator.isPassword(secondPassword)){
            registerView.setSecondPasswordNotAllow();
            return;
        }
        if (!firstPassword.equals(secondPassword)){
            registerView.passwordNotEquals();
            return;
        }
        if (TextUtils.isEmpty(SMSCode)){
            registerView.setSMSCodeError();
            return;
        }
        registerView.showRegisterProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registerView.hideRegisterProgress();
                registerView.registerSuccess(mobile);
            }
        },3000);
//        HttpUtils.registerByPhone(Configs.URL_REGISTER, mobile, mobile, firstPassword, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                registerView.registerFailed(e.getMessage());
//                registerView.hideRegisterProgress();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                registerView.hideRegisterProgress();
//                JSONObject jsonObject = JSONObject.parseObject(response);
//                int code = jsonObject.getIntValue("code");
//                switch (code){
//                    case 1:
//                        JSONObject user = jsonObject.getJSONObject("user");
//                        String tel = user.getString("tel");
//                        registerView.registerSuccess(tel);
//                        break;
//                    case -1:
//                        registerView.registerFailed("该手机号已被注册");
//                        break;
//                    case -2:
//                        registerView.registerFailed("手机号码格式不正确");
//                        break;
//                    default:
//                        registerView.registerFailed("服务器繁忙请重试");
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void sendSmsCode(String mobile) {
        if (TextUtils.isEmpty(mobile)){
            registerView.setMobileError();
            return;
        }
        if (!Validator.isMobile(mobile)){
            registerView.setMobileNotAllow();
            return;
        }
        registerView.showSmsCodeProgress();
        HttpUtils.sendCode(mobile, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                registerView.sendFailed(e.getMessage());
                registerView.hideSmsCodeProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                registerView.hideSmsCodeProgress();
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(response);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Element root = doc.getRootElement();
                String recCode = root.elementText("code");
                String recMsg = root.elementText("msg");
                if (recCode.equals("2")){
                    registerView.sendSuccess(recMsg);
                }else{
                    registerView.sendFailed(recMsg);
                }
            }
        });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestory() {
        registerView = null;
    }
}
