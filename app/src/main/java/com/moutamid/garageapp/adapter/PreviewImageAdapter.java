package com.moutamid.garageapp.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.moutamid.garageapp.R;

import java.util.ArrayList;

public class PreviewImageAdapter extends RecyclerView.Adapter<PreviewImageAdapter.PreviewImageViewHolder> {

    private ArrayList<Uri> imageUriList;
    public PreviewImageAdapter(ArrayList<Uri> imageUriList) {
        this.imageUriList = imageUriList;
    }

    @NonNull
    @Override
    public PreviewImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview, parent, false);
        return new PreviewImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewImageViewHolder holder, int position) {
        Uri imageUri = imageUriList.get(position);
        Glide.with(holder.imageView.getContext()).load(imageUri).into(holder.imageView);
//        holder.btnRemove.setVisibility(View.GONE); // Hide the remove button for the preview
    }

    @Override
    public int getItemCount() {
        return imageUriList.size();
    }

    public static class PreviewImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
//        Button btnRemove;

        public PreviewImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
//            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
