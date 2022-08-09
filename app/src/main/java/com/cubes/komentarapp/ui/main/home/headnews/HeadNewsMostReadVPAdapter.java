package com.cubes.komentarapp.ui.main.home.headnews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.News;

import java.util.ArrayList;

public class HeadNewsMostReadVPAdapter extends FragmentStateAdapter {

    private final ArrayList<News> mostReadList;
    private final ArrayList<News> latestList;
    private final ArrayList<News> mostCommentedList;

    public HeadNewsMostReadVPAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<News> latest, ArrayList<News> mostRead, ArrayList<News> mostCommented) {
        super(fragmentManager, lifecycle);
        this.mostReadList = mostRead;
        this.latestList = latest;
        this.mostCommentedList = mostCommented;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return HeadNewsMostReadFragment.newInstance(latestList);
        } else if (position == 1) {
            return HeadNewsMostReadFragment.newInstance(mostReadList);
        } else {
            return HeadNewsMostReadFragment.newInstance(mostCommentedList);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
