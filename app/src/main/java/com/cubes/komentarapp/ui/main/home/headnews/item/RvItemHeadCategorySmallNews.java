package com.cubes.komentarapp.ui.main.home.headnews.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemSmallNewsHomepageBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemHeadCategorySmallNews implements RvItemHead {

    private final News news;
    private final NewsListener listener;
    private final int[] newsIdList;


    public RvItemHeadCategorySmallNews(News news, ArrayList<News> categoryNews, NewsListener listener) {
        this.listener = listener;
        this.news = news;
        this.newsIdList = MethodsClass.initNewsIdList(categoryNews);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_small_news_homepage;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemSmallNewsHomepageBinding binding = (RvItemSmallNewsHomepageBinding) holder.binding;

        binding.textViewCreatedAt.setText(news.createdAt.substring(11, 16));
        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news.id, newsIdList));

    }

}

