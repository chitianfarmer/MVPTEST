package com.example.user.mvptest.mvpbase.pay.prestener;

import com.example.user.mvptest.mvpbase.pay.contract.PayContract;
import com.example.user.mvptest.mvpbase.pay.utils.aliutils.AliPayApi;
import com.example.user.mvptest.mvpbase.pay.utils.aliutils.PayResult;
import com.example.user.mvptest.mvpbase.pay.utils.wxutils.WXPay;

import java.util.Map;

/**
 * 项目名称：MVPTEST
 * 类描述：PayPresenter 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/29 13:55
 * 邮箱:814326663@qq.com
 */
public class PayPresenter implements PayContract.Prestener {

    private PayContract.View wxPayView;

    public PayPresenter(PayContract.View wxPayView) {
        this.wxPayView = wxPayView;
        this.wxPayView.setPresenter(this);
    }

    @Override
    public void sendWxPayReq(String payMoney,String payTitle,String payUrl) {
        WXPay.pay(wxPayView.getActivity(),payMoney,payTitle,payUrl);
    }

    @Override
    public void sendAliPay(String appId, String rsa2Private, String rsaPrivate,String payMoney,String payTitle) {
        AliPayApi.payBySdk(wxPayView.getActivity(), appId, rsa2Private, rsaPrivate,payMoney, payTitle,new AliPayApi.PayBackListener() {
            @Override
            public void backResult(Map<String, String> result) {
                PayResult payResult = new PayResult(result);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                final String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                final String resultCode = payResult.getResultStatus();
                wxPayView.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //返回数据
                        wxPayView.AliPayBackResult(resultCode,resultInfo);
                    }
                });
            }
        });
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onDestory() {
        wxPayView = null;
    }
}
