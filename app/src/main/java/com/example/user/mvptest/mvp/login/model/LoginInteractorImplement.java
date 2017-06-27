package com.example.user.mvptest.mvp.login.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.config.Configs;
import com.example.user.mvptest.http.HttpUtils;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.StringCallback;
import com.example.user.mvptest.mvp.login.pasenter.LoginInteractor;
import com.example.user.utils.sys.Validator;

import okhttp3.Call;

/**
 * 项目名称：MVPTEST
 * 类描述：LoginInteractorImplement 描述:网络请求实现类
 * 创建人：songlijie
 * 创建时间：2017/6/12 10:08
 * 邮箱:814326663@qq.com
 */
public class LoginInteractorImplement implements LoginInteractor {
    /**
     * 登录
     * @param userName
     * @param passWord
     * @param listener
     */
    @Override
    public void login(final String userName, final String passWord, final onLoginFinishedListener listener) {
                if (TextUtils.isEmpty(userName)) {
                    listener.onUserNameError();
                    return;
                }
                if (!Validator.isMobile(userName)) {
                    listener.onUserNameNotAllow();
                    return;
                }
                if (TextUtils.isEmpty(passWord)) {
                    listener.onPasswordError();
                    return;
                }
                if (!Validator.isPassword(passWord)) {
                    listener.onPasswordNotAllow();
                    return;
                }
                HttpUtils.login(Configs.URL_LOGIN, userName, passWord, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        int code = jsonObject.getIntValue("code");
                        switch (code){
                            case 1:
                                JSONObject user = jsonObject.getJSONObject("user");
                                listener.onSuccess(user.toJSONString());
                                break;
                            default:
                                listener.onFailed("登录失败");
                                break;
                        }
                    }
                });
        }
}
