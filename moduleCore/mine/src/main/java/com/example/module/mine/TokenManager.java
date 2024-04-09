package com.example.module.mine;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "user_token";
    private static final String KEY_USER_TOKEN = "user_token";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUserToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_USER_TOKEN, token);
        editor.apply();
    }

    public static String getUserToken(Context context) {
        return getSharedPreferences(context).getString(KEY_USER_TOKEN, null);
    }

    public static void clearUserToken(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_USER_TOKEN);
        editor.apply();
    }
}

