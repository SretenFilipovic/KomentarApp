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

import com.cubes.komentarapp.data.model.CategoryHomePage;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategory;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadEditorsChoiceSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadMostRead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadTop;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadVideo;

import java.util.ArrayList;


// U ovom fragmentu prikazuju se Naslovne vesti

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

    }


    private void loadData(){

        DataRepository.getInstance().loadHeadNewsData(new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNews response) {
                sliderList = response.data.slider;
                topList = response.data.top;
                editorsChoiceList = response.data.editors_choice;
                videosList = response.data.videos;
                mostReadList = response.data.most_read;
                latestList = response.data.latest;
                mostCommentedList = response.data.most_comented;
                fromCategoryList = response.data.category;

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new HeadNewsAdapter(getContext(), sliderList, topList, editorsChoiceList, videosList, mostReadList, latestList, mostCommentedList, fromCategoryList);
                binding.recyclerView.setAdapter(adapter);

                Log.d("TAG", "Home news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("TAG", "Home news load data failure");

            }
        });
    }

}