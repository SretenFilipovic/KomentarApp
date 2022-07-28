package com.cubes.komentarapp.ui.comments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityPostCommentBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.remote.response.PostComment;

import java.util.ArrayList;

// PostCommentActivity daje korisniku mogucnost da postavi komentar na odredjenu vest

public class PostCommentActivity extends AppCompatActivity {

    private ActivityPostCommentBinding binding;
    private News newsForComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsForComment = (News) getIntent().getSerializableExtra("news");

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.buttonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String news = String.valueOf(newsForComment.id);
                String reply_id = "0";
                String name = String.valueOf(binding.editTextName.getText());
                String email = String.valueOf(binding.editTextEmail.getText());
                String content = String.valueOf(binding.editTextContent.getText());

                ArrayList<String> commentData = new ArrayList<>();
                commentData.add(news);
                commentData.add(reply_id);
                commentData.add(name);
                commentData.add(email);
                commentData.add(content);

                PostComment commentsPost = new PostComment(commentData);

                DataRepository.getInstance().postCommentData(commentsPost, new DataRepository.PostCommentListener() {
                    @Override
                    public void onResponse(PostComment response) {
                        if (binding.editTextName.getText().length() > 0){
                            if (binding.editTextContent.getText().length() > 0){

                                // provera za response code
                                //Toast.makeText(PostCommentActivity.this, "Response code: " + response.code(), Toast.LENGTH_SHORT).show();

                                Toast.makeText(PostCommentActivity.this, "Uspešno ste poslali komentar", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(PostCommentActivity.this, "Morate uneti tekst u polje za komentar", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(PostCommentActivity.this, "Morate uneti IME u naznačeno polje", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }
        });

    }
}