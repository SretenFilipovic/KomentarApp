package com.cubes.komentarapp.ui.main.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.ui.main.home.categories.HomePageCategoryFragment;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsFragment;

import java.util.ArrayList;

public class HomePageAdapter extends FragmentStateAdapter {

    private final ArrayList<Category> categoryList;

    public HomePageAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Category> categoryList) {
        super(fragmentActivity);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            HeadNewsFragment fragment = HeadNewsFragment.newInstance();
            return fragment;
        } else {
            Category category = categoryList.get(position - 1);
            HomePageCategoryFragment fragment = HomePageCategoryFragment.newInstance(category.id);
            return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size() + 1;
    }

}
