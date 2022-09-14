package com.cubes.komentarapp.ui.savednews.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.MyNews;
import com.cubes.komentarapp.databinding.RvItemSavedNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.MyNewsListener;

public class RvItemMyNewsView implements RvItemMyNews {

    private final MyNews news;
    private final MyNewsListener listener;
    public RvItemSavedNewsBinding binding;
    private int[] newsIdList;


    public RvItemMyNewsView(MyNews news, int[] newsIdList, MyNewsListener listener) {
        this.news = news;
        this.listener = listener;
        this.newsIdList = newsIdList;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_saved_news;
    }

    @Override
    public void bind(ViewHolder holder) {

        binding = (RvItemSavedNewsBinding) holder.binding;
        binding.textViewTitle.setText(news.title);
        holder.itemView.setOnClickListener(view -> listener.onNewsClicked(news.id, newsIdList));
    }

    @Override
    public int getNewsId() {
        return news.id;
    }
}
