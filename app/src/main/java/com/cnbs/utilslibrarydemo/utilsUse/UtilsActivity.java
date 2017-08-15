package com.cnbs.utilslibrarydemo.utilsUse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnbs.utilslibrary.utilscnbs.CommUtils;
import com.cnbs.utilslibrary.viewUtils.toast.CenterHintToast;
import com.cnbs.utilslibrarydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UtilsActivity extends AppCompatActivity {

    @BindView(R.id.et0)
    EditText et0;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et20)
    EditText et20;
    @BindView(R.id.et21)
    EditText et21;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.et32)
    EditText et32;
    @BindView(R.id.tv32)
    TextView tv32;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.activity_utils)
    LinearLayout activityUtils;
    @BindView(R.id.right_tv)
    TextView rightTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils);
        ButterKnife.bind(this);
    }

    private boolean isEdit = true;

    @OnClick({R.id.right_tv,R.id.ic_back, R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn32, R.id.btn4, R.id.btn5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                startActivity(new Intent(UtilsActivity.this,UtilsExplainActivity.class));
                break;
            case R.id.ic_back:
                finish();
                break;
            case R.id.btn0:
                String str0 = et0.getText().toString().trim();
                if (!TextUtils.isEmpty(str0)) {
                    boolean mobileNO = CommUtils.isPhoneValid(str0);
                    if (mobileNO) {
                        new CenterHintToast(UtilsActivity.this, "是手机号");
                    } else {
                        new CenterHintToast(UtilsActivity.this, "不是手机号");
                    }
                }
                break;
            case R.id.btn1:
                String str1 = et1.getText().toString().trim();
                if (!TextUtils.isEmpty(str1)) {
                    boolean mobileNO = CommUtils.isIdCardNumber(str1);
                    if (mobileNO) {
                        new CenterHintToast(UtilsActivity.this, "是身份证号");
                    } else {
                        new CenterHintToast(UtilsActivity.this, "不是身份证号");
                    }
                }
                break;
            case R.id.btn2:
                String str20 = et20.getText().toString().trim();
                String str21 = et21.getText().toString().trim();
                if (!TextUtils.isEmpty(str20) && !TextUtils.isEmpty(str21)) {
                    String rate = CommUtils.mathRate(Double.valueOf(str20), Double.valueOf(str21));
                    tv2.setText(rate);
                }
                break;
            case R.id.btn3:
                String str3 = et3.getText().toString().trim();
                if (!TextUtils.isEmpty(str3)) {
                    String md5 = CommUtils.md5(str3);
                    tv3.setText(md5);
                }
                break;
            case R.id.btn32:
                String str32 = et32.getText().toString().trim();
                if (!TextUtils.isEmpty(str32)) {
                    String base64 = CommUtils.getBase64(str32);
                    tv32.setText(base64);
                }
                break;
            case R.id.btn4: //开始是可以编辑的
                isEdit = !isEdit;
                CommUtils.setEditTextEditable(et4, isEdit);
                if (isEdit) {
                    btn4.setText("禁止编辑");
                } else {
                    btn4.setText("允许编辑");
                }
                break;
            case R.id.btn5:
                String sdPath = CommUtils.getSDPath();
                tv5.setText(sdPath);
                break;
        }
    }

}
