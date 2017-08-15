package com.cnbs.utilslibrarydemo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cnbs.utilslibrary.calendarUtils.calendar.MWCalendar;
import com.cnbs.utilslibrary.calendarUtils.listener.OnCalendarChangeListener;
import com.cnbs.utilslibrary.viewUtils.toast.CenterHintToast;
import com.cnbs.utilslibrarydemo.BaseActivity;
import com.cnbs.utilslibrarydemo.R;
import com.cnbs.utilslibrarydemo.model.Calendar_Remark;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ZCalendarActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private MWCalendar mwCalendar;
    private TextView tv_today;
    private ImageView iv_finish;
    private ImageView add_remark;
    private TextView tv_title;
    private List<String> checkedList = new ArrayList<>();
    private AAAdapter aaAdapter;
    private LocalDate localDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zcalendar);
        initView();
        initData();
        mwCalendar.setOnClickCalendarListener(new OnCalendarChangeListener() {
            @Override
            public void onClickCalendar(DateTime dateTime) {
                localDate = dateTime.toLocalDate();
                String date = localDate.toString();
                Toast.makeText(ZCalendarActivity.this, "选择了：：" +date , Toast.LENGTH_SHORT).show();
                refreshRemark(date);
            }

            @Override
            public void onCalendarPageChanged(DateTime dateTime) {
                tv_title.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月");
                localDate = dateTime.toLocalDate();
                String date = localDate.toString();
                refreshRemark(date);
            }
        });
    }

    private void refreshRemark(String date) {
        if (checkedList.contains(date)){
            List<String> remarkList = new ArrayList<>();
            List<Calendar_Remark> list = DataSupport.where("time =?", date).find(Calendar_Remark.class);
            for (Calendar_Remark remark : list) {
                remarkList.add(remark.getRemark());
            }
            aaAdapter.refresh(remarkList);
        }else {
            aaAdapter.refresh(null);
        }
    }

    private void initView() {
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        add_remark = (ImageView) findViewById(R.id.add_remark);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mwCalendar = (MWCalendar) findViewById(R.id.mWCalendar);
        tv_today.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        add_remark.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aaAdapter = new AAAdapter(this);
        recyclerView.setAdapter(aaAdapter);
    }

    private void initData() {
        List<Calendar_Remark> all = DataSupport.findAll(Calendar_Remark.class);
        for (Calendar_Remark remark : all) {
            checkedList.add(remark.getTime());
        }
        mwCalendar.setPointList(checkedList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_today:
                DateTime dateTime = new DateTime();
                LocalDate localDate = dateTime.toLocalDate();
                mwCalendar.setDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
                refreshRemark(localDate.toString());
                break;
            case R.id.add_remark:
                if (this.localDate ==null)return;
                Intent intent = new Intent(ZCalendarActivity.this, AddRemarkActivity.class);
                intent.putExtra("date", this.localDate.toString());
                startActivityForResult(intent,0);
                break;
            case R.id.iv_finish:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                String date = data.getStringExtra("date");
                initData();
                refreshRemark(date);
                new CenterHintToast(ZCalendarActivity.this,"返回成功！");
                break;
            default:
                break;
        }
    }
}
