package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentSliderBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.squareup.picasso.Picasso;

// slajderFragment sluzi za Slajder vesti koje se prikazuju na Naslovnoj strani na vrhu

public class SliderFragment extends Fragment {

    private FragmentSliderBinding binding;
    private News news;

    public SliderFragment() {
    }

    public static SliderFragment newInstance(News news) {
        SliderFragment fragment = new SliderFragment();
        fragment.news = news;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageViewNews);

        binding.imageViewNews.setOnClickListener(view1 -> DataRepository.getInstance().getNewsDetails(news, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(News response) {
                News newsDetails = response;

                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("news",newsDetails);
                getContext().startActivity(i);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }));

        binding.textViewTitle.setOnClickListener(view12 -> DataRepository.getInstance().getNewsDetails(news, new DataRepository.NewsDetailListener() {
            @Override
            public void onResponse(News response) {
                News newsDetails = response;

                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("news",newsDetails);
                getContext().startActivity(i);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }));
    }

}