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
 *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
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
            Manifest.permission.RECORD_AUDIO,//录音
            Manifest.permission.ACCESS_FINE_LOCATION,//定位
            Manifest.permission.CAMERA,//相机
            Manifest.permission.READ_PHONE_STATE,//读取手机状态
            Manifest.permission.READ_CALL_LOG,//读取通话记录
            Manifest.permission.READ_CONTACTS,//读取联系人
            Manifest.permission.READ_EXTERNAL_STORAGE,//读取sd卡
            Manifest.permission.WRITE_EXTERNAL_STORAGE};//写入sd卡
    private static boolean allPermission=true;

    //检查哪些权限已经获取，将未获取到的权限，再申请一遍
    private static void checkPermissions(String[] permissions, int requestCode, Activity activity) {
        if (permissions != null || permissions.length != 0) {
            mRequestCode = requestCode;
            for (String permission : permissions) {
                if (!isGotPermission(permission, activity)) {
                    mListPermissions.add(permission);
                }
            }
            applyPermissions(activity);
        }
    }

    //判断是否已有某个权限
    public static boolean isGotPermission(String permissions, Context context) {
        return ContextCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED;
    }

    //将集合中的权限全部申请
    private static void applyPermissions(Activity activity) {
        if (!mListPermissions.isEmpty()) {
            int size = mListPermissions.size();
            ActivityCompat.requestPermissions(activity, mListPermissions.toArray(new String[size]), mRequestCode);
        }
    }

    /**
     * 检查数组中所有权限，并申请
     * @param isSubmit 是否需要申请权限
     * @param mRequestCode 在onRequestPermissionsResult中返回
     * @return 全部权限是否都拿到
     */
    public static boolean permissionEntry(Activity activity,Context context,boolean isSubmit,int mRequestCode) {
        if (activity == null || context == null) return false;
        for (String tPermission : tPermissions) {
            if (!PermissionUtil.isGotPermission(tPermission, context)) {  // 是否有这个权限
                if (isSubmit) {
                    PermissionUtil.checkPermissions(tPermissions, mRequestCode, activity);
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
