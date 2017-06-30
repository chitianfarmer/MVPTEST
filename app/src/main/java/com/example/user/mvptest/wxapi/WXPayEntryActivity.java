package com.example.user.mvptest.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.example.user.mvptest.R;
import com.example.user.mvptest.config.BaseActivity;
import com.example.user.mvptest.config.Configs;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	
	private static final String TAG = WXPayEntryActivity.class.getSimpleName();
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
    	api = WXAPIFactory.createWXAPI(this, Configs.APPID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode+"----errString:"+resp.errStr);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("WXPAY").putExtra("wxpay",String.valueOf(resp.errCode)));
		}
		finish();
	}
}