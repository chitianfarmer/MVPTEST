package com.example.user.mvptest.register.model;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.config.Configs;
import com.example.user.mvptest.http.HttpUtils;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.StringCallback;
import com.example.user.mvptest.register.pasenter.RegisterInterator;
import com.example.user.utils.sys.Validator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import okhttp3.Call;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterInteractorImpl 描述:网络请求实现类
 * 创建人：songlijie
 * 创建时间：2017/6/12 11:28
 * 邮箱:814326663@qq.com
 */
public class RegisterInteractorImpl implements RegisterInterator {
    /**
     * 注册
     * @param mobile
     * @param firstPassword
     * @param secondPassword
     * @param SMSCode
     * @param listener
     */
    @Override
    public void register(final String mobile, final String firstPassword, final String secondPassword, final String SMSCode, final onRegisterFinishedListener listener) {
                if (TextUtils.isEmpty(mobile)){
                    listener.setMobileError();
                    return;
                }
                if (!Validator.isMobile(mobile)){
                    listener.setMobileNotAllow();
                    return;
                }
                if (TextUtils.isEmpty(firstPassword)){
                    listener.setFirstPasswordError();
                    return;
                }
                if (!Validator.isPassword(firstPassword)){
                    listener.setFirstPasswordNotAllow();
                    return;
                }
                if (TextUtils.isEmpty(secondPassword)){
                    listener.setSecondPasswordError();
                    return;
                }
                if (!Validator.isPassword(secondPassword)){
                    listener.setSecondPasswordNotAllow();
                    return;
                }
                if (!firstPassword.equals(secondPassword)){
                    listener.passwordNotEquals();
                    return;
                }
                if (TextUtils.isEmpty(SMSCode)){
                    listener.setSMSCodeError();
                    return;
                }
        HttpUtils.registerByPhone(Configs.URL_REGISTER, mobile, mobile, firstPassword, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.registerFailed(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                int code = jsonObject.getIntValue("code");
                switch (code){
                    case 1:
                        JSONObject user = jsonObject.getJSONObject("user");
                        String tel = user.getString("tel");
                        listener.registerSuccess(tel);
                        break;
                    case -1:
                        listener.registerFailed("该手机号已被注册");
                        break;
                    case -2:
                        listener.registerFailed("手机号码格式不正确");
                        break;
                    default:
                        listener.registerFailed("服务器繁忙请重试");
                        break;
                }
            }
        });
    }

    /**
     * 发送验证码
     * @param mobile
     * @param listener
     */
    @Override
    public void sendSmsCode(final String mobile, final onRegisterFinishedListener listener) {
               if (TextUtils.isEmpty(mobile)){
                   listener.setMobileError();
                   return;
               }
               if (!Validator.isMobile(mobile)){
                   listener.setMobileNotAllow();
                   return;
               }
        HttpUtils.sendCode(mobile, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.sendFailed(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
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
                    listener.sendSuccess(recMsg);
                }else{
                    listener.sendFailed(recMsg);
                }
            }
        });
    }
}
