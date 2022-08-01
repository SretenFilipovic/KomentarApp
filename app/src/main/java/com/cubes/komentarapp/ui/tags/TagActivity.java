package com.cubes.komentarapp.ui.tags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityTagBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.model.Tags;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

// TagActivity preko RecyclerView prikazuje listu vesti za odredjeni tag
// P.S. komentari za metode sa dna su identicni komentarima napisanim u HomePageCategoryFragment

public class TagActivity extends AppCompatActivity {

    private ActivityTagBinding binding;
    private Tags tag;
    private NewsAdapter adapter;
    private ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tag = (Tags) getIntent().getSerializableExtra("tag");

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
        DataRepository.getInstance().loadTagData(tag.id, page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsData response) {
                newsList = response.news;
                updateUI();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    private void updateUI(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter(getApplicationContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                DataRepository.getInstance().getNewsDetails(news, new DataRepository.NewsDetailListener() {
                    @Override
                    public void onResponse(News response) {
                        News newsDetails = response;

                        Intent i = new Intent(TagActivity.this, NewsDetailActivity.class);
                        i.putExtra("news",newsDetails);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    private void loadMoreNews(){

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {

                DataRepository.getInstance().loadTagData(tag.id, page, new DataRepository.NewsResponseListener() {
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