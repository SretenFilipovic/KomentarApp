package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cubes.komentarapp.databinding.FragmentSliderBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.squareup.picasso.Picasso;

public class HeadNewsSliderFragment extends Fragment {

    private FragmentSliderBinding binding;
    private static final String NEWS_ID = "newsId";
    private static final String NEWS_TITLE = "newsTitle";
    private static final String NEWS_IMAGE = "newsImage";
    private int newsId;
    private String newsTitle;
    private String newsImage;

    public HeadNewsSliderFragment() {
    }

    public static HeadNewsSliderFragment newInstance(int newsId, String newsTitle, String newsImage) {
        HeadNewsSliderFragment fragment = new HeadNewsSliderFragment();
        Bundle args = new Bundle();
        args.putInt(NEWS_ID, newsId);
        args.putString(NEWS_TITLE, newsTitle);
        args.putString(NEWS_IMAGE, newsImage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsId = getArguments().getInt(NEWS_ID);
            newsTitle = getArguments().getString(NEWS_TITLE);
            newsImage = getArguments().getString(NEWS_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSliderBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewTitle.setText(newsTitle);
        Picasso.get().load(newsImage).into(binding.imageViewNews);


        binding.imageViewNews.setOnClickListener(view1 -> {
            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("news", newsId);
            startActivity(i);
        });

        binding.textViewTitle.setOnClickListener(view12 -> {
            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("news", newsId);
            startActivity(i);
        });

    }

}