package com.example.page1.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.page1.SecondModel;
import com.example.page1.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder> {
    private List<SecondModel> dataList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public SecondAdapter(Context context, OnItemClickListener onItemClickListener, List<SecondModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.secondrecyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SecondModel data = dataList.get(position);
        holder.textTitle.setText(data.getTitle());
        holder.textAddress.setText(data.getAddress());

        // Load image asynchronously
        new ImageLoadTask(data.getImageUrl(), holder.imageView).execute();
        holder.bind(data, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onSecondItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textAddress;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textAddress = itemView.findViewById(R.id.location);
            imageView = itemView.findViewById(R.id.placeimg);
        }

        public void bind(final SecondModel data, final OnItemClickListener listener) {
            // Bind data to view elements
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSecondItemClick(getAdapterPosition());
                }
            });
        }
    }

    // AsyncTask to load images asynchronously
    private static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String imageUrl;
        private ImageView imageView;

        public ImageLoadTask(String imageUrl, ImageView imageView) {
            this.imageUrl = imageUrl;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL url = new URL(imageUrl);
                InputStream inputStream = url.openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                // Handle case where image loading failed
                // You can set a placeholder image or hide the ImageView
             //   imageView.setImageResource(R.drawable.placeholder); // Assuming you have a placeholder drawable
            }
        }
    }
}
