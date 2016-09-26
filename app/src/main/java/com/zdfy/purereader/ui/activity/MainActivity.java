package com.zdfy.purereader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
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
import com.zdfy.purereader.ui.fragment.PicFragment;
import com.zdfy.purereader.ui.fragment.ZhiHuFragment;
import com.zdfy.purereader.ui.fragment.video.VideoFragment;
import com.zdfy.purereader.ui.qrcode.activity.MCaptureActivity;
import com.zdfy.purereader.utils.SPUtils;
import com.zdfy.purereader.utils.UiUtils;
import com.zdfy.purereader.utils.UpdateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.zdfy.purereader.constant.Constant.SAVED_INDEX;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ZhiHuFragment mZhiHuFragment;
    private DouBanFragment mDouBanFragment;
    //    private NewsFragment mNewsFragment;
    private PicFragment mPicFragment;
    private VideoFragment mVideoFragment;
    private Handler mHandler = new Handler();
    private String[] TAG_FRAGMENTs = {"ZhiHuFragment", "DouBanFragment", "PicFragment", "VideoFragment"};
    private int tempId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        /***************************修复Fragment重叠现象start*********************************/
        FragmentManager fragmentManager = getSupportFragmentManager();
        int index = 0;
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(SAVED_INDEX, index);
            mZhiHuFragment = (ZhiHuFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENTs[0]);
            mDouBanFragment = (DouBanFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENTs[1]);
            mPicFragment = (PicFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENTs[2]);
            mVideoFragment = (VideoFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENTs[3]);
            setSelect(index);
        }
      
        /***************************修复Fragment重叠现象end*********************************/
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
        fragmentTransaction.add(R.id.fl_container, mZhiHuFragment, TAG_FRAGMENTs[0]);
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

    List<Integer> mIntegers = new ArrayList<>();

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        setSelect(id);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_INDEX, tempId);
        super.onSaveInstanceState(outState);
    }
    private void setSelect(int id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
        if (id == R.id.nav_zhihu) {
            if (mZhiHuFragment == null) {
                mZhiHuFragment = new ZhiHuFragment();
                fragmentTransaction.add(R.id.fl_container, mZhiHuFragment, TAG_FRAGMENTs[0]);
            }
            setToolBarTitle(UiUtils.getString(R.string.ZhiHuJingXuan));
            fragmentTransaction.show(mZhiHuFragment);
            mIntegers.add(id);
            tempId = R.id.nav_zhihu;
        } else if (id == R.id.nav_douban) {
            if (mDouBanFragment == null) {
                mDouBanFragment = new DouBanFragment();
                fragmentTransaction.add(R.id.fl_container, mDouBanFragment, TAG_FRAGMENTs[1]);
            }
            setToolBarTitle(UiUtils.getString(R.string.DouBanYiKe));
            fragmentTransaction.show(mDouBanFragment);
            mIntegers.add(id);
            tempId = R.id.nav_douban;
        } else if (id == R.id.nav_pic) {
            if (mPicFragment == null) {
                mPicFragment = new PicFragment();
                fragmentTransaction.add(R.id.fl_container, mPicFragment, TAG_FRAGMENTs[2]);
            }
            setToolBarTitle(UiUtils.getString(R.string.TuPianYueDu));
            fragmentTransaction.show(mPicFragment);

            mIntegers.add(id);
            tempId = R.id.nav_pic;
        } else if (id == R.id.nav_video) {
            if (mVideoFragment == null) {
                mVideoFragment = new VideoFragment();
                fragmentTransaction.add(R.id.fl_container, mVideoFragment, TAG_FRAGMENTs[3]);
            }
            setToolBarTitle(UiUtils.getString(R.string.ShiPingYueDu));
            fragmentTransaction.show(mVideoFragment);
            mIntegers.add(id);
            tempId = R.id.nav_video;
        }

        if (id == R.id.nav_seetings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mZhiHuFragment != null) {
            transaction.hide(mZhiHuFragment);
        }
        if (mDouBanFragment != null) {
            transaction.hide(mDouBanFragment);
        }
        if (mPicFragment != null) {
            transaction.hide(mPicFragment);
        }
        if (mVideoFragment != null) {
            transaction.hide(mVideoFragment);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        int id=tempId;
        int id = mIntegers.size() > 0 ? mIntegers.get(mIntegers.size() - 1) : R.id.nav_zhihu;
        System.out.println("onRestart~~~~~~~~~~"+id);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
        if (id == R.id.nav_zhihu) {
            if (mZhiHuFragment == null) {
                mZhiHuFragment = new ZhiHuFragment();
                fragmentTransaction.add(R.id.fl_container, mZhiHuFragment);
            }
            setToolBarTitle(UiUtils.getString(R.string.ZhiHuJingXuan));
            fragmentTransaction.show(mZhiHuFragment);
            mNavView.setCheckedItem(R.id.nav_zhihu);
        } else if (id == R.id.nav_douban) {
            if (mDouBanFragment == null) {
                mDouBanFragment = new DouBanFragment();
                fragmentTransaction.add(R.id.fl_container, mDouBanFragment);
            }
            setToolBarTitle(UiUtils.getString(R.string.DouBanYiKe));
            fragmentTransaction.show(mDouBanFragment);
            mNavView.setCheckedItem(R.id.nav_douban);
        } else if (id == R.id.nav_pic) {
            if (mPicFragment == null) {
                mPicFragment = new PicFragment();
                fragmentTransaction.add(R.id.fl_container, mPicFragment);
            }
            setToolBarTitle(UiUtils.getString(R.string.TuPianYueDu));
            fragmentTransaction.show(mPicFragment);
            mNavView.setCheckedItem(R.id.nav_pic);
        } else if (id == R.id.nav_video) {
            if (mVideoFragment == null) {
                mVideoFragment = new VideoFragment();
                fragmentTransaction.add(R.id.fl_container, mVideoFragment);
            }
            setToolBarTitle(UiUtils.getString(R.string.ShiPingYueDu));
            fragmentTransaction.show(mVideoFragment);
            mNavView.setCheckedItem(R.id.nav_video);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
