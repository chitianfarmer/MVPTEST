package com.example.user.mvptest.config;

import android.content.Context;
import android.widget.Toast;

/**
 * 项目名称：MVPTEST
 * 类描述：ToastTools 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/13 16:49
 * 邮箱:814326663@qq.com
 */
public class ToastTools {
    /**
     * 短吐司
     * @param contxt
     * @param msg
     */
    public static void shortToast(Context contxt,String msg){
        Toast.makeText(contxt, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 短吐司
     * @param contxt
     * @param msgId
     */
    public static void shortToast(Context contxt,int msgId){
        Toast.makeText(contxt, msgId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长吐司
     * @param contxt
     * @param msg
     */
    public static void longToast(Context contxt,String msg){
        Toast.makeText(contxt, msg, Toast.LENGTH_LONG).show();
    }
    /**
     * 长吐司
     * @param contxt
     * @param msgId
     */
    public static void longToast(Context contxt,int msgId){
        Toast.makeText(contxt, msgId, Toast.LENGTH_LONG).show();
    }
}
