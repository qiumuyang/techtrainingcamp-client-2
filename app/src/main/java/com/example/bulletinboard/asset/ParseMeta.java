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
    private static final int IMG_H0 = 111;
    private static final int IMG_H1 = 80;
    private final AssetManager mAssetMangaer;

    public ParseMeta(AssetManager assetManager) {
        this.mAssetMangaer = assetManager;
    }

    private static Bitmap resize_h(Bitmap bitmap, int dst_h) {
        int src_h = bitmap.getHeight();
        int src_w = bitmap.getWidth();
        int dst_w = src_w * dst_h / src_h;
        Bitmap ret = Bitmap.createScaledBitmap(bitmap, dst_w, dst_h, true);
        return ret;
    }

    public List<Bulletin> parse(String metaPath) {
        Gson gson = new Gson();
        String metadata = Asset.loadTextAsset(mAssetMangaer, metaPath);
        List<BulletinJson> list = gson.fromJson(metadata, new TypeToken<List<BulletinJson>>() {
        }.getType());
        List<Bulletin> ret = new ArrayList<Bulletin>();
        for (BulletinJson bulletin : list) {
            int type = bulletin.getType();
            String id = bulletin.getId();
            String title = bulletin.getTitle();
            String author = bulletin.getAuthor();
            String publishtime = bulletin.getPublishTime();
            if (type == 0) {
                // plain text
                ret.add(new Bulletin(type, id, title, author, publishtime));
            } else if (type >= 1 && type <= 3) {
                // single image
                String imgpath = bulletin.getCover();
                Bitmap img = Asset.loadImageAsset(mAssetMangaer, imgpath);
                // img = resize_h(img, IMG_H0);
                Log.d(TAG, img.toString());
                ret.add(new Bulletin(type, id, title, author, publishtime, img));
            } else {
                // multi-image
                String[] imgpaths = bulletin.getCovers();
                List<Bitmap> imgs = new ArrayList<>();
                for (String path : imgpaths) {
                    Bitmap img = Asset.loadImageAsset(mAssetMangaer, path);
                    // img = resize_h(img, IMG_H1);
                    imgs.add(img);
                    Log.d(TAG, img.toString());
                }
                Bitmap[] bms = imgs.toArray(new Bitmap[imgs.size()]);
                ret.add(new Bulletin(type, id, title, author, publishtime, bms));
            }
        }
        return ret;
    }

}
