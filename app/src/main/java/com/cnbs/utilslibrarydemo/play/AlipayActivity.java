package com.cnbs.utilslibrarydemo.play;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cnbs.utilslibrary.playUtils.aliplay.AliPayConfig;
import com.cnbs.utilslibrary.playUtils.aliplay.AliPayUtil;
import com.cnbs.utilslibrary.playUtils.aliplay.PayResult;
import com.cnbs.utilslibrarydemo.R;

public class AlipayActivity extends AppCompatActivity {

    private AliPayConfig aliPayConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        initPay();
    }

    private void initPay() {
        aliPayConfig = new AliPayConfig.Builder()
                .partnerID("2088511431670583")          //合作身份者id，以2088开头的16位纯数字 此id用来支付时快速登录
                .sellerAccount("aley@zhimeiapp.com")            //收款支付宝账号  ,商户私钥  ,支付宝公钥
                .privateKey("MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAK50frgC65OSEVhmRLEQspUW3Sdgwqs2h/HsJzJZhRumh78zv8wnaeJKnCK5ZCKxua9KGcQq52kS6rHYnMJ+eRECH4NAavFbO6LBhYQMmLx7sjsgWacP3UGBZ7Zuqlx9EtUD7eG9nEbQRnrjMc54RUp5aJc8bR8/xLoXWzvFq9kHAgMBAAECgYEAgjHyuEWFpnZmd4CrVzHM9TNBDThLUBe8UTIa9pqUmlQoDuwCAcyQWw+vgsK1FqdomodEv3/9gA9cFCpCo2xb0LfjdKGmW8DNqp9kYaD0i397KSssHEnaC0P85BMedYP+fusHZNhwZRZF+c02Wc+gGTrxPoWAktk+ulxasjoOEgECQQDd5g8JG2VdNCK6UxfMIKfvUwEXyPq+L8hRseEx5yziqastlb97o8I3RoEPPG4w5aLpJwEVubLEvcD49JUKRHeHAkEAyUPos1KIjXppoY5OtBxFj3I5HEp/QO1AFQaoXC9p1d7CLFFp9I/2yAcqysGS9JzLfCTwFxDsh1RgifoqjeLygQJBAJpifyGJ8wtWw7a5Kzx1mAHV0VPYHESIgCn+xbxvp/YHsDZmErSWKgFFR3PvSocwrhjjb0jLS4rArutHWA6tti8CQQCtg/t5pj0N5CxvWRXnV7xsuCgeiOd+3UvzFKNPnpzbAspalPIJI5i5yNwOo6aDdy5DYEERUcjpv0ffjKrlWnSBAkEAyzpO8KV8GfRzJilSuC0O3RpFWk8puXkZvVnYKoSNOaq+AthJXbQB8OYxnQANEaDYDEACiTabDxLy60s1v1CROw==")
                .publicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB")
                .notifyUrl("")      //异步通知URL，后台给出
                .build();

        findViewById(R.id.ali_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AliPayUtil.getInstance(AlipayActivity.this)
                        .setAliPayConfig(aliPayConfig)
                        .startPay("支付宝支付", "支付宝支付", "4564318763000235363","0.01",mHandler);
            }
        });
    }

    // 支付宝回调
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    //String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                       finish();     //关闭所有除去直到主界面--微信支付成功后也要关闭
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(AlipayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(AlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(getApplicationContext(), "检查结果为：" + msg.obj, Toast.LENGTH_SHORT)
                            .show();
                    break;
                }
                default:
                    break;
            }
        }
    };
}
