package com.cnbs.utilslibrary.permissionUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */

public interface PermissionListener {
    //授权，同意
    void onGranted();
    //拒绝
    void onDenied(List<String> deniedPermission);
}
