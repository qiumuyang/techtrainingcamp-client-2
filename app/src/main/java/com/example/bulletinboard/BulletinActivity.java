package com.example.bulletinboard;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulletinboard.asset.MetaParser;
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
            customed.getButtonLeft().setOnClickListener(v -> {
                // if logged in then logout
                // if not logged in then login
                if (User.getToken(this) != null) {
                    User.setToken(getApplicationContext(), null);
                    Toast.makeText(getApplicationContext(), "注销成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginActivity.actionStart(this);
                }
                setLeftButtonByLogin();
            });
            customed.getButtonRight().setOnClickListener(v -> {
                // open favor list
                FavorActivity.actionStart(this);
            });
        }

        // init bulletin list
        AssetManager am = getAssets();
        MetaParser metaParser = new MetaParser(am);
        List<Bulletin> bulletins = metaParser.parse(META_PATH);

        RecyclerView main_list = (RecyclerView) findViewById(R.id.main_list);
        main_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // add divider
        main_list.setLayoutManager(new LinearLayoutManager(this));
        main_list.setAdapter(new BulletinAdapter(bulletins));

    }

    private void setLeftButtonByLogin() {
        boolean isLoggedin = User.getToken(this) != null;
        if (isLoggedin) {
            this.customActionBar.setImageLeft(R.drawable.logout);
        } else {
            this.customActionBar.setImageLeft(R.drawable.login);
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