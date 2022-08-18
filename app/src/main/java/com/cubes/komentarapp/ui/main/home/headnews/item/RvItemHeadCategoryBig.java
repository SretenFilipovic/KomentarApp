package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.graphics.Color;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.CategoryNews;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemBigNewsHomepageBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.RvItem;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemHeadCategoryBig implements RvItem {

    private final News news;
    private final CategoryNews category;
    private final NewsListener listener;

    public RvItemHeadCategoryBig(News news, NewsListener listener, CategoryNews category) {
        this.listener = listener;
        this.news = news;
        this.category = category;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_big_news_homepage;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemBigNewsHomepageBinding binding = (RvItemBigNewsHomepageBinding) holder.binding;

        binding.textViewCategoryTitle.setText(category.title);
        binding.viewColor.setBackgroundColor(Color.parseColor(category.color));
        binding.textViewCreatedAt.setText(news.created_at.substring(11, 16));
        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news));


    }
}
