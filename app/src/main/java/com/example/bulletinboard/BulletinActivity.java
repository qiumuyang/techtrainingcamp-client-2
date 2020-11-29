package com.example.bulletinboard;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulletinboard.asset.ParseMeta;
import com.example.bulletinboard.data.Bulletin;
import com.example.bulletinboard.util.CustomActionBar;
import com.example.bulletinboard.util.User;

import java.util.List;

public class BulletinActivity extends AppCompatActivity {

    private static final String TAG = "BulletinActivity";

    private static final String META_PATH = "metadata.json";

    private CustomActionBar customActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            CustomActionBar customed = new CustomActionBar(this);
            this.customActionBar = customed;
            customed.replaceActionBar(actionBar);
            customed.setTitle(getResources().getString(R.string.app_name));
            customed.setImageRight(R.drawable.star_fill_white);
            setLeftButtonByLogin();
            customed.getButtonRight().setOnClickListener(v -> {FavorActivity.actionStart(this);});
        }

        AssetManager am = getAssets();
        ParseMeta metaParser = new ParseMeta(am);
        List<Bulletin> bulletins = metaParser.parse(META_PATH);

        RecyclerView main_list = (RecyclerView) findViewById(R.id.main_list);
        main_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // add divider
        main_list.setLayoutManager(new LinearLayoutManager(this));
        main_list.setAdapter(new BulletinAdapter(bulletins));

    }

    private void setLeftButtonByLogin() {
        boolean isLoggedin = User.getToken(this) != null;
        if (isLoggedin) {
            this.customActionBar.setImageLeft(R.drawable.person);
            this.customActionBar.getButtonLeft().setOnClickListener(null);
        }
        else {
            this.customActionBar.setImageLeft(R.drawable.login);
            this.customActionBar.getButtonLeft().setOnClickListener(v -> {
                LoginActivity.actionStart(this);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setLeftButtonByLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLeftButtonByLogin();
    }
}