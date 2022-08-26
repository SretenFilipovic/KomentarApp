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

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.FragmentSearchBinding;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.detail.NewsDetailWithPagerActivity;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private NewsAdapter adapter;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;


    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        binding.editText.post(() -> automaticKeyboard(requireActivity()));

        binding.imageViewSearch.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.INVISIBLE);
            hideKeyboard(requireActivity());
            loadData();
        });

        binding.refresh.setOnClickListener(view12 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.INVISIBLE);
            loadData();
        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadData();
            binding.pullToRefresh.setRefreshing(false);
        });

        binding.editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.INVISIBLE);
                hideKeyboard(requireActivity());
                loadData();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard(requireActivity());
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter((newsId, newsTitle,  newsListId) -> {
            Intent i = new Intent(getContext(), NewsDetailWithPagerActivity.class);
            i.putExtra("news", newsId);
            i.putExtra("newsIdList", newsListId);
            i.putExtra("newsTitle", newsTitle);
            startActivity(i);
        }, () -> DataRepository.getInstance().loadSearchData(String.valueOf(binding.editText.getText()), nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                adapter.addNewNewsList(response);
                nextPage++;
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

        if (binding.editText.getText().length() == 0) {
            binding.progressBar.setVisibility(View.GONE);
            binding.textViewNoContent.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Unesite pojam u traku za pretragu.", Toast.LENGTH_SHORT).show();
        } else if (binding.editText.getText().length() <= 2) {
            binding.progressBar.setVisibility(View.GONE);
            binding.textViewNoContent.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Pojam za pretragu je prekratak.", Toast.LENGTH_SHORT).show();
        } else {

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, String.valueOf(binding.editText.getText()));
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

            DataRepository.getInstance().loadSearchData(String.valueOf(binding.editText.getText()), 1, new DataRepository.NewsResponseListener() {
                @Override
                public void onResponse(ArrayList<News> response) {

                    setupRecyclerView();

                    if (response.size() > 0) {
                        binding.textViewNoContent.setVisibility(View.GONE);
                        adapter.setData(response);
                    } else {
                        binding.textViewNoContent.setText("Nema vesti za termin: " + binding.editText.getText());
                        binding.textViewNoContent.setVisibility(View.VISIBLE);
                    }

                    nextPage = 2;
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

    private void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void automaticKeyboard(Activity activity) {
        binding.editText.requestFocus();
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(binding.editText, InputMethodManager.SHOW_IMPLICIT);
    }

}