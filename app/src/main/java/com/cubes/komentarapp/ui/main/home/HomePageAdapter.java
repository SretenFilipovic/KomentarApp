package com.cubes.komentarapp.ui.main.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.ui.main.home.categories.HomePageCategoryFragment;
import com.cubes.komentarapp.ui.main.home.headnews.HomePageHeadNewsFragment;

import java.util.ArrayList;

// U prvoj verziji sam koristio ViewPager za HomeFragment kao i za slajder na Naslovnoj strani
// U toj verziji je bilo problema sa prikazivanjem podataka,
// npr. kada bih otisao na LatestFragment VideoFragment i vratio se u HomeFragment, svi elementi bi nestali,
// pa bi se pojavili tek nakon prolaska kroz ostale tabove u HomeFragment-u.
// Istrazio sam malo i presao na ViewPager2 sto je resilo te probleme pa sam ostavio tako
// Slajder je takodje prvobitno radjen sa ViewPager-om pa sam sam i tamo zamenio za ViewPager2, ali sam ostavio stari kod zakomentarisan

// Ovaj adapter se setuje na VP2 u HomeFragment-u

public class HomePageAdapter extends FragmentStateAdapter {

    private ArrayList<Category> categoryList;

    public HomePageAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Category> categoryList) {
        super(fragmentActivity);
        this.categoryList = categoryList;
    }

    public HomePageAdapter(@NonNull Fragment fragment, ArrayList<Category> categoryList) {
        super(fragment);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            HomePageHeadNewsFragment fragment = HomePageHeadNewsFragment.newInstance();
            return fragment;
        }
        else {
            Category category = categoryList.get(position-1);
            HomePageCategoryFragment fragment = HomePageCategoryFragment.newInstance(category);
            return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size() + 1;
    }

}
