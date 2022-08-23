package com.cubes.komentarapp.ui.main.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.ui.main.home.categories.HomePageCategoryFragment;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsFragment;

import java.util.ArrayList;

public class HomePageAdapter extends FragmentStateAdapter {

    private ArrayList<Category> categoryList;

    public HomePageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return HeadNewsFragment.newInstance();
        } else {
            Category category = categoryList.get(position - 1);
            return HomePageCategoryFragment.newInstance(category.id);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size() + 1;
    }

    public void setData(ArrayList<Category> categoryList){
        this.categoryList = categoryList;
    }

}
