package com.example.page1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.page1.MostVisitedModel;
import com.example.page1.R;

import java.util.List;

public class MostVisitedAdapter extends RecyclerView.Adapter<MostVisitedAdapter.MostVisitedViewHolder> {

    private Context context;
    private List<MostVisitedModel> mostVisitedList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MostVisitedModel model);
    }

    public MostVisitedAdapter(Context context, List<MostVisitedModel> mostVisitedList, OnItemClickListener listener) {
        this.context = context;
        this.mostVisitedList = mostVisitedList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MostVisitedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        return new MostVisitedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostVisitedViewHolder holder, int position) {
        MostVisitedModel model = mostVisitedList.get(position);
        holder.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        return mostVisitedList.size();
    }

    public static class MostVisitedViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public MostVisitedViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            image = itemView.findViewById(R.id.imageView);
        }

        public void bind(final MostVisitedModel model, final OnItemClickListener listener) {
            title.setText(model.getTitle());
            Glide.with(itemView.getContext()).load(model.getImageUrl()).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
        }
    }
}

