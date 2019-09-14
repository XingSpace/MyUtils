package com.xing.app.myutils.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要再AndroidManifest中做权限申请配置，然后再动态申请权限
 *     <uses-permission android:name="android.permission.READ_SMS"/>
 *     <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 *     <uses-permission android:name="android.permission.CAMERA"/>
 *     <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 *     <uses-permission android:name="android.permission.READ_CALL_LOG"/>
 *     <uses-permission android:name="android.permission.READ_CONTACTS"/>
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 */
public class PermissionUtil {

    private static int mRequestCode;
    private static List<String> mListPermissions = new ArrayList<>();
    private static String[] tPermissions= new String[]{Manifest.permission.READ_SMS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static boolean allPermission=true;

    private static void checkPermissions(String[] permissions, int requestCode, Activity activity) {
        //权限不能为空
        if (permissions != null || permissions.length != 0) {
            mRequestCode = requestCode;
            for (int i = 0; i < permissions.length; i++) {
                if (!getPermission(permissions[i],activity)) {
                    mListPermissions.add(permissions[i]);
                }
            }
            //遍历完后哪些权限需要申请
            applyPermission(activity);
        }
    }

    //单个权限是否申请完成
    public static boolean getPermission(String permissions, Context context) {
        if (ContextCompat.checkSelfPermission(context, permissions) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //单个权限申请
    private static void applyPermission(Activity activity) {
        if (!mListPermissions.isEmpty()) {
            int size = mListPermissions.size();
            ActivityCompat.requestPermissions(activity, mListPermissions.toArray(new String[size]), mRequestCode);
        }
    }

    /* 检查权限是否全部申请完成
     *  Activity activity
     *  Context context
     *  isSubmit  是否需要申请权限
     *  return 全部权限是否都拿到
     */

    public static boolean permissionEntry(Activity activity,Context context,boolean isSubmit) {
        for (String tPermission : tPermissions) {
            if (!PermissionUtil.getPermission(tPermission, context)) {  // 是否有这个权限
                if (isSubmit) {
                    PermissionUtil.checkPermissions(tPermissions, 300, activity);
                }
                allPermission = false;
                break;
            } else {
                allPermission = true;
            }
        }
        return allPermission;
    }

}
