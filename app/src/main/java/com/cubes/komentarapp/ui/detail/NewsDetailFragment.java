package com.cubes.komentarapp.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.data.model.domain.Vote;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewDetailBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.comments.PostCommentActivity;
import com.cubes.komentarapp.ui.tags.TagActivity;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;
import com.cubes.komentarapp.ui.tools.listeners.DetailListener;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class NewsDetailFragment extends Fragment {

    private static final String NEWS_ID = "newsId";
    private FragmentRecyclerViewDetailBinding binding;
    private NewsDetailAdapter adapter;
    private ArrayList<Vote> votes = new ArrayList<>();
    private int newsId;
    private String newsUrl;
    private String newsTitle;
    private DetailListener detailListener;
    private FirebaseAnalytics analytics;
    private DataRepository dataRepository;
    private ArrayList<MyNews> myNewsList = new ArrayList<>();


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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.detailListener = (DetailListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        analytics = FirebaseAnalytics.getInstance(requireContext());

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        if (getArguments() != null) {
            newsId = getArguments().getInt(NEWS_ID);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.refresh.setOnClickListener(view1 -> loadData());

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });

        setupRecyclerView();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        detailListener.onDetailResponseListener(newsId, newsUrl, newsTitle);
    }

    private void loadData() {

        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.startShimmerAnimation();

        if (PrefConfig.readVoteListFromPref(getActivity()) != null) {
            votes = (ArrayList<Vote>) PrefConfig.readVoteListFromPref(getActivity());
        }

        dataRepository.getNewsDetails(newsId, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(NewsDetail response) {

                binding.recyclerView.setVisibility(View.INVISIBLE);

                newsId = response.id;
                newsUrl = response.url;
                newsTitle = response.title;

                detailListener.onDetailResponseListener(newsId, newsUrl, newsTitle);

                Bundle bundle = new Bundle();
                bundle.putString("news", newsTitle);
                analytics.logEvent("android_komentar", bundle);

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
                        Intent i = new Intent(getContext(), NewsDetailActivity.class);
                        i.putExtra("news", newsId);
                        i.putExtra("newsIdList", newsIdList);
                        i.putExtra("newsTitle", newsTitle);
                        startActivity(i);
                    }

                    @Override
                    public void onShareNewsClicked(String newsUrl) {
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_SEND);
                        i.putExtra(Intent.EXTRA_TEXT, newsUrl);
                        i.setType("text/plain");
                        startActivity(Intent.createChooser(i, null));
                    }

                    @Override
                    public void onCommentNewsClicked(int newsId) {
                        Intent i = new Intent(getContext(), CommentsActivity.class);
                        i.putExtra("news", newsId);
                        startActivity(i);
                    }

                    @Override
                    public void onSaveNewsClicked(int newsId, String newsTitle) {

                        MyNews myNews = new MyNews(newsId, newsTitle);

                        if (PrefConfig.readMyNewsListFromPref(requireActivity()) != null){
                            myNewsList = (ArrayList<MyNews>) PrefConfig.readMyNewsListFromPref(requireActivity());

                            for (int i = 0; i<myNewsList.size(); i++){
                                if (myNews.id == myNewsList.get(i).id){
                                    Toast.makeText(getContext(), "Ova vest je ve?? sa??uvana.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                        }
                        myNewsList.add(myNews);
                        PrefConfig.writeMyNewsListInPref(getActivity(), myNewsList);
                        Toast.makeText(getContext(), "Uspe??no ste sa??uvali vest.", Toast.LENGTH_SHORT).show();

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
                        dataRepository.upvoteComment(comment.id, new DataRepository.CommentsRequestListener() {
                            @Override
                            public void onResponse(ArrayList<Comments> request) {

                                Vote vote = new Vote(comment.id, true);
                                votes.add(vote);

                                PrefConfig.writeVoteListInPref(getActivity(), votes);

                                adapter.commentUpvoted(comment.id);

                                Log.d("UPVOTE", "Upvote success");
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getContext(), "Do??lo je do gre??ke.", Toast.LENGTH_SHORT).show();
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

                                PrefConfig.writeVoteListInPref(getActivity(), votes);

                                adapter.commentDownvoted(comment.id);

                                Log.d("DOWNVOTE", "Downvote success");
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getContext(), "Do??lo je do gre??ke.", Toast.LENGTH_SHORT).show();
                                Log.d("DOWNVOTE", "Downvote failure");
                            }
                        });
                    }
                }, () -> {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.refresh.setVisibility(View.GONE);

                    binding.shimmerViewContainer.setVisibility(View.GONE);
                    binding.shimmerViewContainer.stopShimmerAnimation();
                });

                binding.pullToRefresh.setRefreshing(false);

                Log.d("DETAIL", "Detail load data success");

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Do??lo je do gre??ke.", Toast.LENGTH_SHORT).show();

                binding.refresh.setVisibility(View.VISIBLE);
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Log.d("DETAIL", "Detail load data failure");
            }
        });
    }

    private void  setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsDetailAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.scrollToTop.setOnClickListener(view12 -> layoutManager.smoothScrollToPosition(binding.recyclerView, null, 0));
    }

}
