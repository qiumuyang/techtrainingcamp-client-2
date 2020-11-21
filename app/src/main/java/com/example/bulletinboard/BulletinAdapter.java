package com.example.bulletinboard;

import android.graphics.Bitmap;
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
        TextView textTitle;
        TextView textAuthor;
        TextView textPublishTime;
        public BasicViewHolder(View view) {
            super(view);
            textTitle = (TextView) view.findViewById(R.id.title_text);
            textAuthor = (TextView) view.findViewById(R.id.author_text);
            textPublishTime = (TextView) view.findViewById(R.id.publishtime_text);
        }
    }

    static class ViewHolderWithSingleImage extends BasicViewHolder{
        ImageView imageCover;
        public ViewHolderWithSingleImage(View view) {
            super(view);
            imageCover = (ImageView) view.findViewById(R.id.img);
        }
    }

    static class ViewHolderWithFourImages extends BasicViewHolder{
        ImageView imageCover0;
        ImageView imageCover1;
        ImageView imageCover2;
        ImageView imageCover3;
        public ViewHolderWithFourImages(View view) {
            super(view);
            imageCover0 = (ImageView) view.findViewById(R.id.img0);
            imageCover1 = (ImageView) view.findViewById(R.id.img1);
            imageCover2 = (ImageView) view.findViewById(R.id.img2);
            imageCover3 = (ImageView) view.findViewById(R.id.img3);
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style0, parent, false);
            return new BasicViewHolder(view);
        }
        else if (viewType == STYLE1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style1, parent, false);
            return new ViewHolderWithSingleImage(view);
        }
        else if (viewType == STYLE2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style2, parent, false);
            return new ViewHolderWithSingleImage(view);
        }
        else if (viewType == STYLE3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style3, parent, false);
            return new ViewHolderWithSingleImage(view);
        }
        else if (viewType == STYLE4) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style4, parent, false);
            return new ViewHolderWithFourImages(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bulletin bulletin = bulletinList.get(position);
        if (holder instanceof ViewHolderWithSingleImage) {
            ViewHolderWithSingleImage viewHolder = (ViewHolderWithSingleImage) holder;
            viewHolder.textTitle.setText(bulletin.getTitle());
            viewHolder.textAuthor.setText(bulletin.getAuthor());
            viewHolder.textPublishTime.setText(bulletin.getPublishTime());
            Bitmap bmp = bulletin.getImage();
            viewHolder.imageCover.setImageBitmap(bmp);
        }
        else if (holder instanceof ViewHolderWithFourImages) {
            // TODO make it adaptable for any number of images
            ViewHolderWithFourImages viewHolder = (ViewHolderWithFourImages) holder;
            viewHolder.textTitle.setText(bulletin.getTitle());
            viewHolder.textAuthor.setText(bulletin.getAuthor());
            viewHolder.textPublishTime.setText(bulletin.getPublishTime());
            viewHolder.imageCover0.setImageBitmap(bulletin.getImages(0));
            viewHolder.imageCover1.setImageBitmap(bulletin.getImages(1));
            viewHolder.imageCover2.setImageBitmap(bulletin.getImages(2));
            viewHolder.imageCover3.setImageBitmap(bulletin.getImages(3));
        }
        else if (holder instanceof BasicViewHolder) {
            // Base Class should be judged at last
            // ViewHolderWithImages is also an instance of BasicViewHolder
            BasicViewHolder viewHolder = (BasicViewHolder) holder;
            viewHolder.textTitle.setText(bulletin.getTitle());
            viewHolder.textAuthor.setText(bulletin.getAuthor());
            viewHolder.textPublishTime.setText(bulletin.getPublishTime());
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
