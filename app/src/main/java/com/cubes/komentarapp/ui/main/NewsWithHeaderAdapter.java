package com.cubes.komentarapp.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.databinding.RvItemBigNewsBinding;
import com.cubes.komentarapp.databinding.RvItemLoadingBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.data.tools.LoadingNewsListener;
import com.cubes.komentarapp.data.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// Ovaj adapter se koristi kada imamo fragment ili aktiviti u kojem se prikazuje lista vesti sa dva tipa celije
// Prvi tip treba da bude velika vest, drugi tip male vesti
// Setuje se na RV u HomePageCategoryFragment

public class NewsWithHeaderAdapter extends RecyclerView.Adapter<NewsWithHeaderAdapter.NewsViewHolder> {

    private ArrayList<News> list;
    private Context context;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;
    private int page;
    private boolean isLoading;
    private boolean isFinished;

    public NewsWithHeaderAdapter(Context context, ArrayList<News> list) {
        this.list = list;
        this.context = context;
        this.page = 2;
        this.isLoading = false;
        this.isFinished = false;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0){
            RvItemBigNewsBinding binding =
                    RvItemBigNewsBinding.inflate(LayoutInflater.from(context), parent, false);
            return new NewsViewHolder(binding);
        }
        else if (viewType == 1) {
            RvItemSmallNewsBinding binding =
                    RvItemSmallNewsBinding.inflate(LayoutInflater.from(context), parent, false);
            return new NewsViewHolder(binding);
        }
        else {
            RvItemLoadingBinding binding =
                    RvItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false);
            return new NewsViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        if (position == 0){
            News news = list.get(position);
            holder.bindingBig.textViewCategory.setText(news.category.name);
            holder.bindingBig.textViewCategory.setTextColor(Color.parseColor(news.category.color));
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

        else if (position>0 & position<list.size()) {
            News news = list.get(position);
            holder.bindingSmall.textViewCategory.setText(news.category.name);
            holder.bindingSmall.textViewCategory.setTextColor(Color.parseColor(news.category.color));
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

        else{
            if (isFinished){
                holder.bindingLoading.progressBar.setVisibility(View.INVISIBLE);
                holder.bindingLoading.textView.setVisibility(View.INVISIBLE);
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
        this.isLoading = false;
        this.page = this.page + 1;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private RvItemSmallNewsBinding bindingSmall;
        private RvItemBigNewsBinding bindingBig;
        private RvItemLoadingBinding bindingLoading;

        public NewsViewHolder(RvItemSmallNewsBinding binding) {
            super(binding.getRoot());
            this.bindingSmall = binding;
        }

        public NewsViewHolder(RvItemBigNewsBinding binding) {
            super(binding.getRoot());
            this.bindingBig = binding;
        }

        public NewsViewHolder(RvItemLoadingBinding binding) {
            super(binding.getRoot());
            this.bindingLoading = binding;
        }


    }

}
