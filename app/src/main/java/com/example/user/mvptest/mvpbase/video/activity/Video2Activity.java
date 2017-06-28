package com.example.user.mvptest.mvpbase.video.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.ActivityUtils;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;
import com.example.user.mvptest.mvpbase.video.contract.VideoContract;
import com.example.user.mvptest.mvpbase.video.prestener.VideoPerstener;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 项目名称：MVPTEST
 * 类描述：Video2Activity 描述:视频播放界面
 * 创建人：songlijie
 * 创建时间：2017/6/28 10:53
 * 邮箱:814326663@qq.com
 */
public class Video2Activity extends BaseActivity implements VideoContract.View {
    private JCVideoPlayerStandard videoplayer;
    private String videoString;
    private LinearLayout ll_back;
    private VideoContract.Prestener prestener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.add(this);
        setContentView(R.layout.activity_video_play);
        getData();
        initView();
        initData();
        setListener();
    }

    private void getData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoString = getIntent().getStringExtra("result");
    }

    private void initView() {
        videoplayer = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
    }

    private void initData() {
        prestener = new VideoPerstener(this);
        prestener.play(videoplayer, getPlayUrl(), getVideoTitle(), getVideoImage());
    }

    private void setListener() {
        videoplayer.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Video2Activity.this.finish();
            }
        });
        videoplayer.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Video2Activity.this.finish();
            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Video2Activity.this.finish();
            }
        });
    }


    @Override
    public void showToas(String message) {
        toast(message);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public JSONObject getUrlJson() {
        return JSONObject.parseObject(videoString);
    }

    @Override
    public String getPlayUrl() {
        return getUrlJson().getString("url");
    }

    @Override
    public String getVideoTitle() {
        return getUrlJson().getString("title");
    }

    @Override
    public String getVideoImage() {
        return getUrlJson().getString("img");
    }

    @Override
    public void setPresenter(VideoContract.Prestener presenter) {
        this.prestener = presenter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        prestener.release(videoplayer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoString = null;
        prestener.onDestory();
        ActivityUtils.remove(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
