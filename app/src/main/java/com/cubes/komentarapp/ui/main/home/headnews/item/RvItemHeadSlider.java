package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.cubes.komentarapp.databinding.RvItemHeadSliderBinding;
import com.cubes.komentarapp.data.model.News;

import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsSliderAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class RvItemHeadSlider implements RvItemHead{

    private ArrayList<News> sliderNews;
    private Context context;
    private HeadNewsSliderAdapter adapter;

    public RvItemHeadSlider(Context context, ArrayList<News> sliderNews) {
        this.sliderNews = sliderNews;
        this.context = context;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadSliderBinding binding = (RvItemHeadSliderBinding) holder.binding;
        FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) context).getLifecycle();

//      SETOVANJE ADAPTERA ZA VIEWPAGER2
        adapter = new HeadNewsSliderAdapter(fm, lc, sliderNews);
        binding.slider.setAdapter(adapter);
        new TabLayoutMediator(binding.indicator, binding.slider, (tab, position) -> {}).attach();


//      SETOVANJE ADAPTERa ZA VIEWPAGER (stara verzija)
//      adapter = new SliderAdapter(fm, sliderNews);
//      binding.slider.setAdapter(adapter);
//      binding.indicator.setupWithViewPager(binding.slider);
    }

}
