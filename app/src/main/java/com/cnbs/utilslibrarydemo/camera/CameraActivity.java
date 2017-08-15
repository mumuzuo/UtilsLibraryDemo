package com.cnbs.utilslibrarydemo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cnbs.utilslibrary.galleryPick.config.GalleryConfig;
import com.cnbs.utilslibrary.galleryPick.config.GalleryPick;
import com.cnbs.utilslibrary.galleryPick.inter.IHandlerCallBack;
import com.cnbs.utilslibrary.permissionUtils.PermissionListener;
import com.cnbs.utilslibrary.viewUtils.toast.CenterHintToast;
import com.cnbs.utilslibrarydemo.BaseActivity;
import com.cnbs.utilslibrarydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends BaseActivity {
    @BindView(R.id.talk_thing)
    EditText talkThing;
    @BindView(R.id.rvResultPhoto)
    RecyclerView rvResultPhoto;
    @BindView(R.id.activity_crame)
    LinearLayout activityCrame;
    private String TAG = "---CameraActivity---";
    private IHandlerCallBack iHandlerCallBack;
    private GalleryConfig galleryConfig;
    private List<String> path = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private Context mContext;
    private Activity mActivity;
    private final int maxImgNum = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        initView();
        initGallery();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1101:
                    showChooseDialog();
                    break;
                case 1102:          //删除已选图片同时要更新galleryConfig中选中的图片集合
                    if (photoAdapter!=null)photoAdapter.notifyDataSetChanged();
                    if (galleryConfig!=null)galleryConfig.getBuilder().pathList(path).build();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化页面
     */
    private void initView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvResultPhoto.setLayoutManager(gridLayoutManager);
        photoAdapter = new PhotoAdapter(this, path ,handler);
        rvResultPhoto.setAdapter(photoAdapter);
    }

    /**
     * 初始化选择相册
     */
    private void initGallery() {
        //----监听回调接口
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                path.clear();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    path.add(s);
                }
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.cnbs.utilslibrarydemo.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, maxImgNum)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(maxImgNum)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .isOpenCamera(false)                       //是否打开相机
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
    }


    public  void showChooseDialog() {
        AlertDialog  chooseDialog = new AlertDialog.Builder(mActivity).setItems(
                new String[] { "相机", "相册" },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        switch (arg1) {
                            case 0:         //打开相机
                                GalleryPick.getInstance().setGalleryConfig(galleryConfig).openCamera(mActivity);
                                break;
                            case 1:         //  打开相册
                                initPermissions();
                            default:
                                break;
                        }
                    }
                }).create();
        chooseDialog.show();
    }

    /**
     * 权限检测
     */
    private void initPermissions() {
        String[] permissionStr = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        BaseActivity.requestRuntimePermission(permissionStr, new PermissionListener() {
            @Override
            public void onGranted() {
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(mActivity);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                new CenterHintToast(mContext, "请在 设置-应用管理 中开启此应用的存储授权。");
            }
        });

    }

}
