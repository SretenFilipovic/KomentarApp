package com.cubes.komentarapp.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomePageAdapter adapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new HomePageAdapter(requireActivity());
        loadData();

    }

    private void loadData(){

        DataRepository.getInstance().loadCategoryData(new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> response) {
                adapter.setData(response);
                binding.viewPagerHome.setAdapter(adapter);

                new TabLayoutMediator(binding.tabLayout, binding.viewPagerHome, (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Naslovna");
                    } else {
                        tab.setText(response.get(position - 1).name);
                    }
                }).attach();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}