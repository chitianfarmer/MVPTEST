package com.example.user.mvptest.config;

import android.os.Environment;

/**
 * 项目名称：MVPTEST
 * 类描述：Configs 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/13 13:22
 * 邮箱:814326663@qq.com
 */
public class Configs {
    //服务器端
    public static final String HOST = "http://xxxx/api/";
    public static final String URL_AVATAR= HOST + "upload/";
    public static final String URL_REGISTER = HOST + "register";//注册
    public static final String URL_LOGIN = HOST + "login";//登录

    //获取熊猫TV的直播列表
    public static final String PADNATVLIVELISTURL = "http://static.api.m.panda.tv/android_hd/alllist_.json";  //传入参数 pageno 页数
    //获取熊猫TV的直播分类
    public static final String  PADNATVLIVECLASSURL = "http://static.api.m.panda.tv/android_hd/cate.json";  //无参
    //获取熊猫TV直播间的详情
    public static final String  PADNATVLIVEDETAILURL = "http://www.panda.tv/api_room_v2"; //传入参数 roomid 房间ID

}
