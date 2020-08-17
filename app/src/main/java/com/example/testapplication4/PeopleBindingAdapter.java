package com.example.testapplication4;

import android.content.Context;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication4.databinding.PeopleFragmentBinding;

public class PeopleBindingAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private ObservableList<People> peopleList;
    private Context context;

    public PeopleBindingAdapter(Context context, ObservableList<People> peopleList) {
        this.peopleList = peopleList;
        this.context = context;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_item, parent, false);
        return new BindingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.setBinding(BR.people, peopleList.get(position));
        Log.d(PeopleFragment.TAG, String.valueOf(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if (peopleList==null) {
            return 0;
        }
        return peopleList.size();
    }
}