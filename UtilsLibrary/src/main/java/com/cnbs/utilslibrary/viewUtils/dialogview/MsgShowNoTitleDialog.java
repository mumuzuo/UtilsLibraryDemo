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

public class MsgShowNoTitleDialog extends AlertDialog {
    private ButtonListener mListener;
    private TextView leftbtn, rightbtn, content;
    private String leftStr, rightStr, contentStr;

    public interface ButtonListener {
        public void left();

        public void right();
    }

    /**
     * @param context
     * @param content
     * @param left
     * @param right
     * @param isOutSide  --  点击dialog外面是否会消失
     * @param listener
     */
    public MsgShowNoTitleDialog(Context context, String content , String left , String right ,boolean isOutSide, ButtonListener listener) {
        super(context);
        this.contentStr = content;
        this.leftStr = left;
        this.rightStr = right;
        this.mListener = listener;
        setCanceledOnTouchOutside(isOutSide);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_show_no_title);
        content = (TextView) findViewById(R.id.content_tv);
        leftbtn = (TextView) findViewById(R.id.left_tv);
        rightbtn = (TextView) findViewById(R.id.right_tv);
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
