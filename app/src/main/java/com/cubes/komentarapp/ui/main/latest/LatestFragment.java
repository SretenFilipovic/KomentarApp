package com.cubes.komentarapp.ui.main.latest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewLatestVideoBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsWithHeaderAdapter;

import java.util.ArrayList;

public class LatestFragment extends Fragment {

    private FragmentRecyclerViewLatestVideoBinding binding;
    private NewsWithHeaderAdapter adapter;
    private int nextPage = 2;
    private DataRepository dataRepository;


    public LatestFragment() {
    }

    public static LatestFragment newInstance() {
        return new LatestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewLatestVideoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        loadData();

        binding.refresh.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            setupRecyclerView();
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });

    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsWithHeaderAdapter((newsId, newsListId) -> {
            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("news", newsId);
            i.putExtra("newsIdList", newsListId);
            startActivity(i);
            }, () -> dataRepository.loadLatestData(nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response!=null){
                    if (response.size() > 0) {
                        adapter.addNewNewsList(response);
                    }
                    else {
                        adapter.removeItem();
                    }
                    nextPage++;
                }

            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        }));
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.setItemViewCacheSize(25);

    }

    private void loadData() {

        dataRepository.loadLatestData(1, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response != null) {
                    adapter.setData(response);
                }

                nextPage = 2;
                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);
                Log.d("LATEST", "Latest news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                binding.pullToRefresh.setRefreshing(false);
                Log.d("LATEST", "Latest news load data failure");
            }
        });
    }

}