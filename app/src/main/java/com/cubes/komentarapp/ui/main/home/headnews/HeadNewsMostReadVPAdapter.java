package com.cubes.komentarapp.ui.main.home.headnews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.ui.tools.MethodsClass;

import java.util.ArrayList;

public class HeadNewsMostReadVPAdapter extends FragmentStateAdapter {

    private final NewsList newsList;

    public HeadNewsMostReadVPAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, NewsList newsList) {
        super(fragmentManager, lifecycle);
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return HeadNewsMostReadFragment.newInstance(newsList.latest, MethodsClass.getAllNews(newsList));
        } else if (position == 1) {
            return HeadNewsMostReadFragment.newInstance(newsList.mostRead, MethodsClass.getAllNews(newsList));
        } else {
            return HeadNewsMostReadFragment.newInstance(newsList.mostCommented, MethodsClass.getAllNews(newsList));
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
