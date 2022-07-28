package com.cubes.komentarapp.ui.main.home.headnews;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.main.home.headnews.SliderFragment;

import java.util.ArrayList;

// Ovaj adapter sluzi za prikaz vesti u Slajderu na Naslovnoj strani (prve vesti)
// Setuje se na VP2 u RvItemHeadSlider
// Stara verzija gde sam koristio ViewPager je zakomentarisana na dnu

public class HeadNewsSliderAdapter extends FragmentStateAdapter {

    private ArrayList<News> sliderNews;

    // VIEWPAGER2

    public HeadNewsSliderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<News> sliderNews) {
        super(fragmentManager, lifecycle);
        this.sliderNews = sliderNews;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SliderFragment.newInstance(sliderNews.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderNews.size();
    }


//ODAVDE VIEWPAGER (stara verzija)

//    public SliderAdapter(@NonNull FragmentManager fm, ArrayList<News> list) {
//        super(fm);
//        this.sliderNews = list;
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        SliderFragment fragment = SliderFragment.newInstance(sliderNews.get(position));
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        return sliderNews.size();
//    }

}
