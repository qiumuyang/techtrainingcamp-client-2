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

    public static String loadTextAsset(AssetManager assetManager, String fileName) {
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
            Log.e(TAG, "loadTextAsset: " + e.toString());
            e.printStackTrace();
        }
        return outputStream.toString();
    }

    public static Bitmap loadImageAsset(AssetManager assetManager, String fileName) {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Log.d(TAG, "loadImageAsset");
        try {
            inputStream = assetManager.open(fileName);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "loadImageAsset: " + e.toString());
            e.printStackTrace();
        }
        byte[] bytes = outputStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, getThumbOption(bitmap));
        return bitmap;
    }

    private static BitmapFactory.Options getThumbOption(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int size = w*h;
        if (size < 1000000) {
            options.inSampleSize = 1;
        }
        else if (size < 2000000) {
            options.inSampleSize = 2;
        }
        else {
            options.inSampleSize = 4;
        }
        return options;
    }

}
