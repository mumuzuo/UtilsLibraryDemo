package com.cnbs.utilslibrary.playUtils.aliplay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class AliPayUtil {
    private final int SDK_PAY_FLAG = 1;
    private final int SDK_CHECK_FLAG = 2;

    private Activity mActivity;
    private AliPayConfig aliPayConfig;
    private static AliPayUtil alipayUtil;

    public static AliPayUtil getInstance(Activity activity) {
        if (alipayUtil == null) {
            alipayUtil = new AliPayUtil(activity);
        }
        return alipayUtil;
    }

    public AliPayUtil(Activity activity) {
        this.mActivity = activity;
    }

    public AliPayUtil setAliPayConfig(AliPayConfig aliPayConfig){
        this.aliPayConfig = aliPayConfig;
        return this;
    }

    public AliPayConfig getAliPayConfig() {
        return aliPayConfig;
    }

    /**
     * @param subject    商品名称-----可能用不上
     * @param body       商品详情-----可能用不上
     * @param outTradeNo 订单号
     * @param totalFee   商品金额
     * @param handler 回调处理
     */
    public void startPay(String subject, String body, String outTradeNo, String totalFee ,final Handler handler) {
        if (aliPayConfig==null) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("警告")
                    .setMessage("请先配置AliPayConfig")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    mActivity.finish();
                                }
                            }).show();
            return;
        }
        // 订单
        String orderInfo = getOrderInfo(subject, body, outTradeNo, totalFee);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        if (null == sign)
            return;
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果-----支付宝同步返回的
                String result = alipay.pay(payInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(mActivity);
        String version = payTask.getVersion();
        Toast.makeText(mActivity, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     * subject 商品名称
     * body 商品详情
     * orderSn 订单号
     * price 商品金额
     */
    public String getOrderInfo(String subject, String body, String orderSn, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + aliPayConfig.getPARTNER_ID() + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + aliPayConfig.getSELLER_ACCOUNT() + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderSn + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径--
        orderInfo += "&notify_url=" + "\"" + aliPayConfig.getNotify_url() + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * 获取一个交易流水号---这里没有使用
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }


    /**
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, aliPayConfig.getPRIVATE_KEY());
    }

    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}