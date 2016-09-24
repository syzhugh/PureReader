package com.zdfy.purereader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.VideoFindInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Yaozong on 2016/9/23.
 */

public class VideoFindDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoFindInfo.ItemListBean> list;
    private LayoutInflater inflater;
    private RequestManager manager;

    public VideoFindDetailAdapter(Context context, List<VideoFindInfo.ItemListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        manager = Glide.with(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_video_finddetail, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        VideoFindInfo.ItemListBean.DataBean bean = list.get(position).getData();
        manager.load(bean.getCover().getBlurred())
                .centerCrop()
                .priority(Priority.NORMAL)
                .into(viewHolder.blurred);
        viewHolder.title.setText(bean.getTitle());
        viewHolder.description.setText(bean.getDescription());
        viewHolder.toplay.setUp(bean.getPlayUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");

        if (position == 0)
            ((ViewHolder) holder).getShade().setAlpha(0f);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_video_finddetail_blurred)
        ImageView blurred;
        @Bind(R.id.item_video_finddetail_title)
        TextView title;
        @Bind(R.id.item_video_finddetail_description)
        TextView description;
        @Bind(R.id.item_video_finddetail_toplay)
        JCVideoPlayerStandard toplay;

        @Bind(R.id.item_video_finddetail_shade)
        ImageView shade;

        private int videoProgress = 0;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        public ImageView getShade() {
            return shade;
        }

        public JCVideoPlayerStandard getToplay() {
            return toplay;
        }

        public int getVideoProgress() {
            return videoProgress;
        }

        public void setVideoProgress(int videoProgress) {
            this.videoProgress = videoProgress;
        }
    }


}
