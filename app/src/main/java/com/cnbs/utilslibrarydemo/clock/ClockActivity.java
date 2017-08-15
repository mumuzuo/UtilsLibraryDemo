package com.cnbs.utilslibrarydemo.clock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnbs.utilslibrary.permissionUtils.ActivityCollector;
import com.cnbs.utilslibrary.utilscnbs.ClockUtils;
import com.cnbs.utilslibrary.utilscnbs.WindowsBgAlpha;
import com.cnbs.utilslibrary.viewUtils.dialogview.MsgShowDialog;
import com.cnbs.utilslibrary.viewUtils.dialogview.MsgShowNoTitleDialog;
import com.cnbs.utilslibrary.viewUtils.toast.CenterHintToast;
import com.cnbs.utilslibrarydemo.BaseActivity;
import com.cnbs.utilslibrarydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClockActivity extends BaseActivity {
    @BindView(R.id.exam_activity_vp)
    ViewPager examActivityVp;
    @BindView(R.id.exam_activity_time)
    TextView examActivityTime;
    @BindView(R.id.exam_activity_bottom)
    RelativeLayout examActivityBottom;

    private ClockUtils clockUtils;
    private ClockUtils clock;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        ButterKnife.bind(this);
        setTime();
    }


    /**
     * 设定闯关时间,秒
     */
    private void setTime() {
        String anwserTime = "6";//单位为分钟
        int timeM = Integer.parseInt(anwserTime);
        long longTime = timeM * 60;//换算成秒
        clockUtils = new ClockUtils(longTime, handler, examActivityTime);
        clockUtils.startTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (clockUtils != null) clockUtils.refreshTime(clockUtils.getNowTime());
    }

    // 不可见的时候要关闭计时
    @Override
    protected void onStop() {
        super.onStop();
        if (popupWindow!=null)popupWindow.dismiss();
        if (clockUtils != null) clockUtils.stopTime();
        if (clock != null) clock.stopTime();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ClockUtils.REFRESH_LIMIT:  //具体值查看ClockUtils类
                    if ((ActivityCollector.getTopActivity() == ClockActivity.this)) {     //提前提交后，不需要显示这个,传false-点外面不可以消失
                        new MsgShowNoTitleDialog(ClockActivity.this, "考试结束，请提交！", "放弃考试", "提交", false, new MsgShowNoTitleDialog.ButtonListener() {
                            @Override
                            public void left() {
                                finish();
                            }

                            @Override
                            public void right() {
                             new CenterHintToast(ClockActivity.this,"提交成功！");
                            }
                        }).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick({R.id.header_left_ll, R.id.exam_activity_menu, R.id.exam_activity_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_left_ll:
                finish();
                break;
            case R.id.exam_activity_menu:
                showPopupWindow();
                break;
            case R.id.exam_activity_submit:
                new MsgShowDialog(ClockActivity.this, "提交", "确认提交试卷吗？", "继续做题", "确定", new MsgShowDialog.ButtonListener() {
                    @Override
                    public void left() {

                    }

                    @Override
                    public void right() {
                        new CenterHintToast(ClockActivity.this,"提交成功！");
                    }
                }).show();
                break;
        }
    }

    private void showPopupWindow() {
        View showView = LayoutInflater.from(ClockActivity.this).inflate(R.layout.sub_popup_window, null);
        popupWindow = new PopupWindow(showView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimationPopup);
        View v = LayoutInflater.from(ClockActivity.this).inflate(R.layout.activity_clock, null);
        ImageView menuDown = (ImageView) showView.findViewById(R.id.menu_down);
        menuDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        new WindowsBgAlpha(ClockActivity.this).backgroundAlpha(1.0f, 0.6f, 200);
        popupWindow.setOnDismissListener(new poponDismissListener());
        RecyclerView recyclerView = (RecyclerView) showView.findViewById(R.id.recycler_view);
        TextView popupTime = (TextView) showView.findViewById(R.id.popup_time);
        long nowTime = clockUtils.getNowTime();
        clockUtils.stopTime();
        clock = new ClockUtils(nowTime, handler, popupTime);
        clock.startTime();
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            //这里的时间最好和窗口进出时动画执行时间一致
            new WindowsBgAlpha(ClockActivity.this).backgroundAlpha(0.6f, 1.0f, 300);
            if (clock != null) {
                long nowTime = clock.getNowTime();
                clock.stopTime();
                if (nowTime == 0) {
                    examActivityTime.setText("00:00");
                } else {
                    clockUtils.refreshTime(nowTime);
                }
            }
        }

    }

}
