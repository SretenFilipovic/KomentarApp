package com.cubes.komentarapp.ui.main.home.headnews;

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
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewHeadBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

import java.util.ArrayList;

public class HeadNewsFragment extends Fragment {

    private FragmentRecyclerViewHeadBinding binding;
    private HeadNewsAdapter adapter;
    private DataRepository dataRepository;
    private int[] newsIdList;
    private ArrayList<MyNews> myNewsList = new ArrayList<>();


    public HeadNewsFragment() {

    }

    public static HeadNewsFragment newInstance() {
        return new HeadNewsFragment();
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
        binding = FragmentRecyclerViewHeadBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        loadData();

        binding.refresh.setOnClickListener(view1 -> loadData());

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });
    }

    private void loadData() {

        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.startShimmerAnimation();

        dataRepository.loadHeadNewsData(new DataRepository.HeadNewsResponseListener() {
            @Override
            public void onResponse(NewsList response) {

                newsIdList = MethodsClass.createIdList(response);

                adapter.setData(response, new NewsListener() {
                    @Override
                    public void onNewsClicked(int newsId, int[] newsListId) {
                        Intent i = new Intent(getContext(), NewsDetailActivity.class);
                        i.putExtra("news", newsId);
                        i.putExtra("newsIdList", newsIdList);
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
                });

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);
                binding.shimmerViewContainer.setVisibility(View.GONE);

                Log.d("HEAD", "Head news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);
                binding.shimmerViewContainer.setVisibility(View.GONE);

                Toast.makeText(getContext(), "Do??lo je do gre??ke.", Toast.LENGTH_SHORT).show();

                Log.d("HEAD", "Head news load data failure");
            }

        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new HeadNewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.scrollToTop.setOnClickListener(view12 -> layoutManager.smoothScrollToPosition(binding.recyclerView, null, 0));
    }
}