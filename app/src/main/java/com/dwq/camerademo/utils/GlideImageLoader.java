package com.dwq.camerademo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;

/**
 * Created by dwq on 2018/3/16/016.
 * e-mail:lomapa@163.com
 * Glide 工具类
 */

public class GlideImageLoader {


    public GlideImageLoader(int defaultImage) {
        this.defaultImage = defaultImage;
    }

    private int defaultImage;

    /**
     * Url加载图片
     *
     * @param view
     * @param url
     * @param defaultImage
     */
    public static void loadImage(ImageView view, String url, int defaultImage) {
        loadImage(view, url, defaultImage, -1, false, false);
    }

    public static void loadHeaderImage(ImageView imageView, String url, int defaultImage) {
        loadImage(imageView, url, defaultImage, -1, true, false);
    }

    public static void loadImage(ImageView view, String url) {
        loadImage(view, url, -1);
    }

    public static void loadImage(ImageView view, File imgFile) {
        loadImage(view, imgFile, -1, -1, false, true);
    }


    public static void loadImageFitCenter(ImageView view, String url) {
        loadImage(view, url, -1, -1, false, true);
    }

    /**
     * 加载Bitmap
     *
     * @param imageView
     * @param bitmap
     * @param defaultImage
     */
    public static void loadBmpImage(ImageView imageView, Bitmap bitmap, int defaultImage) {
        loadImage(imageView, bitmap, defaultImage, -1, false, false);
    }

    @SuppressLint("CheckResult")
    private static void loadImage(final ImageView view, Object img
            , @DrawableRes int defaultImage
            , @DrawableRes int errorImage
            , boolean isUpdateCache
            , boolean isFitCenter
    ) {
        // 不能崩
        if (view == null) {
//            LogUtils.e("GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        RequestOptions options = new RequestOptions().centerCrop();
        if (defaultImage != -1) {
            options.placeholder(defaultImage);
        }
        if (errorImage != -1) {
            options.error(errorImage);
        }
        // 为头像图片缓存添加signature来判断是否更新缓存
        if (isUpdateCache) {
            options.signature(new ObjectKey(SharedPreferencesUtil.getInstance()
                    .getString("head_signature")));
        }
        if (isFitCenter) {
            options.fitCenter();
        }
        Glide.with(context)
                .load(img)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(view);
    }

}
