package com.example.user.mvptest.mvp.main.pasenter;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 项目名称：MVPTEST
 * 类描述：MainInterator 描述:网络请求实现类接口回调
 * 创建人：songlijie
 * 创建时间：2017/6/13 14:39
 * 邮箱:814326663@qq.com
 */
public interface MainInterator {
    interface onMainLoadDataFinishedListener{
        void showLoadingDialog();
        void hideLoadingDialog();
        void onRequestError(String errorMsg);
        void onRequestSuccess(List<JSONObject> list);
        void onItemClickResult(String url);
    }
    void requestData(int page,int pageSize,onMainLoadDataFinishedListener listener);
    void onItemClick(JSONObject object,onMainLoadDataFinishedListener listener);
}
