package com.example.user.mvptest.mvpbase.pay.utils.aliutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.sdk.app.PayTask;
import java.util.Map;

/**
 * 项目名称：MVPTEST
 * 类描述：AliPayApi 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/30 13:55
 * 邮箱:814326663@qq.com
 */
public class AliPayApi {
    private static String TAG = AliPayApi.class.getSimpleName();

    /**
     * 支付数据请求
     * @param activity
     * @param appId
     * @param RSA2_PRIVATE
     * @param RSA_PRIVATE
     * @param listener
     */
    public static void payBySdk(final Activity activity, String appId, String RSA2_PRIVATE, String RSA_PRIVATE,String payMoney,String payTitle, final PayBackListener listener){
        if (TextUtils.isEmpty(appId) || (TextUtils.isEmpty(appId) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            activity.finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(appId,payMoney, payTitle,rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        Log.e(TAG, "orderParam支付请求数据:"+orderParam.toString());
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.e(TAG, "支付请求数据:"+result.toString());
                listener.backResult(result);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    public interface PayBackListener{
        void backResult(Map<String, String> result);
    }
}
