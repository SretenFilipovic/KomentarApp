package com.cubes.komentarapp.ui.subcategory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SubcategoryPagerAdapter extends FragmentStateAdapter {

    private final int[] idList;
    private final String subcategoryName;


    public SubcategoryPagerAdapter(@NonNull FragmentActivity fragmentActivity, int[] idList, String subcategoryName) {
        super(fragmentActivity);
        this.idList = idList;
        this.subcategoryName = subcategoryName;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SubcategoryFragment.newInstance(subcategoryName, idList[position]);
    }

    @Override
    public int getItemCount() {
        return idList.length;
    }
}
