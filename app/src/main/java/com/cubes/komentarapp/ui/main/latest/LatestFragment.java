package com.cubes.komentarapp.ui.main.latest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.data.tools.LoadingNewsListener;
import com.cubes.komentarapp.data.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;

// u ovom fragmentu prikazuju se najnovije vesti sa servera
// P.S. komentari za metode sa dna su identicni komentarima napisanim u HomePageCategoryFragment

public class LatestFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private NewsAdapter adapter;
    private ArrayList<News> newsList;

    public LatestFragment() {
    }

    public static LatestFragment newInstance() {
        LatestFragment fragment = new LatestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);

        newsList = new ArrayList<>();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
    }

    private void loadData(){

        DataRepository.getInstance().loadLatestData(DataContainer.page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNews response) {
                newsList = response.data.news;
                updateUI();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void updateUI(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                DataRepository.getInstance().getNewsDetails(getContext(), news);
            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    private void loadMoreNews(){
        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadLatestData(page, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(ResponseNews response) {
                        adapter.addNewNewsList(response.data.news);
                        if(response.data.news.size()<20){
                            adapter.setFinished(true);
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        adapter.setFinished(true);
                    }
                });
            }
        });
    }


}