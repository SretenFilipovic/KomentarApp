package com.cubes.komentarapp.ui.mynews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.databinding.ActivitySavedNewsBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.MyNewsListener;

import java.util.ArrayList;
import java.util.List;

public class MyNewsActivity extends AppCompatActivity {

    private List<MyNews> myNewsList = new ArrayList<>();
    private ActivitySavedNewsBinding binding;
    private MyNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.pullToRefresh.setOnRefreshListener(this::setupRecyclerView);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                adapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

                PrefConfig.readMyNewsListFromPref(MyNewsActivity.this).clear();
                PrefConfig.writeMyNewsListInPref(MyNewsActivity.this, adapter.getNewList());

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(binding.recyclerView);


        setupRecyclerView();
    }

    private void setupRecyclerView() {

        if (PrefConfig.readMyNewsListFromPref(this) != null) {
            myNewsList = PrefConfig.readMyNewsListFromPref(this);
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MyNewsAdapter();

        adapter.setData(myNewsList, new MyNewsListener() {
            @Override
            public void onNewsClicked(int newsId, int[] idList) {
                Intent i = new Intent(MyNewsActivity.this, NewsDetailActivity.class);
                i.putExtra("news", newsId);
                i.putExtra("newsIdList", idList);
                startActivity(i);
            }

            @Override
            public void onRemoveClicked(int newsId) {

                for (int i = 0; i < myNewsList.size(); i++) {
                    if (newsId == myNewsList.get(i).id) {
                        myNewsList.remove(myNewsList.get(i));
                        PrefConfig.writeMyNewsListInPref(MyNewsActivity.this, myNewsList);
                        Toast.makeText(MyNewsActivity.this, "Uspešno ste izbacili vest iz liste.", Toast.LENGTH_SHORT).show();
                        setupRecyclerView();
                    }
                }
            }
        });

        binding.recyclerView.setAdapter(adapter);

        if (myNewsList.size() == 0){
            binding.textViewNoNews.setVisibility(View.VISIBLE);
        }
        else {
            binding.textViewNoNews.setVisibility(View.GONE);
        }

        binding.pullToRefresh.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
    }

}