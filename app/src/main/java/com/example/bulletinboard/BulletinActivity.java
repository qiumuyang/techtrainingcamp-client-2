package com.example.bulletinboard;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulletinboard.asset.ParseMeta;
import com.example.bulletinboard.data.Bulletin;

import java.util.List;

public class BulletinActivity extends AppCompatActivity {

    private static final String TAG = "BulletinActivity";

    private static final String META_PATH = "metadata_test.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.hide();
        }

        AssetManager am = getAssets();
        ParseMeta metaParser = new ParseMeta(am);
        List<Bulletin> bulletins = metaParser.parse(META_PATH);

        RecyclerView main_list = (RecyclerView) findViewById(R.id.main_list);
        main_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // add divider
        main_list.setLayoutManager(new LinearLayoutManager(this));
        main_list.setAdapter(new BulletinAdapter(bulletins));

    }

}