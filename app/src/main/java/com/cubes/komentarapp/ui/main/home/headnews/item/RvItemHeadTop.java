package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.graphics.Color;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemHeadTop implements RvItemHead {

    private final News news;
    private final NewsListener listener;
    private final int[] newsIdList;

    public RvItemHeadTop(News news, ArrayList<News> topNews, NewsListener listener) {
        this.news = news;
        this.listener = listener;
        this.newsIdList = MethodsClass.initNewsIdList(topNews);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_small_news;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemSmallNewsBinding binding = (RvItemSmallNewsBinding) holder.binding;

        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
        binding.textViewCreatedAt.setText(news.createdAt.substring(11, 16));
        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news.id, news.title, newsIdList));
    }
}
