package com.cubes.komentarapp.ui.comments;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.data.source.remote.response.RequestCommentPost;
import com.cubes.komentarapp.databinding.ActivityPostCommentBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.tools.MethodsClass;

import java.util.ArrayList;

public class PostCommentActivity extends AppCompatActivity {

    private ActivityPostCommentBinding binding;
    private String commentId = "0";
    private String newsId;
    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        commentId = getIntent().getStringExtra("commentId");
        newsId = getIntent().getStringExtra("newsId");

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.buttonPostComment.setOnClickListener(view -> {
            postComment();
            MethodsClass.hideKeyboard(PostCommentActivity.this);
        });

        binding.editTextContent.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEND) {
                postComment();
                MethodsClass.hideKeyboard(PostCommentActivity.this);
                return true;
            }
            return false;
        });

    }

    private void postComment() {
        String news = String.valueOf(newsId);
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

        RequestCommentPost commentsPost = new RequestCommentPost(commentData);

        if (binding.editTextName.getText().length() > 0) {

            if (isEmailValid(binding.editTextEmail.getText())) {

                if (binding.editTextContent.getText().length() > 0) {

                    dataRepository.postCommentData(commentsPost, new DataRepository.PostCommentListener() {
                        @Override
                        public void onResponse(ArrayList<String> request) {

                            Toast.makeText(PostCommentActivity.this, "Uspe??no ste poslali komentar", Toast.LENGTH_SHORT).show();
                            finish();

                            Log.d("POST", "Post comment data success");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(PostCommentActivity.this, "Do??lo je do gre??ke.", Toast.LENGTH_SHORT).show();

                            Log.d("POST", "Post comment data failure");
                        }
                    });
                } else {
                    Toast.makeText(PostCommentActivity.this, "Morate uneti tekst u polje za komentar", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PostCommentActivity.this, "Unesite ispravan email u polje.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(PostCommentActivity.this, "Morate uneti IME u nazna??eno polje", Toast.LENGTH_SHORT).show();
        }
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}