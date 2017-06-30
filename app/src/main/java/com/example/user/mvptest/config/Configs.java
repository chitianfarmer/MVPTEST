package com.example.user.mvptest.config;

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

    /**
     * 支付相关
     */
//    微信支付相关
    //支付相关  商户号PARTNERID
    public static final String WXPARTNERID="";
    //APPID
    public static final String WXAPPID = "";
    //APPPRIVATE
    public static final String WXAPPPRIVATE = "";
    //获取到微信的支付的统一下单
    public static final String WXPAYURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

//    支付宝支付参数
    /** 支付宝支付业务：入参app_id */
    public static final String ALIPAYAPPID = "";
     /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String ALIPAYRSA2_PRIVATE ="";
    public static final String ALIPAYRSA_PRIVATE ="";
}
