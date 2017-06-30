package com.example.user.mvptest.mvpbase.pay.utils.wxutils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import com.example.user.mvptest.config.Configs;
import com.example.user.mvptest.config.ToastTools;
import com.example.user.mvptest.mvpbase.pay.utils.aliutils.MathUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.xmlpull.v1.XmlPullParser;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WXPay {
    private static String TAG = WXPay.class.getSimpleName();
    private static Activity sendActivity;
    private static ProgressDialog dialog;

    /**
     * 微信支付网络支付调用接口 获取到服务器返回的订单号
     *
     * @param aty   activity
     * @param prepay_id  订单号
     */
    private static void pay(Activity aty, String prepay_id) {
        PayReq req = genPayReq(prepay_id);
        sendPayReq(aty, req);
    }

    /**
     * 微信支付本地调用实现接口
     * @param aty  发起支付的activity
     * @param payFee  钱数/金额
     * @param payTitle 标题/说明
     */
    public static void pay(final Activity aty,String payFee,String payTitle,String payUrl) {
        sendActivity = aty;
        if (isWeixinAvilible(aty)) {//判断是否安装了微信
            new WXPayTask().execute(payFee,payTitle,payUrl);
        } else {
            ToastTools.shortToast(aty, "您没有安装微信,请下载微信后使用该支付功能!");
        }
    }

    /**
     * 调用微信统一下单接口
     */
    private static class WXPayTask extends AsyncTask<String, Void, Map<String, String>> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(sendActivity);
            dialog.setMessage("支付申请中...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Map<String, String> stringStringMap) {
            dialog.dismiss();
            String prepayId = stringStringMap.get("prepay_id");
            String nonce_str = stringStringMap.get("nonce_str");
            String sign = stringStringMap.get("sign");
            Log.d(TAG, "----支付单号:" + prepayId);
//            pay(sendActivity, prepayId, nonce_str, sign);
            pay(sendActivity, prepayId);
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            String payMoney = params[0];
            String payTitle = params[1];
            String payUrl = params[2];
            byte[] bytes = Util.httpPost(payUrl, genProductArgs(payMoney,payTitle));
            String content = new String(bytes);
            Log.e(TAG, "请求下来的数据:" + content);
            Map<String, String> xml = decodeXml(content);
            return xml;
        }
    }

    /**
     * 生成预览订单的MD5加密签名,以请求微信支付
     * @param params
     * @return
     */
    private static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Configs.WXAPPPRIVATE);//商户平台密钥
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e(TAG, "appSign:" + appSign);
        return appSign;
    }

    /**
     * 获取NonceStr
     * @return
     */
    private static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 获取发起请求的时间
     * @return
     */
    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取预支付订单号,重新签名后封装到 PayReq里面
     * @param prepay_id
     * @return
     */
    private static PayReq genPayReq(String prepay_id) {
        PayReq req = new PayReq();
        req.appId = Configs.WXAPPID;//APPID
        req.partnerId = Configs.WXPARTNERID;//商户号
        req.prepayId = prepay_id;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        req.extData = "app data"; // optional
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new NameValuePair("appid", req.appId));
        signParams.add(new NameValuePair("noncestr", req.nonceStr));
        signParams.add(new NameValuePair("package", req.packageValue));
        signParams.add(new NameValuePair("partnerid", req.partnerId));
        signParams.add(new NameValuePair("prepayid", req.prepayId));
        signParams.add(new NameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        return req;
    }

    /**
     * 发起请求
     * @param activity 发起的activity
     * @param req  payReq
     */
    private static void sendPayReq(Activity activity, PayReq req) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, null);
        Log.e(TAG, "----发起请求:" + req.partnerId + "-----:" + req.appId + "-----" + req.nonceStr + "----" + req.prepayId);
        msgApi.registerApp(Configs.WXAPPID);//APPID
        msgApi.sendReq(req);
    }

    /**
     * 参数封装
     */
    static class NameValuePair {
        String name;
        String value;

        public NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 把集合转换成xml形式的数据
     * @param params 集合数据
     * @return
     */
    private static String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        Log.e(TAG, "-----toXml---" + sb.toString());
        return sb.toString();
    }

    /**
     * 获取 OutTradNo
     * @return
     */
    private static String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    /**
     * 生成请求服务器的签名
     */
    private static String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Configs.WXAPPPRIVATE);//商户密钥
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e(TAG, "订单的签名:" + packageSign);
        return packageSign;
    }

    /**
     * 转换xml文件为map集合
     * @param content
     * @return
     */
    private static Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e(TAG, "----转换错误:" + e.toString());
        }
        return null;

    }

    /**
     * 把一个参数添加到 一个集合中，按字典顺序，这是为了后面生成 签名方便
     * @return
     */
    private static String genProductArgs(String money,String paytitle) {
        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
        packageParams.add(new NameValuePair("appid", Configs.WXAPPID));
        packageParams.add(new NameValuePair("body", paytitle));
        packageParams.add(new NameValuePair("mch_id", Configs.WXPARTNERID));
        packageParams.add(new NameValuePair("nonce_str",
                genNonceStr()));
        packageParams.add(new NameValuePair("notify_url",
                "http://www.baidu.com"));
        packageParams.add(new NameValuePair("out_trade_no",
                genOutTradNo()));
        packageParams.add(new NameValuePair("spbill_create_ip",
                "127.0.0.1"));
        packageParams.add(new NameValuePair("total_fee", MathUtil.double2String(Double.valueOf(money))));
        packageParams.add(new NameValuePair("trade_type", "APP"));
        packageParams.add(new NameValuePair("sign", genPackageSign(packageParams)));
        //调用genXml()方法获得xml格式的请求数据
        String toXml = toXml(packageParams);
        try {
            return new String(toXml.toString().getBytes(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断是否安装了微信
     * @param context
     * @return
     */
    private static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
