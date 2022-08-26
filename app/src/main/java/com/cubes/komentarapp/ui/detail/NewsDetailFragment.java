package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.data.model.domain.Vote;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.comments.PostCommentActivity;
import com.cubes.komentarapp.ui.tags.TagActivity;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;

import java.util.ArrayList;

public class NewsDetailFragment extends Fragment {

    private static final String NEWS_ID = "newsId";
    private FragmentRecyclerViewBinding binding;
    private NewsDetailAdapter adapter;
    private ArrayList<Vote> votes = new ArrayList<>();
    private int newsId;


    public NewsDetailFragment() {
    }

    public static NewsDetailFragment newInstance(int newsId) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putInt(NEWS_ID, newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsId = getArguments().getInt(NEWS_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.refresh.setOnClickListener(view1 -> {
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

    private void loadData() {

        if (PrefConfig.readListFromPref(getActivity()) != null) {
            votes = (ArrayList<Vote>) PrefConfig.readListFromPref(getActivity());
        }

        DataRepository.getInstance().getNewsDetails(newsId, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(NewsDetail response) {

                adapter.setData(response, new NewsDetailListener() {
                    @Override
                    public void onAllCommentsClicked(int newsId) {
                        Intent i = new Intent(getContext(), CommentsActivity.class);
                        i.putExtra("news", newsId);
                        startActivity(i);
                    }

                    @Override
                    public void onLeaveCommentClicked(String newsId) {
                        Intent i = new Intent(getContext(), PostCommentActivity.class);
                        i.putExtra("newsId", newsId);
                        startActivity(i);
                    }

                    @Override
                    public void onTagClicked(int tagId, String tagTitle) {
                        Intent i = new Intent(getContext(), TagActivity.class);
                        i.putExtra("tagId", tagId);
                        i.putExtra("tagTitle", tagTitle);
                        startActivity(i);
                    }

                    @Override
                    public void onNewsClicked(int newsId, String newsTitle, int[] newsIdList) {
                        Intent i = new Intent(getContext(), NewsDetailWithPagerActivity.class);
                        i.putExtra("news", newsId);
                        i.putExtra("newsIdList", newsIdList);
                        i.putExtra("newsTitle", newsTitle);
                        startActivity(i);
                    }
                }, new CommentsListener() {
                    @Override
                    public void onReplyClicked(Comments comment) {
                        Intent i = new Intent(getContext(), PostCommentActivity.class);
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

                                PrefConfig.writeListInPref(getActivity(), votes);

                                adapter.commentUpvoted(comment.id);

                                Log.d("UPVOTE", "Upvote success");
                            }
                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();
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

                                PrefConfig.writeListInPref(getActivity(), votes);

                                adapter.commentDownvoted(comment.id);

                                Log.d("DOWNVOTE", "Downvote success");
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Log.d("DETAIL", "Detail load data failure");
            }
        });
    }

    private void  setupRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsDetailAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

}
