package com.example.user.mvptest.http;

import android.content.Context;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.Configs;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.FileCallBack;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.StringCallback;
import com.example.user.utils.file.FileUtil;
import com.example.user.utils.util.LogUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/4.
 * 网络请求工具类
 */

public class HttpUtils {
    /**
     * 智能匹配模版发送接口的http地址
     */
    public static String URI_SEND_SMS = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
    public static String URI_SEND_USERNAME = "C26846914";
    //短信验证的key
    public static String SMSAPPKEY = "cc30ed16d5036ef486f931e25860dd17";
    //短信模板//设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
    public static String SMSTEXT = "您的验证码是：【%s】。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
    /**
     * 账号登录接口
     */
    public static void login(String url,final String username, final String password, StringCallback stringCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("usertel",username);
        params.put("password",password);
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(stringCallback);
    }


    /**
     * 发送验证码
     */
    public static void sendCode(String mobile,StringCallback stringCallback) {
        Map<String, String> params = new HashMap<>();
        //发送验证码
        final int smsCode = (int) (Math.random() * 9000 + 1000);
        params.put("account", URI_SEND_USERNAME);
        params.put("password", SMSAPPKEY);
        params.put("mobile", mobile);
        params.put("content",String.format(SMSTEXT,String.valueOf(smsCode)));
        params.put("time", String.valueOf(System.currentTimeMillis()));
        LogUtils.d("验证码:"+smsCode);
        OkHttpUtils.post()
                .url(URI_SEND_SMS)
                .params(params)
                .build()
                .execute(stringCallback);
    }


    /**
     * 手机号注册
     */
    public static void registerByPhone(String url,String mobile, String usernick, String password, StringCallback stringCallback) {
        Map<String, String> map =new HashMap<>();
        map.put("usertel", mobile);
        map.put("usernick", usernick);
        map.put("password", password);
        OkHttpUtils.get()
                .url(url)
                .params(map)
                .build()
                .execute(stringCallback);
    }

    /**
     *  请求PanderTV数据列表
     * @param page
     * @param pageSize
     * @param stringCallback
     */
    public static void requestData(int page,int pageSize, StringCallback stringCallback){
        Map<String, String> map =new HashMap<>();
        map.put("pageno",String.valueOf(page));
        OkHttpUtils.get()
                .url(Configs.PADNATVLIVELISTURL)
                .params(map)
                .build()
                .execute(stringCallback);
    }

    /**
     * 获取PadnaTV直播详情
     * @param roomId
     * @param stringCallback
     */
    public static void requestPadnaLiveDetail(String roomId,StringCallback stringCallback){
        Map<String, String> map =new HashMap<>();
        map.put("roomid",roomId);
        OkHttpUtils.get()
                .url(Configs.PADNATVLIVEDETAILURL)
                .params(map)
                .build()
                .execute(stringCallback);
    }

    /**
     * 修改/忘记用户密码
     */
    public static void changeOrForgetPassword(String url,String mobile, String newPassword, StringCallback stringCallback) {
        Map<String, String> map =new HashMap<>();
        map.put("newPassword", newPassword);
        map.put("tel", mobile);
        OkHttpUtils.get()
                .url(url)
                .params(map)
                .build()
                .execute(stringCallback);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param fileName
     * @param onDownLoadFinish
     */
    public static void downloadFile(Context context, String url, String fileName, final OnDownLoadFinish onDownLoadFinish) {
        String destFileDir = FileUtil.getDir(context, context.getString(R.string.app_name));
        File targetFile = new File(destFileDir, fileName);
        if (targetFile.exists()) {
            if (onDownLoadFinish != null) {
                onDownLoadFinish.onResponse(targetFile);
            }
            return;
        }
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(destFileDir, fileName) {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        LogUtils.i("下载失败：" + e.getMessage());
                        if (onDownLoadFinish != null) {
                            onDownLoadFinish.onError();
                        }
                    }

                    @Override
                    public void onResponse(File file, int i) {
                        if (onDownLoadFinish != null) {
                            onDownLoadFinish.onResponse(file);
                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        onDownLoadFinish.onProgress(progress);
                        super.inProgress(progress, total, id);
                    }
                });
    }




    public interface OnDownLoadFinish {
        void onError();

        void onResponse(File file);

        void onProgress(float progress);
    }
}
