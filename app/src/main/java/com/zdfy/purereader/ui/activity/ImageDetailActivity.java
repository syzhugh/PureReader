package com.zdfy.purereader.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.github.kayvannj.permission_utils.Func;
import com.github.kayvannj.permission_utils.PermissionUtil;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.utils.ToastUtils;
import com.zdfy.purereader.utils.UiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_STORAGE = 2;
    private static final String PACKAGE_URL_SCHEME = "package:";
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    private PermissionUtil.PermissionRequestObject mStoragePermissionRequest;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_detail)
    ImageView mIvDetail;
    private String picUrl;
    private String picName;
    private File pureReaderDir;
    private File externalStorageDirectory;
    //文件的地址
    private String mFileurl;
    //是否已经保存过文件
    private File isHasSavedFile;
    private boolean isClickShare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        initPermissions();
        initToolbar();
        initEvent();
    }

    private void initEvent() {
        mIvDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageDetailActivity.this);
                builder.setMessage("小伙好眼力,赶紧收了<(￣︶￣)>?");
                builder.setCancelable(false);
                builder.setNegativeButton("算了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveImage();
                    }
                });
                builder.create().show();
                return false;
            }
        });
    }

    private void initData() {
        picUrl = getIntent().getStringExtra(Constant.PIC_URL);
        picName = getIntent().getStringExtra(Constant.PIC_CREATE);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPermissions() {
        mStoragePermissionRequest = PermissionUtil.with(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).onAllGranted(
                new Func() {
                    @Override
                    protected void call() {
                        doOnPermissionGranted();
                    }
                }).onAnyDenied(
                new Func() {
                    @Override
                    protected void call() {
                        doOnPermissionDenied();
                    }
                }).ask(REQUEST_CODE_STORAGE);
    }

    private void initFiles() {
        try {
            externalStorageDirectory = Environment.getExternalStorageDirectory();
            pureReaderDir = new File(externalStorageDirectory, "PureReader");
            if (!pureReaderDir.exists()) {
                pureReaderDir.mkdir();
            }
            mFileurl = pureReaderDir + "/" + picName;
            isHasSavedFile = new File(pureReaderDir, picName + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doOnPermissionDenied() {
        UiUtils.ShowSnackBarPermission(mCoordinatorLayout, ImageDetailActivity.this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if (mStoragePermissionRequest != null) {
            mStoragePermissionRequest.ask(REQUEST_CODE_STORAGE);
        }
    }

    private void doOnPermissionGranted() {
        initFiles();
        if (isHasSavedFile.exists()) {
            Glide.with(this)
                    .load(isHasSavedFile)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .into(mIvDetail);
        } else {
            Glide.with(this)
                    .load(picUrl)
                    .asBitmap()
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .into(mIvDetail);
        }
    }

    /**
     * 保存图片到本地
     */
    private void saveImage() {
        System.out.println(mFileurl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap myBitmap = Glide.with(ImageDetailActivity.this)
                            .load(picUrl)
                            .asBitmap()
                            .centerCrop()
                            .into(500, 800)
                            .get();
                    if (isHasSavedFile.exists()) {
                        if (!isClickShare) {
                            UiUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(ImageDetailActivity.this, "图片已存在");
                                }
                            });
                        }
                        return;
                    } else {
                        isHasSavedFile = new File(pureReaderDir, picName + ".jpg");
                        FileOutputStream fos = new FileOutputStream(isHasSavedFile);
                        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        if (!isClickShare) {
                            UiUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(ImageDetailActivity.this, "保存到" + mFileurl);
                                }
                            });
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //分享单张图片
    private void shareSingleImage() {
        if (!isHasSavedFile.exists()) {
            saveImage();
            SystemClock.sleep(1000);
        }
        isHasSavedFile = new File(pureReaderDir, picName + ".jpg");
        Uri imageUri = Uri.fromFile(isHasSavedFile);
        Log.d("share", "uri:" + imageUri);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_save) {
            //此时的点击的是保存按钮
            isClickShare = false;
            saveImage();
        }
        if (item.getItemId() == R.id.action_share) {
            //此时点击的是分享按钮
            isClickShare = true;
            shareSingleImage();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pic, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mStoragePermissionRequest != null)
            mStoragePermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}