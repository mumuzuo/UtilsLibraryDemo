package com.cnbs.utilslibrarydemo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cnbs.utilslibrary.viewUtils.toast.CenterHintToast;
import com.cnbs.utilslibrarydemo.R;
import com.cnbs.utilslibrarydemo.model.Calendar_Remark;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRemarkActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.input_remark)
    EditText inputRemark;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remark);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
    }

    @OnClick({R.id.iv_finish, R.id.tv_today})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.tv_today:
                String trim = inputRemark.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    new CenterHintToast(AddRemarkActivity.this,"不能添加空记录！");
                    return;
                }
                Calendar_Remark calendar_remark = new Calendar_Remark();
                calendar_remark.setTime(date);
                calendar_remark.setRemark(trim);
                calendar_remark.save();
                new CenterHintToast(AddRemarkActivity.this,"添加记录成功！");
                this.setResult(RESULT_OK,new Intent().putExtra("date",date));
                this.finish();
                break;
        }
    }
}
