package com.cnbs.utilslibrary.viewUtils.dialogview;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cnbs.utilslibrary.R;
import com.cnbs.utilslibrary.viewUtils.progressbar.NumberProgressBar;


/**
 * Created by Administrator on 2017/2/22.
 */

public class DownloadDialog extends AlertDialog {
    private TextView downProText, downProCancel;
    private NumberProgressBar downProBar;
    private String mTitle;

    public DownloadDialog(Context context, String downTitle) {
        super(context);
        this.mTitle = downTitle;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download);
        downProText = (TextView) findViewById(R.id.down_pro_text);
        downProCancel = (TextView) findViewById(R.id.down_pro_cancel);
        downProBar = (NumberProgressBar) findViewById(R.id.down_pro_bar);
        downProText.setText(mTitle);
        downProCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setProgressAndMax(int progress , int max){
        downProBar.setProgressAndMax(progress, max);
    }

}
