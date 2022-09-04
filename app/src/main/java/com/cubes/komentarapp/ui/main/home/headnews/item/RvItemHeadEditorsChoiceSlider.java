package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.databinding.RvItemHeadSliderBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsSliderAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class RvItemHeadEditorsChoiceSlider implements RvItemHead {


    private final NewsList newsList;



    public RvItemHeadEditorsChoiceSlider(NewsList newsList) {
        this.newsList = newsList;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_head_slider;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemHeadSliderBinding binding = (RvItemHeadSliderBinding) holder.binding;

        if (newsList.editorsChoice.size() > 0) {
            binding.frameLayout.setVisibility(View.VISIBLE);
        }

        FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) holder.itemView.getContext()).getLifecycle();

        HeadNewsSliderAdapter adapter = new HeadNewsSliderAdapter(fm, lc, newsList);
        binding.slider.setAdapter(adapter);
        new TabLayoutMediator(binding.indicator, binding.slider, (tab, position) -> {
        }).attach();

    }
}
