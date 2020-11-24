package com.example.bulletinboard.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class UserToken {

    private static final String XML_PATH = "user.xml";
    private static final String TOKEN_KEY = "token";

    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(XML_PATH, MODE_PRIVATE);
        return sp.getString(TOKEN_KEY, null);
    }

    public static void setToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("user.xml", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }
}
