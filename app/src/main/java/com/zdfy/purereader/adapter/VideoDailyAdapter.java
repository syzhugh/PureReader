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

    private enum VideType {
        Video, Title;
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
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemListBean.DataBean dataBean = list.get(position).getData();
        if (holder instanceof MHolder0) {
            ((MHolder0) holder).itemVideoTitle.setText(dataBean.getTitle());
            int duration = dataBean.getDuration();
            ((MHolder0) holder).itemVideoType.setText(
                    dataBean.getCategory() + " / " + (duration / 60) + ":" + (duration % 60));


            Glide.with(context)
                    .load(dataBean.getCover().getDetail())
                    .priority(Priority.HIGH)
//                    .fitCenter()
                    .centerCrop()
                    .into(((MHolder0) holder).itemVideoImg);
        } else if (holder instanceof MHolder1) {
            ((MHolder1) holder).textView.setText(dataBean.getText());
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        int type = 0;

        switch (list.get(position).getType()) {
            case "video":
                type = VideType.Video.ordinal();
                break;
            case "textHeader":
                type = VideType.Title.ordinal();
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


}
