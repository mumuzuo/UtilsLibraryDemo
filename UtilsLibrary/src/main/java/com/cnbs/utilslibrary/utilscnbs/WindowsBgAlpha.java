package com.cnbs.utilslibrary.utilscnbs;

import android.app.Activity;
import android.os.Handler;
import android.view.WindowManager;

/**
 * 修改窗口透明度
 * Created by Administrator on 2017/2/15.
 */

public class WindowsBgAlpha {
    private Activity mActivity;
    Handler handler = new Handler();

    public WindowsBgAlpha(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 添加屏幕的背景透明度,
     * 在给定的时间内渐变
     */
    private float currentAlpha ;
    private boolean isAdd = false;
    public void backgroundAlpha(float startBgAlpha, final float endBgAlpha, long time) {
        //计算，实现渐变过程
        int end = (int) (endBgAlpha * 10);
        int start = (int) (startBgAlpha * 10);
        final int num ; //时间分为几份
        if (start>end){
            isAdd = false;
            num = start - end;
        }else {
            isAdd = true;
            num = end - start;
        }
        final long mTime = (time / num);//每段变化使用的时长
        final WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        currentAlpha = startBgAlpha;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdd){
                    currentAlpha += 0.1;
                    if (currentAlpha>endBgAlpha){
                       handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,mTime);
                    }
                }else {
                    currentAlpha -= 0.1;
                    if (currentAlpha<endBgAlpha){
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,mTime);
                    }
                }
                lp.alpha = currentAlpha; //0.0-1.0
                mActivity.getWindow().setAttributes(lp);
            }
        }, mTime);
    }

}
