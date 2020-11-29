package com.example.bulletinboard.asset;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import com.example.bulletinboard.data.Bulletin;
import com.example.bulletinboard.json.BulletinJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MetaParser {

    private static final String TAG = "ParseMeta";
    private static final int IMG_H0 = 111;
    private static final int IMG_H1 = 80;
    private final AssetManager mAssetMangaer;

    public MetaParser(AssetManager assetManager) {
        this.mAssetMangaer = assetManager;
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
                Bitmap img = Asset.loadImageAssetThumb(mAssetMangaer, imgpath);
                ret.add(new Bulletin(type, id, title, author, publishtime, img));
            } else {
                // multi-image
                String[] imgpaths = bulletin.getCovers();
                List<Bitmap> imgs = new ArrayList<>();
                for (String path : imgpaths) {
                    Bitmap img = Asset.loadImageAssetThumb(mAssetMangaer, path);
                    imgs.add(img);
                }
                Bitmap[] bms = imgs.toArray(new Bitmap[imgs.size()]);
                ret.add(new Bulletin(type, id, title, author, publishtime, bms));
            }
        }
        return ret;
    }

    public List<BulletinJson> getRawBulletin(String metaPath, Set<String> target) {
        Gson gson = new Gson();
        String metadata = Asset.loadTextAsset(mAssetMangaer, metaPath);
        List<BulletinJson> list = gson.fromJson(metadata, new TypeToken<List<BulletinJson>>() {
        }.getType());
        List<BulletinJson> ret = new ArrayList<>();
        for (BulletinJson bulletin : list) {
            if (target == null || target.contains(bulletin.getId())) {
                ret.add(bulletin);
            }
        }
        return ret;
    }
}
