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
            Log.e(TAG, "loadImageAsset: " + e.toString());
            e.printStackTrace();
        }
        byte[] bytes = outputStream.toByteArray();
        Log.d(TAG, fileName + " length: " + bytes.length);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
