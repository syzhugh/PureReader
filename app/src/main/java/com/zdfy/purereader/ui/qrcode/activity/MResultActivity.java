package com.zdfy.purereader.ui.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.ui.qrcode.decode.DecodeThread;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MResultActivity extends AppCompatActivity {

    public static final String SCAN_RESULT = "result";

    public static final String SCAN_MODE = "scanMode";
    public static final String FROMCAMERA = "fromCamera";
    public static final String FROMFILES = "fromFiles";


    @Bind(R.id.capresult_img)
    ImageView capresultImg;
    @Bind(R.id.capresult_line2)
    LinearLayout lineTime;
    @Bind(R.id.capresult_time)
    TextView capresultTime;
    @Bind(R.id.capresult_result)
    TextView capresultResult;
    @Bind(R.id.capresult_toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcoderesult);
        ButterKnife.bind(this);

        initView();


        initResultData();

    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initResultData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (!(bundle == null)) {
            String mode = bundle.getString(SCAN_MODE);
            Log.i("info", "-----------------------------" + mode);

            if (mode.equals(FROMCAMERA))
                setView1(bundle);
            else if (mode.equals(FROMFILES))
                setView2(bundle);
        }
    }

    private void setView2(Bundle bundle) {
        capresultImg.setVisibility(View.GONE);
        lineTime.setVisibility(View.GONE);
        String result = bundle.getString(SCAN_RESULT);
        capresultResult.setText(result);
    }

    private void setView1(Bundle bundle) {
        String result = bundle.getString(SCAN_RESULT);
        String time = bundle.getString(DecodeThread.DECODE_TIME);
        byte[] byteArray = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        capresultResult.setText(result);
        capresultTime.setText(time);
        capresultImg.setImageBitmap(bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
