package com.cnbs.utilslibrary.playUtils.wxapi;







import com.tencent.mm.opensdk.modelbase.BaseResp;

import java.io.Serializable;

/**
 * 微信支付 配置器
 * Created by zzj on 2017/7/25.
 */
public class WeiXinConfig {
    // 请同时修改 androidmanifest.xml里面，
    // 在调用微信付的activity里的属性<data android:scheme="···"/>
    // appid
    private String APP_ID;
    private String AppSecret;
    // 商户号
    private String MCH_ID;
    // API密钥，在商户平台设置
    private String API_KEY;
    // 接收微信支付异步通知回调地址
    private String notify_url;
    // 微信授权域,固定值
    private String scope;
    private BaseResp logResp;// 登录信息
    private BaseResp payResp;// 支付信息

    private Builder builder;

    private WeiXinConfig(Builder builder) {
        setBuilder(builder);
    }


    private void setBuilder(Builder builder) {
        this.APP_ID = builder.APP_ID;
        this.AppSecret = builder.AppSecret;
        this.MCH_ID = builder.MCH_ID;
        this.API_KEY = builder.API_KEY;
        this.notify_url = builder.notify_url;
        this.scope = builder.scope;
        this.logResp = builder.logResp;
        this.payResp = builder.payResp;
        this.builder = builder;
    }

    public static class Builder implements Serializable {
        private static WeiXinConfig weiXinConfig;
        //----需要定义的属性
        private String APP_ID = "";
        private String AppSecret = "";
        private String MCH_ID = "";
        private String API_KEY = "";
        private String notify_url = "";
        private String scope = "snsapi_userinfo";
        private BaseResp logResp ;
        private BaseResp payResp ;

        /**
         * 微信开放平台创建应用获得的APP_ID
         * @param APP_ID
         * @return
         */
        public Builder appID(String APP_ID) {
            this.APP_ID = APP_ID;
            return this;
        }

        /**
         * 微信开放平台创建应用获得的AppSecret
         * @param AppSecret
         * @return
         */
        public Builder appSecret(String AppSecret) {
            this.AppSecret = AppSecret;
            return this;
        }

        /**
         * 获取应用支付功能后对应的商户号
         * @param MCH_ID
         * @return
         */
        public Builder mchID(String MCH_ID) {
            this.MCH_ID = MCH_ID;
            return this;
        }

        /**
         * API密钥，在商户平台设置
         * @param API_KEY
         * @return
         */
        public Builder apiKEY(String API_KEY) {
            this.API_KEY = API_KEY;
            return this;
        }

        /**
         * 接收微信支付异步通知回调的地址，后台提供
         * @param notify_url
         * @return
         */
        public Builder notifyUrl(String notify_url) {
            this.notify_url = notify_url;
            return this;
        }

        /**
         * 微信授权域,固定值
         * @param scope
         * @return
         */
        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        /**
         * 微信登录信息
         * @param logResp
         * @return
         */
        public Builder logResp(BaseResp logResp) {
            this.logResp = logResp;
            return this;
        }

        /**
         * 微信支付信息
         * @param payResp
         * @return
         */
        public Builder payResp(BaseResp payResp) {
            this.payResp = payResp;
            return this;
        }

        public WeiXinConfig build() {
            if (weiXinConfig == null) {
                weiXinConfig = new WeiXinConfig(this);
            } else {
                weiXinConfig.setBuilder(this);
            }
            return weiXinConfig;
        }
    }

    //WeiXinConfig类属性的get方法

    public String getAPP_ID() {
        return APP_ID;
    }

    public String getAppSecret() {
        return AppSecret;
    }

    public String getMCH_ID() {
        return MCH_ID;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getScope() {
        return scope;
    }

    public BaseResp getResp() {
        return logResp;
    }

    public BaseResp getPayResp() {
        return payResp;
    }

    public Builder getBuilder() {
        return builder;
    }
}
