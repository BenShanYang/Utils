package com.benshanyang.toolslibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具类
 */
public class PermissionUtil {

    public static final int REQUEST_CODE = 1024;//权限请求码
    private static final int GPS_SETTIONG_REQUEST = 1025;//打开gps定位服务的请求码

    /**
     * 存储权限
     */
    public static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 拍照访问相册权限
     */
    public static final String[] CAMERA_STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    /**
     * 所有定位权限
     */
    public static String[] LOCATION_PERMISSION = null;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //大于或等于 Android 10/Android Q
            LOCATION_PERMISSION = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            };
        } else {
            LOCATION_PERMISSION = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
        }
    }

    /**
     * 检查权限
     *
     * @param context    上下文
     * @param permission 权限
     * @return
     */
    public static boolean check(@NonNull Context context, @NonNull String permission) {
        return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 批量检查权限
     *
     * @param context     上下文
     * @param permissions 权限数组
     * @return
     */
    public static boolean check(@NonNull Context context, @NonNull String... permissions) {
        boolean isPermission = true;
        if (context != null && permissions != null && permissions.length > 0) {
            for (int i = 0; i < permissions.length; i++) {
                isPermission = (isPermission && (ContextCompat.checkSelfPermission(context, permissions[i]) == PackageManager.PERMISSION_GRANTED));
                if (!isPermission) {
                    break;
                }
            }
        } else {
            isPermission = false;
        }
        return isPermission;
    }

    /**
     * 获取拒绝的权限
     *
     * @param context     上下文
     * @param permissions 权限数组
     * @return
     */
    public static List<String> denied(@NonNull Context context, @NonNull String... permissions) {
        List<String> deniedArr = new ArrayList<>();
        if (context != null && permissions != null && permissions.length > 0) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    //被拒绝的权限
                    deniedArr.add(permission);
                }
            }
        }
        return deniedArr;
    }

    /**
     * Activity中请求权限
     *
     * @param activity    Activity页面引用
     * @param permissions 权限数组
     */
    public static void request(final @NonNull Activity activity, final @NonNull String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
    }

    /**
     * Fragment中请求权限
     *
     * @param fragment    Fragment页面引用
     * @param permissions 权限数组
     */
    public static void request(final @NonNull Fragment fragment, final @NonNull String... permissions) {
        fragment.requestPermissions(permissions, REQUEST_CODE);
    }

    /**
     * Activity中请求权限
     *
     * @param activity    Activity页面引用
     * @param requestCode 请求码
     * @param permissions 权限数组
     */
    public static void request(final @NonNull Activity activity, final @IntRange(from = 0) int requestCode, final @NonNull String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * Fragment中请求权限
     *
     * @param fragment    Fragment页面引用
     * @param requestCode 请求码
     * @param permissions 权限数组
     */
    public static void request(final @NonNull Fragment fragment, final @IntRange(from = 0) int requestCode, final @NonNull String... permissions) {
        fragment.requestPermissions(permissions, requestCode);
    }

    /**
     * 权限申请后，判断是否都授予权限了
     *
     * @param grantResults 权限申请结果
     * @return
     */
    public static boolean grantResults(@NonNull int[] grantResults) {
        boolean isGrant = true;
        if (grantResults != null && grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                isGrant = (isGrant && grantResults[i] == PackageManager.PERMISSION_GRANTED);
            }
        } else {
            isGrant = false;
        }
        return isGrant;
    }

    /**
     * 判断GPS是否开启
     *
     * @param context 上下文
     * @return true 表示开启
     */
    public static final boolean isOPenGPS(final Context context) {
        if (context != null) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            //boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            //return gps || network;
            return gps;
        } else {
            return false;
        }
    }

    /**
     * 打开gps设置页面
     *
     * @param activity Activity引用
     */
    public static final void openGPSSetting(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivityForResult(intent, GPS_SETTIONG_REQUEST);
        }
    }

    /**
     * 打开权限设置页面
     *
     * @param activity    Activity上下文
     * @param packageName 传入 BuildConfig.APPLICATION_ID
     */
    public static final void openSetting(Activity activity, String packageName) {
        if (activity != null) {
            String sdk = Build.VERSION.SDK; // SDK号
            String model = Build.MODEL; // 手机型号
            String release = Build.VERSION.RELEASE; // android系统版本号
            String brand = Build.BRAND;//手机厂商
            if (TextUtils.isEmpty(brand)) {
                brand = Build.MANUFACTURER.toLowerCase();
            }
            if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                //红米、小米
                gotoMiuiPermission(activity);
            } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                //魅族
                gotoMeizuPermission(activity, packageName);
            } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                //华为、荣耀
                gotoHuaweiPermission(activity);
            } else if (TextUtils.equals(brand.toLowerCase(), "oppo")) {
                //oppo
                gotoOPPOPermission(activity, packageName);
            } else if (TextUtils.equals(brand.toLowerCase(), "vivo")) {
                //vivo
                gotoVIVOPermission(activity);
            } else {
                //其他
                activity.startActivity(getAppDetailSettingIntent(activity));
            }
        }
    }

    /**
     * 跳转到oppo的权限管理页面
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void gotoOPPOPermission(Context context, String packageName) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", packageName);
            ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 跳转到vivo的权限管理页面
     *
     * @param context 上下文
     */
    public static void gotoVIVOPermission(Context context) {
        try {
            Intent intent;
            if (((Build.MODEL.contains("Y85")) && (!Build.MODEL.contains("Y85A"))) || (Build.MODEL.contains("vivo Y53L"))) {
                intent = new Intent();
                intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
                intent.putExtra("packagename", context.getPackageName());
                intent.putExtra("tabId", "1");
                context.startActivity(intent);
            } else {
                intent = new Intent();
                intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
                intent.setAction("secure.intent.action.softPermissionDetail");
                intent.putExtra("packagename", context.getPackageName());
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 跳转到miui的权限管理页面
     *
     * @param context 上下文
     */
    public static void gotoMiuiPermission(Context context) {
        try { // MIUI 8
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(intent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(intent);
            } catch (Exception e1) { // 否则跳转到应用详情
                context.startActivity(getAppDetailSettingIntent(context));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void gotoMeizuPermission(Context context, String packageName) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 华为的权限管理页面
     *
     * @param context 上下文
     */
    public static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return intent;
    }

    /**
     * 请求权限完毕后回调接口，fragment去实现，在对应Activity去调用该方法
     */
    public interface ResultCallBack {
        void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }

}
