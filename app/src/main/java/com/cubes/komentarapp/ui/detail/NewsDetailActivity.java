package com.cubes.komentarapp.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityNewsDetailBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.comments.CommentsActivity;

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private NewsDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int id = (int) getIntent().getSerializableExtra("news");

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsDetailAdapter();
        binding.recyclerView.setAdapter(adapter);

        DataRepository.getInstance().getNewsDetails(id, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(News response) {

                binding.imageViewShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_SEND);
                        i.putExtra(Intent.EXTRA_STREAM, response.url);
                        i.setType("text/plain");
                        startActivity(Intent.createChooser(i, null));
                    }
                });

                binding.imageViewComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (response.comments_count == 0){
                            Toast.makeText(NewsDetailActivity.this, "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent i = new Intent(NewsDetailActivity.this, CommentsActivity.class);
                            i.putExtra("news", response.id);
                            startActivity(i);
                        }
                    }
                });

                adapter.setData(response);

                Log.d("DETAIL", "Detail load data success");

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("DETAIL", "Detail load data failure");
            }
        });


    }
}