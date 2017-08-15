package com.cnbs.utilslibrary.viewUtils.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cnbs.utilslibrary.R;

/**
 * Created by Administrator on 2017/2/24.
 */

public class RoundProgressBar extends View {

    private Paint roundPaint,roundProgressPaint,titleTextPaint,rateTextPaint;
    private int roundColor;
    private int roundProgressColor;
    private int rateTextColor;
    private float rateTextSize;
    private int titleTextColor;
    private float titleTextSize;
    private String titleText;
    private float roundWidth;
    private int maxProgress;
    private int currentProgress;
    private boolean textIsDisplayable;
    private int style;
    public static final int STROKE = 0;
    public static final int FILL = 1;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        roundPaint = new Paint();
        roundProgressPaint = new Paint();
        titleTextPaint = new Paint();
        rateTextPaint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.GRAY);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.BLUE);
        titleTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_titleTextColor, Color.BLACK);
        titleTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_titleTextSize, 15);
        titleText = mTypedArray.getString(R.styleable.RoundProgressBar_titleText);
        rateTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_rateTextColor, Color.BLACK);
        rateTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_rateTextSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        maxProgress = mTypedArray.getInteger(R.styleable.RoundProgressBar_maxProgress, 100);
        currentProgress = mTypedArray.getInteger(R.styleable.RoundProgressBar_currentProgress, 10);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        mTypedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        int centre = getWidth()/2; //获取圆心的x坐标
        int radius = (int) (centre - roundWidth/2)-2; //圆环的半径
        roundPaint.setColor(roundColor); //设置圆环的颜色
        roundPaint.setStyle(Paint.Style.STROKE); //设置空心
        roundPaint.setStrokeWidth(roundWidth-2); //设置圆环的宽度
        roundPaint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, roundPaint); //画出圆环
        Log.e("log", centre + "");

        /**
         * 画圆弧 ，画圆环的进度
         */
        //设置进度是实心还是空心
        roundProgressPaint.setStrokeWidth(roundWidth); //设置圆环的宽度
        roundProgressPaint.setColor(roundProgressColor);  //设置进度的颜色
        roundProgressPaint.setStyle(Paint.Style.STROKE);
        roundPaint.setAntiAlias(true);  //消除锯齿
        RectF oval = new RectF(centre - radius-1, centre - radius-1, centre
                + radius+1, centre + radius+1);  //用于定义的圆弧的形状和大小的界限

        switch (style) {
            case STROKE:{
                roundProgressPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 270, 360 * currentProgress/maxProgress, false, roundProgressPaint);  //根据进度画圆弧
                break;
            }
            case FILL:{
                roundProgressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                if(currentProgress !=0)
                    canvas.drawArc(oval, 270, 360 * currentProgress/maxProgress, true, roundProgressPaint);  //根据进度画圆弧
                break;
            }
        }

        /**
         * 画进度标题
         */
        titleTextPaint.setColor(titleTextColor);
        titleTextPaint.setTextSize(titleTextSize);
        titleTextPaint.setStyle(Paint.Style.FILL);
        titleTextPaint.setTypeface(Typeface.DEFAULT); //设置字体
        float titleTextWidth = titleTextPaint.measureText(titleText);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        if(textIsDisplayable  && style == STROKE){
            canvas.drawText(titleText, centre - titleTextWidth / 2, centre-titleTextSize/2, titleTextPaint); //画出进度百分比
        }


        /**
         * 画进度百分比
         */
        rateTextPaint.setColor(rateTextColor);
        rateTextPaint.setTextSize(rateTextSize);
        rateTextPaint.setStyle(Paint.Style.FILL);
        rateTextPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        int percent = (int)(((float)currentProgress / (float)maxProgress) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float rateTextWidth = rateTextPaint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        if(textIsDisplayable  && style == STROKE){
            canvas.drawText(percent + "%", centre - rateTextWidth / 2, centre + rateTextSize, rateTextPaint); //画出进度百分比
        }

    }


    public synchronized int getMax() {
        return maxProgress;
    }

    /**
     * 设置进度的最大值
     * @param max
     */
    public synchronized void setMax(int max) {
        if(max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.maxProgress = max;
    }

    /**
     * 获取进度.需要同步
     * @return
     */
    public synchronized int getProgress() {
        return currentProgress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if(progress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(progress > maxProgress){
            progress = maxProgress;
        }
        if(progress <= maxProgress){
            this.currentProgress = progress;
            postInvalidate();
        }

    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getRateTextColor() {
        return rateTextColor;
    }

    public void setRateTextColor(int rateTextColor) {
        this.rateTextColor = rateTextColor;
    }

    public float getRateTextSize() {
        return rateTextSize;
    }

    public void setRateTextSize(float rateTextSize) {
        this.rateTextSize = rateTextSize;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public float getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getRoundProgressColor() {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }
}
