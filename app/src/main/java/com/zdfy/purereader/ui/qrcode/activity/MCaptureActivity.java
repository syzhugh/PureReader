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
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.zdfy.purereader.R;
import com.zdfy.purereader.ui.qrcode.camera.CameraManager;
import com.zdfy.purereader.ui.qrcode.decode.DecodeThread;
import com.zdfy.purereader.ui.qrcode.decode.DecodeUtils;
import com.zdfy.purereader.ui.qrcode.utils.BeepManager;
import com.zdfy.purereader.ui.qrcode.utils.CaptureActivityHandler;
import com.zdfy.purereader.ui.qrcode.utils.CommonUtils;
import com.zdfy.purereader.ui.qrcode.utils.InactivityTimer;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Yaozong on 2016/9/8.
 */

public class MCaptureActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {

    public static final String TAG = "MCaptureActivity";

    /*view*/
    private RelativeLayout container;

    @Bind(R.id.cap_surface)
    SurfaceView capSurface;

    @Bind(R.id.cap_shade_error)
    ImageView capShadeError;
    @Bind(R.id.cap_scan)
    ImageView capScanbar;
    @Bind(R.id.cap_cropview)
    RelativeLayout capCropview;

    @Bind(R.id.cap_bt_fromfiles)
    ImageView capBtFromfiles;
    @Bind(R.id.cap_bt_light)
    ImageView capBtLight;
    @Bind(R.id.cap_bt_qrcode)
    TextView capBtQrcode;
    @Bind(R.id.cap_bt_barcode)
    TextView capBtBarcode;

    /*核心*/
    private CaptureActivityHandler capActHandler;

    /*相机*/
    private CameraManager cameraManager;

    /*解码*/
    private int dataMode = DecodeUtils.DECODE_DATA_MODE_QRCODE;
    private Rect cropRect;

    /*图形变换*/
    private ObjectAnimator mScanMaskObjectAnimator = null;
    private int mQrcodeCropWidth;
    private int mQrcodeCropHeight;
    private int mBarcodeCropWidth;
    private int mBarcodeCropHeight;

    /*工具：蜂鸣 震动 计时器 闪光灯*/
    private BeepManager beepManager;
    private InactivityTimer timer;
    private boolean isLightOn;

    /*switch*/
    private boolean hasSurface;


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

        capBtQrcode.setSelected(true);

        beepManager = new BeepManager(this);
        timer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        Log.i("info", "onResume-----------------------------");
        super.onResume();

        cameraManager = new CameraManager(getApplication());

