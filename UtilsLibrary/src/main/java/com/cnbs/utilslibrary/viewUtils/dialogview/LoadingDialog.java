package com.cnbs.utilslibrary.viewUtils.dialogview;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.cnbs.utilslibrary.R;


/**
 * Created by Administrator on 2017/2/22.
 */

public class LoadingDialog extends AlertDialog {

    public LoadingDialog(Context context) {
        super(context);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }
}
