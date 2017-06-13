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
    public static final String HOST = "http://api.fanxinmsg.com/api/"; //116.62.180.69
    public static final String URL_AVATAR= HOST + "upload/";
    public static final String URL_REGISTER = HOST + "register";//注册
    public static final String URL_LOGIN = HOST + "login";//登录
    public static final String URL_THIRDLOGIN = HOST + "thirdLogin";//第三方登录
    public static final String URL_FriendList = HOST + "fetchFriends";//获取好友列表
    public static final String URL_Search_User = HOST + "searchUser";//查询好友
    public static final String URL_Get_UserInfo = HOST + "getUserInfo";//获取详情
    public static final String URL_UPDATE = HOST + "update";//更新
    public static final String URL_RESETPASSWORD = HOST + "resetPassword";//更新密码
    public static final String URL_ADD_FRIEND=HOST + "addFriend"; //添加好友
    public static final String URL_DELETE_FRIEND=HOST + "removeFriend";//删除好友
    public static final String URL_ADD_BLACKLIST=HOST +"addBlackList";//添加黑名单
    public static final String URL_REMOVE_BLACKLIST=HOST +"removeBlackList";//移除黑名单
    public static final String URL_GET_BLACKLIST=HOST +"fetchBlackList";//获取黑名单列表
    public static final String URL_SEARCH_BLACKLIST=HOST +"searchToBlackList";//查询是否在黑名单

    //    朋友圈接口
//     服务器端
    public static final String URL_PUBLISH = HOST + "publish";//发布动态
    public static final String URL_SOCIAL = HOST + "fetchTimeline";//获取动态列表
    public static final String URL_SOCIAL_DELETE = HOST + "removeTimeline";//删除动态
    public static final String URL_SOCIAL_FRIEND = HOST + "fetchOtherTimeline";//获取好友朋友圈列表
    public static final String URL_SOCIAL_COMMENT = HOST + "commentTimeline";//朋友圈动态评论
    public static final String URL_SOCIAL_DELETE_COMMENT = HOST + "deleteCommentTimeline";//删除朋友圈动态评论
    public static final String URL_SOCIAL_REPLY_COMMENT = HOST + "replyCommentTimeline";//回复朋友圈动态评论
    public static final String URL_SOCIAL_DELETE_REPLY_COMMENT = HOST + "deleteReplyCommentTimeline";//删除朋友圈动态评论回复
    public static final String URL_SOCIAL_GOOD = HOST + "praiseTimeline";//点赞
    public static final String URL_SOCIAL_GOOD_CANCEL = HOST + "deletePraiseTimeline";//取消点赞
    public static final String URL_SOCIAL_GET_PRAISELIST = HOST + "fetchTimelineParises";//获取赞列表
    public static final String URL_SOCIAL_GET_COMMENTLIST = HOST + "fetchTimelineComments";//获取评论列表

    //群相关接口
    public static final String GROUP_HOST = "http://api.fanxinmsg.com/group/";  //116.62.180.69
    public static final String URL_GROUP_CREATE = GROUP_HOST + "groupCreate.php";
    public static final String URL_UPDATE_Groupnanme  = GROUP_HOST +"update_groupname.php";
    public static final String URL_GROUP_MEMBERS = GROUP_HOST + "mucMembers.php";
    public static final String URL_GROUP_ADD_MEMBERS = GROUP_HOST + "addMembers.php";

    //活动相关接口
    public static final String URL_GET_ACTIVITY_LIST = HOST + "getActivityList";//获取活动列表
    public static final String URL_PUSH_ACTIVITY = HOST + "releaseActivity";//发布活动
    public static final String URL_DELETE_ACTIVITY = HOST + "deleteActivity";//删除活动
    public static final String URL_UPDATE_ACTIVITY = HOST + "updateActivity";//更新活动
    public static final String URL_JOIN_ACTIVITY = HOST + "joinActivity";//报名接口
    public static final String URL_UNJOIN_ACTIVITY = HOST + "ignoreActivity";//不参加
    public static final String URL_CLOCK_ACTIVITY = HOST + "clockActivity";//打卡签到
    public static final String URL_GET_ACTIVITY_JOIN_MEMBERS = HOST + "getJoinActivityMembers";//获取报名人员列表
    public static final String URL_COMMENT_ACTIVITY = HOST + "commentActivity";//对活动进行评论
    public static final String URL_DELETE_COMMENT_ACTIVITY = HOST + "deleteActivityComment";//删除活动评论
    public static final String URL_PRAISE_COMMENT_ACTIVITY = HOST + "praiseComment";//点赞活动评论
    public static final String URL_DELETE_PRAISE_COMMENT_ACTIVITY = HOST + "deletePraiseComment";//取消活动点赞
    public static final String URL_REPLY_COMMENT_ACTIVITY = HOST + "replyComment";//回复活动评论
    public static final String URL_DELETE_REPLY_COMMENT_ACTIVITY = HOST + "deleteActivityCommentReply";//删除评论下的回复
    public static final String URL_GET_COMMENT_LIST_ACTIVITY = HOST + "getCommentList";//获取活动评论列表
    public static final String URL_GET_PRAISE_LIST_COMMENT__ACTIVITY = HOST + "getCommentPraiseList";//获取活动点赞列表
    public static final String URL_GET_REPLY_COMMENT_ACTIVITY = HOST + "getCommentReplyCommentList";//获取活动评论下的回复列表
    public static final String URL_PRAISE_REPLY_COMMENT_ACTIVITY = HOST + "praiseReplyContent";//点赞评论下的回复
    public static final String URL_DELETE_PRAISE_REPLYCOMMENT_ACTIVITY = HOST + "deletePraiseComment";//取消评论下的回复点赞
    public static final String URL_GET_DETAILS_ACTIVITY = HOST + "getActivityDetails";//获取活动详情
    public static final String URL_GET_MY_ACTIVITY_LIST = HOST + "getMyActivityList";//获取我的活动列表
    public static final String URL_CHECK_UPDATE = "http://api.fanxinmsg.com/group/version.php";	//查询更新
    public static final String URL_GET_CLOCK_PEOPLE = HOST + "getClockMembers";//获取打卡人员
    public static final String URL_AUTH_VIP = HOST + "authVIP";//会员认证
    public static final String URL_GET_EGGS = HOST + "eggs";//获取用户下级人员


    //文件/及图片上传接口
    public static final String endpoint = "oss-cn-shanghai.aliyuncs.com";
    public static final String accessKeyId = "LTAIBUjHs9ZYoo3t";
    public static final String accessKeySecret = "93OfZok7hnT2b3ecXVndKAua35vVC9";
    public static final String bucket = "fanxin-file-server";
    public static final String baseImgUrl = "http://fanxin-file-server.oss-cn-shanghai.aliyuncs.com/";
    //?x-oss-process=image/resize,m_fill,h_100,w_100
