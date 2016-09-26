package com.zdfy.purereader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.VideoFindInfo;
import com.zdfy.purereader.domain.VideoInfo;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/26.
 */
public class VideoFavAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private List<VideoInfo.IssueListBean.ItemListBean.DataBean> list;


//    private enum VideoType {
//        Video, Foot;
//    }

    public VideoFavAdapter(Context context, List<VideoInfo.IssueListBean.ItemListBean.DataBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        switch (viewType) {
            case 0:
                View view = inflater.inflate(R.layout.item_video_daily, parent, false);
                holer = new VideoFavAdapter.MHolder(view);
                break;

            case 1:
                break;
        }
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        VideoInfo.IssueListBean.ItemListBean.DataBean dataBean = list.get(position);
        final MHolder mHolder = (MHolder) holder;


        mHolder.itemVideoTitle.setText(dataBean.getTitle());
        int duration = dataBean.getDuration();
        mHolder.itemVideoType.setText(
                dataBean.getCategory() + " / " + (duration / 60) + ":" + (duration % 60));


        Glide.with(context)
                .load(dataBean.getCover().getDetail())
                .priority(Priority.LOW)
                .centerCrop()
                .into(mHolder.itemVideoImg)
        ;

        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(mHolder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return VideoType.Video.ordinal();
//    }

    class MHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_videodaily_img)
        ImageView itemVideoImg;
        @Bind(R.id.item_videodaily_title)
        TextView itemVideoTitle;
        @Bind(R.id.item_videodaily_type)
        TextView itemVideoType;

        public MHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    private VideoDailyAdapter.onItemCicklistener listener;

    public void setlistener(VideoDailyAdapter.onItemCicklistener listener) {
        this.listener = listener;
    }

    public interface onItemCicklistener {
        void onClick(View view, int position);
    }


}
