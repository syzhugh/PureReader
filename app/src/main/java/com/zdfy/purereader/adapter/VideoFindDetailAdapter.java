package com.zdfy.purereader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.VideoFindInfo;
import com.zdfy.purereader.ui.view.VideoCenterTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/23.
 */

public class VideoFindDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoFindInfo.ItemListBean> list;
    private LayoutInflater inflater;

    public VideoFindDetailAdapter(Context context, List<VideoFindInfo.ItemListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_video_daily, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("info", "VideoFindDetailAdapter:onBindViewHolder----------------------");
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.itemVideodailyType.setText(
                list.get(position).getData().getTitle());
        holder1.itemVideodailyTitle.setText("" + position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_videodaily_title)
        VideoCenterTextView itemVideodailyTitle;
        @Bind(R.id.item_videodaily_type)
        TextView itemVideodailyType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
