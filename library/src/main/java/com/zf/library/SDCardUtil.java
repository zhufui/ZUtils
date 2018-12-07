package com.zf.library;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * author: zhufu
 * email : zhufui@sina.com
 * time  : 2018/03/10
 * desc  : sd卡工具类
 * version : 1.0
 */

public final class SDCardUtil {
    private SDCardUtil() {
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true:可用,false:不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) {
            throw new NullPointerException("sdcard permission denied");
        }
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        return strArray[1].replace("/.android_secure", "") + File.separator;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO(bufferedReader);
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡data路径
     *
     * @return SD卡data路径
     */
    public static String getDataPath() {
        if (!isSDCardEnable()) {
            throw new NullPointerException("sdcard permission denied");
        }
        return StringUtil.append(Environment.getExternalStorageDirectory().getPath(), File.separator, "data", File.separator);
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间, 将字节数转换成了KB, MB, GB
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpaceStr() {
        return ConvertUtil.byte2MemorySize(getFreeSpace());
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return 剩余空间的字节数
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getFreeSpace() {
        if (!isSDCardEnable()) {
            throw new NullPointerException("sdcard permission denied");
        }
        StatFs stat = new StatFs(getSDCardPath());
        long availableBlocks = stat.getAvailableBlocksLong();
        long blockSize = stat.getBlockSizeLong();
        return availableBlocks * blockSize;
    }

    /**
     * 获取SD卡总空间
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getTotalSpaceStr() {
        return ConvertUtil.byte2MemorySize(getTotalSpace());
    }

    /**
     * 获取SD卡总空间
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getTotalSpace() {
        if (!isSDCardEnable()) {
            throw new NullPointerException("sdcard permission denied");
        }
        StatFs stat = new StatFs(getSDCardPath());
        long availableBlocks = stat.getBlockCountLong();
        long blockSize = stat.getBlockSizeLong();
        return availableBlocks * blockSize;
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo() {
        if (!isSDCardEnable()) return null;
        SDCardInfo sd = new SDCardInfo();
        sd.isExist = true;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes();
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd;
    }

    public static class SDCardInfo {
        public boolean isExist;//sd卡是否存在
        public long totalBlocks;
        public long freeBlocks;
        public long availableBlocks;
        public long blockByteSize;
        public long totalBytes;
        public long freeBytes;
        public long availableBytes;
    }
}
