package com.cubes.komentarapp.ui.main.home.headnews;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.News;

import java.util.ArrayList;


public class HeadNewsSliderAdapter extends FragmentStateAdapter {

    private ArrayList<News> sliderNews;

    public HeadNewsSliderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<News> sliderNews) {
        super(fragmentManager, lifecycle);
        this.sliderNews = sliderNews;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HeadNewsSliderFragment.newInstance(sliderNews.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderNews.size();
    }

}
