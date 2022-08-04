package com.cubes.komentarapp.ui.main.home.headnews;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.databinding.RvItemHeadVideoBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HeadNewsVideoAdapter extends RecyclerView.Adapter<HeadNewsVideoAdapter.NewsViewHolder> {

    private ArrayList<News> list;
    private NewsListener newsListener;

    public HeadNewsVideoAdapter(ArrayList<News> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RvItemHeadVideoBinding binding =
                    RvItemHeadVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News news = list.get(position);

        if (position == 0){

            holder.binding.textViewCategory.setText(news.category.name);
            holder.binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
            holder.binding.textViewCreatedAt.setText(news.created_at.substring(11,16));
            holder.binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(holder.binding.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsListener.onNewsClicked(list.get(position));
                }
            });
        }

        else {
            holder.binding.frameLayout.setVisibility(View.GONE);

            holder.binding.textViewCategory.setText(news.category.name);
            holder.binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
            holder.binding.textViewCreatedAt.setText(news.created_at.substring(11,16));
            holder.binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(holder.binding.imageView);

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
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private RvItemHeadVideoBinding binding;

        public NewsViewHolder(RvItemHeadVideoBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }

}
