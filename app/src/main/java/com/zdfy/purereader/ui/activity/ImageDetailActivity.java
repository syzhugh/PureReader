package com.zdfy.purereader.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_detail)
    ImageView mIvDetail;
    private String picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picUrl = getIntent().getStringExtra(Constant.PIC_URL);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this)
                .load(picUrl)
                .asBitmap()
                .centerCrop()
                .priority(Priority.HIGH)
                .into(mIvDetail);

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

    /**
     * 保存图片到本地
     */
    private void saveImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File pureReaderDir = new File(externalStorageDirectory, "PureReader");
                if (!pureReaderDir.exists()) {
                    pureReaderDir.mkdir();
                }
                try {
                    Bitmap myBitmap = Glide.with(ImageDetailActivity.this)
                            .load(picUrl)
                            .asBitmap()
                            .centerCrop()
                            .into(500, 500)
                            .get();
                    final String url = externalStorageDirectory + "/" + new Date().getTime() + "jpg";
                    File file = new File(pureReaderDir, new Date().getTime() + ".jpg");
                    FileOutputStream fos = new FileOutputStream(file);
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ImageDetailActivity.this, url + "保存成功", Toast.LENGTH_SHORT).show();
                        }
                    });
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
    private void shareSingleImage(String url) {
        try {
            Glide.with(ImageDetailActivity.this)
                    .load(picUrl)
                    .centerCrop()
                    .into(500, 500)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String imagePath = Environment.getExternalStorageDirectory() + File.separator + "aa.png";
        Uri imageUri = Uri.fromFile(new File(imagePath));
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pic, menu);
        return true;
    }
}