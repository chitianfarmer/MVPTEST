package com.example.user.mvptest.main.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.ActivityUtils;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;
import com.example.user.mvptest.main.adapter.MainAdapter;
import com.example.user.mvptest.main.view.SpacesItemDecoration;
import com.example.user.mvptest.main.model.MainPasenterImpl;
import com.example.user.mvptest.main.pasenter.MainPasenter;
import com.example.user.mvptest.main.view.MainView;
import com.example.user.utils.weight.swipyrefresh.SwipyRefreshLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页:
 */
public class MainActivity extends BaseActivity implements MainView, SwipyRefreshLayout.OnRefreshListener,MainAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private SwipyRefreshLayout srfl_refresh;
    private ProgressDialog dialog;
    private MainPasenter pasenter;
    private MainAdapter adapter;
    private List<JSONObject> objectList = new ArrayList<>();

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
        pasenter = new MainPasenterImpl(this);
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
    public void showLoadingDialog() {
        //显示
        if (dialog!=null){
            dialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        //数据dialog 隐藏
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
        startActivity(new Intent(MainActivity.this,VideoPlayActivity.class).putExtra("result",reuslt));
    }

    @Override
    public void OnItemClick(View view, JSONObject object) {
        //recycleview的点击事件
        pasenter.onItemClick(object);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pasenter.onDestory();
        ActivityUtils.remove(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
