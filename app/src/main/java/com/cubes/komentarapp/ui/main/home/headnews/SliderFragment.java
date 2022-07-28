package com.cubes.komentarapp.ui.main.home.headnews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentSliderBinding;
import com.cubes.komentarapp.data.model.News;
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

        binding.imageViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRepository.getInstance().getNewsDetails(getContext(), news);
            }
        });
        binding.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRepository.getInstance().getNewsDetails(getContext(), news);
            }
        });
    }

}