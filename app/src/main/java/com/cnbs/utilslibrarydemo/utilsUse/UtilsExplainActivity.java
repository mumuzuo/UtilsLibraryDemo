package com.cnbs.utilslibrarydemo.utilsUse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cnbs.utilslibrarydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UtilsExplainActivity extends AppCompatActivity {

    @BindView(R.id.utils_explain)
    TextView utilsExplain;

    private String str = " CommUtils--通用工具类： \n" +
            "     1.base64加密---getBase64(String str) \n" +
            "     2.MD5加密---md5(String rawPass) \n" +
            "     3.检查URl是否有效(子线程中执行)---checkURL(String url) \n" +
            "     4.检查密码是否有效，英文字母大小写加数字6-16位---isPassword(String pwd) \n" +
            "     5.设置EditText是否可编辑---setEditTextEditable(EditText editText, boolean value) \n" +
            "     6.检查电话号码是否有效---isPhoneValid(String phone) \n" +
            "     7.检查网络是否连接---isNetWorkConnected(Context context) \n" +
            "     8.隐藏软键盘---hideKeyboard(Activity context, View view) \n" +
            "     9.切换软键盘---hideKeyboard(Activity context, View view) \n" +
            "     10.检测Sdcard是否存在---isExitsSdcard() \n" +
            "     11.获取sd卡路径---getSDPath() \n" +
            "     12.计算比率(保留一位小数，可修改)---mathRate(double num, double total) \n" +
            "     \n" +
            "     WindowsBgAlpha--背景透明度： \n" +
            "     1.添加屏幕的背景透明度,在给定的时间内渐变--- \n" +
            "         backgroundAlpha(float startBgAlpha, final float endBgAlpha, long time) \n" +
            "     \n" +
            "     ZTimeUtils--时间工具类： \n" +
            "     1.获取系统的当前时间以yyyy-MM-dd HH:mm:ss格式返回---getTimeYMDHMS() \n" +
            "     2.获取系统的当前日期以yyyy-MM-dd格式返回---getTodayYMD() \n" +
            "     3.将已有long型时间以yyyy-MM-dd HH:mmss格式返回---long2TimeYMDHMS(Long time) \n" +
            "     4.将已有long型时间以yyyy-MM-dd格式返回---long2DateYMD(Long time) \n" +
            "     5.将已有long型时间以mm:ss格式返回---long2TimeMS(Long time) \n" +
            "     6.把HH:mm:ss格式的字符串转化为long型时间，秒为单位---time2Long(String timeStr) \n" +
            "     7.把yyyy-MM-dd格式的字符串转化为long型时间，天为单位---day2Long(String dateStr) \n" +
            "     8.把yyyy-MM-dd格式的字符串转化为long型时间，秒为单位---dayTime2Long(String dateTimeStr) ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils_explain);
        ButterKnife.bind(this);
        utilsExplain.setText(str);
    }

    @OnClick(R.id.ic_back)
    public void onClick() {
        finish();
    }
}
