package com.cubes.komentarapp.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityCommentsBinding;
import com.cubes.komentarapp.ui.tools.CommentsListener;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private CommentsAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = (int) getIntent().getSerializableExtra("news");

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.buttonLeaveComment.setOnClickListener(view -> {
            Intent i = new Intent(CommentsActivity.this, PostCommentActivity.class);
            i.putExtra("newsId", String.valueOf(id));
            startActivity(i);
        });

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        setupRecyclerView();

        loadData();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommentsAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setCommentListener(new CommentsListener() {
            @Override
            public void onCommentsClicked(Comments comment) {
                Intent i = new Intent(getApplicationContext(), PostCommentActivity.class);
                i.putExtra("commentId", comment.id);
                i.putExtra("newsId", comment.news);
                startActivity(i);
            }
        });
    }

    private void loadData() {
        DataRepository.getInstance().loadCommentsData(id, new DataRepository.CommentsResponseListener() {
            @Override
            public void onResponse(ArrayList<Comments> response) {

                adapter.setData(response);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Log.d("COMMENT", "Comment load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(CommentsActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("COMMENT", "Comment load data failure");
            }
        });
    }
}