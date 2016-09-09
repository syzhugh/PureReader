package com.zdfy.purereader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.zdfy.purereader.utils.UiUtils;

import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

;

/**
 * Created by ZhangPeng on 2016/9/7.
 */
public class NewsAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<ContentlistEntity> datas;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public NewsAdapter(List<ContentlistEntity> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View Item_view = UiUtils.inflate(R.layout.item_recycler_news);
            return new ItemViewHolder(Item_view);
        }
        if (viewType == TYPE_FOOTER) {
            View Footer_view = UiUtils.inflate(R.layout.item_recycler_footerview);
            return new FooterViewHolder(Footer_view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
          
            if (datas.get(position).getImageurls() != null) {
                if (datas.get(position).getImageurls().size()!= 0) {
                    x.image().bind(((ItemViewHolder) holder).mIvNews, datas.get(position).getImageurls().get(0).getUrl());
                }else{
                    return;
                }
            }
                ((ItemViewHolder) holder).mTvPubDate.setText(datas.get(position).getPubDate());
                ((ItemViewHolder) holder).mTvTitle.setText(datas.get(position).getTitle());
               if (mOnItemClickListener!=null){
                   holder.itemView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           mOnItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                       }
                   });
                   holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                       @Override
                       public boolean onLongClick(View view) {
                           mOnItemClickListener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                           return false;
                       }
                   });
               }   
        }
        }
        @Override
        public int getItemCount () {
            return datas == null ? 0 : datas.size() + 1;
        }

        @Override
        public int getItemViewType ( int position){
            return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
        }
        /**
         * 普通条目
         */
        static class ItemViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_title)
            TextView mTvTitle;
            @Bind(R.id.tv_pubDate)
            TextView mTvPubDate;
            @Bind(R.id.iv_news)
            ImageView mIvNews;
            @Bind(R.id.rl_item)
            RelativeLayout mRlItem;
            ItemViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        /**
         * 脚布局加载更多
         */
        static class FooterViewHolder extends RecyclerView.ViewHolder {
            public FooterViewHolder(View footer_view) {
                super(footer_view);
            }
        }
    }
