package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityNewsDetailBinding;
import com.cubes.komentarapp.ui.comments.CommentsActivity;

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private NewsDetailAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = (int) getIntent().getSerializableExtra("news");

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsDetailAdapter();
        binding.recyclerView.setAdapter(adapter);

        loadData();

    }

    private void loadData() {
        DataRepository.getInstance().getNewsDetails(id, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(News response) {

                binding.imageViewShare.setOnClickListener(view -> {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_STREAM, response.url);
                    i.setType("text/plain");
                    startActivity(Intent.createChooser(i, null));
                });

                binding.imageViewComments.setOnClickListener(view -> {
                    if (response.comments_count == 0) {
                        Toast.makeText(NewsDetailActivity.this, "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(NewsDetailActivity.this, CommentsActivity.class);
                        i.putExtra("news", response.id);
                        startActivity(i);
                    }
                });

                adapter.setData(response);
                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);

                Log.d("DETAIL", "Detail load data success");

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(NewsDetailActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d("DETAIL", "Detail load data failure");
            }
        });
    }

}