package com.zf.library;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * fragment的管理类
 */
public class FragmentUtils {
    /**
     * 替换
     *
     * @param fragment
     * @param layoutId
     */
    public static void replace(Fragment fragment, int layoutId) {
        FragmentManager fm = fragment.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(layoutId, fragment);
        ft.commitAllowingStateLoss();
        //为什么不使用commit
        //因为在Activity调用onSaveInstanceState()之后并且还未调用onCreate,onResume,onStart中的任意一个方法之前，
        // 调用了commit方法或者按了返回键调用了onBackPressI()方法都会抛出异常。
//        ft.commit();
    }

    /**
     * 添加fragment
     *
     * @param fragment
     * @param layoutId
     * @param tagName
     */
    public static void add(Fragment fragment, int layoutId, String tagName) {
        FragmentManager fm = fragment.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(layoutId, fragment, tagName);
        ft.commitAllowingStateLoss();
    }
}
