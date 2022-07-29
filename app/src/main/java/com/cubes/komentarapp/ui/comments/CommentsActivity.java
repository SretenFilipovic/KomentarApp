package com.cubes.komentarapp.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.response.ResponseComments;
import com.cubes.komentarapp.databinding.ActivityCommentsBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;

import java.util.ArrayList;

// ComentsActivity preko RecyclerView-a prikazuje sve komentare prvog nivoa za otvorenu vest

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private News news;
    public ArrayList<Comments> commentList = new ArrayList<>();


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

        DataRepository.getInstance().loadCommentsData(news.id, new DataRepository.CommentsResponseListener() {
            @Override
            public void onResponse(ResponseComments response) {
                commentList = response.data;

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerView.setAdapter(new CommentsFirstLevelAdapter(getApplicationContext(), commentList));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }
}