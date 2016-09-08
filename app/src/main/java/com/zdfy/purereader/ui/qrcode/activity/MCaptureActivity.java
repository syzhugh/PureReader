package com.zdfy.purereader.ui.qrcode.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.zdfy.purereader.ui.qrcode.camera.CameraManager;
import com.zdfy.purereader.ui.qrcode.utils.BeepManager;
import com.zdfy.purereader.ui.qrcode.utils.InactivityTimer;
import com.zdfy.purereader.ui.qrcode.utils.LightManager;

/**
 * Created by Yaozong on 2016/9/8.
 */
public class MCaptureActivity extends AppCompatActivity {

    /*view*/

    /*核心*/

    /*相机*/
    private CameraManager cameraManager;

    /*解码*/
    private int decodeMode;

    /*图形变换*/
    private Rect cropRect;

    /*工具：蜂鸣 震动 闪光灯 计时器*/
    private BeepManager beepManager;
    private LightManager lightManager;
    private InactivityTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Rect getCropRect() {
        return cropRect;
    }

    public void initCrop() {

    }

    public int getDataMode() {
        return 0;
    }

    public Handler getHandler() {
        return null;
    }

    public void handleDecode(String obj, Bundle data) {

    }
}
