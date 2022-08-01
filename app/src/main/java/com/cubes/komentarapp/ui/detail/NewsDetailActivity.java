package com.cubes.komentarapp.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentarapp.databinding.ActivityNewsDetailBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.comments.CommentsActivity;

// NewsDetailActivity prikazuje detalje vesti koju korisnik odabere

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private News news;
    private NewsDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        news = (News) getIntent().getSerializableExtra("news");

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // klik za share
        binding.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_STREAM, news.url);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, null));
            }
        });

        // klik za komentare
        binding.imageViewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (news.comments_count == 0){
                    Toast.makeText(NewsDetailActivity.this, "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(NewsDetailActivity.this, CommentsActivity.class);
                    i.putExtra("news", news);
                    startActivity(i);
                }
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsDetailAdapter(NewsDetailActivity.this, news);
        binding.recyclerView.setAdapter(adapter);

    }
}