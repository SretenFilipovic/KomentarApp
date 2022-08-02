package com.cubes.komentarapp.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

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

        HomePageAdapter adapter = new HomePageAdapter(getActivity(), categories);
        binding.viewPagerHome.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPagerHome, (tab, position) -> {
            if (position == 0) {
                tab.setText("Naslovna");
            } else {
                tab.setText(categories.get(position - 1).name);
            }
        }).attach();

    }

}