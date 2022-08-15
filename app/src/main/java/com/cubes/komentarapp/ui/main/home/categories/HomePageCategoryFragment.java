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

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.model.NewsList;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsWithHeaderAdapter;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;

public class HomePageCategoryFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private static final String CATEGORY_ID = "categoryId";
    private int categoryId;
    private NewsWithHeaderAdapter adapter;

    public HomePageCategoryFragment() {

    }

    public static HomePageCategoryFragment newInstance(int categoryId) {
        HomePageCategoryFragment fragment = new HomePageCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(CATEGORY_ID);
        }
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

        setupRecyclerView();

        loadData();

        binding.refresh.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });
    }


    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsWithHeaderAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setNewsListener(news -> {

            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("news", news.id);
            getContext().startActivity(i);

        });

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            int nextPage = 2;

            @Override
            public void loadMoreNews() {

                DataRepository.getInstance().loadCategoryNewsData(categoryId, nextPage, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(NewsList response) {
                        adapter.addNewNewsList(response.news);
                        nextPage++;
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.refresh.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void loadData() {

        int page = 1;
        DataRepository.getInstance().loadCategoryNewsData(categoryId, page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsList response) {

                if (response != null) {
                    adapter.setData(response.news);
                }

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Log.d("CATEGORY", "Category news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("CATEGORY", "Category news load data failure");
            }
        });

    }
}