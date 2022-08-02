package com.cubes.komentarapp.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.databinding.RvItemLoadingBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.tools.LoadingNewsListener;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<News> list;
    private Context context;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;
    private int page;
    private boolean isLoading;
    private boolean isFinished;

    public NewsAdapter(Context context, ArrayList<News> list) {
        this.list = list;
        this.context = context;
        this.page = 2; // 2 je zato sto ucitavanje novih vesti treba da krene od druge strane
        this.isLoading = false;
        this.isFinished = false;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 0){
            RvItemSmallNewsBinding binding =
                    RvItemSmallNewsBinding.inflate(LayoutInflater.from(context), parent, false);
            return new NewsViewHolder(binding);
        }
        else{
            RvItemLoadingBinding binding =
                    RvItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false);
            return new NewsViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (position < list.size()){
            News news = list.get(position);
            holder.bindingNews.textViewCategory.setText(news.category.name);
            holder.bindingNews.textViewCategory.setTextColor(Color.parseColor(news.category.color));
            holder.bindingNews.textViewCreatedAt.setText(news.created_at.substring(11,16));
            holder.bindingNews.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(holder.bindingNews.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsListener.onNewsClicked(list.get(position));
                }
            });
        }

        else{

            if (isFinished){
                holder.bindingLoading.progressBar.setVisibility(View.GONE);
                holder.bindingLoading.textView.setVisibility(View.GONE);
            }

            if (!isLoading & !isFinished & loadingNewsListener != null){
                isLoading = true;
                loadingNewsListener.loadMoreNews(page);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == list.size()){
            return 1;
        }
        return 0;
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
        this.isLoading = false;
        this.page = this.page + 1;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private RvItemSmallNewsBinding bindingNews;
        private RvItemLoadingBinding bindingLoading;

        public NewsViewHolder(RvItemSmallNewsBinding binding) {
            super(binding.getRoot());

            this.bindingNews = binding;
        }

        public NewsViewHolder(RvItemLoadingBinding binding) {
            super(binding.getRoot());

            this.bindingLoading = binding;
        }

    }

}
