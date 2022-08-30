package com.cubes.komentarapp.ui.main.home.headnews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.ui.tools.MethodsClass;

import java.util.ArrayList;


public class HeadNewsSliderAdapter extends FragmentStateAdapter {

    private final ArrayList<News> sliderNews;
    private final int[] newsIdList;

    public HeadNewsSliderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<News> sliderNews) {
        super(fragmentManager, lifecycle);
        this.sliderNews = sliderNews;
        this.newsIdList = MethodsClass.initNewsIdList(sliderNews);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HeadNewsSliderFragment.newInstance(sliderNews.get(position).id, sliderNews.get(position).title, sliderNews.get(position).image, newsIdList);
    }

    @Override
    public int getItemCount() {
        return sliderNews.size();
    }

}
