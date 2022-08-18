package com.cubes.komentarapp.ui.detail.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsTitleBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.RvItem;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;

public class RvItemDetailCommentsTitle implements RvItem {

    private final News news;
    private final NewsDetailListener listener;

    public RvItemDetailCommentsTitle(News news, NewsDetailListener listener) {
        this.news = news;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_news_detail_comments_title;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemNewsDetailCommentsTitleBinding binding = (RvItemNewsDetailCommentsTitleBinding) holder.binding;

        binding.textViewCommentCount.setText("(" + news.comments_count + ")");

        binding.buttonLeaveComment.setOnClickListener(view -> listener.onLeaveCommentClicked(String.valueOf(news.id)));

    }

}
