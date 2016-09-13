package com.zdfy.purereader.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.GankImgInfo.ResultsEntity;
import com.zdfy.purereader.ui.activity.ImageDetailActivity;
import com.zdfy.purereader.utils.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/13.
 */
public class ImgAdapter extends BaseAdapter<ResultsEntity> {
    
    public ImgAdapter(List<ResultsEntity> datas) {
        super(datas);
    }

    @Override
    protected RecyclerView.ViewHolder createViewItemHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(UiUtils.inflate(R.layout.img_item));
        }
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(UiUtils.inflate(R.layout.item_recycler_footerview));
        }
        return null;
    }

    @Override
    protected void showBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mTvTitle.setText(datas.get(position).getCreatedAt());
            Glide.with(UiUtils.getContext())
                    .load(datas.get(position).getUrl())
                    .asBitmap()
                    .centerCrop()
                    .error(R.drawable.no_img)
                    .into(((ItemViewHolder) holder).mIvMn);
            ((ItemViewHolder) holder).mIvMn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.setClass(UiUtils.getContext(), ImageDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.PIC_URL,datas.get(position).getUrl());
                    UiUtils.getContext().startActivity(intent);
                }
            });
        }
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_mn)
        ImageView mIvMn;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
