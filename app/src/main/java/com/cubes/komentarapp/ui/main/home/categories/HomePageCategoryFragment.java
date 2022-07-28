package com.cubes.komentarapp.ui.main.home.categories;

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
import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.source.remote.response.ResponseNews;
import com.cubes.komentarapp.data.tools.LoadingNewsListener;
import com.cubes.komentarapp.data.tools.NewsListener;
import com.cubes.komentarapp.ui.main.NewsWithHeaderAdapter;

import java.util.ArrayList;


// U ovom fragmentu prikazuju se vesti u ViewPager-u HomeFragment-a, ali samo vesti iz kategorija koje dobijemo od servera, ne i za Naslovne

public class HomePageCategoryFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private Category category;
    private NewsWithHeaderAdapter adapter;
    private ArrayList<News> newsList;

    public HomePageCategoryFragment() {

    }

    public static HomePageCategoryFragment newInstance(Category category) {
        HomePageCategoryFragment fragment = new HomePageCategoryFragment();
        fragment.category = category;
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

    // u metodi loadData() pozivamo server da nam vrati listu vesti koju treba prikazati u fragmentu
    // nakon odgovora servera potrebno je napuniti staticku listu koja se nalazi u klasi DataContainer i pozvati metodu UpdateUI() kojom se setuje adapter za prikazivanje trazene liste
     private void loadData() {

         DataRepository.getInstance().loadCategoryData(category.id, DataContainer.page, new DataRepository.NewsResponseListener() {
             @Override
             public void onResponse(ResponseNews response) {
                if (response!=null){
                    newsList = response.data.news;
                }
                 updateUI();
             }
             @Override
             public void onFailure(Throwable t) {

             }
         });

    }

    // metoda updateUI() sluzi da se setuje adapter preko kojeg ce se prikazati vesti.
    // unutar metode, potrebno je pozvati metodu koja sluzi da se na klik na vest otvori aktiviti koji prikazuje detalje kliknute vesti kao i metodu za ucitavanje dodatnih vesti
    // na klik pravi se poziv ka serveru koji treba da vrati detalje kliknute vesti
    // ukoliko server odgovori, pravi se novi poziv koji treba da nam vrati detalje o komentarima za kliknutu vest.
    // nakon odgovora servera otvara se NewsDetailActivity
    private void updateUI(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsWithHeaderAdapter(getContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {
                DataRepository.getInstance().getNewsDetails(getContext(), news);
            }
        });

        loadMoreNews();

        binding.recyclerView.setAdapter(adapter);
    }

    // Metoda loadMoreNews() sluzi za ucitavanje dodatnih vesti kada se skroluje do dna liste vesti koje su prethodno pozvane
    private void loadMoreNews(){
        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {

                DataRepository.getInstance().loadMoreCategoryNews(category.id, page, new DataRepository.NewsResponseListener() {
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