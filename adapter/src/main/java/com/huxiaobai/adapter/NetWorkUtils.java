package com.huxiaobai.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.annotation.NonNull;


/**
 * 作者: 胡庆岭
 * 创建时间: 2020/4/9 18:38
 * 更新时间: 2020/4/9 18:38
 * 描述:网络状态相关类
 */
public class NetWorkUtils {
    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetWrokConnected(@NonNull Context context) {
        ConnectivityManager netManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManger != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network network = netManger.getActiveNetwork();
                NetworkCapabilities capabilities = netManger.getNetworkCapabilities(network);
                if (capabilities == null) {
                    return false;
                }
                return (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));

            } else {
                NetworkInfo networkInfo = netManger.getActiveNetworkInfo();
                if (networkInfo != null) {
                    return networkInfo.isConnected();
                }
            }

        }
        return false;
    }

    /**
     * 判断手机wifi是否连接可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileWifiConnected(@NonNull Context context) {
        ConnectivityManager netManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManger != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network network = netManger.getActiveNetwork();
                NetworkCapabilities capabilities = netManger.getNetworkCapabilities(network);
                if (capabilities == null) {
                    return false;
                }
                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);

            } else {
                NetworkInfo networkInfo = netManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                return networkInfo != null && networkInfo.isConnected();

            }
        }
        return false;
    }

    /**
     * 判断手机移动网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileNetConnected(@NonNull Context context) {
        ConnectivityManager netManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManger != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network network = netManger.getActiveNetwork();
                NetworkCapabilities capabilities = netManger.getNetworkCapabilities(network);
                if (capabilities == null) {
                    return false;
                }
                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            } else {
                NetworkInfo networkInfo = netManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                return networkInfo != null && networkInfo.isConnected();
            }

        }
        return false;
    }

    public static boolean isNetCanUse(@NonNull Context context) {
        return NetWorkUtils.isMobileNetConnected(context) || NetWorkUtils.isMobileWifiConnected(context);
    }
}
