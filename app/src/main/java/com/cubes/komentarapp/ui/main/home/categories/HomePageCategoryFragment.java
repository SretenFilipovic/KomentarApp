package com.cubes.komentarapp.ui.main.home.categories;

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
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsWithHeaderAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class HomePageCategoryFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private static final String CATEGORY_ID = "categoryId";
    private static final String CATEGORY_TITLE = "categoryTitle";
    private int categoryId;
    private String categoryTitle;
    private NewsWithHeaderAdapter adapter;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DataRepository dataRepository;

    public HomePageCategoryFragment() {
    }

    public static HomePageCategoryFragment newInstance(int categoryId, String categoryTitle) {
        HomePageCategoryFragment fragment = new HomePageCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        args.putString(CATEGORY_TITLE, categoryTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(CATEGORY_ID);
            categoryTitle = getArguments().getString(CATEGORY_TITLE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle.putString("category", categoryTitle);
        mFirebaseAnalytics.logEvent("android_komentar", bundle);

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
        }, () -> dataRepository.loadCategoryNewsData(categoryId, nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response==null || response.size() == 0){
                    adapter.removeItem();
                }
                else{
                    adapter.addNewNewsList(response);
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

        dataRepository.loadCategoryNewsData(categoryId, 1, new DataRepository.NewsResponseListener() {
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

                Log.d("CATEGORY", "Category news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("CATEGORY", "Category news load data failure");
            }
        });

    }
}