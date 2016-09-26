package com.zdfy.purereader.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.utils.DataCleanManager;
import com.zdfy.purereader.utils.SPUtils;
import com.zdfy.purereader.utils.ToastUtils;
import com.zdfy.purereader.utils.UiUtils;
import com.zdfy.purereader.utils.UpdateUtils;
import com.zdfy.purereader.utils.VersionUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_update_byhand)
    TextView mTvUpdateByhand;
    @Bind(R.id.tv_auto_update)
    TextView mTvAutoUpdate;
    @Bind(R.id.switch_autoUpdate)
    Switch mSwitchAutoUpdate;
    @Bind(R.id.tv_version)
    TextView mTvVersion;
    @Bind(R.id.tv_des)
    TextView mTvDes;
    @Bind(R.id.tv_clear_cache)
    TextView mTvClearCache;
    @Bind(R.id.tv_cache_size)
    TextView mTvCacheSize;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }

    /**
     * 处理事务
     */
    private void initEvents() {
        mSwitchAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ToastUtils.showToast(SettingsActivity.this, "自动更新打开~");
                    SPUtils.put(SettingsActivity.this, Constant.AUTO_UPDATE, true);
                } else {
                    ToastUtils.showToast(SettingsActivity.this, "自动更新关闭~");
                    SPUtils.put(SettingsActivity.this, Constant.AUTO_UPDATE, false);
                }
            }
        });
    }

    /**
     * 初始化版本信息描述
     */
    private void initVersionDes() {
        mTvDes.setText(R.string.UPDATE_DES);
    }

    /**
     * 设置当前缓存大小
     */
    private void setCacheSize() {
        File file = new File(String.valueOf(UiUtils.getContext().getCacheDir()));
        try {
            mTvCacheSize.setText(DataCleanManager.getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setCacheSize();
    }

    private void initViews() {
        mSwitchAutoUpdate.setChecked((Boolean) SPUtils.get(SettingsActivity.this, Constant.AUTO_UPDATE, false));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setCacheSize();
        mTvVersion.setText("当前版本: " + VersionUtils.getVersionName(this));
        initVersionDes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tv_update_byhand, R.id.tv_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_byhand:
                final ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
                progressDialog.setMessage("正在检查更新当中...");
                progressDialog.show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUtils.CheckVersion(SettingsActivity.this);
                        progressDialog.dismiss();
                    }
                }, 2000);

                break;
            case R.id.tv_clear_cache:
                DataCleanManager.cleanInternalCache(SettingsActivity.this);
                setCacheSize();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SettingsActivity.this", "onDestroy");
    }
}
