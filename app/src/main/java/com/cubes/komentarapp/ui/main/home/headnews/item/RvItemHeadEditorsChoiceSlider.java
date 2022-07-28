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
    private Context context;
    private HeadNewsSliderAdapter adapter;

    public RvItemHeadEditorsChoiceSlider(Context context, ArrayList<News> sliderNews) {
        this.editorsChoiceNews = sliderNews;
        this.context = context;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadSliderBinding binding = (RvItemHeadSliderBinding) holder.binding;

        // ako ima vesti u listi Izbor Urednika onda ce frameLayout koji nosi naslov rubrike postati vidljiv na ekranu
        // u suprotnom nece se prikazati na naslovnoj stranici
        if(editorsChoiceNews.size() > 0){
            binding.frameLayout.setVisibility(View.VISIBLE);
        }

        FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) context).getLifecycle();

//      ADAPTER ZA VIEWPAGER2
        adapter = new HeadNewsSliderAdapter(fm, lc, editorsChoiceNews);
        binding.slider.setAdapter(adapter);
        new TabLayoutMediator(binding.indicator, binding.slider, (tab, position) -> {
        }).attach();

    }
}
