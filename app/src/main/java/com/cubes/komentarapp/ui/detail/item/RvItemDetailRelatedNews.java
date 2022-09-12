package com.cubes.komentarapp.ui.detail.item;

import android.graphics.Color;
import android.view.View;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.MethodsClass;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemDetailRelatedNews implements RvItemDetail {

    private final News news;
    private final NewsDetailListener listener;
    private final int[] newsIdList;


    public RvItemDetailRelatedNews(News news, ArrayList<News> relatedNews, NewsDetailListener listener) {
        this.news = news;
        this.listener = listener;
        this.newsIdList = MethodsClass.initNewsIdList(relatedNews);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_small_news;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemSmallNewsBinding binding = (RvItemSmallNewsBinding) holder.binding;

        binding.showMore.setOnClickListener(view -> {
            MethodsClass.animationAppear(binding.frameShowMore);
        });

        binding.closeMore.setOnClickListener(view -> {
            MethodsClass.animationDisappear(binding.frameShowMore);
        });

        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
        binding.textViewCreatedAt.setText(news.createdAt.substring(11, 16));
        binding.textViewTitle.setText(news.title);
        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news.id, news.title, newsIdList));
        binding.shareNews.setOnClickListener(view -> listener.onShareNewsClicked(news.url));
        binding.commentNews.setOnClickListener(view -> listener.onCommentNewsClicked(news.id));
    }

}
