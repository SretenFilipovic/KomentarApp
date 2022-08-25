package com.cubes.komentarapp.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemAdviewBinding;
import com.cubes.komentarapp.databinding.RvItemBigNewsBinding;
import com.cubes.komentarapp.databinding.RvItemLoadingBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.item.RvItemNews;
import com.cubes.komentarapp.ui.main.item.RvItemNewsAdView;
import com.cubes.komentarapp.ui.main.item.RvItemNewsBig;
import com.cubes.komentarapp.ui.main.item.RvItemNewsLoading;
import com.cubes.komentarapp.ui.main.item.RvItemNewsSmall;
import com.cubes.komentarapp.ui.tools.listeners.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

import java.util.ArrayList;

public class NewsWithHeaderAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<RvItemNews> items = new ArrayList<>();

    private final NewsListener newsListener;
    private final LoadingNewsListener loadingNewsListener;

    public NewsWithHeaderAdapter(NewsListener newsListener, LoadingNewsListener loadingNewsListener) {
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
        } else if (viewType == R.layout.rv_item_big_news){
            binding = RvItemBigNewsBinding.inflate(inflater, parent, false);
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

        items.add(new RvItemNewsBig(list.get(0), newsListener));

        items.add(new RvItemNewsAdView());

        for (int i = 1; i < list.size(); i++) {
            if (i<6){
                items.add(new RvItemNewsSmall(list.get(i), newsListener));
            }
        }
        if (list.size() >= 6){
            items.add(new RvItemNewsAdView());
        }
        for (int i = 6; i < list.size(); i++) {
            if (i < 11){
                items.add(new RvItemNewsSmall(list.get(i), newsListener));
            }
        }
        if (list.size() >= 11){
            items.add(new RvItemNewsAdView());
        }
        for (int i = 11; i < list.size(); i++) {
            if (i < 16){
                items.add(new RvItemNewsSmall(list.get(i), newsListener));
            }
        }
        if (list.size() >= 16){
            items.add(new RvItemNewsAdView());
        }
        for (int i = 16; i < list.size(); i++) {
            items.add(new RvItemNewsSmall(list.get(i), newsListener));
        }

        if (list.size() == 20){
            items.add(new RvItemNewsLoading(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

}
