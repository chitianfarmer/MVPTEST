package com.example.user.mvptest.mvpbase.video.prestener;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.user.mvptest.mvpbase.video.contract.VideoContract;
import com.example.user.utils.media.GildeTools.GlideUtils;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 项目名称：MVPTEST
 * 类描述：VideoPerstener 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/28 10:50
 * 邮箱:814326663@qq.com
 */
public class VideoPerstener implements VideoContract.Prestener {

   private VideoContract.View  videoView;

    public VideoPerstener(VideoContract.View videoView) {
        this.videoView = videoView;
        this.videoView.setPresenter(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestory() {
        videoView =null;
    }

    @Override
    public void play(JCVideoPlayerStandard videoPlayerStandard, String url, String title, String imgUrl) {
        if (TextUtils.isEmpty(url)){
            videoView.showToas("播放地址不能为空");
            return;
        }
        videoPlayerStandard.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,title);
        videoPlayerStandard.startButton.performClick();//设置自动播放
        videoPlayerStandard.setAllControlsVisible(View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
        GlideUtils.downLoadRoundTransform(videoView.getContext(),imgUrl, videoPlayerStandard.thumbImageView, android.R.color.transparent, android.R.color.transparent);//设置播放时的
    }

    @Override
    public void release(JCVideoPlayerStandard jcVideoPlayerStandard) {
        jcVideoPlayerStandard.release();
    }
}
