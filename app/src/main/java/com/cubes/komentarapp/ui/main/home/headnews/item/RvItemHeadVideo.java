package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.graphics.Color;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.api.NewsApi;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemHeadVideoBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.squareup.picasso.Picasso;

public class RvItemHeadVideo implements RvItemHead {

    private final News news;
    private final NewsListener listener;

    public RvItemHeadVideo(News news, NewsListener listener) {
        this.news = news;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_head_video;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemHeadVideoBinding binding = (RvItemHeadVideoBinding) holder.binding;

            binding.textViewCategory.setText(news.category.name);
            binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
            binding.textViewCreatedAt.setText(news.created_at.substring(11, 16));
            binding.textViewTitle.setText(news.title);
            Picasso.get().load(news.image).into(binding.imageView);

            holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news));

    }
}