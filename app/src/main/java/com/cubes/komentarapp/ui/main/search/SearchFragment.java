package com.cubes.komentarapp.ui.main.search;

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

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentSearchBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private NewsAdapter adapter;
    private int nextPage = 1;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        binding.imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                loadData();
            }
        });

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                loadData();
            }
        });
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {

                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("news",news.id);
                getContext().startActivity(i);

            }
        });

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews() {

                DataRepository.getInstance().loadSearchData(String.valueOf(binding.editText.getText()), nextPage, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(NewsData response) {
                        adapter.addNewNewsList(response.news);
                        nextPage++;
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        binding.refresh.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void loadData() {

        int page = 1;

        if (binding.editText.getText().length() == 0){
            Toast.makeText(getContext(), "Unesite pojam u traku za pretragu.", Toast.LENGTH_SHORT).show();
        }
        else if (binding.editText.getText().length() <= 2){
            Toast.makeText(getContext(), "Pojam za pretragu je prekratak.", Toast.LENGTH_SHORT).show();
        }
        else {

            DataRepository.getInstance().loadSearchData(String.valueOf(binding.editText.getText()), page, new DataRepository.NewsResponseListener() {
                @Override
                public void onResponse(NewsData response) {

                    if(response.news.size()>0){
                        binding.textViewNoContent.setVisibility(View.GONE);
                        adapter.setData(response);
                    }
                    else{
                        binding.textViewNoContent.setText("Nema vesti za termin: " + binding.editText.getText());
                        binding.textViewNoContent.setVisibility(View.VISIBLE);
                    }

                    nextPage++;
                    binding.refresh.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);

                    Log.d("SEARCH", "Search load data success");
                }

                @Override
                public void onFailure(Throwable t) {
                    binding.refresh.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.textViewNoContent.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                    Log.d("SEARCH", "Search load data failure");
                }
            });
        }

    }


}