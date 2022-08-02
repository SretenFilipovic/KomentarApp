package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.databinding.RvItemSmallestNewsBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class HeadNewsMostReadRVAdapter extends RecyclerView.Adapter<HeadNewsMostReadRVAdapter.NewsViewHolder>{

    private ArrayList<News> list;
    private Context context;
    private NewsListener newsListener;

    public HeadNewsMostReadRVAdapter(Context context, ArrayList<News> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HeadNewsMostReadRVAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemSmallestNewsBinding binding =
                RvItemSmallestNewsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadNewsMostReadRVAdapter.NewsViewHolder holder, int position) {

        News news = list.get(position);

        holder.binding.textViewCreatedAt.setText(news.created_at.substring(11,16));
        holder.binding.textViewTitle.setText(news.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsListener.onNewsClicked(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private RvItemSmallestNewsBinding binding;

        public NewsViewHolder(RvItemSmallestNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
