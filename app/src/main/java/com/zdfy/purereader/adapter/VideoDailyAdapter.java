package com.zdfy.purereader.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.VideoInfo;
import com.zdfy.purereader.domain.VideoInfo.IssueListBean.ItemListBean;
import com.zdfy.purereader.utils.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/21.
 */

public class VideoDailyAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private List<ItemListBean> list;

    public VideoDailyAdapter(Context context, List<ItemListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    private enum VideoType {
        Video, Title, Foot;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_video_daily, parent, false);
                holder = new MHolder0(view);
                break;
            case 1:
                view = new LinearLayout(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                view.setLayoutParams(layoutParams);
                holder = new MHolder1(view);
                break;
            case 2:
                view = inflater.inflate(R.layout.item_recycler_footerview, parent, false);
                holder = new FootViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MHolder0) {
            ItemListBean.DataBean dataBean = list.get(position).getData();
            ((MHolder0) holder).itemVideoTitle.setText(dataBean.getTitle());
            int duration = dataBean.getDuration();
            ((MHolder0) holder).itemVideoType.setText(
                    dataBean.getCategory() + " / " + (duration / 60) + ":" + (duration % 60));

            Log.i("test", "type:" + (list.get(position).getType()));
            Log.i("test", "dataBean:" + (dataBean == null));
            Log.i("test", "getCover:" + (dataBean.getCover() == null));
            Log.i("test", "getDetail:" + (dataBean.getCover().getDetail() == null));


            Glide.with(context)
                    .load(dataBean.getCover().getDetail())
                    .priority(Priority.NORMAL)
                    .centerCrop()
                    .into(((MHolder0) holder).itemVideoImg);


            if (listener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(holder.itemView, position);
                    }
                });
            }

        } else if (holder instanceof MHolder1) {
            ItemListBean.DataBean dataBean = list.get(position).getData();
            ((MHolder1) holder).textView.setText(dataBean.getText());
        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;

        return list.size() == 0 ? list.size() : (list.size() + 1);
    }


    @Override
    public int getItemViewType(int position) {
        int type = 0;

        if (position + 1 == getItemCount())
            return VideoType.Foot.ordinal();

        switch (list.get(position).getType()) {
            case "video":
                type = VideoType.Video.ordinal();
                break;
            case "textHeader":
                type = VideoType.Title.ordinal();
                break;
        }

        return type;
    }

    class MHolder0 extends RecyclerView.ViewHolder {
        @Bind(R.id.item_videodaily_img)
        ImageView itemVideoImg;
        @Bind(R.id.item_videodaily_title)
        TextView itemVideoTitle;
        @Bind(R.id.item_videodaily_type)
        TextView itemVideoType;

        public MHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class MHolder1 extends RecyclerView.ViewHolder {
        TextView textView;

        public MHolder1(View view) {
            super(view);
            LinearLayout linearLayout = (LinearLayout) view;
            linearLayout.setGravity(Gravity.CENTER);

            textView = new TextView(context);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);

            linearLayout.addView(textView);

        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View footer_view) {
            super(footer_view);
        }
    }

    private onItemCicklistener listener;

    public void setlistener(onItemCicklistener listener) {
        this.listener = listener;
    }

    public interface onItemCicklistener {
        void onClick(View view, int position);
    }


}
