package com.cubes.komentarapp.ui.main.home.headnews;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemSmallestNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

import java.util.ArrayList;

public class HeadNewsMostReadRVAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<News> list;
    private NewsListener newsListener;

    public HeadNewsMostReadRVAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemSmallestNewsBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        News news = list.get(position);

        RvItemSmallestNewsBinding binding = (RvItemSmallestNewsBinding) holder.binding;

        binding.textViewCreatedAt.setText(news.createdAt.substring(11, 16));
        binding.textViewTitle.setText(news.title);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news.id, MethodsClass.initNewsIdList(list)));
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


}
