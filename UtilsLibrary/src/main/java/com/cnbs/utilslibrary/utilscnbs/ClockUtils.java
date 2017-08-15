package com.cnbs.utilslibrary.utilscnbs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * 计时器不使用内部类的时候一定要注意生命周期管理
 * 传入的时间用秒做单位
 * Created by Administrator on 2017/3/29.
 */

public class ClockUtils extends Activity {
    private long time ;
    private Handler mHandler;
    private TextView mView;
    public static final int REFRESH_LIMIT = 2017;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int getTAG(){
        return REFRESH_LIMIT;
    }

    public ClockUtils(long second, Handler handler, TextView view) {
        this.time = second;
        this.mHandler = handler;
        this.mView = view;
    }

    public void startTime(){
        runnable.run();
    }

    public void refreshTime(long second){
        this.time = second;
        runnable.run();
    }

    public long getNowTime(){
        return time<0?0:time;
    }

    public void stopTime(){
        timeHandler.removeCallbacks(runnable);
    }

    Handler timeHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (time>0){
                time--;
                timeHandler.postDelayed(this, 1000);
                //需要显示小时的倒计时
//                long hour = (time / (60 * 60));
//                long minute = ((time / 60 ) - hour * 60);
//                long second = (time  - (hour * 60 * 60 )- (minute * 60));
//                mView.setText(timeFormatting(hour) + ":" + timeFormatting(minute) + ":" + timeFormatting(second));
                //只需要从分钟开始显示的倒计时
                long minute = ((time / 60 ));
                long second = (time - (minute * 60));
                mView.setText(timeFormatting(minute) + ":" + timeFormatting(second));
            }else
            if (time == 0){
                //时间走完，告知activity处理
                Message message = new Message();
                message.what = REFRESH_LIMIT;
                mHandler.sendMessage(message);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeHandler.removeCallbacks(runnable);
    }

    // 时间格式化，在小于10的情况下前面自动补0
    private String timeFormatting(long number) {
        String str = String.valueOf(number);
        if (str.length() < 2) {
            str = "0" + str;
        }
        return str;
    }
}
