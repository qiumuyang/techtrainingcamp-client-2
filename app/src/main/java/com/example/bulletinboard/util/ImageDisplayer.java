package com.example.bulletinboard.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDisplayer {

    private static final int SAVE_ITEM = 100;

    private static ImageView newDlgImgView(Context context, Bitmap src) {
        ImageView imgView = new ImageView(context);
        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setImageBitmap(src);
        return imgView;
    }

    private static String saveImage(Context context, Bitmap image) {
        // TODO create a new thread to save
        String dir = context.getExternalFilesDir("Image").getAbsolutePath();
        // Toast.makeText(context, dir + File.separator + image.toString(), Toast.LENGTH_LONG).show();
        File folder = new File(dir);
        File file = new File(dir + File.separator + image.toString().substring(25) + ".png");
        // TODO change folder to DCMI (permission required)
        if (!folder.exists() && !folder.mkdirs())
            return null;
        try {
            if (!file.exists() && !file.createNewFile())
                return null;
            FileOutputStream outputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file.toString();
    }

    public static Dialog getDialog(Context context, Bitmap image) {
        Dialog dlg = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ImageView dlgImgView = newDlgImgView(context, image);
        dlg.setContentView(dlgImgView);
        dlg.registerForContextMenu(dlgImgView);

        dlgImgView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem item = menu.add(0, SAVE_ITEM, 0, "保存图片");
                item.setOnMenuItemClickListener(item1 -> {
                    String toast = "保存成功";
                    String path = saveImage(context, image);
                    int length = Toast.LENGTH_SHORT;
                    if (path == null) {
                        toast = "保存失败";
                    } else {
                        toast += "，路径为" + path;
                        length = Toast.LENGTH_LONG;
                    }
                    Toast.makeText(context, toast, length).show();
                    return true;
                });
            }
        });

        dlgImgView.setOnClickListener(v -> dlg.dismiss());
        return dlg;
    }

}
