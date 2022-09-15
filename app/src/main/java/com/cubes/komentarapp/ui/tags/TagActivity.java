package com.cubes.komentarapp.ui.tags;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityTagBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity {

    private ActivityTagBinding binding;
    private int tagId;
    private NewsAdapter adapter;
    private int nextPage = 2;
    private DataRepository dataRepository;
    private ArrayList<MyNews> myNewsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        tagId = getIntent().getIntExtra("tagId", -1);
        String tagTitle = getIntent().getStringExtra("tagTitle");

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.textViewTagTitle.setText(tagTitle);

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            setupRecyclerView();
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
        adapter = new NewsAdapter(new NewsListener() {
            @Override
            public void onNewsClicked(int newsId, int[] newsListId) {
                Intent i = new Intent(TagActivity.this, NewsDetailActivity.class);
                i.putExtra("news", newsId);
                i.putExtra("newsIdList", newsListId);
                startActivity(i);
            }

            @Override
            public void onShareNewsClicked(String newsUrl) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, newsUrl);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, null));
            }

            @Override
            public void onCommentNewsClicked(int newsId) {
                Intent i = new Intent(TagActivity.this, CommentsActivity.class);
                i.putExtra("news", newsId);
                startActivity(i);
            }

            @Override
            public void onSaveNewsClicked(int newsId, String newsTitle) {
                MyNews myNews = new MyNews(newsId, newsTitle);

                if (PrefConfig.readMyNewsListFromPref(TagActivity.this) != null){
                    myNewsList = (ArrayList<MyNews>) PrefConfig.readMyNewsListFromPref(TagActivity.this);

                    for (int i = 0; i<myNewsList.size(); i++){
                        if (myNews.id == myNewsList.get(i).id){
                            Toast.makeText(TagActivity.this, "Ova vest je već sačuvana.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    myNewsList.add(myNews);
                    PrefConfig.writeMyNewsListInPref(TagActivity.this, myNewsList);
                    Toast.makeText(TagActivity.this, "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();
                }
                else {
                    myNewsList.add(myNews);
                    PrefConfig.writeMyNewsListInPref(TagActivity.this, myNewsList);
                    Toast.makeText(TagActivity.this, "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();
                }
            }
        }, () -> dataRepository.loadTagData(tagId, nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                if (response == null || response.size() == 0) {
                    adapter.removeItem();
                } else {
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
    }

    private void loadData() {

        int page = 1;
        dataRepository.loadTagData(tagId, page, new DataRepository.NewsResponseListener() {
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