//    缩略图拼接
    public static final String baseImgUrl_set = "?x-oss-process=image/resize,m_fill,h_300,w_300";


    public static final String JSON_KEY_NICK ="nick";
    public static final String JSON_KEY_HXID ="userId";
    public static final String JSON_KEY_FXID ="fxid";
    public static final String JSON_KEY_SEX ="sex";
    public static final String JSON_KEY_AVATAR ="avatar";
    public static final String JSON_KEY_CITY ="city";
    public static final String JSON_KEY_PASSWORD ="password";
    public static final String JSON_KEY_PROVINCE ="province";
    public static final String JSON_KEY_TEL ="tel";
    public static final String JSON_KEY_SIGN ="sign";
    public static final String JSON_KEY_ROLE ="role";
    public static final String JSON_KEY_ACTIVITYAUTH ="activityAuth";
    public static final String JSON_KEY_MEETINGAUTH ="meetingAuth";
    public static final String JSON_KEY_FATHERID ="fatherId";
    public static final String JSON_KEY_TEAMS="teams";
    public static final String JSON_KEY_ROLES ="roles";
    public static final String JSON_KEY_BIGREGIONS ="bigRegions";
    public static final String JSON_KEY_ACTIVITYTYPE ="activityTypes";
    public static final String JSON_KEY_SESSION ="session";



    public static final String DIR_AVATAR = Environment.getExternalStorageDirectory().toString()+"/tczhi/";

    //进入用户详情页传递json字符串
    public static final String KEY_USER_INFO="userInfo";


    //获取活动的key
    public static final String KEY_enteredSettings = "enteredSettings";
    public static final String KEY_cover = "cover";
    public static final String KEY_activityPlace = "activityPlace";
    public static final String KEY_time = "time";
    public static final String KEY_id = "id";
    public static final String KEY_activityPeoples = "activityPeoples";
    public static final String KEY_activityDesc = "activityDesc";
    public static final String KEY_endTime = "endTime";
    public static final String KEY_startTime = "startTime";
    public static final String KEY_uid = "uid";
    public static final String KEY_activityType = "activityType";
    public static final String KEY_activityCost = "activityCost";
    public static final String KEY_ACTIVITYLAT = "activityLat";
    public static final String KEY_ACTIVITYLNG = "activityLng";
    public static final String KEY_ACTIVITYPEOPLES = "activityPeoples";
    public static final String KEY_activityTitle = "title";
    public static final String KEY_isJoin = "isJoin";
    public static final String KEY_state = "state";
    public static final String KEY_qrcode = "qrcode";
    public static final String KEY_groupName = "groupName";//activityTitle
    public static final String KEY_user = "user";//activityTitle
    public static final String KEY_usernick = "usernick";//activityTitle

    //获取熊猫TV的直播列表
    public static final String PADNATVLIVELISTURL = "http://static.api.m.panda.tv/android_hd/alllist_.json";  //传入参数 pageno 页数
    //获取熊猫TV的直播分类
    public static final String  PADNATVLIVECLASSURL = "http://static.api.m.panda.tv/android_hd/cate.json";  //无参
    //获取熊猫TV直播间的详情
    public static final String  PADNATVLIVEDETAILURL = "http://www.panda.tv/api_room_v2"; //传入参数 roomid 房间ID

}
