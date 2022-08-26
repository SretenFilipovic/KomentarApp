package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.FragmentMostReadNewsBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailWithPagerActivity;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

import java.util.ArrayList;

public class HeadNewsMostReadFragment extends Fragment {

    private FragmentMostReadNewsBinding binding;
    private ArrayList<News> mostReadNews;
    private int[] newsIdList;

    public HeadNewsMostReadFragment() {

    }

    public static HeadNewsMostReadFragment newInstance(ArrayList<News> list) {
        HeadNewsMostReadFragment fragment = new HeadNewsMostReadFragment();
        fragment.mostReadNews = list;
        fragment.newsIdList = MethodsClass.initNewsIdList(fragment.mostReadNews);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMostReadNewsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HeadNewsMostReadRVAdapter adapter = new HeadNewsMostReadRVAdapter();

        adapter.setMostReadData(mostReadNews);
        adapter.setNewsListener((newsId, newsTitle, newsListId) -> {
            Intent i = new Intent(getContext(), NewsDetailWithPagerActivity.class);
            i.putExtra("news", newsId);
            i.putExtra("newsIdList", newsListId);
            i.putExtra("newsTitle", newsTitle);
            startActivity(i);
        });

        binding.recyclerView.setAdapter(adapter);
    }
}