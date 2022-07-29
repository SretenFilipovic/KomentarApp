package com.cubes.komentarapp.ui.main.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.databinding.FragmentHomeBinding;
import com.cubes.komentarapp.data.model.Category;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

// HomeFragment je fragment u kojem se prikazuje Naslovna strana i stranice sa vestima po kategorijama u ViewPager-u

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Category> categories;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(ArrayList<Category> categories) {
        HomeFragment fragment = new HomeFragment();
        fragment.categories = categories;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomePageAdapter adapter = new HomePageAdapter(getActivity(), DataContainer.categoryList);
        binding.viewPagerHome.setAdapter(adapter);

        // u ViewPager2 se drugacije setuje tabLayout i nazivi tabova se daju preko TabLayoutMediator-a (nasao na stackoverflow)
        new TabLayoutMediator(binding.tabLayout, binding.viewPagerHome, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Naslovna");
                }
                else {
                    tab.setText(categories.get(position-1).name);
                }
            }
        }).attach();

    }

}