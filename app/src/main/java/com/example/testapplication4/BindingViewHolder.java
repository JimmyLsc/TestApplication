package com.example.testapplication4;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBinding;

    public BindingViewHolder(View v) {
        super(v);
        mBinding = DataBindingUtil.bind(v);
    }

    public BindingViewHolder setBinding(int variableId , Object object){
        mBinding.setVariable(variableId , object);
        mBinding.executePendingBindings();
        return this;
    }

}
