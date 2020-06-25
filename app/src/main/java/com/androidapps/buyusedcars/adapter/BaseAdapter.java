package com.androidapps.buyusedcars.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder> {
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new MyViewHolder(binding);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Object obj = getObjForPosition(position);
        ViewModel viewModel = getViewmodel();
        holder.bind(obj, viewModel);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj, ViewModel viewModel) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.obj, obj);
            binding.executePendingBindings();
        }
    }

    protected abstract Object getObjForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);

    protected abstract ViewModel getViewmodel();
}