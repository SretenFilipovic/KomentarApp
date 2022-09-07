package com.cubes.komentarapp.ui.tools;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cubes.komentarapp.data.model.domain.CategoryBox;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsList;

import java.util.ArrayList;

public class MethodsClass {


    public static int[] initNewsIdList(ArrayList<News> newsList) {

        int[] newsIdList = new int[newsList.size()];

        for (int i = 0; i < newsList.size(); i++) {
            newsIdList[i] = newsList.get(i).id;
        }

        return newsIdList;
    }

    public static ArrayList<News> getAllNews(NewsList response){
        ArrayList<News> allNews = new ArrayList<>();

        allNews.addAll(response.slider);
        allNews.addAll(response.top);
        allNews.addAll(response.latest);
        allNews.addAll(response.mostRead);
        allNews.addAll(response.mostCommented);
        allNews.addAll(response.editorsChoice);
        for (CategoryBox category : response.category){
            if (category.title.equalsIgnoreCase("Sport")) {
                allNews.addAll(category.news);
            }
        }
        allNews.addAll(response.videos);
        for (CategoryBox category : response.category){
            if (!category.title.equalsIgnoreCase("Sport")) {
                allNews.addAll(category.news);
            }
        }

        return allNews;
    }

    public static int[] createIdList(NewsList response){
        return MethodsClass.initNewsIdList(getAllNews(response));
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
