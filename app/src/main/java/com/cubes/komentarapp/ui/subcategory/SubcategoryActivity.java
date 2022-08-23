package com.cubes.komentarapp.ui.subcategory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivitySubcategoryBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {

    private ActivitySubcategoryBinding binding;
    private int categoryId;
    private NewsAdapter adapter;
    private int nextPage = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubcategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryId = getIntent().getIntExtra("categoryId", -1);
        String categoryName = getIntent().getStringExtra("categoryName");

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });

        binding.textViewSubcategoryTitle.setText(categoryName);

        setupRecyclerView();
        loadData();
    }


    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setNewsListener(news -> {
            Intent i = new Intent(SubcategoryActivity.this, NewsDetailActivity.class);
            i.putExtra("news", news.id);
            startActivity(i);

        });

        adapter.setLoadingNewsListener(() -> DataRepository.getInstance().loadCategoryNewsData(categoryId, nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                adapter.addNewNewsList(response);

                nextPage++;
            }
            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        }));

    }

    private void loadData() {
        DataRepository.getInstance().loadCategoryNewsData(categoryId, 1, new DataRepository.NewsResponseListener() {
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

                Log.d("SUBCATEGORY", "Subcategory load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Toast.makeText(SubcategoryActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("SUBCATEGORY", "Subcategory load data failure");
            }
        });
    }

}
