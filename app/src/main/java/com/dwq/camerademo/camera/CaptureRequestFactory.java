package com.dwq.camerademo.camera;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Surface;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CaptureRequestFactory
{
    //创建预览请求，后面的setXXX方法是根据不同情况设置预览参数
    public static CaptureRequest.Builder createPreviewBuilder(CameraDevice cameraDevice, Surface surface) throws CameraAccessException
    {
        CaptureRequest.Builder previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        previewBuilder.addTarget(surface);
        return previewBuilder;
    }

    //设置预览-自动聚焦模式
    public static void setPreviewBuilderPreview(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        Log.d("AutoFocus","自动聚焦！");
        previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
    }

    //设置预览-锁定焦点
    public static void setPreviewBuilderLockfocus(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
    }

    //设置预览-解除锁定焦点
    public static void setPreviewBuilderUnlockfocus(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
        previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
    }

    //设置预览-聚焦区域
    public static void setPreviewBuilderFocusRegion(CaptureRequest.Builder previewBuilder, MeteringRectangle[] meteringRectangleArr) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, meteringRectangleArr);
        previewBuilder.set(CaptureRequest.CONTROL_AE_REGIONS, meteringRectangleArr);
        previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO);
    }

    //设置预览-开始手动聚焦
    public static void setPreviewBuilderFocusTrigger(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
        previewBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_START);
    }



    // 设置预览-防手抖
    public static CaptureRequest setPreviewBuilderSteady(CaptureRequest.Builder previewBuilder, boolean antiShake) throws CameraAccessException
    {
        if(antiShake){
            previewBuilder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, CameraMetadata.LENS_OPTICAL_STABILIZATION_MODE_ON);
        }else {
            previewBuilder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, CameraMetadata.LENS_OPTICAL_STABILIZATION_MODE_OFF);
        }
        return previewBuilder.build();
    }


    //创建拍照请求
    public static CaptureRequest.Builder createCaptureStillBuilder(CameraDevice cameraDevice, Surface surface) throws CameraAccessException
    {
        CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
        captureBuilder.addTarget(surface);
        return captureBuilder;
    }


    //设置拍照模式-连续拍摄
    public static void setCaptureBuilderPrecapture(CaptureRequest.Builder captureBuilder) throws CameraAccessException
    {
        captureBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
    }


    //设置拍照模式-延时拍着
    public static void setCaptureBuilderDelay(CaptureRequest.Builder captureBuilder, long nanoseconds)
    {
        captureBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, nanoseconds);
    }




    //创建录像请求
    public static CaptureRequest.Builder createRecordBuilder(CameraDevice cameraDevice, List<Surface> surfaces) throws CameraAccessException
    {
        CaptureRequest.Builder recordBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
        recordBuilder.addTarget(surfaces.get(0));
        recordBuilder.addTarget(surfaces.get(1));
        return recordBuilder;
    }

    //设置预览模式-录像预览
    public static void setPreviewBuilderRecordPreview(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO);
//        previewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    }

}
