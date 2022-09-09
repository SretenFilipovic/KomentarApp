package com.cubes.komentarapp.ui.main.item;

import android.graphics.Color;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemBigNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemNewsBig implements RvItemNews {

    private final News news;
    private final NewsListener listener;
    private final int[] newsIdList;

    public RvItemNewsBig(News news, ArrayList<News> newsList, NewsListener listener) {
        this.listener = listener;
        this.news = news;
        this.newsIdList = MethodsClass.initNewsIdList(newsList);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_big_news;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemBigNewsBinding binding = (RvItemBigNewsBinding) holder.binding;

        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
        binding.textViewCreatedAt.setText(news.createdAt.substring(11, 16));
        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news.id, newsIdList));


    }
}
