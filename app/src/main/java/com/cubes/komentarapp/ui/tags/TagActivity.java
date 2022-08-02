package com.cubes.komentarapp.ui.tags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityTagBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity {

    private ActivityTagBinding binding;
    private int tagId;
    private NewsAdapter adapter;
    private ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tagId = (int) getIntent().getSerializableExtra("tag");

        newsList = new ArrayList<>();

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadData();

    }

    private void loadData(){

        int page = 1;
        DataRepository.getInstance().loadTagData(tagId, page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsData response) {
                newsList = response.news;
                updateUI();

                Log.d("TAG", "Tag load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("TAG", "Tag load data failure");
            }
        });

    }

    private void updateUI(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter(getApplicationContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                Intent i = new Intent(TagActivity.this, NewsDetailActivity.class);
                i.putExtra("news",news.id);
                startActivity(i);
            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    private void loadMoreNews(){

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {

                DataRepository.getInstance().loadTagData(tagId, page, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(NewsData response) {
                        adapter.addNewNewsList(response.news);

                        if(response.news.size()<20){
                            adapter.setFinished(true);
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        adapter.setFinished(true);
                    }
                });
            }
        });
    }

}