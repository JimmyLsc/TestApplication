package com.example.testapplication4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

public class EventBindingAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private ObservableList<Event> eventList;

    public EventBindingAdapter(Context context, ObservableList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new BindingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.setBinding(BR.event, eventList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
