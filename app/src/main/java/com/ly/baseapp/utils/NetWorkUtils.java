package com.ly.baseapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络连接
 * Created by admin on 2016/11/24.
 */

public class NetWorkUtils {
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 3; // Default network thread pool size;

    /**
     * 设置当前线程数
     *
     * @param context 上下文
     * @return 数目
     */
    public static int adjustThreadCount(Context context) {
        if (context == null) {
            return DEFAULT_NETWORK_THREAD_POOL_SIZE;
        }
        ConnectivityManager connectivityManager = getService(context, Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnectedOrConnecting()) {
            return DEFAULT_NETWORK_THREAD_POOL_SIZE;
        }
        switch (info.getType()) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_WIMAX:
            case ConnectivityManager.TYPE_ETHERNET:
                return 4;
            case ConnectivityManager.TYPE_MOBILE:
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_LTE:  // 4G
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                        return 4;
                    case TelephonyManager.NETWORK_TYPE_UMTS: // 3G
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        return 3;
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 2G
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return 2;
                    default:
                        return DEFAULT_NETWORK_THREAD_POOL_SIZE;
                }
            default:
                return DEFAULT_NETWORK_THREAD_POOL_SIZE;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Context context, String service) {
        return (T) context.getSystemService(service);
    }
}
