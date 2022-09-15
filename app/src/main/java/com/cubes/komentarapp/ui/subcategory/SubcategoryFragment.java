package com.cubes.komentarapp.ui.subcategory;

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

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentRecyclerViewBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class SubcategoryFragment extends Fragment {

    private FragmentRecyclerViewBinding binding;
    private static final String CATEGORY_ID = "categoryId";
    private static final String SUBCATEGORY_NAME = "subcategoryName";
    private int categoryId;
    private String subcategoryName;
    private NewsAdapter adapter;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DataRepository dataRepository;
    private ArrayList<MyNews> myNewsList = new ArrayList<>();


    public SubcategoryFragment() {
    }

    public static SubcategoryFragment newInstance(String subcategoryName, int categoryId) {
        SubcategoryFragment fragment = new SubcategoryFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        args.putString(SUBCATEGORY_NAME, subcategoryName);
        fragment.setArguments(args);
        fragment.categoryId = categoryId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            categoryId = getArguments().getInt(CATEGORY_ID);
            subcategoryName = getArguments().getString(SUBCATEGORY_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle.putString("subcategory", subcategoryName);
        mFirebaseAnalytics.logEvent("android_komentar", bundle);

        setupRecyclerView();

        loadData();

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
        });

        binding.refresh.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            setupRecyclerView();
            loadData();
        });
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsAdapter(new NewsListener() {
            @Override
            public void onNewsClicked(int newsId, int[] newsListId) {
                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("news", newsId);
                i.putExtra("newsIdList", newsListId);
                startActivity(i);
            }

            @Override
            public void onShareNewsClicked(String newsUrl) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, newsUrl);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, null));
            }

            @Override
            public void onCommentNewsClicked(int newsId) {
                Intent i = new Intent(getContext(), CommentsActivity.class);
                i.putExtra("news", newsId);
                startActivity(i);
            }

            @Override
            public void onSaveNewsClicked(int newsId, String newsTitle) {
                MyNews myNews = new MyNews(newsId, newsTitle);

                if (PrefConfig.readMyNewsListFromPref(requireActivity()) != null){
                    myNewsList = (ArrayList<MyNews>) PrefConfig.readMyNewsListFromPref(requireActivity());

                    for (int i = 0; i<myNewsList.size(); i++){
                        if (myNews.id == myNewsList.get(i).id){
                            Toast.makeText(getContext(), "Ova vest je već sačuvana.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    myNewsList.add(myNews);
                    PrefConfig.writeMyNewsListInPref(getActivity(), myNewsList);
                    Toast.makeText(getContext(), "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();

                }
                else {
                    myNewsList.add(myNews);
                    PrefConfig.writeMyNewsListInPref(getActivity(), myNewsList);
                    Toast.makeText(getContext(), "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();
                }
            }

        }, () -> dataRepository.loadCategoryNewsData(categoryId, nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                if (response==null || response.size() == 0){
                    adapter.removeItem();
                }
                else{
                    adapter.addNewNewsList(response);
                    nextPage++;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        }));
        binding.recyclerView.setAdapter(adapter);
    }

    private void loadData() {

        dataRepository.loadCategoryNewsData(categoryId, 1, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response != null) {
                    adapter.setData(response);
                }

                nextPage = 2;
                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);

                Log.d("SUBCATEGORY", "Subcategory load data success");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pullToRefresh.setRefreshing(false);

                Toast.makeText(getContext(), "Došlo je do greške.", Toast.LENGTH_SHORT).show();

                Log.d("SUBCATEGORY", "Subcategory load data failure");
            }
        });
    }

}
