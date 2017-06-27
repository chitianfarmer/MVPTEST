package com.example.user.mvptest.mvp.main.model;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.mvp.main.pasenter.MainInterator;
import com.example.user.mvptest.mvp.main.pasenter.MainPasenter;
import com.example.user.mvptest.mvp.main.view.MainView;

import java.util.List;

/**
 * 项目名称：MVPTEST
 * 类描述：MainPasenterImpl 描述:主页数据中间层实现类把model与view绑定
 * 创建人：songlijie
 * 创建时间：2017/6/13 15:06
 * 邮箱:814326663@qq.com
 */
public class MainPasenterImpl implements MainPasenter,MainInterator.onMainLoadDataFinishedListener {
    private MainInterator interator;
    private MainView mainView;

    public MainPasenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.interator = new MainInteratorImpl();//网络请求实现
    }

    @Override
    public void showLoadingDialog() {
        if (mainView!=null){
            mainView.showLoadingDialog();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mainView!=null){
            mainView.hideLoadingDialog();
        }
    }

    @Override
    public void onRequestError(String errorMsg) {
        if (mainView!=null){
            mainView.onRequestError(errorMsg);
        }
    }

    @Override
    public void onRequestSuccess(List<JSONObject> list) {
        if (mainView!=null){
            mainView.onRequestSuccess(list);
        }
    }

    @Override
    public void onItemClickResult(String result) {
        if (mainView!=null){
            mainView.onItemOnClickResult(result);
        }
    }


    @Override
    public void requestData(int page, int pageSize) {
        if (mainView!=null){
            interator.requestData(page,pageSize,this);
        }
    }

    @Override
    public void onItemClick(JSONObject url) {
        interator.onItemClick(url,this);
    }


    @Override
    public void onDestory() {
        mainView =null;
    }
}
