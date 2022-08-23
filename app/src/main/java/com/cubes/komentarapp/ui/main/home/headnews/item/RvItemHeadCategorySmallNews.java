package com.cubes.komentarapp.ui.main.home.headnews.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemSmallNewsHomepageBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

public class RvItemHeadCategorySmallNews implements RvItemHead {

    private final News news;
    private final NewsListener listener;

    public RvItemHeadCategorySmallNews(News news, NewsListener listener) {
        this.listener = listener;
        this.news = news;
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

        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news));

    }

}

