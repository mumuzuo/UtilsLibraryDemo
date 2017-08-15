package com.cnbs.utilslibrary.viewUtils.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 一个单例的吐司，吐司之间互相不受干扰，当前吐司的内容可以将上一个吐司的内容覆盖
 * Created by Administrator on 2017/2/22.
 */

public class SingleToast extends Toast {
    private static Toast sToast;

    public SingleToast(Context context, String hintMsg) {
        super(context);
        if (TextUtils.isEmpty(hintMsg))return;      //加上非空判断
        if (sToast == null) {
            sToast = Toast.makeText(context, hintMsg, Toast.LENGTH_SHORT);
        }
        sToast.setText(hintMsg);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }

}
