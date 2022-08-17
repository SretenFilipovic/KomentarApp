package com.cubes.komentarapp.ui.main.home.headnews;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadVideoBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HeadNewsVideoAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<News> list;
    private NewsListener newsListener;

    public HeadNewsVideoAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemHeadVideoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        News news = list.get(position);
        RvItemHeadVideoBinding binding = (RvItemHeadVideoBinding) holder.binding;


        if (position == 0) {

            binding.textViewCategory.setText(news.category.name);
            binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
            binding.textViewCreatedAt.setText(news.created_at.substring(11, 16));
            binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(binding.imageView);

            holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
        } else {
            binding.frameLayout.setVisibility(View.GONE);

            binding.textViewCategory.setText(news.category.name);
            binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
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
        return list.size();
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public void setVideoData(ArrayList<News> list){
        this.list = list;
    }


}
