package com.cubes.komentarapp.ui.main.latest;

import android.content.Intent;
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

import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

public class LatestFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private NewsAdapter adapter;
    private ArrayList<News> newsList;

    public LatestFragment() {
    }

    public static LatestFragment newInstance() {
        LatestFragment fragment = new LatestFragment();
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

        DataRepository.getInstance().loadLatestData(page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsData response) {
                if (response!=null){
                    newsList = response.news;
                }
                updateUI();

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Log.d("LATEST", "Latest news load data success");

            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);

                Log.d("LATEST", "Latest news load data failure");

            }
        });
    }

    private void updateUI(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {

                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("news",news.id);
                getContext().startActivity(i);

            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    private void loadMoreNews(){
        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadLatestData(page, new DataRepository.NewsResponseListener() {
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