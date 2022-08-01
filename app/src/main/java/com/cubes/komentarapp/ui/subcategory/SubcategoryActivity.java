package com.cubes.komentarapp.ui.subcategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.source.local.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivitySubcategoryBinding;
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

// U CategoryActivity prikazuju se vesti iz kategorija i podkategorija kada se na njih klikne u meniju

// P.S. komentari za metode sa dna su identicni komentarima napisanim u HomePageCategoryFragment

public class SubcategoryActivity extends AppCompatActivity {

    private ActivitySubcategoryBinding binding;
    private Category category;
    private NewsAdapter adapter;
    private ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubcategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        category = (Category) getIntent().getSerializableExtra("category");
        newsList = new ArrayList<>();

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);

                loadData();
            }
        });

        binding.textViewSubcategoryTitle.setText(category.name);

        loadData();

    }

    private void loadData() {
        int page = 1;
        DataRepository.getInstance().loadCategoryNewsData(category.id, page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsData response) {
                if (response!=null){
                    newsList = response.news;
                }
                updateUI();

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                Log.d("TAG", "Subcategory load data success");
            }
            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                Log.d("TAG", "Subcategory load data failure");
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

                        Intent i = new Intent(SubcategoryActivity.this, NewsDetailActivity.class);
                        i.putExtra("news",newsDetails);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    private void loadMoreNews(){
        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {

                DataRepository.getInstance().loadCategoryNewsData(category.id, page, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(NewsData response) {
                        adapter.addNewNewsList(response.news);
                        if(response.news.size()<20){
                            adapter.setFinished(true);
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        binding.refresh.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        adapter.setFinished(true);
                    }
                });
            }
        });
    }
}
