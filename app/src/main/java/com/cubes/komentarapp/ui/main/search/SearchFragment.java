package com.cubes.komentarapp.ui.main.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentSearchBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.cubes.komentarapp.ui.comments.CommentsActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tags.TagActivity;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.PrefConfig;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private NewsAdapter adapter;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DataRepository dataRepository;
    private ArrayList<MyNews> myNewsList = new ArrayList<>();

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        binding.editText.post(() ->
                automaticKeyboard(requireActivity()));

        binding.imageViewSearch.setOnClickListener(view1 -> {
            binding.recyclerView.setVisibility(View.INVISIBLE);
            MethodsClass.hideKeyboard(requireActivity());
            loadData();
        });

        binding.refresh.setOnClickListener(view12 -> {
            binding.recyclerView.setVisibility(View.INVISIBLE);
            setupRecyclerView();
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
            binding.pullToRefresh.setRefreshing(false);
        });

        binding.editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                binding.recyclerView.setVisibility(View.INVISIBLE);
                MethodsClass.hideKeyboard(requireActivity());
                loadData();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        MethodsClass.hideKeyboard(requireActivity());
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

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
                            Toast.makeText(getContext(), "Ova vest je ve?? sa??uvana.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                myNewsList.add(myNews);
                PrefConfig.writeMyNewsListInPref(getActivity(), myNewsList);
                Toast.makeText(getContext(), "Uspe??no ste sa??uvali vest.", Toast.LENGTH_SHORT).show();
            }
        },() -> dataRepository.loadSearchData(String.valueOf(binding.editText.getText()), nextPage, new DataRepository.NewsResponseListener() {
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

        binding.scrollToTop.setOnClickListener(view12 -> layoutManager.smoothScrollToPosition(binding.recyclerView, null, 0));
    }

    private void loadData() {

        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.startShimmerAnimation();

        if (binding.editText.getText().length() == 0) {
            binding.textViewNoContent.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Unesite pojam u traku za pretragu.", Toast.LENGTH_SHORT).show();
        } else if (binding.editText.getText().length() <= 2) {
            binding.textViewNoContent.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Pojam za pretragu je prekratak.", Toast.LENGTH_SHORT).show();
        } else {

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, String.valueOf(binding.editText.getText()));
            mFirebaseAnalytics.logEvent("android_komentar", bundle);

            dataRepository.loadSearchData(String.valueOf(binding.editText.getText()), 1, new DataRepository.NewsResponseListener() {
                @Override
                public void onResponse(ArrayList<News> response) {

                    setupRecyclerView();

                    if (response.size() > 0) {
                        binding.textViewNoContent.setVisibility(View.GONE);
                        adapter.setData(response);
                    } else {
                        String noContent = "Nema vesti za termin: " + binding.editText.getText();
                        binding.textViewNoContent.setText(noContent);
                        binding.textViewNoContent.setVisibility(View.VISIBLE);
                    }

                    nextPage = 2;
                    binding.refresh.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.shimmerViewContainer.setVisibility(View.GONE);

                    Log.d("SEARCH", "Search load data success");
                }

                @Override
                public void onFailure(Throwable t) {
                    binding.refresh.setVisibility(View.VISIBLE);
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                    binding.textViewNoContent.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Do??lo je do gre??ke.", Toast.LENGTH_SHORT).show();

                    Log.d("SEARCH", "Search load data failure");
                }
            });
        }
    }

    private void automaticKeyboard(Activity activity) {
        binding.editText.requestFocus();
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(binding.editText, InputMethodManager.SHOW_IMPLICIT);
    }

}