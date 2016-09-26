package com.zdfy.purereader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.ui.fragment.DouBanFragment;
import com.zdfy.purereader.ui.fragment.NewsFragment;
import com.zdfy.purereader.ui.fragment.PicFragment;
import com.zdfy.purereader.ui.fragment.ZhiHuFragment;
import com.zdfy.purereader.ui.fragment.video.VideoFragment;
import com.zdfy.purereader.ui.qrcode.activity.MCaptureActivity;
import com.zdfy.purereader.utils.SPUtils;
import com.zdfy.purereader.utils.UiUtils;
import com.zdfy.purereader.utils.UpdateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ZhiHuFragment mZhiHuFragment;
    private DouBanFragment mDouBanFragment;
    private NewsFragment mNewsFragment;
    private PicFragment mPicFragment;
    private VideoFragment mVideoFragment;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initData();
        if (((Boolean) SPUtils.get(MainActivity.this, Constant.AUTO_UPDATE, false))) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    UpdateUtils.CheckVersion(MainActivity.this);
                }
            }, 5000);
        }

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mZhiHuFragment = new ZhiHuFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container, mZhiHuFragment);
        fragmentTransaction.commit();

        setToolBarTitle(UiUtils.getString(R.string.ZhiHuJingXuan));
    }

    /**
     * 设置ToolBar标题
     *
     * @param titleName
     */
    private void setToolBarTitle(String titleName) {
        mToolbar.setTitle(titleName);
    }

    private void initViews() {
        setToolBarTitle(UiUtils.getString(R.string.ZhiHuJingXuan));
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        initData();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_qrcode) {
            Log.i("info", "-----------------------------");
            startActivity(new Intent(this, MCaptureActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
//        mFlContainer.removeAllViews();
        if (id == R.id.nav_zhihu) {
            if (mZhiHuFragment == null) {
                mZhiHuFragment = new ZhiHuFragment();
                fragmentTransaction.add(R.id.fl_container, mZhiHuFragment);
            }
            setToolBarTitle(UiUtils.getString(R.string.ZhiHuJingXuan));
            fragmentTransaction.show(mZhiHuFragment);
        } else if (id == R.id.nav_douban) {
            if (mDouBanFragment == null) {
                mDouBanFragment = new DouBanFragment();
                fragmentTransaction.add(R.id.fl_container, mDouBanFragment);
            }
            System.out.println("loadData----DoubanFragment" + System.currentTimeMillis());
//            mDouBanFragment.loadData();
            setToolBarTitle(UiUtils.getString(R.string.DouBanYiKe));
            fragmentTransaction.show(mDouBanFragment);
        } else if (id == R.id.nav_news) {
            if (mNewsFragment == null) {
                mNewsFragment = new NewsFragment();
                fragmentTransaction.add(R.id.fl_container, mNewsFragment);
            }

            setToolBarTitle(UiUtils.getString(R.string.XinWenYueDu));
            fragmentTransaction.show(mNewsFragment);
        } else if (id == R.id.nav_pic) {
            if (mPicFragment == null) {
                mPicFragment = new PicFragment();
                fragmentTransaction.add(R.id.fl_container, mPicFragment);
            }

            setToolBarTitle(UiUtils.getString(R.string.TuPianYueDu));
            fragmentTransaction.show(mPicFragment);

        } else if (id == R.id.nav_video) {
            if (mVideoFragment == null) {
                mVideoFragment = new VideoFragment();
                fragmentTransaction.add(R.id.fl_container, mVideoFragment);
            }
            setToolBarTitle(UiUtils.getString(R.string.ShiPingYueDu));
            fragmentTransaction.show(mVideoFragment);

        }
// else if (id == R.id.nav_about) {
//
//        }
        fragmentTransaction.commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mZhiHuFragment != null) {
            transaction.hide(mZhiHuFragment);
        }
        if (mDouBanFragment != null) {
            transaction.hide(mDouBanFragment);
        }
        if (mNewsFragment != null) {
            transaction.hide(mNewsFragment);
        }
        if (mPicFragment != null) {
            transaction.hide(mPicFragment);
        }
        if (mVideoFragment != null) {
            transaction.hide(mVideoFragment);
        }
    }
}
