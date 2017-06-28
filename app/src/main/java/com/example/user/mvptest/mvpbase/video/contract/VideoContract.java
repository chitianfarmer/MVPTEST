package com.example.user.mvptest.mvpbase.video.contract;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.mvpbase.base.BasePasenter;
import com.example.user.mvptest.mvpbase.base.BaseView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 项目名称：MVPTEST
 * 类描述：VideoContract 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/28 10:39
 * 邮箱:814326663@qq.com
 */
public interface VideoContract {
    interface View extends BaseView<Prestener>{
        void showToas(String message);
        String getPlayUrl();
        String getVideoTitle();
        String getVideoImage();
        JSONObject getUrlJson();
    }
    interface Prestener extends BasePasenter{
        void play(Context context, JCVideoPlayerStandard videoPlayerStandard, String url, String title, String imgUrl);
        void release(JCVideoPlayerStandard jcVideoPlayerStandard);
    }
}
