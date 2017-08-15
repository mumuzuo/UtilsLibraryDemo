package com.cnbs.utilslibrarydemo.play;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cnbs.utilslibrary.playUtils.wxapi.WXPayUtil;
import com.cnbs.utilslibrary.playUtils.wxapi.WeiXinConfig;
import com.cnbs.utilslibrarydemo.R;
import com.tencent.mm.opensdk.modelbase.BaseResp;


public class WXPayActivity extends AppCompatActivity {

    private WeiXinConfig weiXinConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay);
        initPay();
    }

    private void initPay() {
        weiXinConfig = new WeiXinConfig.Builder()
                .appID("wx51a63dc4c2571961")            //微信开放平台创建应用获得的APP_ID
                .appSecret("10c5dba23092441d6d76b64874a9ae3b")               //微信开放平台创建应用获得的AppSecret
                .mchID("1426344202")            //获取应用支付功能后对应的商户号
                .apiKEY("0123456789fengyedainzi1426344202")         //API密钥，在商户平台设置
                .notifyUrl("")                //接收微信支付异步通知回调的地址，后台提供
                .build();

        findViewById(R.id.wx_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXPayUtil.getInstance(WXPayActivity.this)
                        .setWeiXinConfig(weiXinConfig)
                        .startPay("微信支付", "201304566464" , "0.01");
            }
        });
    }

    /**
     * weixin支付回调,可以不写
     */
    //微信回调
    private BaseResp resp;
    protected void onResume() {
        // 微信支付回调
        super.onResume();
        if (weiXinConfig!=null && weiXinConfig.getPayResp() != null) {
            resp = weiXinConfig.getPayResp();
            switch (resp.errCode) {
                case 0://成功      展示成功页面
                    finish();
                    break;
                case -1://错误      可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                    break;
                case -2://用户取消     无需处理。发生场景：用户不支付了，点击取消，返回APP
                    break;
            }
            weiXinConfig.getBuilder().payResp(null).build();
        }
    }

}
