package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.databinding.RvItemBigNewsHomepageBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsHomepageBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// Ovaj adapter se koristi za prikaz vesti po kategorijama na stranici Naslovna
// Slican je kao i kao i NewsWithHeaderAdapter s tim sto se prikaz vesti malo razlikuje i ima maksimalno 5 clanova
// Setuje se na RV u RvItemHeadCategory

public class HeadNewsCategoryAdapter extends RecyclerView.Adapter<HeadNewsCategoryAdapter.NewsViewHolder> {

    private ArrayList<News> list;
    private Context context;
    private NewsListener newsListener;

    public HeadNewsCategoryAdapter(Context context, ArrayList<News> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HeadNewsCategoryAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0){
            RvItemBigNewsHomepageBinding binding =
                    RvItemBigNewsHomepageBinding.inflate(LayoutInflater.from(context), parent, false);
            return new NewsViewHolder(binding);
        }
        else {
            RvItemSmallNewsHomepageBinding binding =
                    RvItemSmallNewsHomepageBinding.inflate(LayoutInflater.from(context), parent, false);
            return new HeadNewsCategoryAdapter.NewsViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HeadNewsCategoryAdapter.NewsViewHolder holder, int position) {

        News news = list.get(position);

        if (position == 0){
            holder.bindingBig.textViewCategoryTitle.setText(news.category.name);
            holder.bindingBig.viewColor.setBackgroundColor(Color.parseColor(news.category.color));
            holder.bindingBig.textViewCreatedAt.setText(news.created_at.substring(11,16));
            holder.bindingBig.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(holder.bindingBig.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsListener.onNewsClicked(list.get(position));
                }
            });
        }

        else {
            holder.bindingSmall.textViewCreatedAt.setText(news.created_at.substring(11,16));
            holder.bindingSmall.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(holder.bindingSmall.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsListener.onNewsClicked(list.get(position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        if (list == null){
            return 0;
        }
        if (list.size() < 5){
            return list.size();
        }
        else{
            return 5;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return 0;
        }
        else {
            return 1;
        }
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private RvItemSmallNewsHomepageBinding bindingSmall;
        private RvItemBigNewsHomepageBinding bindingBig;

        public NewsViewHolder(RvItemSmallNewsHomepageBinding binding) {
            super(binding.getRoot());
            this.bindingSmall = binding;
        }

        public NewsViewHolder(RvItemBigNewsHomepageBinding binding) {
            super(binding.getRoot());
            this.bindingBig = binding;
        }
    }
}
