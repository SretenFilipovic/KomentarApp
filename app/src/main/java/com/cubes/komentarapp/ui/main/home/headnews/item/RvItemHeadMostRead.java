package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.databinding.RvItemHeadMostReadBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsMostReadVPAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class RvItemHeadMostRead implements RvItemHead {

    private final NewsList newsList;

    public RvItemHeadMostRead(NewsList newsList) {
        this.newsList = newsList;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_head_most_read;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemHeadMostReadBinding binding = (RvItemHeadMostReadBinding) holder.binding;

        FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) holder.itemView.getContext()).getLifecycle();

        HeadNewsMostReadVPAdapter adapter = new HeadNewsMostReadVPAdapter(fm, lc, newsList);
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
