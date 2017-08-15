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

public class MsgShowDialog extends AlertDialog {
    private ButtonListener mListener;
    private TextView leftbtn, rightbtn, titletext, content;
    private String leftStr, rightStr, titleStr, contentStr;

    public interface ButtonListener {
        public void left();

        public void right();
    }

    public MsgShowDialog(Context context, String title , String content , String left ,String right ,ButtonListener listener) {
        super(context);
        this.titleStr = title;
        this.contentStr = content;
        this.leftStr = left;
        this.rightStr = right;
        this.mListener = listener;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_show);
        titletext = (TextView) findViewById(R.id.title_tv);
        content = (TextView) findViewById(R.id.content_tv);
        leftbtn = (TextView) findViewById(R.id.left_tv);
        rightbtn = (TextView) findViewById(R.id.right_tv);
        titletext.setText(titleStr);
        content.setText(contentStr);
        leftbtn.setText(leftStr);
        rightbtn.setText(rightStr);
        leftbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                mListener.left();
            }
        });
        rightbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                mListener.right();
            }
        });
    }
}
