package com.example.user.mvptest.mvpbase.main.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.ActivityUtils;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;
import com.example.user.mvptest.mvp.main.adapter.MainAdapter;
import com.example.user.mvptest.mvpbase.main.contract.MainContract;
import com.example.user.mvptest.mvpbase.main.presenter.MainPresenter;
import com.example.user.mvptest.mvp.main.view.SpacesItemDecoration;
import com.example.user.mvptest.mvpbase.pay.activity.PayActivity;
import com.example.user.mvptest.mvpbase.video.activity.Video2Activity;
import com.example.user.utils.weight.swipyrefresh.SwipyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MVPTEST
 * 类描述：Main2Activity 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 15:38
 * 邮箱:814326663@qq.com
 */
public class Main2Activity extends BaseActivity implements MainContract.View , SwipyRefreshLayout.OnRefreshListener,MainAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private SwipyRefreshLayout srfl_refresh;
    private ProgressDialog dialog;
    private MainAdapter adapter;
    private List<JSONObject> objectList = new ArrayList<>();
    private MainContract.Pasenter pasenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.add(this);
        setContentView(R.layout.activity_main);
        getData();
        initView();
        initData();
        setListener();
    }

    private void getData() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("主页");
        actionBar.setDisplayShowCustomEnabled(true);
        pasenter = new MainPresenter(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据加载中...");
    }

    private void initView() {
        srfl_refresh = (SwipyRefreshLayout) findViewById(R.id.srfl_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initData() {
        //设置layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        onRefresh(1);//第一次进来请求一次
        adapter = new MainAdapter(this,objectList);
        recyclerView.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(12);
        recyclerView.addItemDecoration(decoration);
    }

    private void setListener() {
        //监听
        srfl_refresh.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void showLoadingDialog() {
        //显示
        if (dialog!=null){
            dialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        //显示
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void onRequestError(String errorMsg) {
        //请求失败
        toast(errorMsg);
        srfl_refresh.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestSuccess(List<JSONObject> list) {
        //请求成功
        objectList.addAll(list);
        srfl_refresh.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemOnClickResult(String reuslt) {
        //获取到直播信息 然后跳转
        startActivity(new Intent(Main2Activity.this,Video2Activity.class).putExtra("result",reuslt));
    }

    @Override
    public void setPresenter(MainContract.Pasenter presenter) {
       this.pasenter = presenter;
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void onRefresh(int i) {
        //刷新
        objectList.clear();
        pasenter.requestData(i,20);
    }

    @Override
    public void onLoad(int i) {
        //加载更多
        i++;
        pasenter.requestData(i,20);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.item_search){
            startActivity(new Intent(Main2Activity.this,PayActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(View view, JSONObject jsonObject) {
        pasenter.onItemClick(jsonObject);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pasenter.onDestory();
        ActivityUtils.remove(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
