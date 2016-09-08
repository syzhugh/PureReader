package com.zdfy.purereader.ui.qrcode.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.ObjectAnimator;
import com.zdfy.purereader.R;
import com.zdfy.purereader.ui.qrcode.camera.CameraManager;
import com.zdfy.purereader.ui.qrcode.decode.DecodeUtils;
import com.zdfy.purereader.ui.qrcode.utils.BeepManager;
import com.zdfy.purereader.ui.qrcode.utils.CaptureActivityHandler;
import com.zdfy.purereader.ui.qrcode.utils.InactivityTimer;
import com.zdfy.purereader.utils.CommonUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/8.
 */
public class MCaptureActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {

    public static final String TAG = "MCaptureActivity";

    /*view*/
    private RelativeLayout container;

    @Bind(R.id.cap_surface)
    SurfaceView capSurface;
    @Bind(R.id.cap_cropview_cropbar)
    ImageView capCropviewCropbar;
    @Bind(R.id.cap_cropview)
    RelativeLayout capCropview;
    @Bind(R.id.cap_bt_fromfiles)
    ImageView capBtFromfiles;
    @Bind(R.id.cap_bt_light)
    ImageView capBtLight;
    @Bind(R.id.cap_bt_qrcode)
    ImageView capBtQrcode;
    @Bind(R.id.cap_bt_barcode)
    ImageView capBtBarcode;
    @Bind(R.id.cap_shade_error)
    ImageView capShadeError;

    /*核心*/
    private CaptureActivityHandler capActHandler;

    /*相机*/
    private CameraManager cameraManager;

    /*解码*/
    private int dataMode = DecodeUtils.DECODE_DATA_MODE_QRCODE;

    /*图形变换*/
    private Rect cropRect;
    private ObjectAnimator mScanMaskObjectAnimator = null;
    private int mQrcodeCropWidth;
    private int mQrcodeCropHeight;
    private int mBarcodeCropWidth;
    private int mBarcodeCropHeight;

    /*工具：蜂鸣 震动 计时器*/
    private BeepManager beepManager;
    private InactivityTimer timer;

    /*switch*/
    private boolean hasSurface;
    private boolean isLightOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_qrcodecapture, null);
        setContentView(container);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        hasSurface = false;
        isLightOn = false;
        mQrcodeCropWidth = getResources().getDimensionPixelSize(R.dimen.qrcode_width);
        mQrcodeCropHeight = getResources().getDimensionPixelSize(R.dimen.qrcode_height);

        mBarcodeCropWidth = getResources().getDimensionPixelSize(R.dimen.barcode_width);
        mBarcodeCropHeight = getResources().getDimensionPixelSize(R.dimen.barcode_height);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(this);
        capActHandler = null;

        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(capSurface.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            capSurface.getHolder().addCallback(this);
        }

        timer.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (capActHandler != null) {
            capActHandler.quitSynchronously();
            capActHandler = null;
        }

        beepManager.close();
        timer.onPause();
        cameraManager.closeDriver();

        if (!hasSurface) {
            capSurface.getHolder().removeCallback(this);
        }

        if (null != mScanMaskObjectAnimator && mScanMaskObjectAnimator.isStarted()) {
            mScanMaskObjectAnimator.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.shutdown();
    }

    /*-------------核心部分--------------*/
    public void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        int[] location = new int[2];
        capCropview.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1];

        int cropWidth = capCropview.getWidth();
        int cropHeight = capCropview.getHeight();

        int containerWidth = container.getWidth();
        int containerHeight = container.getHeight();

        int x = cropLeft * cameraWidth / containerWidth;
        int y = cropTop * cameraHeight / containerHeight;

        int width = cropWidth * cameraWidth / containerWidth;
        int height = cropHeight * cameraHeight / containerHeight;

        setCropRect(new Rect(x, y, width + x, height + y));
    }

    private void initCamera(SurfaceHolder holder) {
        if (holder == null) {
            Log.i(TAG, "initCamera: SurfaceHolder" + (holder == null));
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(holder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (capActHandler == null) {
                capActHandler = new CaptureActivityHandler(this, cameraManager);
            }
            onCameraPreviewSuccess();
        } catch (IOException ioe) {

        } catch (RuntimeException e) {

        }
    }

    private void onCameraPreviewSuccess() {
        initCrop();
        capShadeError.setVisibility(View.GONE);
    }

    private void onCameraPreviewFailed() {
        capShadeError.setVisibility(View.VISIBLE);
    }

    public void handleDecode(String result, Bundle bundle) {
        timer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        if (!CommonUtils.isEmpty(result) && CommonUtils.isUrl(result)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MResultActivity.class);
            bundle.putString("result", result);
            intent.putExtra("result", bundle);
            startActivity(intent);
        }
    }

    /*-------------surface接口--------------*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.i(TAG, "surfaceCreated: SurfaceHolder" + (holder == null));
        }
        if (!hasSurface)
            hasSurface = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    /*-----------模式选择----------------*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*来自本地  闪光灯*/
            case R.id.cap_bt_fromfiles:
                startActFromFiles();
                break;
            case R.id.cap_bt_light:
                changeLightState();
                break;

            /*二维码 条形码*/
            case R.id.cap_bt_qrcode:
                changeToQRCode();
                break;
            case R.id.cap_bt_barcode:
                changeToBarCode();
                break;
        }
    }

    private void changeToBarCode() {
    }

    private void changeToQRCode() {
    }

    private void changeLightState() {
    }

    private void startActFromFiles() {

    }

    /*-------------getter&setter--------------*/
    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Rect getCropRect() {
        return cropRect;
    }

    public void setCropRect(Rect rect) {
        this.cropRect = rect;
    }


    public int getDataMode() {
        return 0;
    }

    public Handler getHandler() {
        return null;
    }


}
