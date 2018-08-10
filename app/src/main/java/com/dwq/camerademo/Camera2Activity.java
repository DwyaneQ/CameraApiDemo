package com.dwq.camerademo;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dwq.camerademo.camera.Camera2TextureView;
import com.dwq.camerademo.event.ImageAvailableEvent;
import com.dwq.camerademo.utils.CommonUtils;
import com.dwq.camerademo.utils.ImageSaver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DWQ on 2018/8/9.
 * E-Mail:lomapa@163.com
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Activity extends AppCompatActivity {

    private Unbinder unbinder;

    //拍照
    @BindView(R.id.cameraTextureView)
    Camera2TextureView cameraTextureView;

    File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera2);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {

    }


    @Override
    public void onResume() {
        super.onResume();
        cameraTextureView.openCamera();
    }

    @Override
    public void onPause() {
        if (cameraTextureView != null) {
            cameraTextureView.closeCamera();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.iv_camera_button)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_camera_button:// 快门拍照
                Toast.makeText(this, "拍照！！！！", Toast.LENGTH_SHORT).show();
                cameraTextureView.takePicture();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //拍照完成后，拿到ImageReader，然后做保存图片的操作
    public void onImageReaderAvailable(ImageAvailableEvent.ImageReaderAvailable imageReaderAvailable) {
        mFile = CommonUtils.createImageFile(System.currentTimeMillis() + ".jpg");
        new Thread(new ImageSaver(imageReaderAvailable.getImageReader(), this, mFile)).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //存储图像完成后，拿到ImagePath图片路径
    public void onImagePathAvailable(ImageAvailableEvent.ImagePathAvailable imagePathAvailable) {
        setResult(200, getIntent().putExtra("imagePath", imagePathAvailable.getImagePath()));
        finish();
    }
}
