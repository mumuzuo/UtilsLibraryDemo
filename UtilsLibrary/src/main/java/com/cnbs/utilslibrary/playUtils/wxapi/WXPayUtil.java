package com.cnbs.utilslibrary.playUtils.wxapi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Xml;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 1.要调用微信的统一下单API生成预支付订单信息
 * 2.在预支付订单上签名
 */
public class WXPayUtil {
    private PayReq req;
    private IWXAPI iwxapi;
    private ProgressDialog dialog;
    private Map<String, String> resultunifiedorder;
    private Context context;
    private WeiXinConfig weiXinConfig;

    private static WXPayUtil wxPayUtil;

    public static WXPayUtil getInstance(Context context) {
        if (wxPayUtil == null) {
            wxPayUtil = new WXPayUtil(context);
        }
        return wxPayUtil;
    }

    public WXPayUtil(Context context) {
        this.context = context;
        this.iwxapi = WXAPIFactory.createWXAPI(context, null);
        this.req = new PayReq();

    }

    public WXPayUtil setWeiXinConfig(WeiXinConfig weiXinConfig){
        this.weiXinConfig = weiXinConfig;
        this.iwxapi.registerApp(weiXinConfig.getAPP_ID());
        return this;
    }

    public WeiXinConfig getWeiXinConfig() {
        return weiXinConfig;
    }

    /**
     * @param body       商品或支付单简要描述
     * @param outTradeNo 商户订单号
     * @param price      订单总金额
     */
    public void startPay(String body, String outTradeNo, String price) {
        //--检查微信支付配置文件
        if (wxPayUtil.weiXinConfig == null) {
            Toast.makeText(context, "请配置WeiXinConfig！", Toast.LENGTH_SHORT).show();
            return;
        }
        //--检查微信客户端是否安装
        if (!isWXAPPInstalled(context)) {
            Toast.makeText(context, "请安装微信客户端！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在请求微信...");
            dialog.setCancelable(true);
        }
        dialog.setCanceledOnTouchOutside(true);
        new GetPrepayIdTask(body, outTradeNo, price).execute();
    }

    public class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
        private String body;
        private String outTradeNo;
        private String price;

        public GetPrepayIdTask(String body, String outTradeNo, String price) {
            super();
            this.body = body;
            this.outTradeNo = outTradeNo;
            this.price = price;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs(body, outTradeNo, price);
//			Log.e("orion", entity);
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            //	Log.e("orion", content);
            Map<String, String> xml = decodeXml(content);
            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            resultunifiedorder = result;
            genPayReq();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    /*
     * 生成订单参数
     */
    private String genProductArgs(String body, String outTradeNo, String price) {
        StringBuffer xml = new StringBuffer();
        try {
            double money = Double.parseDouble(price) * 100;
            price = Integer.parseInt(((int) money) + "") + "";
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid",
                    weiXinConfig.getAPP_ID()));
            // 商品或支付单简要描述
            packageParams.add(new BasicNameValuePair("body", body));
            packageParams.add(new BasicNameValuePair("mch_id",
                    weiXinConfig.getMCH_ID()));
            // 随机字符串
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            // 接收微信支付异步通知回调地址
            packageParams.add(new BasicNameValuePair("notify_url",
                    weiXinConfig.getNotify_url()));
            // 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
            packageParams.add(new BasicNameValuePair("out_trade_no", outTradeNo));
            // 终端IP APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
            packageParams.add(new BasicNameValuePair("spbill_create_ip",
                    "127.0.0.1"));
            // 总金额 订单总金额，只能为整数，详见支付金额
            packageParams.add(new BasicNameValuePair("total_fee", price));
            // 交易类型
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring = toXml(packageParams);
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
        } catch (Exception e) {
            return null;
        }

    }

    /*
     * 支付接口请求参数
     */
    private void genPayReq() {
        req.appId = weiXinConfig.getAPP_ID();
        req.partnerId = weiXinConfig.getMCH_ID();
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        // 随机字符串
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        // 扩展字段 暂填写固定值Sign=WXPay
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        // 商户号
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        // 预支付交易会话ID 微信返回的支付交易会话ID
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        // 时间戳
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        sendPayReq();
    }

    /*
     * 调起微信支付
     */
    private void sendPayReq() {
        iwxapi.sendReq(req);
    }

    /**
     * 生成签名
     */

    @SuppressLint("DefaultLocale")
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(weiXinConfig.getAPI_KEY());
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        //Log.e("orion", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(weiXinConfig.getAPI_KEY());

        String appSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        //Log.e("orion", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        //Log.e("orion", sb.toString());
        return sb.toString();
    }

    public Map<String, String> decodeXml(String content) {

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
                            // 实例化对象
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
            //Log.e("orion", e.toString());
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    public boolean isWXAPPInstalled(Context ctx) {
        if (iwxapi == null) {
            return false;
        }
        boolean result = iwxapi.isWXAppInstalled();
        if (result) {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                try {
                    ApplicationInfo info = packageManager.getApplicationInfo(
                            "com.tencent.mm", PackageManager.GET_META_DATA);
                    if (info != null) {
                        result = true;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    result = false;
                }
            }
        }
        return result;
    }

}
