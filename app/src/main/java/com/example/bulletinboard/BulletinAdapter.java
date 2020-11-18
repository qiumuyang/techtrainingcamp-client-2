package com.example.bulletinboard;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BulletinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "BulletinAdapter";
    
    private final int STYLE0 = 0;
    private final int STYLE1 = 1;
    private final int STYLE2 = 2;
    private final int STYLE3 = 3;
    private final int STYLE4 = 4;
    private List<Bulletin> bulletinList;

    static class BasicViewHolder extends RecyclerView.ViewHolder{
        TextView bTitle;
        TextView bAuthor;
        TextView bPublishTime;
        public BasicViewHolder(View view) {
            super(view);
            bTitle = (TextView) view.findViewById(R.id.title_text);
            bAuthor = (TextView) view.findViewById(R.id.author_text);
            bPublishTime = (TextView) view.findViewById(R.id.publishtime_text);
        }
    }

    static class ViewHolderWithSingleImage extends BasicViewHolder{
        ImageView bImage;
        public ViewHolderWithSingleImage(View view) {
            super(view);
            bImage = (ImageView) view.findViewById(R.id.img);
        }
    }

    static class ViewHolderWithFourImages extends BasicViewHolder{
        ImageView bImage0;
        ImageView bImage1;
        ImageView bImage2;
        ImageView bImage3;
        public ViewHolderWithFourImages(View view) {
            super(view);
            bImage0 = (ImageView) view.findViewById(R.id.img0);
            bImage1 = (ImageView) view.findViewById(R.id.img1);
            bImage2 = (ImageView) view.findViewById(R.id.img2);
            bImage3 = (ImageView) view.findViewById(R.id.img3);
        }
    }

    public BulletinAdapter(List<Bulletin> bList) {
        bulletinList = bList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == STYLE0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.b_style0, parent, false);
            return new BasicViewHolder(view);
        }
        else if (viewType == STYLE1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.b_style1, parent, false);
            return new ViewHolderWithSingleImage(view);
        }
        else if (viewType == STYLE2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.b_style2, parent, false);
            return new ViewHolderWithSingleImage(view);
        }
        else if (viewType == STYLE3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.b_style3, parent, false);
            return new ViewHolderWithSingleImage(view);
        }
        else if (viewType == STYLE4) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.b_style4, parent, false);
            return new ViewHolderWithFourImages(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bulletin bulletin = bulletinList.get(position);
        if (holder instanceof ViewHolderWithSingleImage) {
            ViewHolderWithSingleImage viewHolder = (ViewHolderWithSingleImage) holder;
            viewHolder.bTitle.setText(bulletin.getTitle());
            viewHolder.bAuthor.setText(bulletin.getAuthor());
            viewHolder.bPublishTime.setText(bulletin.getPublishTime());
            Bitmap bmp = bulletin.getImage();
            viewHolder.bImage.setImageBitmap(bmp);
        }
        else if (holder instanceof ViewHolderWithFourImages) {
            ViewHolderWithFourImages viewHolder = (ViewHolderWithFourImages) holder;
            viewHolder.bTitle.setText(bulletin.getTitle());
            viewHolder.bAuthor.setText(bulletin.getAuthor());
            viewHolder.bPublishTime.setText(bulletin.getPublishTime());
            viewHolder.bImage0.setImageBitmap(bulletin.getImages(0));
            viewHolder.bImage1.setImageBitmap(bulletin.getImages(1));
            viewHolder.bImage2.setImageBitmap(bulletin.getImages(2));
            viewHolder.bImage3.setImageBitmap(bulletin.getImages(3));
        }
        else if (holder instanceof BasicViewHolder) {
            // Base Class should be judged at last
            // ViewHolderWithImages is also an instance of BasicViewHolder
            BasicViewHolder viewHolder = (BasicViewHolder) holder;
            viewHolder.bTitle.setText(bulletin.getTitle());
            viewHolder.bAuthor.setText(bulletin.getAuthor());
            viewHolder.bPublishTime.setText(bulletin.getPublishTime());
        }
    }

    @Override
    public int getItemCount() {
        return bulletinList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (bulletinList.get(position) != null) {
            return ((Bulletin)bulletinList.get(position)).getType();
        }
        return super.getItemViewType(position);
    }
}
