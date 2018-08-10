package com.dwq.camerademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dwq.camerademo.utils.GlideImageLoader;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {


    private Unbinder unbinder;

    @BindView(R.id.iv_display)
    ImageView ivDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_camera1, R.id.btn_camera2})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_camera1:
                intent = new Intent(this, CameraActivity.class);
                break;
            case R.id.btn_camera2:
                intent = new Intent(this, Camera2Activity.class);
                break;
        }
        startActivityForResult(intent, App.TAKE_PHOTO_CUSTOM);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != 200)
            return;

        if (requestCode == App.TAKE_PHOTO_CUSTOM) {
            File imageFile = new File(data.getStringExtra("imagePath"));
            Log.d("imageFile", "imageFile = " + imageFile.getAbsolutePath());
//            FrescoUtils.load(imageFile.getAbsolutePath()).into(ivDisplay);
            GlideImageLoader.loadImage(ivDisplay, imageFile);
        }


    }

}
