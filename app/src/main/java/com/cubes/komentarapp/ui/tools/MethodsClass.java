package com.cubes.komentarapp.ui.tools;

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

    public static int[] createIdList(NewsList response){

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

        return MethodsClass.initNewsIdList(allNews);
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

}
