package com.cubes.komentarapp.ui.tags;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.NewsList;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityTagBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.listeners.LoadingNewsListener;

public class TagActivity extends AppCompatActivity {

    private ActivityTagBinding binding;
    private int tagId;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tagId = getIntent().getIntExtra("tag", -1);

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        setupRecyclerView();

        loadData();

    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setNewsListener(news -> {
            Intent i = new Intent(TagActivity.this, NewsDetailActivity.class);
            i.putExtra("news", news.id);
            startActivity(i);
        });

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            int nextPage = 2;

            @Override
            public void loadMoreNews() {

                DataRepository.getInstance().loadTagData(tagId, nextPage, new DataRepository.NewsResponseListener() {
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
        DataRepository.getInstance().loadTagData(tagId, page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsList response) {

                if (response != null) {
                    adapter.setData(response.news);
                }

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Log.d("TAG", "Tag load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);

                Toast.makeText(TagActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("TAG", "Tag load data failure");
            }
        });

    }


}