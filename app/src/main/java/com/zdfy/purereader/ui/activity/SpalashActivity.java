package com.zdfy.purereader.ui.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import za.co.riggaroo.materialhelptutorial.MaterialTutorialFragment;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.adapter.MaterialTutorialAdapter;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialContract;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialPresenter;
import za.co.riggaroo.materialhelptutorial.view.CirclePageIndicator;

public class SpalashActivity  extends AppCompatActivity implements MaterialTutorialContract.View {
    private static final String TAG = "MaterialTutActivity";
    public static final String MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS = "tutorial_items";
    private ViewPager mHelpTutorialViewPager;
    private View mRootView;
    private TextView mTextViewSkip;
    private ImageButton mNextButton;
    private Button mDoneButton;
    private MaterialTutorialPresenter materialTutorialPresenter;
    private View.OnClickListener finishTutorialClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            materialTutorialPresenter.doneOrSkipClick();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);
        if ((Boolean)SPUtils.get(SpalashActivity.this,Constant.HASIN_SPALSH,false)){
            startActivity(new Intent(SpalashActivity.this,MainActivity.class));
            finish();
        }
        materialTutorialPresenter = new MaterialTutorialPresenter(this, this);
        setStatusBarColor();
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            getActionBar().hide();
        }
        mRootView = findViewById(R.id.activity_help_root);
        mHelpTutorialViewPager = (ViewPager) findViewById(R.id.activity_help_view_pager);
        mTextViewSkip = (TextView) findViewById(R.id.activity_help_skip_textview);
        mNextButton = (ImageButton) findViewById(R.id.activity_next_button);
        mDoneButton = (Button) findViewById(R.id.activity_tutorial_done);
        mTextViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(SpalashActivity.this, Constant.HASIN_SPALSH,true);
                startActivity(new Intent(SpalashActivity.this,MainActivity.class));
                finish();
            }
        });
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(SpalashActivity.this, Constant.HASIN_SPALSH,true);
                startActivity(new Intent(SpalashActivity.this,MainActivity.class));
                finish();
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialTutorialPresenter.nextClick();
            }
        });
        materialTutorialPresenter.loadViewPagerFragments(getTutorialItems(this));
    }
    private void setStatusBarColor() {
        if (isFinishing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @Override
    public void showNextTutorial() {
        int currentItem = mHelpTutorialViewPager.getCurrentItem();
        if (currentItem < materialTutorialPresenter.getNumberOfTutorials()) {
            mHelpTutorialViewPager.setCurrentItem(mHelpTutorialViewPager.getCurrentItem() + 1);
        }
    }
    @Override
    public void showEndTutorial() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setBackgroundColor(int color) {
        mRootView.setBackgroundColor(color);
    }
    @Override
    public void showDoneButton() {
        mTextViewSkip.setVisibility(View.INVISIBLE);
        mNextButton.setVisibility(View.GONE);
        mDoneButton.setVisibility(View.VISIBLE);
    }
    @Override
    public void showSkipButton() {
        mTextViewSkip.setVisibility(View.VISIBLE);
        mNextButton.setVisibility(View.VISIBLE);
        mDoneButton.setVisibility(View.GONE);
    }

    @Override
    public void setViewPagerFragments(List<MaterialTutorialFragment> materialTutorialFragments) {
        mHelpTutorialViewPager.setAdapter(new MaterialTutorialAdapter(getSupportFragmentManager(), materialTutorialFragments));
        CirclePageIndicator mCirclePageIndicator = (CirclePageIndicator) findViewById(za.co.riggaroo.materialhelptutorial.R.id.activity_help_view_page_indicator);
        mCirclePageIndicator.setViewPager(mHelpTutorialViewPager);
        mCirclePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                materialTutorialPresenter.onPageSelected(mHelpTutorialViewPager.getCurrentItem());

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mHelpTutorialViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
                    @Override
                    public void transformPage(View page, float position) {
                        materialTutorialPresenter.transformPage(page, position);
                    }
                }

        );
    }
    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem("PureReader",
                "一种阅读体验",
                R.color.slide_3, 0,0
        );
        TutorialItem tutorialItem2 = new TutorialItem("简洁",
                "却又不失品质",
                R.color.slide_2,  0, 0);
        TutorialItem tutorialItem3  = new TutorialItem("青,取之于蓝而胜于蓝",
                "从这里开始",
                R.color.slide_1,  0, 0);
        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        return tutorialItems;
    }
}