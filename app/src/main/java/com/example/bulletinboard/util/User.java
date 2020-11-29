package com.example.bulletinboard.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class User {

    private static final String XML_PATH = "user.xml";
    private static final String FAVOR_PATH = "favor.xml";
    private static final String TOKEN_KEY = "token";
    private static final String USER_KEY = "username";
    private static final String PSWD_KEY = "password";
    private static final String FAVOR_KEY = "favorite";
    private static final String SAVEPSWD_KEY = "save_password";
    // Set<String> favorite: article_id
    // Note: favorite should be stored by back-end,
    // use this module just for substitution

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

    public static boolean isSavePswd(Context context) {
        return getString(context, SAVEPSWD_KEY, "false").equals("true");
    }

    public static Set<String> getFavorite(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FAVOR_PATH, MODE_PRIVATE);
        Set<String> ret = sp.getStringSet(FAVOR_KEY, new HashSet<String>());
        ret.remove(null);
        return ret;
    }

    public static void setFavorite(Context context, Set<String> favor) {
        SharedPreferences sp = context.getSharedPreferences(FAVOR_PATH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().clear();
        editor.putStringSet(FAVOR_KEY, favor);
        editor.apply();
    }

    public static void swapFavorite(Context context, String articleId) {
        Set<String> favor = getFavorite(context);
        if (favor.contains(articleId)) {
            favor.remove(articleId);
        } else {
            favor.add(articleId);
        }
        setFavorite(context, favor);
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

    public static void setSavePswd(Context context, boolean save) {
        setString(context, SAVEPSWD_KEY, save ? "true" : "false");
    }
}
