package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bulletinboard.asset.MetaParser;
import com.example.bulletinboard.json.BulletinJson;
import com.example.bulletinboard.util.CustomActionBar;
import com.example.bulletinboard.util.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavorActivity extends AppCompatActivity {

    private static final String META_PATH = "metadata.json";

    private static List<BulletinJson> article;

    private MetaParser metaParser;

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, FavorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            CustomActionBar customed = new CustomActionBar(this);
            customed.replaceActionBar(actionBar);
            customed.setTitle("收藏夹");
            customed.setImageLeft(R.drawable.back_arrow);
            customed.getButtonLeft().setOnClickListener(v -> finish());
            customed.getButtonRight().setEnabled(false);
        }

        AssetManager am = getAssets();
        metaParser = new MetaParser(am);
        Set<String> favor = User.getFavorite(this);
        article = metaParser.getRawBulletin(META_PATH, favor);

        ListView listView = findViewById(R.id.favor_list);
        updateData();
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = (TextView) view;
            BulletinJson bulletin = getBulletinByTitle(textView.getText().toString());
            ArticleActivity.actionStart(parent.getContext(), bulletin.getId(), bulletin.getTitle(), bulletin.getAuthor() + bulletin.getPublishTime());
        });
    }

    private void updateData() {
        List<String> data = new ArrayList<>();
        for (BulletinJson bulletin : article) {
            data.add(bulletin.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, data);
        ListView listView = findViewById(R.id.favor_list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Set<String> favor = User.getFavorite(this);
        article = metaParser.getRawBulletin(META_PATH, favor);
        updateData();
    }

    private BulletinJson getBulletinByTitle(String title) {
        for (BulletinJson bulletin : article) {
            if (bulletin.getTitle().equals(title)) {
                return bulletin;
            }
        }
        return null;
    }
}