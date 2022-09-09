package com.cubes.komentarapp.ui.tools;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
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

    public static void animationSwipe(View view, float fromDegrees, float toDegrees){
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0f);
        alpha.setDuration(2000);

        RotateAnimation rotate1 = new RotateAnimation(toDegrees, fromDegrees,100,100);
        rotate1.setDuration(500);
        RotateAnimation rotateBack = new RotateAnimation(fromDegrees, toDegrees,100,100);
        rotateBack.setStartOffset(500);
        rotateBack.setDuration(500);
        RotateAnimation rotate2 = new RotateAnimation(toDegrees, fromDegrees,100,100);
        rotate2.setStartOffset(1000);
        rotate2.setDuration(500);

        animationSet.addAnimation(alpha);
        animationSet.addAnimation(rotate1);
        animationSet.addAnimation(rotateBack);
        animationSet.addAnimation(rotate2);

        view.startAnimation(animationSet);

        new Handler().postDelayed(() -> view.setVisibility(View.GONE), 2000);
    }



}
