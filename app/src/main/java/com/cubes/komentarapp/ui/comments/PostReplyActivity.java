package com.cubes.komentarapp.ui.comments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.response.ResponseCommentPost;
import com.cubes.komentarapp.databinding.ActivityPostReplyBinding;

import java.util.ArrayList;

public class PostReplyActivity extends AppCompatActivity {

    private ActivityPostReplyBinding binding;
    private String commentId;
    private String commentNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostReplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        commentId = (String) getIntent().getSerializableExtra("commentId");
        commentNews = (String) getIntent().getSerializableExtra("commentNews");


        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.buttonPostReply.setOnClickListener(view -> {
            postReply();
            hideKeyboard(PostReplyActivity.this);
        });

        binding.editTextContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    postReply();
                    hideKeyboard(PostReplyActivity.this);
                    return true;
                }
                return false;
            }
        });

    }

    private void postReply(){
        String news = commentNews;
        String reply_id = commentId;
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

        if (binding.editTextName.getText().length() > 0) {

            if (binding.editTextContent.getText().length() > 0) {

                DataRepository.getInstance().postCommentData(replyPost, new DataRepository.PostCommentListener() {
                    @Override
                    public void onResponse(ArrayList<String> response) {

                        Toast.makeText(PostReplyActivity.this, "Uspešno ste poslali komentar", Toast.LENGTH_SHORT).show();
                        finish();

                        Log.d("REPLY", "Post reply data success");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(PostReplyActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                        Log.d("REPLY", "Post reply data failure");
                    }
                });
            } else {
                Toast.makeText(PostReplyActivity.this, "Morate uneti tekst u polje za komentar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PostReplyActivity.this, "Morate uneti IME u naznačeno polje", Toast.LENGTH_SHORT).show();
        }
    }


    private void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}