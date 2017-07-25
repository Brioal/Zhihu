package com.brioal.zhihudaily.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected View rootView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        rootView = itemView;
    }

    //绑定数据到视图
    public abstract void bindView(T t, int position);

}
