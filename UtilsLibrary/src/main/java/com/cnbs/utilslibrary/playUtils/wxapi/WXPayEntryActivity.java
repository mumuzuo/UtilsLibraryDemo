package com.cnbs.utilslibrary.playUtils.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cnbs.utilslibrary.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;
    private WeiXinConfig weiXinConfig;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);
        weiXinConfig = WXPayUtil.getInstance(this).getWeiXinConfig();
        //支付回调在支付后面执行，所以这里的weiXinConfig不用进行非空判断，不过为了健壮性可以加上异常捕获
        try {
            api = WXAPIFactory.createWXAPI(this, weiXinConfig.getAPP_ID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
		finish();
	}

	@Override
	public void onReq(BaseReq req) {
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            try {
                weiXinConfig.getBuilder().payResp(resp).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		finish();
	}
}