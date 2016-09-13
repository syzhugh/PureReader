package com.zdfy.purereader.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;

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
                builder.setMessage("保存到本地吗?");
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ImageDetailActivity.this, "yes", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}