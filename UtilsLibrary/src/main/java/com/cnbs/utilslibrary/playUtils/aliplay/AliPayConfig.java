package com.cnbs.utilslibrary.playUtils.aliplay;

import java.io.Serializable;


/**
 * 支付宝支付 配置器
 * Created by zzj on 2017/7/25.
 */
public class AliPayConfig {
    //合作身份者id，以2088开头的16位纯数字 此id用来支付时快速登录
    private String PARTNER_ID;
    //收款支付宝账号
    private String SELLER_ACCOUNT;
    //商户私钥，自助生成，在压缩包中有openssl，用此软件生成商户的公钥和私钥，写到此处要不然服务器返回错误。公钥要传到淘宝合作账户里详情请看淘宝的sdk文档
    private String PRIVATE_KEY;
    //支付宝公钥
    private String PUBLIC_KEY;
    //服务器异步通知URl
    private String notify_url;

    private Builder builder;

    private AliPayConfig(Builder builder) {
        setBuilder(builder);
    }

    private void setBuilder(Builder builder) {
        this.PARTNER_ID = builder.PARTNER_ID;
        this.SELLER_ACCOUNT = builder.SELLER_ACCOUNT;
        this.PRIVATE_KEY = builder.PRIVATE_KEY;
        this.PUBLIC_KEY = builder.PUBLIC_KEY;
        this.notify_url = builder.notify_url;
        this.builder = builder;
    }

    public static class Builder implements Serializable {
        private static AliPayConfig aliPayConfig;

        //----需要定义的属性
        private String PARTNER_ID = "";
        private String SELLER_ACCOUNT = "";
        private String PRIVATE_KEY = "";
        private String PUBLIC_KEY = "";
        private String notify_url = "";

        //--传入属性值的方法

        /**
         * 合作身份者id，以2088开头的16位纯数字 此id用来支付时快速登录
         * @param partner_ID
         * @return
         */
        public Builder partnerID(String partner_ID) {
            this.PARTNER_ID = partner_ID;
            return this;
        }

        /**
         * 收款支付宝账号
         * @param SELLER_ACCOUNT
         * @return
         */
        public Builder sellerAccount(String SELLER_ACCOUNT) {
            this.SELLER_ACCOUNT = SELLER_ACCOUNT;
            return this;
        }

        /**
         * 商户私钥，自助生成,商户公钥要传到淘宝合作账户里
         * @param PRIVATE_KEY
         * @return
         */
        public Builder privateKey(String PRIVATE_KEY) {
            this.PRIVATE_KEY = PRIVATE_KEY;
            return this;
        }

        /**
         * 支付宝公钥不是商户公钥，用于支付宝返回数据的验签。
         * @param PUBLIC_KEY
         * @return
         */
        public Builder publicKey(String PUBLIC_KEY) {
            this.PUBLIC_KEY = PUBLIC_KEY;
            return this;
        }

        /**
         * 接收支付异步通知回调的地址，后台提供
         * @param notify_url
         * @return
         */
        public Builder notifyUrl(String notify_url) {
            this.notify_url = notify_url;
            return this;
        }

        public AliPayConfig build() {
            if (aliPayConfig == null) {
                aliPayConfig = new AliPayConfig(this);
            } else {
                aliPayConfig.setBuilder(this);
            }
            return aliPayConfig;
        }
    }

    public String getPARTNER_ID() {
        return PARTNER_ID;
    }

    public String getSELLER_ACCOUNT() {
        return SELLER_ACCOUNT;
    }

    public String getPRIVATE_KEY() {
        return PRIVATE_KEY;
    }

    public String getPUBLIC_KEY() {
        return PUBLIC_KEY;
    }

    public Builder getBuilder() {
        return builder;
    }

    public String getNotify_url() {
        return notify_url;
    }
}

