package com.example.bulletinboard.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.bulletinboard.R;

public class CustomActionBar {

    private final Context context;
    private final View actionBar;
    private final TextView titleView;
    private final ImageButton leftImage;
    private final ImageButton rightImage;

    public CustomActionBar(Context context) {
        this.actionBar = LayoutInflater.from(context).inflate(R.layout.actionbar_menu, null);
        this.context = context;
        this.titleView = this.actionBar.findViewById(R.id.title);
        this.leftImage = this.actionBar.findViewById(R.id.leftButton);
        this.rightImage = this.actionBar.findViewById(R.id.rightButton);
    }

    public String getTitle() {
        return this.titleView.getText().toString();
    }

    public void setTitle(String title) {
        this.titleView.setText(title);
    }

    public void setImageLeft(int resId) {
        this.leftImage.setImageResource(resId);
    }

    public void setImageLeft(Bitmap bitmap) {
        this.leftImage.setImageBitmap(bitmap);
    }

    public void setImageRight(int resId) {
        this.rightImage.setImageResource(resId);
    }

    public void setImageRight(Bitmap bitmap) {
        this.rightImage.setImageBitmap(bitmap);
    }

    public ImageButton getButtonLeft() {
        return this.leftImage;
    }

    public ImageButton getButtonRight() {
        return this.rightImage;
    }

    public void replaceActionBar(ActionBar original) {
        original.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        original.setDisplayShowCustomEnabled(true);
        original.setDisplayShowHomeEnabled(true);
        original.setDisplayShowTitleEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        original.setCustomView(this.actionBar, layoutParams);
    }
}
