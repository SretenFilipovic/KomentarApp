package com.cubes.komentarapp.ui.tags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityTagBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;


public class TagActivity extends AppCompatActivity {

    private ActivityTagBinding binding;
    private int tagId;
    private NewsAdapter adapter;
    private int nextPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tagId = (int) getIntent().getSerializableExtra("tag");

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                loadData();
            }
        });

        setupRecyclerView();

        loadData();

    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                Intent i = new Intent(TagActivity.this, NewsDetailActivity.class);
                i.putExtra("news",news.id);
                startActivity(i);
            }
        });

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews() {

                DataRepository.getInstance().loadTagData(tagId, nextPage, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(NewsData response) {
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

    private void loadData(){

        int page = 1;
        DataRepository.getInstance().loadTagData(tagId, page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsData response) {

                if (response!=null){
                    adapter.setData(response);
                }

                nextPage++;
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