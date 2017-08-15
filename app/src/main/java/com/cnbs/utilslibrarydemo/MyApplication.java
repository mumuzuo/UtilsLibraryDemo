package com.cnbs.utilslibrarydemo;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2017/7/14.
 */

public class MyApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxd24ce3a059209814", "af4c69e6bd6c9b757302e3b795e631e6");
        PlatformConfig.setQQZone("1105868154","Cs34lyfuAChiDXCW");
        PlatformConfig.setSinaWeibo("1683354702","fc72b660721c0be21b03f92685bec9cc","http://sns.whalecloud.com");
    }

}
