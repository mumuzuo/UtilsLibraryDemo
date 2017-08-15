package com.cnbs.utilslibrary.viewUtils.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/22.
 */

public class ImgHintToast extends Toast {

    public ImgHintToast(Context context, String hintMsg, int imgId) {
        super(context);
        if (TextUtils.isEmpty(hintMsg))return;      //加上非空判断
        Toast toast = Toast.makeText(context, hintMsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setPadding(0,30,0,10);
        imageCodeProject.setImageResource(imgId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

}
