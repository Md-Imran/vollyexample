package com.example.imran.vollyexample.adapter.custom.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bind(T item);
}
