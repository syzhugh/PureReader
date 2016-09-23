package com.zdfy.purereader.ui.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.ui.fragment.video.CustomMediaController;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    public static final String FILENAME = "fileName";
    public static final String PLAYURI = "playUri";


    @Bind(R.id.video_play_buffer)
    VideoView videoView;
    @Bind(R.id.video_play_progressbar)
    ProgressBar progressbar;
    @Bind(R.id.video_play_download_rate)
    TextView downloadRate;
    @Bind(R.id.video_play_load_rate)
    TextView loadRate;


    private Uri uri;


    private String fileName;
    private String playUri;
    private MediaController mediaController;
    private CustomMediaController customController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*设置全屏*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*必须*/
        Vitamio.isInitialized(this);

        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);


        init();
        initVideoView();

    }

    private void init() {
        fileName = getIntent().getStringExtra(FILENAME);
        playUri = getIntent().getStringExtra(PLAYURI);

        mediaController = new MediaController(this);
        customController = new CustomMediaController(this, videoView, this);
        customController.setFileName(fileName);
    }


    private void initVideoView() {

        videoView.setMediaController(customController);//设置媒体控制器。
        /*设置视频质量。 low medium high*/
        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        /*设置显示时间，过后隐藏*/
        mediaController.show(5000);
        /*获取焦点方法，extends from View*/
        videoView.requestFocus();
        /*注册一个回调函数，在有警告或错误信息时调用。例如：开始缓冲、缓冲结束、下载速度变化。*/
        videoView.setOnInfoListener(this);
        /*注册一个回调函数，在网络视频流缓冲变化时调用。*/
        videoView.setOnBufferingUpdateListener(this);
        /* 注册一个回调函数，在视频预处理完成后调用,在视频预处理完成后被调用。
        * 此时视频的宽度、高度、宽高比信息已经获取到，此时可调用seekTo让视频从指定位置开始播放。*/
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });

        if (playUri == null) {
            return;
        }

        uri = Uri.parse(playUri);
        videoView.setVideoURI(uri);
    }


    /*-------------videoview各种接口，用于监听加载状态--------------*/
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    progressbar.setVisibility(View.VISIBLE);
                    downloadRate.setText("");
                    downloadRate.setVisibility(View.VISIBLE);
                    loadRate.setText("");
                    loadRate.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                videoView.start();
                progressbar.setVisibility(View.GONE);
                loadRate.setVisibility(View.GONE);
                downloadRate.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRate.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRate.setText(percent + "%");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (videoView != null) {
            videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }


    /*----------activity----------------*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!videoView.isPlaying())
            videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }
}
