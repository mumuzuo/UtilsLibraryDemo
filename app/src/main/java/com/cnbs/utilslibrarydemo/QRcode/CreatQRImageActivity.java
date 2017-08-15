package com.cnbs.utilslibrarydemo.QRcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cnbs.utilslibrary.zXingUtils.QRCodeUtil;
import com.cnbs.utilslibrarydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatQRImageActivity extends AppCompatActivity {

    @BindView(R.id.qr_button)
    Button qrButton;
    @BindView(R.id.qr_cb)
    CheckBox qrCb;
    @BindView(R.id.qr_image)
    ImageView qrImage;
    @BindView(R.id.qr_et)
    EditText qrEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_qrimage);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.qr_button)
    public void onClick() {
        if (qrEt.getText().toString().length()>=1) {
            if (qrCb.isChecked()) {
                Bitmap bitmap = QRCodeUtil.createQRCodeWithLogo(qrEt.getText().toString(), 500,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.utils_app_logo));
                qrImage.setImageBitmap(bitmap);
            } else {
                Bitmap bitmap = QRCodeUtil.createQRCode(qrEt.getText().toString(), 500);
                qrImage.setImageBitmap(bitmap);
            }
        }else {
            Toast.makeText(CreatQRImageActivity.this,"生成二维码内容不能为空",Toast.LENGTH_LONG).show();
        }
    }
}
