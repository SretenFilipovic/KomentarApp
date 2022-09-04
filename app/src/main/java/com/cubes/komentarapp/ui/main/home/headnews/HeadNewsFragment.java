package com.cubes.komentarapp.ui.main.home.headnews;

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

import com.cubes.komentarapp.data.model.domain.CategoryBox;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategoryBigNews;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategorySmallNews;
import com.cubes.komentarapp.ui.tools.MethodsClass;

import java.util.ArrayList;

public class HeadNewsFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private HeadNewsAdapter adapter;
    private DataRepository dataRepository;
    private int[] newsIdList;

    public HeadNewsFragment() {

    }

    public static HeadNewsFragment newInstance() {
        return new HeadNewsFragment();
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
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        loadData();

        binding.refresh.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });
    }

    private void loadData() {

        dataRepository.loadHeadNewsData(new DataRepository.HeadNewsResponseListener() {
            @Override
            public void onResponse(NewsList response) {

                newsIdList = MethodsClass.createIdList(response);

                adapter.setData(response, (newsId, newsListId) -> {
                    Intent i = new Intent(getContext(), NewsDetailActivity.class);
                    i.putExtra("news", newsId);
                    i.putExtra("newsIdList", newsIdList);
                    startActivity(i);
                });

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);

                Log.d("HEAD", "Head news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);

                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("HEAD", "Head news load data failure");
            }

        });

    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HeadNewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(null);
        binding.recyclerView.setItemViewCacheSize(50);
    }
}