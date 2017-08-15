package com.cnbs.utilslibrary.viewUtils.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 一个可以在子线程中弹的吐司
 * Created by Administrator on 2017/2/22.
 */

public class SafeToast extends Toast {
    private static Toast sToast;
    //new一个Handler参数值：它可以在子线程中new，不管handler在哪里new它所投递的消息都是在主线程处理
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public SafeToast(final Context context, final String hintMsg) {
        super(context);
        if (TextUtils.isEmpty(hintMsg))return;      //加上非空判断
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                sToast =  Toast.makeText(context, hintMsg, Toast.LENGTH_SHORT);
                sToast.setGravity(Gravity.CENTER, 0, 0);
                sToast.show();
            }
        });
    }

}
