package com.cubes.komentarapp.ui.detail.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.api.NewsApi;
import com.cubes.komentarapp.data.model.domain.NewsDetail;
import com.cubes.komentarapp.databinding.RvItemNewsDetailCommentsTitleBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;

public class RvItemDetailCommentsTitle implements RvItemDetail {

    private final NewsDetail news;
    private final NewsDetailListener listener;

    public RvItemDetailCommentsTitle(NewsDetail news, NewsDetailListener listener) {
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
