package com.example.bulletinboard.asset;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Asset {

    private static final String TAG = "Asset";

    private static byte[] loadRawAsset(AssetManager assetManager, String fileName) {
        InputStream inputStream;
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
            Log.e(TAG, "loadAsset: " + e.toString());
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static String loadTextAsset(AssetManager assetManager, String fileName) {
        byte[] bytes = loadRawAsset(assetManager, fileName);
        return new String(bytes);
    }

    public static Bitmap loadImageAsset(AssetManager assetManager, String fileName) {
        byte[] bytes = loadRawAsset(assetManager, fileName);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap loadImageAssetThumb(AssetManager assetManager, String fileName) {
        byte[] bytes = loadRawAsset(assetManager, fileName);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, getThumbOption(bitmap));
        return bitmap;
    }

    private static BitmapFactory.Options getThumbOption(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int size = w * h;
        int sampleSize = 1;
        if (size < 1000000) {
            sampleSize = 1;
        } else if (size < 2000000) {
            sampleSize = 2;
        } else {
            sampleSize = 4;
        }
        options.inSampleSize = sampleSize;
        return options;
    }

}