        capActHandler = null;

        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(capSurface.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            capSurface.getHolder().addCallback(this);
        }

    }

    /*-------------核心部分--------------*/
    public void initCrop() {
        Log.i("info", "initCrop-----------------------------");
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
        Log.i("info", "initCamera-----------------------------");
        if (holder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
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

            cameraSuccess();
        } catch (IOException ioe) {
            cameraFailed();
        } catch (RuntimeException e) {
            cameraFailed();
        }

    }


    /*------------响应操作---------------*/
    private void cameraSuccess() {
        Log.i("info", "cameraSuccess-----------------------------");
        capShadeError.setVisibility(View.GONE);
        initCrop();
        ViewHelper.setPivotX(capScanbar, 0f);
        ViewHelper.setPivotY(capScanbar, 0f);
        mScanMaskObjectAnimator = ObjectAnimator.ofFloat(capScanbar, "scaleY", 0.0f, 1.0f);
        mScanMaskObjectAnimator.setDuration(2000);
        mScanMaskObjectAnimator.setInterpolator(new DecelerateInterpolator());
        mScanMaskObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mScanMaskObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        mScanMaskObjectAnimator.start();

    }

    private void cameraFailed() {
        Log.i("info", "cameraFailed-----------------------------");
        capShadeError.setVisibility(View.VISIBLE);
        Toast.makeText(MCaptureActivity.this, "开启失败", Toast.LENGTH_SHORT).show();
    }


    public void handleDecode(String result, Bundle bundle) {
        Log.i("info", "handleDecode-----------------------------");

        beepManager.playBeepSoundAndVibrate();

        int mode = bundle.getInt(DecodeThread.DECODE_MODE);
        String time = bundle.getString(DecodeThread.DECODE_TIME);
        Log.i("info", "resule  " + result
                + "\nmode  " + mode
                + "\ntime  " + time);


        Log.i("info","isUrl"+CommonUtils.isUrl(result));
        if (!CommonUtils.isEmpty(result) && CommonUtils.isUrl(result)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MResultActivity.class);
            intent.putExtra(MResultActivity.SCAN_RESULT, result);
            intent.putExtra(MResultActivity.SCAN_BUNDLE, bundle);
            startActivity(intent);
        }
        finish();
    }

    /*-------------surface接口--------------*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("info", "surfaceCreated-----------------------------");
        if (holder == null) {

        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("info", "surfaceChanged-----------------------------");
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("info", "surfaceDestroyed-----------------------------");
        hasSurface = false;
    }

    /*-----------模式选择----------------*/

    @OnClick({R.id.cap_bt_fromfiles, R.id.cap_bt_light, R.id.cap_bt_qrcode, R.id.cap_bt_barcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cap_bt_fromfiles:
                Log.i("info", "cap_bt_fromfiles");
                startActFromFiles();
                break;
            case R.id.cap_bt_light:
                Log.i("info", "cap_bt_light");
                changeLightState();
                break;
            case R.id.cap_bt_qrcode:
                Log.i("info", "cap_bt_qrcode");
                changeToQRCode();
                break;
            case R.id.cap_bt_barcode:
                Log.i("info", "cap_bt_barcode");
                changeToBarCode();
                break;
        }
    }

    /*框图变化*/
    private int currentMode = 0;

    private void changeToQRCode() {
        if (currentMode == 0)
            return;
        capBtQrcode.setSelected(true);
        capBtBarcode.setSelected(false);
        PropertyValuesHolder bar2qrWidthVH = PropertyValuesHolder.ofFloat("width",
                1.0f, (float) mQrcodeCropWidth / mBarcodeCropWidth);
        PropertyValuesHolder bar2qrHeightVH = PropertyValuesHolder.ofFloat("height",
                1.0f, (float) mQrcodeCropHeight / mBarcodeCropHeight);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(bar2qrWidthVH, bar2qrHeightVH);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float fractionW = (Float) animation.getAnimatedValue("width");
                Float fractionH = (Float) animation.getAnimatedValue("height");

                RelativeLayout.LayoutParams parentLayoutParams = (RelativeLayout.LayoutParams) capCropview.getLayoutParams();
                parentLayoutParams.width = (int) (mBarcodeCropWidth * fractionW);
                parentLayoutParams.height = (int) (mBarcodeCropHeight * fractionH);
                capCropview.setLayoutParams(parentLayoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initCrop();
                setDataMode(DecodeUtils.DECODE_DATA_MODE_QRCODE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        valueAnimator.start();
        currentMode = 0;
    }

    private void changeToBarCode() {
        if (currentMode == 1)
            return;
        capBtQrcode.setSelected(false);
        capBtBarcode.setSelected(true);
        PropertyValuesHolder qr2barWidthVH = PropertyValuesHolder.ofFloat("width",
                1.0f, (float) mBarcodeCropWidth / mQrcodeCropWidth);
        PropertyValuesHolder qr2barHeightVH = PropertyValuesHolder.ofFloat("height",
                1.0f, (float) mBarcodeCropHeight / mQrcodeCropHeight);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(qr2barWidthVH, qr2barHeightVH);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float fractionW = (Float) animation.getAnimatedValue("width");
                Float fractionH = (Float) animation.getAnimatedValue("height");

                RelativeLayout.LayoutParams parentLayoutParams = (RelativeLayout.LayoutParams) capCropview.getLayoutParams();
                parentLayoutParams.width = (int) (mQrcodeCropWidth * fractionW);
                parentLayoutParams.height = (int) (mQrcodeCropHeight * fractionH);
                capCropview.setLayoutParams(parentLayoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initCrop();
                setDataMode(DecodeUtils.DECODE_DATA_MODE_BARCODE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        valueAnimator.start();
        currentMode = 1;
    }

    private void startActFromFiles() {

    }

    private void changeLightState() {
        isLightOn = !isLightOn;
        capBtLight.setSelected(isLightOn);
        cameraManager.setTorch(isLightOn);
    }


    /*-------------getter&setter--------------*/


    public Rect getCropRect() {
        return cropRect;
    }

    public void setCropRect(Rect rect) {
        this.cropRect = rect;
    }

    public int getDataMode() {
        return dataMode;
    }

    private void setDataMode(int dataMode) {
        this.dataMode = dataMode;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Handler getHandler() {
        return capActHandler;
    }


}
