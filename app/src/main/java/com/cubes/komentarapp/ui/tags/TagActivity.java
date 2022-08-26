package com.cubes.komentarapp.ui.tags;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityTagBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailWithPagerActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity {

    private ActivityTagBinding binding;
    private int tagId;
    private NewsAdapter adapter;
    private int nextPage = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        tagId = getIntent().getIntExtra("tagId", -1);
        String tagTitle = getIntent().getStringExtra("tagTitle");

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });

        Bundle bundle = new Bundle();
        bundle.putString("tags", tagTitle);
        mFirebaseAnalytics.logEvent("select_tags", bundle);

        setupRecyclerView();
        loadData();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter((newsId, newsTitle, newsListId) -> {
            Intent i = new Intent(TagActivity.this, NewsDetailWithPagerActivity.class);
            i.putExtra("news", newsId);
            i.putExtra("newsIdList", newsListId);
            i.putExtra("newsTitle", newsTitle);
            startActivity(i);
        }, () -> DataRepository.getInstance().loadTagData(tagId, nextPage, new DataRepository.NewsResponseListener() {
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
        binding.recyclerView.setAdapter(adapter);
    }

    private void loadData() {

        int page = 1;
        DataRepository.getInstance().loadTagData(tagId, page, new DataRepository.NewsResponseListener() {
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

                Log.d("TAG", "Tag load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Toast.makeText(TagActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("TAG", "Tag load data failure");
            }
        });

    }


}