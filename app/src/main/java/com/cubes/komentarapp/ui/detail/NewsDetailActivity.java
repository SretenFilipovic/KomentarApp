package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.data.model.domain.Vote;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivityNewsDetailBinding;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.comments.PostCommentActivity;
import com.cubes.komentarapp.ui.tags.TagActivity;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private NewsDetailAdapter adapter;
    private ArrayList<Vote> votes = new ArrayList<>();
    private int newsId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        newsId = getIntent().getIntExtra("news", -1);
        String newsTitle = getIntent().getStringExtra("newsTitle");

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });

        Bundle bundle = new Bundle();
        bundle.putString("news", newsTitle);
        mFirebaseAnalytics.logEvent("select_news", bundle);

        setupRecyclerView();
    }

    private void loadData() {

        if (PrefConfig.readListFromPref(NewsDetailActivity.this) != null) {
            votes = (ArrayList<Vote>) PrefConfig.readListFromPref(NewsDetailActivity.this);
        }

        DataRepository.getInstance().getNewsDetails(newsId, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(NewsDetail response) {

                binding.imageViewShare.setOnClickListener(view -> {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_STREAM, response.url);
                    i.setType("text/plain");
                    startActivity(Intent.createChooser(i, null));
                });

                binding.imageViewComments.setOnClickListener(view -> {
                    if (response.commentsCount == 0) {
                        Toast.makeText(NewsDetailActivity.this, "Nema komentara na ovoj vesti", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(NewsDetailActivity.this, CommentsActivity.class);
                        i.putExtra("news", response.id);
                        startActivity(i);
                    }
                });

                adapter.setData(response, new NewsDetailListener() {
                    @Override
                    public void onAllCommentsClicked(int newsId) {
                        Intent i = new Intent(getApplicationContext(), CommentsActivity.class);
                        i.putExtra("news", newsId);
                        startActivity(i);
                    }

                    @Override
                    public void onLeaveCommentClicked(String newsId) {
                        Intent i = new Intent(getApplicationContext(), PostCommentActivity.class);
                        i.putExtra("newsId", newsId);
                        startActivity(i);
                    }

                    @Override
                    public void onTagClicked(int tagId, String tagTitle) {
                        Intent i = new Intent(getApplicationContext(), TagActivity.class);
                        i.putExtra("tagId", tagId);
                        i.putExtra("tagTitle", tagTitle);
                        startActivity(i);
                    }

                    @Override
                    public void onNewsClicked(int newsId, String newsTitle, int[] newsListId) {
                        Intent i = new Intent(NewsDetailActivity.this, NewsDetailWithPagerActivity.class);
                        i.putExtra("news", newsId);
                        i.putExtra("newsIdList", newsListId);
                        i.putExtra("newsTitle", newsTitle);
                        startActivity(i);
                    }
                }, new CommentsListener() {
                    @Override
                    public void onReplyClicked(Comments comment) {
                        Intent i = new Intent(getApplicationContext(), PostCommentActivity.class);
                        i.putExtra("commentId", comment.id);
                        i.putExtra("newsId", comment.newsId);
                        startActivity(i);
                    }

                    @Override
                    public void upvote(Comments comment) {
                        DataRepository.getInstance().upvoteComment(comment.id, new DataRepository.CommentsRequestListener() {
                            @Override
                            public void onResponse(ArrayList<Comments> request) {

                                Vote vote = new Vote(comment.id, true);
                                votes.add(vote);

                                PrefConfig.writeListInPref(NewsDetailActivity.this, votes);

                                adapter.commentUpvoted(comment.id);

                                Log.d("UPVOTE", "Upvote success");
                            }
                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(NewsDetailActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                                Log.d("UPVOTE", "Upvote failure");
                            }
                        });
                    }

                    @Override
                    public void downVote(Comments comment) {
                        DataRepository.getInstance().downvoteComment(comment.id, new DataRepository.CommentsRequestListener() {
                            @Override
                            public void onResponse(ArrayList<Comments> request) {

                                Vote vote = new Vote(comment.id, false);
                                votes.add(vote);

                                PrefConfig.writeListInPref(NewsDetailActivity.this, votes);

                                adapter.commentDownvoted(comment.id);

                                Log.d("DOWNVOTE", "Downvote success");
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(NewsDetailActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                                Log.d("DOWNVOTE", "Downvote failure");
                            }
                        });
                    }
                });
                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Log.d("DETAIL", "Detail load data success");

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(NewsDetailActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Log.d("DETAIL", "Detail load data failure");
            }
        });
    }

    private void  setupRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsDetailAdapter();
        binding.recyclerView.setAdapter(adapter);

        loadData();
    }


}