package com.benshanyang.toolslibrary.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: NetUtil
 * @Description: 网络工具
 * @Author: YangKuan
 * @CreateDate: 2021/1/29 18:52
 */
public class NetUtils {

    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_3G = "3g";
    public static final String NETWORK_TYPE_2G = "2g";
    public static final String NETWORK_TYPE_WAP = "wap";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 获取网络类型
     *
     * @param context 上下文
     * @return
     */
    public static int getNetworkType(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
            return networkInfo == null ? -1 : networkInfo.getType();
        }
        return -1;
    }

    /**
     * 获取网络名称
     *
     * @param context 上下文
     * @return
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getNetworkTypeName(Context context) {
        if (context == null) {
            return "";
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        String type = NETWORK_TYPE_DISCONNECT;
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return type;
        }

        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                type = NETWORK_TYPE_WIFI;
            } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                //String proxyHost = android.net.Proxy.getDefaultHost();//deprecated
                String proxyHost = System.getProperty("http.proxyHost");
                type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G) : NETWORK_TYPE_WAP;
            } else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }

    /**
     * 网路是否连接
     *
     * @param context 上下文
     * @return
     */
    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnected() && (info.getState() == NetworkInfo.State.CONNECTED);
        }
        return false;
    }

    /**
     * 网络可用性
     *
     * @param context 上下文
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            try {
                ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivity != null) {
                    NetworkInfo info = connectivity.getActiveNetworkInfo();
                    return info.isAvailable();
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否wifi
     *
     * @param context 上下文
     * @return
     */
    public static boolean isWiFi(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            // wifi的状态：ConnectivityManager.TYPE_WIFI、3G的状态：ConnectivityManager.TYPE_MOBILE
            return networkInfo == null ? false : networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    /**
     * 打开网络设置界面
     *
     * @param activity 上下文
     */
    public static void openNetSetting(Activity activity) {
        if (activity != null) {
            try {
                Intent intent = new Intent("/");
                ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                intent.setComponent(cm);
                intent.setAction("android.intent.action.VIEW");
                activity.startActivityForResult(intent, 0);
            } catch (Exception e) {
                try {
                    activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //直接进入手机中的wifi网络设置界面
                } catch (Exception exception) {

                }
            }
        }
    }


    /**
     * Whether is fast mobile network
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    private static boolean isFastMobileNetwork(Context context) {
        if (context == null) {
            return false;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 设置wifi状态
     *
     * @param context 上下文
     * @param enabled 设置wifi状态
     */
    public static void setWifiEnabled(Context context, boolean enabled) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(enabled);
        }
    }

    /**
     * 获取wifi列表
     *
     * @param context 上下文
     * @return
     */
    public static List<ScanResult> getWifiScanResults(Context context) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return wifiManager.startScan() ? wifiManager.getScanResults() : new ArrayList<ScanResult>();
        }
        return new ArrayList<ScanResult>();
    }

    /**
     * 过滤扫描结果
     *
     * @param context 上下文
     * @param bssid   访问点的地址
     * @return
     */
    public static ScanResult getScanResultsByBSSID(Context context, String bssid) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            ScanResult scanResult = null;
            boolean f = wifiManager.startScan();
            if (!f) {
                getScanResultsByBSSID(context, bssid);
            }
            List<ScanResult> list = wifiManager.getScanResults();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    scanResult = list.get(i);
                    if (scanResult.BSSID.equals(bssid)) {
                        break;
                    }
                }
            }
            return scanResult;
        }
        return null;
    }

    /**
     * 获取wifi连接信息
     *
     * @param context 上下文
     * @return
     */
    public static WifiInfo getWifiConnectionInfo(Context context) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return wifiManager.getConnectionInfo();
        }
        return null;
    }
}
