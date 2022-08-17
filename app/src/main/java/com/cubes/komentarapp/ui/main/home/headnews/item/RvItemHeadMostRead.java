package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadMostReadBinding;
import com.cubes.komentarapp.ui.ViewHolder.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsMostReadVPAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class RvItemHeadMostRead implements RvItem {

    private final ArrayList<News> mostReadList;
    private final ArrayList<News> latestList;
    private final ArrayList<News> mostCommentedList;

    public RvItemHeadMostRead(ArrayList<News> latest, ArrayList<News> mostRead, ArrayList<News> mostCommented) {
        this.mostCommentedList = mostCommented;
        this.mostReadList = mostRead;
        this.latestList = latest;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemHeadMostReadBinding binding = (RvItemHeadMostReadBinding) holder.binding;

        FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) holder.itemView.getContext()).getLifecycle();

        HeadNewsMostReadVPAdapter adapter = new HeadNewsMostReadVPAdapter(fm, lc, latestList, mostReadList, mostCommentedList);
        binding.viewPagerHome.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPagerHome, (tab, position) -> {
            if (position == 0) {
                tab.setText("NAJNOVIJE");
            } else if (position == 1) {
                tab.setText("NAJČITANIJE");
            } else {
                tab.setText("KOMENTARI");
            }
        }).attach();

    }
}
