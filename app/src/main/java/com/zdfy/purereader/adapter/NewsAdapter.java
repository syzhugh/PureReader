package com.zdfy.purereader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.zdfy.purereader.utils.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/7.
 */
public class NewsAdapter extends BaseAdapter<ContentlistEntity> {
    public NewsAdapter(List<ContentlistEntity> datas) {
        super(datas);
    }

    @Override
    protected RecyclerView.ViewHolder createViewItemHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View Item_view = UiUtils.inflate(R.layout.item_douban);
            return new ItemViewHolder(Item_view);
        }
        if (viewType == TYPE_FOOTER) {
            View Footer_view = UiUtils.inflate(R.layout.item_recycler_footerview);
            return new FooterViewHolder(Footer_view);
        }
        return null;
    }

    @Override
    protected void showBindItemViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            if (datas.get(position).getImageurls() != null) {
                if (datas.get(position).getImageurls().size() != 0) {
                    Glide.with(UiUtils.getContext())
                            .load(datas.get(position).getImageurls().get(0).getUrl())
                            .asBitmap()
                            .centerCrop()
                            .error(R.drawable.no_img)
                            .into(((ItemViewHolder) holder).mImageView);

                } else {
                    return;
                }
            }
            ((ItemViewHolder) holder).mTvSummary.setText(datas.get(position).getPubDate());
            ((ItemViewHolder) holder).mTvTitle.setText(datas.get(position).getTitle());
        }
    }

    /**
     * 普通条目
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_view)
        ImageView mImageView;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_summary)
        TextView mTvSummary;
        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
