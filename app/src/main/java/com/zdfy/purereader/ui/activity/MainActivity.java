package com.zdfy.purereader.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.zdfy.purereader.R;
import com.zdfy.purereader.ui.fragment.BaseFragment;
import com.zdfy.purereader.ui.fragment.NewsFragment;
import com.zdfy.purereader.ui.fragment.PicFragment;
import com.zdfy.purereader.ui.fragment.VideoFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.fl_container)
    FrameLayout mFlContainer;
    private BaseFragment mNewsFragment;
    private BaseFragment mPicFragment;
    private BaseFragment mVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mNewsFragment = new NewsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container, mNewsFragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
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
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
        if (id == R.id.nav_news) {
            if (mNewsFragment == null) {
                mNewsFragment = new NewsFragment();
                fragmentTransaction.add(R.id.fl_container, mNewsFragment);
            }
            fragmentTransaction.show(mNewsFragment);
        } else if (id == R.id.nav_pic) {
            if (mPicFragment == null) {
                mPicFragment = new PicFragment();
                fragmentTransaction.add(R.id.fl_container, mPicFragment);
            }
            fragmentTransaction.show(mPicFragment);
        } else if (id == R.id.nav_video) {
            if (mVideoFragment == null) {
                mVideoFragment = new VideoFragment();
                fragmentTransaction.add(R.id.fl_container, mVideoFragment);
            }
            fragmentTransaction.show(mVideoFragment);
        }
        fragmentTransaction.commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFragments(FragmentTransaction transaction) {
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
