package com.example.user.mvptest.mvp.register.pasenter;

/**
 * 项目名称：MVPTEST
 * 类描述：RegisterPasenter 描述:注册中间层model与view绑定回调接口
 * 创建人：songlijie
 * 创建时间：2017/6/12 11:36
 * 邮箱:814326663@qq.com
 */
public interface RegisterPasenter {
    void register(String mobile,String firstPassword,String secondPassword,String SMSCode);
    void sendSmsCode(String mobile);
    void onDestory();
}
