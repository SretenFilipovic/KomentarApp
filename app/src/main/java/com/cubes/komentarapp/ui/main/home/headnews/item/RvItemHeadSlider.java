package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadSliderBinding;
import com.cubes.komentarapp.ui.ViewHolder.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsSliderAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class RvItemHeadSlider implements RvItem {

    private final ArrayList<News> sliderNews;

    public RvItemHeadSlider(ArrayList<News> sliderNews) {
        this.sliderNews = sliderNews;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemHeadSliderBinding binding = (RvItemHeadSliderBinding) holder.binding;
        FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) holder.itemView.getContext()).getLifecycle();

        HeadNewsSliderAdapter adapter = new HeadNewsSliderAdapter(fm, lc, sliderNews);
        binding.slider.setAdapter(adapter);
        new TabLayoutMediator(binding.indicator, binding.slider, (tab, position) -> {
        }).attach();
    }

}
