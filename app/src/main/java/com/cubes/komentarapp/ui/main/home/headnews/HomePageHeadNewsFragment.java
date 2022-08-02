package com.cubes.komentarapp.ui.main.home.headnews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentarapp.data.model.CategoryHomePage;
import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;

import java.util.ArrayList;

public class HomePageHeadNewsFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private HeadNewsAdapter adapter;
    private ArrayList<News> sliderList;
    private ArrayList<News> topList;
    private ArrayList<News> editorsChoiceList;
    private ArrayList<News> videosList;
    private ArrayList<News> mostReadList;
    private ArrayList<News> latestList;
    private ArrayList<News> mostCommentedList;
    private ArrayList<CategoryHomePage> fromCategoryList;

    public HomePageHeadNewsFragment() {

    }

    public static HomePageHeadNewsFragment newInstance() {
        HomePageHeadNewsFragment fragment = new HomePageHeadNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);

                loadData();
            }
        });
    }

    private void loadData(){

        DataRepository.getInstance().loadHeadNewsData(new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsData response) {
                sliderList = response.slider;
                topList = response.top;
                editorsChoiceList = response.editors_choice;
                videosList = response.videos;
                mostReadList = response.most_read;
                latestList = response.latest;
                mostCommentedList = response.most_comented;
                fromCategoryList = response.category;

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new HeadNewsAdapter(getContext(), sliderList, topList, editorsChoiceList, videosList, mostReadList, latestList, mostCommentedList, fromCategoryList);
                binding.recyclerView.setAdapter(adapter);

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Log.d("HEAD", "Head news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);

                Log.d("HEAD", "Head news load data failure");
            }
        });
    }

}