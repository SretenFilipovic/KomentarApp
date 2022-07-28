package com.cubes.komentarapp.ui.tags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityTagBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.data.model.Tags;
import com.cubes.komentarapp.data.tools.LoadingNewsListener;
import com.cubes.komentarapp.data.tools.NewsListener;
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

        DataRepository.getInstance().loadTagData(tag.id, DataContainer.page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNews response) {
                newsList = response.data.news;
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
                DataRepository.getInstance().getNewsDetails(TagActivity.this, news);
            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    private void loadMoreNews(){

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {

                DataRepository.getInstance().loadMoreTagNews(tag.id, page, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(ResponseNews response) {
                        adapter.addNewNewsList(response.data.news);

                        if(response.data.news.size()<20){
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