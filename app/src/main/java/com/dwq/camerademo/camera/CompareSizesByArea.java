package com.dwq.camerademo.camera;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;

import java.util.Comparator;

/**
 * Created by Razer on 2017/4/9.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CompareSizesByArea implements Comparator<Size>
{
    @Override
    public int compare(Size lhs, Size rhs)
    {
        // We cast here to ensure the multiplications won't overflow
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                (long) rhs.getWidth() * rhs.getHeight());
    }
}
