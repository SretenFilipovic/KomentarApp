package com.cubes.komentarapp.ui.main.home.headnews;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.CategoryNews;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemBigNewsHomepageBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsHomepageBinding;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HeadNewsCategoryAdapter extends RecyclerView.Adapter<HeadNewsCategoryAdapter.NewsViewHolder> {

    private ArrayList<News> list;
    private CategoryNews category;
    private NewsListener newsListener;

    public HeadNewsCategoryAdapter() {
    }

    @NonNull
    @Override
    public HeadNewsCategoryAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;

        if (viewType == 0) {
            binding = RvItemBigNewsHomepageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        } else {
            binding = RvItemSmallNewsHomepageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadNewsCategoryAdapter.NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        News news = list.get(position);

        if (position == 0) {
            RvItemBigNewsHomepageBinding binding = (RvItemBigNewsHomepageBinding) holder.binding;

            binding.textViewCategoryTitle.setText(category.title);
            binding.viewColor.setBackgroundColor(Color.parseColor(category.color));
            binding.textViewCreatedAt.setText(news.created_at.substring(11, 16));
            binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(binding.imageView);

            holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
        } else {
            RvItemSmallNewsHomepageBinding binding = (RvItemSmallNewsHomepageBinding) holder.binding;

            binding.textViewCreatedAt.setText(news.created_at.substring(11, 16));
            binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(binding.imageView);

            holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
        }

    }

    @Override
    public int getItemCount() {

        if (list == null) {
            return 0;
        }
        if (list.size() < 5) {
            return list.size();
        } else {
            return 5;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public void setCategoryData(ArrayList<News> list, CategoryNews category) {
        this.list = list;
        this.category = category;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public NewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
