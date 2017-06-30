package com.example.user.mvptest.mvpbase.pay.contract;

import android.app.Activity;

import com.example.user.mvptest.mvpbase.base.BasePasenter;
import com.example.user.mvptest.mvpbase.base.BaseView;

import java.util.Map;

/**
 * 项目名称：MVPTEST
 * 类描述：PayContract 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/29 13:50
 * 邮箱:814326663@qq.com
 */
public interface PayContract {

    interface View extends BaseView<Prestener>{
        String getAliPayAppId();
        Activity getActivity();
        String getRsa2Private();
        String getRsaPrivate();
        void AliPayBackResult(String payResultCode,String payResultInfo);
    }

    interface Prestener extends BasePasenter{
        void  sendWxPayReq(String payMoney,String payTitle,String payUrl);
        void sendAliPay(String appId, String rsa2Private, String rsaPrivate,String payMoney,String payTitle);
    }
}
