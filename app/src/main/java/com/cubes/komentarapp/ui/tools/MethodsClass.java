package com.cubes.komentarapp.ui.tools;

import com.cubes.komentarapp.data.model.domain.News;

import java.util.ArrayList;

public class MethodsClass {


    public static int[] initNewsIdList(ArrayList<News> newsList) {

        int[] newsIdList = new int[newsList.size()];

        for (int i = 0; i < newsList.size(); i++) {
            newsIdList[i] = newsList.get(i).id;
        }

        return newsIdList;
    }

}
