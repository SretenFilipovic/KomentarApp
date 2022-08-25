package com.cubes.komentarapp.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemAdviewBinding;
import com.cubes.komentarapp.databinding.RvItemLoadingBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadAdView;
import com.cubes.komentarapp.ui.main.item.RvItemNews;
import com.cubes.komentarapp.ui.main.item.RvItemNewsAdView;
import com.cubes.komentarapp.ui.main.item.RvItemNewsBig;
import com.cubes.komentarapp.ui.main.item.RvItemNewsLoading;
import com.cubes.komentarapp.ui.main.item.RvItemNewsSmall;
import com.cubes.komentarapp.ui.tools.listeners.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<RvItemNews> items = new ArrayList<>();

    private final NewsListener newsListener;
    private final LoadingNewsListener loadingNewsListener;

    public NewsAdapter(NewsListener newsListener, LoadingNewsListener loadingNewsListener) {
        this.newsListener = newsListener;
        this.loadingNewsListener = loadingNewsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.rv_item_small_news) {
            binding = RvItemSmallNewsBinding.inflate(inflater, parent, false);
        } else if (viewType == R.layout.rv_item_adview){
            binding = RvItemAdviewBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);
        }
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getType();
    }

    public void addNewNewsList(ArrayList<News> newsList) {

        items.remove(items.size() - 1);


        for (int i = 0; i < newsList.size(); i++) {
            items.add(new RvItemNewsSmall(newsList.get(i), newsListener));
        }

        if (newsList.size() == 20){
            items.add(new RvItemNewsLoading(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

    public void setData(ArrayList<News> list) {

        for (int i = 0; i < list.size(); i++) {
            if (i<5){
                items.add(new RvItemNewsSmall(list.get(i), newsListener));
            }
        }
        if (list.size() >= 5){
            items.add(new RvItemNewsAdView());
        }
        for (int i = 5; i < list.size(); i++) {
            if (i < 10){
                items.add(new RvItemNewsSmall(list.get(i), newsListener));
            }
        }
        if (list.size() >= 10){
            items.add(new RvItemNewsAdView());
        }
        for (int i = 10; i < list.size(); i++) {
            if (i < 15){
                items.add(new RvItemNewsSmall(list.get(i), newsListener));
            }
        }
        if (list.size() >= 15){
            items.add(new RvItemNewsAdView());
        }

        for (int i = 15; i < list.size(); i++) {
            if (i < 20){
                items.add(new RvItemNewsSmall(list.get(i), newsListener));
            }
        }
        if (list.size() >= 20){
            items.add(new RvItemNewsAdView());
        }

        if (list.size() == 20){
            items.add(new RvItemNewsLoading(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

}
