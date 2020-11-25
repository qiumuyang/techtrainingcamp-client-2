package com.example.bulletinboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulletinboard.data.Bulletin;
import com.example.bulletinboard.util.ImageDisplayer;

import java.util.List;

public class BulletinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BulletinAdapter";

    private final int STYLE0 = 0;
    private final int STYLE1 = 1;
    private final int STYLE2 = 2;
    private final int STYLE3 = 3;
    private final int STYLE4 = 4;
    private final List<Bulletin> bulletinList;

    public BulletinAdapter(List<Bulletin> bList) {
        bulletinList = bList;
    }

    private static void loadArticle(Context context, Bulletin bulletin) {
        ArticleActivity.actionStart(context,
                bulletin.getId(),
                bulletin.getTitle(),
                bulletin.getAuthor() + " " + bulletin.getPublishTime());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int[] layout_id = {R.layout.style0, R.layout.style1, R.layout.style2, R.layout.style3, R.layout.style4};
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id[viewType], parent, false);
        switch (viewType) {
            case STYLE0:
                BasicViewHolder basicHolder = new BasicViewHolder(view);
                basicHolder.bulletinView.setOnClickListener(v -> {
                    int position = basicHolder.getAdapterPosition();
                    Bulletin bulletin = bulletinList.get(position);
                    // Toast.makeText(v.getContext(), "Article '"+ bulletin.getId()+ "' clicked", Toast.LENGTH_SHORT).show();
                    loadArticle(v.getContext(), bulletin);
                });
                return basicHolder;
            case STYLE1:
            case STYLE2:
            case STYLE3:
                ViewHolderWithSingleImage singleHolder = new ViewHolderWithSingleImage(view);
                singleHolder.imageCover.setOnClickListener(v -> {
                    ImageView imageView = (ImageView) v;
                    final Dialog dlg = ImageDisplayer.getDialog(v.getContext(), ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    dlg.show();
                });
                singleHolder.bulletinView.setOnClickListener(v -> {
                    int position = singleHolder.getAdapterPosition();
                    Bulletin bulletin = bulletinList.get(position);
                    // Toast.makeText(v.getContext(), "Article '"+ bulletin.getId()+ "' clicked", Toast.LENGTH_SHORT).show();
                    loadArticle(v.getContext(), bulletin);
                });
                return singleHolder;
            case STYLE4:
                ViewHolderWithFourImages fourHolder = new ViewHolderWithFourImages(view);
                for (int i = 0; i < 4; i++) {
                    fourHolder.imageCovers[i].setOnClickListener(v -> {
                        ImageView imageView = (ImageView) v;
                        final Dialog dlg = ImageDisplayer.getDialog(v.getContext(), ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                        dlg.show();
                    });
                }
                fourHolder.bulletinView.setOnClickListener(v -> {
                    int position = fourHolder.getAdapterPosition();
                    Bulletin bulletin = bulletinList.get(position);
                    // Toast.makeText(v.getContext(), "Article '"+ bulletin.getId()+ "' clicked", Toast.LENGTH_SHORT).show();
                    loadArticle(v.getContext(), bulletin);
                });
                return fourHolder;
            default:
                return null;
        }
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
        } else if (holder instanceof ViewHolderWithFourImages) {
            // TODO make it adaptable for any number of images
            ViewHolderWithFourImages viewHolder = (ViewHolderWithFourImages) holder;
            viewHolder.textTitle.setText(bulletin.getTitle());
            viewHolder.textAuthor.setText(bulletin.getAuthor());
            viewHolder.textPublishTime.setText(bulletin.getPublishTime());
            for (int i = 0; i < 4; i++) {
                viewHolder.imageCovers[i].setImageBitmap(bulletin.getImages(i));
            }
        } else if (holder instanceof BasicViewHolder) {
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
            return ((Bulletin) bulletinList.get(position)).getType();
        }
        return super.getItemViewType(position);
    }

    static class BasicViewHolder extends RecyclerView.ViewHolder {
        View bulletinView;
        TextView textTitle;
        TextView textAuthor;
        TextView textPublishTime;

        public BasicViewHolder(View view) {
            super(view);
            bulletinView = view;
            textTitle = (TextView) view.findViewById(R.id.title_text);
            textAuthor = (TextView) view.findViewById(R.id.author_text);
            textPublishTime = (TextView) view.findViewById(R.id.publishtime_text);
        }
    }

    static class ViewHolderWithSingleImage extends BasicViewHolder {
        ImageView imageCover;

        public ViewHolderWithSingleImage(View view) {
            super(view);
            imageCover = (ImageView) view.findViewById(R.id.img);
        }
    }

    static class ViewHolderWithFourImages extends BasicViewHolder {
        ImageView[] imageCovers = new ImageView[4];

        public ViewHolderWithFourImages(View view) {
            super(view);
            int[] res_id = {R.id.img0, R.id.img1, R.id.img2, R.id.img3};
            for (int i = 0; i < 4; i++) {
                imageCovers[i] = (ImageView) view.findViewById(res_id[i]);
            }
        }
    }
}
