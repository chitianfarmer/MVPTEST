package com.example.user.mvptest.mvpbase.pay.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.ActivityUtils;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.config.Configs;
import com.example.user.mvptest.mvpbase.pay.contract.PayContract;
import com.example.user.mvptest.mvpbase.pay.prestener.PayPresenter;

/**
 * 项目名称：MVPTEST
 * 类描述：PayActivity 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/29 13:46
 * 邮箱:814326663@qq.com
 */
public class PayActivity extends BaseActivity implements PayContract.View, View.OnClickListener {
    private PayContract.Prestener prestener;
    private Button appay_btn, appay_btn_alipay;
    private WXPayListener payListener;
    private String payMoney = "0.3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay);
        getData();
        initView();
        initData();
        setListener();

    }

    private void getData() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("支付页面");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        prestener = new PayPresenter(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WXPAY");
        payListener = new WXPayListener();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(payListener, intentFilter);
    }

    private void initView() {
        appay_btn = (Button) findViewById(R.id.appay_btn_wx);
        appay_btn_alipay = (Button) findViewById(R.id.appay_btn_alipay);
    }

    private void initData() {

    }

    private void setListener() {
        appay_btn.setOnClickListener(this);
        appay_btn_alipay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appay_btn_wx:
                if (TextUtils.isEmpty(Configs.WXAPPID) || TextUtils.isEmpty(Configs.WXPARTNERID) || TextUtils.isEmpty(Configs.WXAPPPRIVATE)){
                    new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | PARTNERID | PRIVATE")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    finish();
                                }
                            }).show();
                    return;
                }
                prestener.sendWxPayReq(payMoney, "微信支付测试", Configs.WXPAYURL);
                break;
            case R.id.appay_btn_alipay:
                prestener.sendAliPay(getAliPayAppId(), getRsa2Private(), getRsaPrivate(), payMoney, "支付宝支付测试");
                break;
        }
    }

    @Override
    public String getAliPayAppId() {
        return Configs.ALIPAYAPPID;
    }

    @Override
    public Activity getActivity() {
        return PayActivity.this;
    }

    @Override
    public void AliPayBackResult(String payResultCode, String payResultInfo) {
        // 判断resultStatus 为9000则代表支付成功
        if (TextUtils.equals(payResultCode, "9000")) {
            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
            toast("支付成功");
        } else {
            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
            toast("支付失败");
        }
    }

    @Override
    public String getRsa2Private() {
        return Configs.ALIPAYRSA2_PRIVATE;
    }

    @Override
    public String getRsaPrivate() {
        return Configs.ALIPAYRSA_PRIVATE;
    }


    @Override
    public void setPresenter(PayContract.Prestener presenter) {
        this.prestener = presenter;
    }

    @Override
    public Context getContext() {
        return PayActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prestener.onDestory();
        ActivityUtils.remove(this);
        if (payListener != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(payListener);
        }
    }

    private class WXPayListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String payResult = null;
            if ("WXPAY".equals(intent.getAction())) {
                String wxpay = intent.getStringExtra("wxpay");
                switch (wxpay) {
                    case "0":
                        payResult = "支付成功";
                        break;
                    case "-1":
                        payResult = "支付已取消";
                        break;
                    case "-2":
                        payResult = "支付失败";
                        break;
                }
                toast(payResult);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        PayActivity.this.finish();
        return super.onSupportNavigateUp();
    }
}
