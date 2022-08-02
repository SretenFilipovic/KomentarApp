package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class MostReadFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private ArrayList<News> list;
    private HeadNewsMostReadRVAdapter adapter;

    public MostReadFragment() {

    }

    public static MostReadFragment newInstance(ArrayList<News> list) {
        MostReadFragment fragment = new MostReadFragment();
        fragment.list = list;
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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HeadNewsMostReadRVAdapter(getContext(), list);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("news",news.id);
                getContext().startActivity(i);
            }
        });

        binding.recyclerView.setAdapter(adapter);
    }
}