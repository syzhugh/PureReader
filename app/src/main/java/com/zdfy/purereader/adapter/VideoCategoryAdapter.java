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
import com.bumptech.glide.RequestManager;
import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.VideoCategoryInfo;
import com.zdfy.purereader.domain.VideoInfo.IssueListBean.ItemListBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/21.
 */

public class VideoCategoryAdapter extends RecyclerView.Adapter<VideoCategoryAdapter.MViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<VideoCategoryInfo> list;
    private RequestManager manager;

    public VideoCategoryAdapter(Context context, List<VideoCategoryInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        manager = Glide.with(context);
    }


    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_video_category, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, final int position) {

        manager.load(list.get(position).getBgPicture())
                .centerCrop()
                .priority(Priority.NORMAL)
                .into(holder.categoryImg);
        holder.categoryType.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_video_category_img)
        ImageView categoryImg;
        @Bind(R.id.item_video_category_type)
        TextView categoryType;

        MViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /*------------点击事件接口---------------*/
    private onItemCicklistener listener;

    public void setlistener(onItemCicklistener listener) {
        this.listener = listener;
    }

    public interface onItemCicklistener {
        void onClick(View view, int position);
    }


}
