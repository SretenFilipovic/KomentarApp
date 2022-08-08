package com.cubes.komentarapp.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.NewsData;
import com.cubes.komentarapp.databinding.RvItemBigNewsBinding;
import com.cubes.komentarapp.databinding.RvItemLoadingBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsWithHeaderAdapter extends RecyclerView.Adapter<NewsWithHeaderAdapter.NewsViewHolder> {

    private ArrayList<News> list = new ArrayList<>();
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;
    private boolean isLoading;
    private boolean isFinished;

    public NewsWithHeaderAdapter() {
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;

        if (viewType == 0){
            binding = RvItemBigNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        else if (viewType == 1) {
            binding = RvItemSmallNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        else {
            binding = RvItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        }
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (position == 0){
            News news = list.get(position);
            RvItemBigNewsBinding binding = (RvItemBigNewsBinding) holder.binding;

            binding.textViewCategory.setText(news.category.name);
            binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
            binding.textViewCreatedAt.setText(news.created_at.substring(11,16));
            binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(binding.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsListener.onNewsClicked(list.get(position));
                }
            });
        }

        else if (position>0 & position<list.size()) {
            News news = list.get(position);
            RvItemSmallNewsBinding binding = (RvItemSmallNewsBinding) holder.binding;

            binding.textViewCategory.setText(news.category.name);
            binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
            binding.textViewCreatedAt.setText(news.created_at.substring(11,16));
            binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(binding.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsListener.onNewsClicked(list.get(position));
                }
            });
        }

        else{
            RvItemLoadingBinding binding = (RvItemLoadingBinding) holder.binding;

            if (isFinished){
                binding.progressBar.setVisibility(View.GONE);
                binding.textView.setVisibility(View.GONE);
            }

            if (!isLoading & !isFinished & loadingNewsListener != null){
                isLoading = true;
                loadingNewsListener.loadMoreNews();
            }
        }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        else if (list.size() >= 20){
            return list.size() + 1;
        }
        else{
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return 0;
        }
        else if(position == list.size()){
            return 2;
        }
        else {
            return 1;
        }
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public void setLoadingNewsListener(LoadingNewsListener loadingNewsListener) {
        this.loadingNewsListener = loadingNewsListener;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void addNewNewsList(ArrayList<News> newsList){
        this.list.addAll(newsList);

        if(newsList.size()<20){
            setFinished(true);
        }

        this.isLoading = false;
        notifyDataSetChanged();
    }

    public void setData(NewsData data){
        this.list = data.news;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        public ViewBinding binding;

        public NewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
