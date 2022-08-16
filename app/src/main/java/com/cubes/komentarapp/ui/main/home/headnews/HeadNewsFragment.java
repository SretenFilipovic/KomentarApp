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

import com.cubes.komentarapp.data.model.NewsList;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;

public class HeadNewsFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private HeadNewsAdapter adapter;

    public HeadNewsFragment() {

    }

    public static HeadNewsFragment newInstance() {
        HeadNewsFragment fragment = new HeadNewsFragment();
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
        adapter = new HeadNewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        loadData();

        binding.refresh.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData();
        });
    }

    private void loadData() {

        DataRepository.getInstance().loadHeadNewsData(new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(NewsList response) {

                adapter.setData(response, news -> {
                    Intent i = new Intent(getContext(), NewsDetailActivity.class);
                    i.putExtra("news", news.id);
                    startActivity(i);
                });

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Log.d("HEAD", "Head news load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("HEAD", "Head news load data failure");
            }
        });
    }

}