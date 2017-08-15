package com.cnbs.utilslibrarydemo;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cnbs.utilslibrary.zXingUtils.CaptureActivity;
import com.cnbs.utilslibrarydemo.QRcode.CreatQRImageActivity;
import com.cnbs.utilslibrarydemo.calendar.ZCalendarActivity;
import com.cnbs.utilslibrarydemo.camera.CameraActivity;
import com.cnbs.utilslibrarydemo.clock.ClockActivity;
import com.cnbs.utilslibrarydemo.customView.CustomViewActivity;
import com.cnbs.utilslibrarydemo.play.AlipayActivity;
import com.cnbs.utilslibrarydemo.play.WXPayActivity;
import com.cnbs.utilslibrarydemo.utilsUse.UtilsActivity;
import com.cnbs.utilslibrarydemo.video.VideoActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.textView05)
    TextView textView05;
    @BindView(R.id.textView8)
    TextView textView8;
    private View view;
    private Dialog share;
    private ImageView share_wx, share_wb, share_pyq, share_qq, login_wx, login_qq;
    private Button share_cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

    }


    @OnClick({R.id.textView00, R.id.textView01, R.id.textView02, R.id.textView03, R.id.textView04, R.id.textView05, R.id.textView06,
            R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7,R.id.textView8})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView00:
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.textView01:
                startActivity(new Intent(MainActivity.this, ClockActivity.class));
                break;
            case R.id.textView02:
                startActivity(new Intent(MainActivity.this, AlipayActivity.class));
                break;
            case R.id.textView03:
                startActivity(new Intent(MainActivity.this, WXPayActivity.class));
                break;
            case R.id.textView04:
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
                break;
            case R.id.textView05:
                startActivity(new Intent(MainActivity.this, ZCalendarActivity.class));
                break;
            case R.id.textView06:
                startActivity(new Intent(MainActivity.this, UtilsActivity.class));
                break;
            case R.id.textView4:
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(this, mPermissionList, 123);
                }

                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.um_share, null);
                share_wx = (ImageView) v.findViewById(R.id.share_wx);
                share_wx.setOnClickListener(this);
                share_qq = (ImageView) v.findViewById(R.id.share_qq);
                share_qq.setOnClickListener(this);
                share_pyq = (ImageView) v.findViewById(R.id.share_pyq);
                share_pyq.setOnClickListener(this);
                share_wb = (ImageView) v.findViewById(R.id.share_wb);
                share_wb.setOnClickListener(this);
                share_cancel_btn = (Button) v.findViewById(R.id.share_cancel_btn);
                share_cancel_btn.setOnClickListener(this);
                share = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                share.setContentView(v);
                Window dialogWindows = share.getWindow();
                dialogWindows.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                WindowManager.LayoutParams lps = dialogWindows.getAttributes();
                lps.width = WindowManager.LayoutParams.MATCH_PARENT;
                lps.x = 0;
                lps.y = 0;
                dialogWindows.setAttributes(lps);
                share.show();
                break;
            case R.id.textView5:
                View login_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.um_login, null);
                login_wx = (ImageView) login_view.findViewById(R.id.login_wx);
                login_wx.setOnClickListener(this);
                login_qq = (ImageView) login_view.findViewById(R.id.login_qq);
                login_qq.setOnClickListener(this);
                share = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                share.setContentView(login_view);
                Window dialogW = share.getWindow();
                dialogW.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                WindowManager.LayoutParams lp = dialogW.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.x = 0;
                lp.y = 0;
                dialogW.setAttributes(lp);
                share.show();
                break;
            case R.id.textView6:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //去请求权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 114);
                } else {
                    startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                }
                break;
            case R.id.textView7:
                startActivity(new Intent(MainActivity.this, CreatQRImageActivity.class));
                break;
            case R.id.share_wx:
                new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).withText("汇聚国家电网、南方电网校园招聘考试最新资讯、备考攻略、辅导培训为一体的移动备考平台，一机在手，学习无忧！")
                        //.withMedia(imagelocal)
                        //  .withTargetUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cnbs.powernet")
                        //.withTitle("电网人")
                        .setCallback(shareListener)
                        .share();
                break;
            case R.id.share_pyq:
                new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withText("汇聚国家电网、南方电网校园招聘考试最新资讯、备考攻略、辅导培训为一体的移动备考平台，一机在手，学习无忧！")
                        .setCallback(shareListener)
                        .share();
                break;
            case R.id.share_qq:
                new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.QQ).withText("汇聚国家电网、南方电网校园招聘考试最新资讯、备考攻略、辅导培训为一体的移动备考平台，一机在手，学习无忧！")
                        .setCallback(shareListener)
                        .share();
                break;
            case R.id.share_cancel_btn:
                share.cancel();
                break;
            case R.id.login_wx:
                UMShareAPI.get(this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.textView8:
                startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                break;
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            String id = data.get("uid");
            String name = data.get("name");
            String gender = data.get("gender");
            String iconurl = data.get("iconurl");
            Toast.makeText(MainActivity.this, "成功了" + "id=" + id + "name=" + name + "性别=" + gender + "头像地址=" + iconurl, Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        }
    }
}
