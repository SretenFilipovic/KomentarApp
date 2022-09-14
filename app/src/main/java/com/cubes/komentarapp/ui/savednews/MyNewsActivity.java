package com.cubes.komentarapp.ui.savednews;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.databinding.ActivitySavedNewsBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.PrefConfig;

import java.util.ArrayList;
import java.util.List;

public class MyNewsActivity extends AppCompatActivity {

    private List<MyNews> myNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySavedNewsBinding binding = ActivitySavedNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.imageViewBack.setOnClickListener(view -> finish());

        if (PrefConfig.readMyNewsListFromPref(this) != null){
            myNewsList = PrefConfig.readMyNewsListFromPref(this);
        }


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MyNewsAdapter adapter = new MyNewsAdapter();
        adapter.setData(myNewsList, (newsId, idList) -> {
            Intent i = new Intent(MyNewsActivity.this, NewsDetailActivity.class);
            i.putExtra("news", newsId);
            i.putExtra("newsIdList", idList);
            startActivity(i);
        });
        binding.recyclerView.setAdapter(adapter);

    }
}