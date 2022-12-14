package com.cubes.komentarapp.ui.tools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.data.model.domain.Vote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfig {


    private static final String NEWS_LIST_KEY = "news_list_key";
    private static final String LIST_KEY = "list_key";
    private static final String NOTIFICATION_KEY = "notification_key";


    public static void writeVoteListInPref(Activity activity, List<Vote> list) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static List<Vote> readVoteListFromPref(Activity activity){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        String jsonString = pref.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Vote>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }

    public static void writeMyNewsListInPref(Activity activity, List<MyNews> list) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NEWS_LIST_KEY, jsonString);
        editor.apply();
    }

    public static List<MyNews> readMyNewsListFromPref(Activity activity){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        String jsonString = pref.getString(NEWS_LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MyNews>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }

    public static void setNotificationStatus(Activity activity, boolean isOn){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        pref.edit().putBoolean(NOTIFICATION_KEY, isOn).apply();
    }

    public static boolean isNotificationOn(Activity activity){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        return pref.getBoolean(NOTIFICATION_KEY, false);
    }

}
