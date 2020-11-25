package com.example.bulletinboard.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class User {

    private static final String XML_PATH = "user.xml";
    private static final String TOKEN_KEY = "token";
    private static final String USER_KEY = "username";
    private static final String PSWD_KEY = "password";
    private static final String FAVOR_KEY = "favorite";
    // TODO add save pswd; add favorite
    // Set<String> favorite: article_id

    private static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(XML_PATH, MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    private static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(XML_PATH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getToken(Context context) {
        return getString(context, TOKEN_KEY, null);
    }

    public static String getUsername(Context context) {
        return getString(context, USER_KEY, "");
    }

    public static String getPassword(Context context) {
        return getString(context, PSWD_KEY, "");
    }

    public static void setToken(Context context, String token) {
        setString(context, TOKEN_KEY, token);
    }

    public static void setUsername(Context context, String username) {
        setString(context, USER_KEY, username);
    }

    public static void setPassword(Context context, String password) {
        setString(context, PSWD_KEY, password);
    }
}
