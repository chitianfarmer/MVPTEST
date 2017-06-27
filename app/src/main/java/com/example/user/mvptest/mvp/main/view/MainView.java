package com.example.user.mvptest.mvp.main.view;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 项目名称：MVPTEST
 * 类描述：MainView 描述:主页view回调显示接口
 * 创建人：songlijie
 * 创建时间：2017/6/13 14:32
 * 邮箱:814326663@qq.com
 */
public interface MainView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void onRequestError(String errorMsg);
    void onRequestSuccess(List<JSONObject> list);
    void onItemOnClickResult(String reuslt);
}
