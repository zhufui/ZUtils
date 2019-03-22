package com.zf.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: zhufu
 * email : zhufui@sina.com
 * time  : 2018/03/18
 * desc  : 获取设备信息,设备的相关操作
 * version : 1.0
 */

public final class DeviceUtil {
    public DeviceUtil() {
    }

    /**
     * 判断cpu是否是64位的
     *
     * @return
     */
    public static boolean isCPU64() {
        boolean result = false;
        String mProcessor = null;
        try {
            mProcessor = getFieldFromCpuinfo("Processor");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mProcessor != null) {
            if (mProcessor.contains("aarch64")) {
                result = true;
            }
        }
        return result;
    }

    private static String getFieldFromCpuinfo(String field) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));
        Pattern p = Pattern.compile(field + "\\s*:\\s*(.*)");

        try {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    return m.group(1);
                }
            }
        } finally {
            br.close();
        }
        return null;
    }

    /**
     * 获取CPU核数
     *
     * @return
     */
    public static int getCPUCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取CPU型号
     *
     * @return
     */
    public static String getCPUName() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 查询cpu的ABIs
     *
     * @return
     */
    public static String[] getCPUABIs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS;
        }
        return new String[]{Build.CPU_ABI};
    }

    /**
     * @param activity
     * @return 返回的是屏幕密度和屏幕密度dpi
     */
    @SuppressLint("NewApi")
    public static int[] getDensity(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return new int[]{(int) displayMetrics.density, displayMetrics.densityDpi};
    }

    /**
     * 顶部状态栏的高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 底部导航的高度
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取真实的屏幕分辨率
     *
     * @return int[]{宽度,高度}
     */
    @SuppressLint("NewApi")
    public static int[] getRealMetrics(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    /**
     * 判断设备是否root
     *
     * @return true:是,false:否
     */
    public static boolean isRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取系统SDK版本号
     *
     * @return 系统版本号
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取IMEI
     * 需要权限<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @return
     */
    @SuppressLint({"MissingPermission"})
    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            Log.e("DeviceUtil", "getIMEI error", e);
        }
        return null;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public static Device getDevice() {
        Device device = new Device();
        device.phoneBrand = android.os.Build.BRAND;
        device.phoneModel = android.os.Build.MODEL;
        device.buildLevel = android.os.Build.VERSION.SDK_INT;
        device.buildVersion = android.os.Build.VERSION.RELEASE;
        return device;
    }

    public static class Device {
        public String phoneBrand;   //手机品牌
        public String phoneModel;   //手机型号
        public int buildLevel;      //手机Android API等级(22,23)
        public String buildVersion; //手机Android 版本(5.0,6.0,7.0...)
    }
}
