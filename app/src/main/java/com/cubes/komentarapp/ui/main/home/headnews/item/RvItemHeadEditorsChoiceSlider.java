package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.cubes.komentarapp.databinding.RvItemHeadSliderBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsSliderAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class RvItemHeadEditorsChoiceSlider implements RvItemHead {

    private ArrayList<News> editorsChoiceNews;

    public RvItemHeadEditorsChoiceSlider(ArrayList<News> sliderNews) {
        this.editorsChoiceNews = sliderNews;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadSliderBinding binding = (RvItemHeadSliderBinding) holder.binding;

        if(editorsChoiceNews.size() > 0){
            binding.frameLayout.setVisibility(View.VISIBLE);
        }

        FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) holder.itemView.getContext()).getLifecycle();

        HeadNewsSliderAdapter adapter = new HeadNewsSliderAdapter(fm, lc, editorsChoiceNews);
        binding.slider.setAdapter(adapter);
        new TabLayoutMediator(binding.indicator, binding.slider, (tab, position) -> {
        }).attach();

    }
}
