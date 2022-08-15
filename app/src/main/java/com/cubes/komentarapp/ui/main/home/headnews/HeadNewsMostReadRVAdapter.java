package com.cubes.komentarapp.ui.main.home.headnews;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemSmallestNewsBinding;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class HeadNewsMostReadRVAdapter extends RecyclerView.Adapter<HeadNewsMostReadRVAdapter.NewsViewHolder> {

    private ArrayList<News> list;
    private NewsListener newsListener;

    public HeadNewsMostReadRVAdapter() {
    }

    @NonNull
    @Override
    public HeadNewsMostReadRVAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding =
                RvItemSmallestNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadNewsMostReadRVAdapter.NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        News news = list.get(position);

        RvItemSmallestNewsBinding binding = (RvItemSmallestNewsBinding) holder.binding;

        binding.textViewCreatedAt.setText(news.created_at.substring(11, 16));
        binding.textViewTitle.setText(news.title);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public void setMostReadData(ArrayList<News> list) {
        this.list = list;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public NewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
