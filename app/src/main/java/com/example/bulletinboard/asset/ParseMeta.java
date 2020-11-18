package com.example.bulletinboard.asset;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.bulletinboard.Bulletin;
import com.example.bulletinboard.json.BulletinJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ParseMeta {

    private static final String TAG = "ParseMeta";

    private AssetManager mAssetMangaer;

    public ParseMeta(AssetManager assetManager) {
        this.mAssetMangaer = assetManager;
    }

    public List<Bulletin> parse(String metaPath) {
        Gson gson = new Gson();
        String metadata = Asset.loadTextAsset(mAssetMangaer, metaPath);
        List<BulletinJson> list = gson.fromJson(metadata, new TypeToken<List<BulletinJson>>() {}.getType());
        List<Bulletin> ret = new ArrayList<Bulletin>();
        for (BulletinJson b : list) {
            int type = b.getType();
            String title = b.getTitle();
            String author = b.getAuthor();
            String publishtime = b.getPublishTime();
            if (type == 0) {
                // plain text
                ret.add(new Bulletin(type, title, author, publishtime));
            }
            else if (type >= 1 && type <= 3) {
                // single image
                String imgpath = b.getCover();
                Bitmap img = Asset.loadImageAsset(mAssetMangaer, imgpath);
                Log.d(TAG, img.toString());
                ret.add(new Bulletin(type, title, author, publishtime, img));
            }
            else {
                // multi-image
                String[] imgpaths = b.getCovers();
                List<Bitmap> imgs = new ArrayList<>();
                for (String path: imgpaths) {
                    Bitmap img = Asset.loadImageAsset(mAssetMangaer, path);
                    imgs.add(img);
                    Log.d(TAG, img.toString());
                }
                Bitmap bms[] = imgs.toArray(new Bitmap[imgs.size()]);
                ret.add(new Bulletin(type, title, author, publishtime, bms));
            }
        }
        return ret;
    }
}
