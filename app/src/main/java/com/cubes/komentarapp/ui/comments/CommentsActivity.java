package com.cubes.komentarapp.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.Vote;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityCommentsBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private CommentsAdapter adapter;
    private int newsId;
    private ArrayList<Vote> votes = new ArrayList<>();
    private final ArrayList<Comments> allComments = new ArrayList<>();
    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsId = getIntent().getIntExtra("news", -1);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.buttonLeaveComment.setOnClickListener(view -> {
            Intent i = new Intent(CommentsActivity.this, PostCommentActivity.class);
            i.putExtra("newsId", String.valueOf(newsId));
            startActivity(i);
        });

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });

        setupRecyclerView();
        loadData();
    }

    private void setupRecyclerView() {
        allComments.clear();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommentsAdapter();
        binding.recyclerView.setAdapter(adapter);

        if (PrefConfig.readListFromPref(CommentsActivity.this) != null) {
            votes = (ArrayList<Vote>) PrefConfig.readListFromPref(CommentsActivity.this);
        }

        adapter.setCommentListener(new CommentsListener() {
            @Override
            public void onReplyClicked(Comments comment) {
                Intent i = new Intent(getApplicationContext(), PostCommentActivity.class);
                i.putExtra("commentId", comment.id);
                i.putExtra("newsId", comment.newsId);
                startActivity(i);
            }

            @Override
            public void upvote(Comments comment) {
                dataRepository.upvoteComment(comment.id, new DataRepository.CommentsRequestListener() {
                    @Override
                    public void onResponse(ArrayList<Comments> request) {

                        Vote vote = new Vote(comment.id, true);
                        votes.add(vote);

                        PrefConfig.writeListInPref(CommentsActivity.this, votes);

                        adapter.commentUpvoted(comment.id);

                        Log.d("UPVOTE", "Upvote success");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(CommentsActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                        Log.d("UPVOTE", "Upvote failure");
                    }
                });
            }

            @Override
            public void downVote(Comments comment) {
                dataRepository.downvoteComment(comment.id, new DataRepository.CommentsRequestListener() {
                    @Override
                    public void onResponse(ArrayList<Comments> request) {

                        Vote vote = new Vote(comment.id, false);
                        votes.add(vote);

                        PrefConfig.writeListInPref(CommentsActivity.this, votes);

                        adapter.commentDownvoted(comment.id);

                        Log.d("DOWNVOTE", "Downvote success");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(CommentsActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                        Log.d("DOWNVOTE", "Downvote failure");
                    }
                });
            }
        });
    }

    private void loadData() {
        dataRepository.loadCommentsData(newsId, new DataRepository.CommentsResponseListener() {
            @Override
            public void onResponse(ArrayList<Comments> response) {

                setCommentData(response);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);

                Log.d("COMMENT", "Comment load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Toast.makeText(CommentsActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("COMMENT", "Comment load data failure");
            }
        });
    }

    public void setCommentData(ArrayList<Comments> responseComments) {

        for (Comments comment : responseComments) {
            allComments.add(comment);
            addChildren(comment.children);
        }
        if (votes != null) {
            loadVoteData(allComments, votes);
        }

        adapter.updateList(allComments);
    }

    private void addChildren(ArrayList<Comments> comments) {
        if (comments != null && !comments.isEmpty()) {
            for (Comments comment : comments) {
                allComments.add(comment);
                addChildren(comment.children);
            }
        }
    }

    private void loadVoteData(ArrayList<Comments> comments, ArrayList<Vote> votes) {
        for (Comments comment : comments) {
            for (Vote vote : votes) {
                if (comment.id.equals(vote.commentId)) {
                    comment.commentVote = vote;
                }
                if (comment.children != null) {
                    loadVoteData(comment.children, votes);
                }
            }
        }
    }
}