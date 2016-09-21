package com.zdfy.purereader.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Yaozong on 2016/9/21.
 */

public class VideoCenterTextView extends TextView {
    public VideoCenterTextView(Context context) {
        super(context);
    }

    public VideoCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private StaticLayout myStaticLayout;
    private TextPaint tp;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView();
    }

    private void initView() {
        tp = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        tp.setTextSize(getTextSize());
        tp.setColor(getCurrentTextColor());
        myStaticLayout = new StaticLayout(getText(), tp, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        myStaticLayout.draw(canvas);
    }
}
