package com.cnbs.utilslibrarydemo.customView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cnbs.utilslibrary.viewUtils.dialogview.DownloadDialog;
import com.cnbs.utilslibrary.viewUtils.dialogview.LoadingDialog;
import com.cnbs.utilslibrary.viewUtils.dialogview.MsgShowDialog;
import com.cnbs.utilslibrary.viewUtils.dialogview.MsgShowIsOkDialog;
import com.cnbs.utilslibrary.viewUtils.dialogview.MsgShowNoTitleDialog;
import com.cnbs.utilslibrarydemo.BaseActivity;
import com.cnbs.utilslibrarydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class CustomViewActivity extends BaseActivity {
    @BindView(R.id.textView0)
    TextView textView0;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.textView0,R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView1:
                LoadingDialog loadingDialog = new LoadingDialog(CustomViewActivity.this);
                loadingDialog.show();
                break;
            case R.id.textView2:
                MsgShowDialog msgShowDialog = new MsgShowDialog(CustomViewActivity.this,"title","content","ok","cancel",new MsgShowDialog.ButtonListener() {
                @Override
                public void left() {

                }

                @Override
                public void right() {

                }
            });
                msgShowDialog.show();
                break;
            case R.id.textView3:
                MsgShowIsOkDialog msgShowIsOkDialog = new MsgShowIsOkDialog(CustomViewActivity.this, "content", "ok", new MsgShowIsOkDialog.ButtonListener() {
                    @Override
                    public void isOk() {

                    }
                });
                msgShowIsOkDialog.show();
                break;
            case R.id.textView4:
                MsgShowNoTitleDialog msgShowNoTitleDialog = new MsgShowNoTitleDialog(CustomViewActivity.this, "content", "ok", "cancel", true, new MsgShowNoTitleDialog.ButtonListener() {
                    @Override
                    public void left() {

                    }

                    @Override
                    public void right() {

                    }
                });
                msgShowNoTitleDialog.show();
                break;
            case R.id.textView0:
                DownloadDialog dialog = new DownloadDialog(CustomViewActivity.this, "下载");
                dialog.show();
                break;
        }
    }
}
