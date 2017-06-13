package com.example.user.mvptest.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.ActivityUtils;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;
import com.example.user.utils.media.GildeTools.GlideUtils;
import com.example.user.utils.util.LogUtils;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 项目名称：MVPTEST
 * 类描述：VideoPlayActivity 描述: 视频播放界面
 * 创建人：songlijie
 * 创建时间：2017/6/13 17:19
 * 邮箱:814326663@qq.com
 */
public class VideoPlayActivity extends BaseActivity {
    private JCVideoPlayerStandard videoplayer;
    private String videoString;
    private JSONObject videoObject;
    private LinearLayout ll_back;

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
        videoString= getIntent().getStringExtra("result");
        videoObject = JSONObject.parseObject(videoString);
    }

    private void initView() {
        videoplayer = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
    }

    private void initData() {
        String url = videoObject.getString("url");
        toast("视频地址:"+url);
        LogUtils.d("视频地址:"+url);
        videoplayer.setUp(url,JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,videoObject.getString("title"));
        videoplayer.startButton.performClick();
        videoplayer.setAllControlsVisible(View.GONE,View.VISIBLE,View.VISIBLE,View.VISIBLE,View.VISIBLE,View.VISIBLE,View.VISIBLE);
        GlideUtils.downLoadRoundTransform(this,videoObject.getString("img"),videoplayer.thumbImageView,android.R.color.transparent,android.R.color.transparent);
    }

    private void setListener() {
        videoplayer.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                VideoPlayActivity.this.finish();
            }
        });
        videoplayer.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                VideoPlayActivity.this.finish();
            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        videoplayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoObject = null;
        videoString = null;
        ActivityUtils.remove(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
