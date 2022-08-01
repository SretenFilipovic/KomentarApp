package com.cubes.komentarapp.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityCommentsBinding;
import com.cubes.komentarapp.data.model.News;

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

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);

                loadData();
            }
        });

        loadData();

    }

    private  void loadData(){
        DataRepository.getInstance().loadCommentsData(news.id, new DataRepository.CommentsResponseListener() {
            @Override
            public void onResponse(ArrayList<Comments> response) {
                commentList = response;

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerView.setAdapter(new CommentsFirstLevelAdapter(getApplicationContext(), commentList));

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Log.d("TAG", "Comment load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);

                Log.d("TAG", "Comment load data failure");
            }
        });
    }
}