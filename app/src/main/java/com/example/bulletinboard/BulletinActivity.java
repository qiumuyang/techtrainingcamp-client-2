package com.example.bulletinboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.example.bulletinboard.asset.Asset;
import com.example.bulletinboard.asset.ParseMeta;

import java.util.ArrayList;
import java.util.List;

public class BulletinActivity extends AppCompatActivity {

    private static final String TAG = "BulletinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);

        //String ret = Asset.loadTextAsset(getAssets(), "metadata.json");
        //Log.i(TAG, ret);
        AssetManager am = getAssets();
        ParseMeta metaParser = new ParseMeta(am);
        List<Bulletin> data = metaParser.parse("metadata.json");

        RecyclerView main_list = (RecyclerView) findViewById(R.id.main_list);
        main_list.setLayoutManager(new LinearLayoutManager(this));
        main_list.setAdapter(new BulletinAdapter(data));

        /*main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, position + "clicked" + id, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private List<Bulletin> getData() {
        List<Bulletin> ret = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Bulletin bulletin = new Bulletin(1, "TitleTitle" + i,
                    "Author" + i, "Pt" + i);
            ret.add(bulletin);
        }
        return ret;
    }

}