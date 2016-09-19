package com.zdfy.purereader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.ZhiHuInfo.StoriesEntity;
import com.zdfy.purereader.utils.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/18.
 */
public class ZhiHuAdapter extends BaseAdapter<StoriesEntity> {
    public ZhiHuAdapter(List<StoriesEntity> datas) {
        super(datas);
    }
    @Override
    protected RecyclerView.ViewHolder createViewItemHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(UiUtils.inflate(R.layout.item_zhihu));
        }
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(UiUtils.inflate(R.layout.item_recycler_footerview));
        }
        return null;
    }
    @Override
    protected void showBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Glide.with(UiUtils.getContext())
                    .load(datas.get(position).getImages().get(0))
                    .asBitmap()
                    .centerCrop()
                    .error(R.drawable.no_img)
                    .into(((ItemViewHolder) holder).mImageView);
            ((ItemViewHolder) holder).mTvTitle.setText(datas.get(position).getTitle());
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_view)
        ImageView mImageView;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
