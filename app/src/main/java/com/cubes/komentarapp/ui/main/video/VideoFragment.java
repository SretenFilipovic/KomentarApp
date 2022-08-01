package com.cubes.komentarapp.ui.main.video;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

// u ovom fragmentu prikazuju se video vesti
// P.S. komentari za metode sa dna su identicni komentarima napisanim u HomePageCategoryFragment

public class VideoFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private NewsAdapter adapter;
    private ArrayList<News> newsList;

    public VideoFragment() {

    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
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

        newsList = new ArrayList<>();

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
        int page = 1;

        DataRepository.getInstance().loadVideoData(page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsData response) {
                newsList = response.news;
                updateUI();

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    private void updateUI(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                DataRepository.getInstance().getNewsDetails(news, new DataRepository.NewsDetailListener() {
                    @Override
                    public void onResponse(News response) {
                        News newsDetails = response;

                        Intent i = new Intent(getContext(), NewsDetailActivity.class);
                        i.putExtra("news",newsDetails);
                        getContext().startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    private void loadMoreNews(){
        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {

                DataRepository.getInstance().loadVideoData(page, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(NewsData response) {
                        adapter.addNewNewsList(response.news);
                        if(response.news.size()<20){
                            adapter.setFinished(true);
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        binding.refresh.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        adapter.setFinished(true);
                    }
                });
            }
        });
    }

}