package com.cubes.komentarapp.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.databinding.ActivityCommentsBinding;
import com.cubes.komentarapp.data.model.News;

// ComentsActivity preko RecyclerView-a prikazuje sve komentare prvog nivoa za otvorenu vest

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        news = (News) getIntent().getSerializableExtra("news");

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.buttonLeaveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommentsActivity.this, PostCommentActivity.class);
                i.putExtra("news", news);
                startActivity(i);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setAdapter(new CommentsFirstLevelAdapter(getApplicationContext(), DataContainer.commentList));
    }
}