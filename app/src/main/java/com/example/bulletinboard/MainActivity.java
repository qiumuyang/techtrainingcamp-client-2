package com.example.bulletinboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AssetManager assetManager = getAssets();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ret = loadTextAsset("metadata.json");
        Log.i(TAG, ret);

        RecyclerView main_list = (RecyclerView) findViewById(R.id.main_list);
        main_list.setLayoutManager(new LinearLayoutManager(this));
        main_list.setAdapter(new BulletinAdapter(getData()));
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
            Bulletin bulletin = new Bulletin("TitleTitle" + i,
                    "Author" + i, "Pt" + i, 1, R.mipmap.ic_launcher);
            ret.add(bulletin);
        }
        return ret;
    }

    public String loadTextAsset(String fileName) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            inputStream = assetManager.open(fileName);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "loadAsset: "+ e.toString());
            e.printStackTrace();
        }
        return outputStream.toString();
    }

    public Bitmap loadImageAsset(String fileName) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            inputStream = assetManager.open(fileName);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "loadAsset: "+ e.toString());
            e.printStackTrace();
        }
        byte[] bytes = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}