package com.cubes.komentarapp.ui.main.video;

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

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewLatestVideoBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private FragmentRecyclerViewLatestVideoBinding binding;
    private NewsAdapter adapter;
    private int nextPage = 2;
    private DataRepository dataRepository;
    private ArrayList<MyNews> myNewsList = new ArrayList<>();


    public VideoFragment() {
    }

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewLatestVideoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        loadData();

        binding.refresh.setOnClickListener(view1 -> {
            setupRecyclerView();
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });


    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new NewsAdapter(new NewsListener() {
            @Override
            public void onNewsClicked(int newsId, int[] newsListId) {
                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("news", newsId);
                i.putExtra("newsIdList", newsListId);
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
        }, () -> dataRepository.loadVideoData(nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                if (response==null || response.size() == 0){
                    adapter.removeItem();
                }
                else{
                    adapter.addNewNewsList(response);
                    nextPage++;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        }));
        binding.recyclerView.setAdapter(adapter);

        binding.scrollToTop.setOnClickListener(view12 -> layoutManager.smoothScrollToPosition(binding.recyclerView, null, 0));
    }

    private void loadData() {

        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.startShimmerAnimation();

        int page = 1;
        dataRepository.loadVideoData(page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response != null) {
                    adapter.setData(response);
                }

                nextPage = 2;
                binding.refresh.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);

                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.shimmerViewContainer.stopShimmerAnimation();

                Log.d("VIDEO", "Video news load data success");

            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Do??lo je do gre??ke.", Toast.LENGTH_SHORT).show();

                Log.d("VIDEO", "Video news load data failure");
            }
        });
    }

}