package com.dwq.camerademo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


import com.dwq.camerademo.App;

import java.io.File;

public class CommonUtils {

    /**
     * dp转px
     *
     * @param dpValue dp
     * @return px
     */
    public static int dp2px(float dpValue) {
        final float scale = App.sApp.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = App.sApp.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断设备是否具有虚拟导航栏
     * @return 设备是否具有虚拟导航栏
     */
    public static boolean hasNavigationBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) return true;
        WindowManager wm = (WindowManager) App.sApp.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    /**
     * 得到以px为单位的虚拟导航栏高度，若设备没有虚拟导航栏，返回0.
     * @return 虚拟导航栏高度(px)，若设备没有虚拟导航栏，返回0.
     */
    public static int getNavigationBarHeightInPx() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) return dp2px(48);
        int navBarHeightInPx = 0;
        Resources rs = App.sApp.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar()) navBarHeightInPx = rs.getDimensionPixelSize(id);
        return navBarHeightInPx;
    }

    /**
     * 创建File对象，对应于data/data/${packageName}/cache/fileName.
     *
     * @param fileName 文件名
     * @return File
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createImageFile(String fileName) {
        File dir = new File(App.sApp.getExternalCacheDir(), "images");
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, fileName);
    }
}
