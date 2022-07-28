package com.cubes.komentarapp.ui.detail.item;

import com.cubes.komentarapp.databinding.RvItemNewsDetailTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;

public class RvItemDetailTop implements RvItemDetail{

    private News news;

    public RvItemDetailTop(News news) {
        this.news = news;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void bind(NewsDetailAdapter.NewsDetailViewHolder holder) {

        RvItemNewsDetailTopBinding binding = (RvItemNewsDetailTopBinding) holder.binding;

            binding.textViewTitle.setText(news.title);
            binding.textViewAuthor.setText(news.author_name);
            binding.textViewCreatedAt.setText(news.created_at);
            binding.textViewDescription.setText(news.description);
            binding.textViewSource.setText(news.source);
            binding.textViewCommentCount.setText(String.valueOf(news.comments_count));
    }
}
