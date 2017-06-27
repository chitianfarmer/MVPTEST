package com.example.user.mvptest.mvpbase.main.contract;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.mvpbase.base.BasePasenter;
import com.example.user.mvptest.mvpbase.base.BaseView;

import java.util.List;

/**
 * 项目名称：MVPTEST
 * 类描述：MainContract 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 15:30
 * 邮箱:814326663@qq.com
 */
public interface MainContract {
    interface View extends BaseView<Pasenter>{
        void showLoadingDialog();
        void hideLoadingDialog();
        void onRequestError(String errorMsg);
        void onRequestSuccess(List<JSONObject> list);
        void onItemOnClickResult(String reuslt);
    }
    interface Pasenter extends BasePasenter{
        void requestData(int page,int pageSize);
        void onItemClick(JSONObject url);
    }
}
