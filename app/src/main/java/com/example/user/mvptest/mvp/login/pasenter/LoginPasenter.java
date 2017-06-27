package com.example.user.mvptest.mvp.login.pasenter;

/**
 * 项目名称：MVPTEST
 * 类描述：LoginPasenter 描述:中间层的接口回调
 * 创建人：songlijie
 * 创建时间：2017/6/12 10:38
 * 邮箱:814326663@qq.com
 */
public interface LoginPasenter {
    void InputUserInfo(String userName,String password);
    void onDestory();
}
