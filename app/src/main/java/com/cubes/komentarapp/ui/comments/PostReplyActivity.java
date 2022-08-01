package com.cubes.komentarapp.ui.comments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityPostReplyBinding;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.data.source.remote.response.ResponseCommentPost;

import java.util.ArrayList;

// PostReplyActivity daje korisniku mogucnost da posalje odgovor na odredjeni komentar

public class PostReplyActivity extends AppCompatActivity {

    private ActivityPostReplyBinding binding;
    private Comments comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostReplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        comment = (Comments) getIntent().getSerializableExtra("comment");

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.buttonPostReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String news = comment.news;
                String reply_id = comment.id;
                String name = String.valueOf(binding.editTextName.getText());
                String email = String.valueOf(binding.editTextEmail.getText());
                String content = String.valueOf(binding.editTextContent.getText());

                ArrayList<String> commentData = new ArrayList<>();
                commentData.add(news);
                commentData.add(reply_id);
                commentData.add(name);
                commentData.add(email);
                commentData.add(content);

                ResponseCommentPost replyPost = new ResponseCommentPost(commentData);

                DataRepository.getInstance().postCommentData(replyPost, new DataRepository.PostCommentListener() {
                    @Override
                    public void onResponse(ArrayList<String> response) {
                        if (binding.editTextName.getText().length() > 0){

                            if (binding.editTextContent.getText().length() > 0){

                                Toast.makeText(PostReplyActivity.this, "Uspešno ste poslali komentar", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(PostReplyActivity.this, "Morate uneti tekst u polje za komentar", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(PostReplyActivity.this, "Morate uneti IME u naznačeno polje", Toast.LENGTH_SHORT).show();
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