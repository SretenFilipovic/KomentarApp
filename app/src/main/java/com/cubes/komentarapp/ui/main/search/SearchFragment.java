package com.cubes.komentarapp.ui.main.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentSearchBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.data.tools.LoadingNewsListener;
import com.cubes.komentarapp.data.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsAdapter;

import java.util.ArrayList;


// u ovom fragmentu prikazuju se vesti keje dobijamo pretragom

// P.S. komentari za metode sa dna su slicni komentarima napisanim u HomePageCategoryFragment,
// razlikuje se se samo metoda loadData() u tome sto se u SearchFragment vesti ne ucitavaju automatski vec je potrebno uneti termin za koji ce nam server vratiti listu vesti

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private NewsAdapter adapter;
    private ArrayList<News> newsList;

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

        newsList = new ArrayList<>();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
    }

    private void loadData() {

        binding.imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // prvo proveravamo da li je korisnik uneo pojam u traku za pretragu i da li je pojam dovoljne duzine
                if (binding.editText.getText().length() == 0){
                    Toast.makeText(getContext(), "Unesite pojam u traku za pretragu.", Toast.LENGTH_SHORT).show();
                }
                else if (binding.editText.getText().length() <= 2){
                    Toast.makeText(getContext(), "Pojam za pretragu je prekratak.", Toast.LENGTH_SHORT).show();
                }
                // poziva se server
                else {

                    DataRepository.getInstance().loadSearchData(String.valueOf(binding.editText.getText()),DataContainer.page, new DataRepository.NewsResponseListener() {
                        @Override
                        public void onResponse(ResponseNews response) {
                            newsList = response.data.news;

                            if(newsList.size()>0){
                                binding.textViewNoContent.setVisibility(View.GONE);
                            }
                            // ako server nije nasao nijednu vest za uneti pojam, pojavice se poruka
                            else{
                                binding.textViewNoContent.setText("Nema vesti za termin: " + binding.editText.getText());
                                binding.textViewNoContent.setVisibility(View.VISIBLE);
                            }
                            updateUI();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
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

                DataRepository.getInstance().loadSearchData(String.valueOf(binding.editText.getText()), page, new DataRepository.NewsResponseListener() {
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