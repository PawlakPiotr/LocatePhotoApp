package com.example.locate_photo_app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locate_photo_app.R;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.ViewHolder> {
    private ItemData[] itemsData;

    public GalleryItemAdapter(ItemData[] itemsData) {
        this.itemsData = itemsData;
    }

    @Override
    public GalleryItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.txtViewTitle.setText(itemsData[position].getTitle());
        viewHolder.imgViewIcon.setImageBitmap(itemsData[position].getImage());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = itemLayoutView.findViewById(R.id.imgName);
            imgViewIcon = itemLayoutView.findViewById(R.id.imageView);
        }
    }


    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}