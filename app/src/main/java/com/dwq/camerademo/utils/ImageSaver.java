package com.dwq.camerademo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.dwq.camerademo.event.ImageAvailableEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ImageSaver implements Runnable {
    private ImageReader mImageReader;
    private Context context;
    private File file;


    public ImageSaver(ImageReader mImageReader, Context context, File file) {
        this.mImageReader = mImageReader;
        this.context = context;
        this.file = file;
    }


    @Override
    public void run() {
        Image image = mImageReader.acquireLatestImage();

        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        if (bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(270);
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            try {
                FileOutputStream fout = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fout);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageAvailableEvent.ImagePathAvailable imagePathAvailable = new ImageAvailableEvent.ImagePathAvailable();
            imagePathAvailable.setImagePath(file.getAbsolutePath());
            EventBus.getDefault().post(imagePathAvailable);

        }
    }


    /**
     * 保存
     *
     * @param bytes
     * @param file
     * @throws IOException
     */
    private void save(byte[] bytes, File file) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

}
