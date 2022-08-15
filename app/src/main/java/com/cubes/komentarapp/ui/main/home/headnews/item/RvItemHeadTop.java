package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class RvItemHeadTop implements RvItemHead {

    private final ArrayList<News> topNews;
    private final NewsListener listener;

    public RvItemHeadTop(ArrayList<News> topNews, NewsListener listener) {
        this.topNews = topNews;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        NewsAdapter adapter = new NewsAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setData(topNews);
        adapter.setNewsListener(listener);
        adapter.setFinished(true);

    }
}
