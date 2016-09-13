package com.zdfy.purereader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/11.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
    protected List<T> datas;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }
    public OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public BaseAdapter(List<T> datas) {
        this.datas = datas;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewItemHolder(parent,viewType);
    }


    /**
     * 根据类型创建holder
     * @param parent
     * @param viewType
     */
    protected abstract RecyclerView.ViewHolder createViewItemHolder(ViewGroup parent,int viewType);
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            
             showBindItemViewHolder(holder,position);
            
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
    /**
     * 绑定ItemViewHolder 控件和数据
     * @param holder
     * @param position
     */
    protected abstract void showBindItemViewHolder(RecyclerView.ViewHolder holder, int position);
    @Override
    public int getItemCount () {
        return datas == null ? 0 : datas.size() + 1;
    }
    @Override
    public int getItemViewType ( int position){
        return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
    }
    /**
     * 脚布局加载更多
     */
    protected static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View footer_view) {
            super(footer_view);
        }
    }
}
