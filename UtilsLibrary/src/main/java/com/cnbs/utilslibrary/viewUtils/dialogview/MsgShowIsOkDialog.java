package com.cnbs.utilslibrary.viewUtils.dialogview;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cnbs.utilslibrary.R;

/**
 * Created by Administrator on 2017/2/21.
 */

public class MsgShowIsOkDialog extends AlertDialog {
    private ButtonListener mListener;
    private TextView isOkBtn , content;
    private String titleStr, contentStr,yesStr;

    public interface ButtonListener {
        public void isOk();
    }

    public MsgShowIsOkDialog(Context context, String content , String yes , ButtonListener listener) {
        super(context);
        this.contentStr = content;
        this.yesStr = yes;
        this.mListener = listener;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_show_isok);
        content = (TextView) findViewById(R.id.content_tv);
        isOkBtn = (TextView) findViewById(R.id.yes_tv);
        content.setText(contentStr);
        isOkBtn.setText(yesStr);
        isOkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                mListener.isOk();
            }
        });

    }
}
