package com.cubes.komentarapp.ui.detail.item;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemNewsDetailTagsAndNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.NewsAdapter;
import com.cubes.komentarapp.ui.tools.listeners.NewsDetailListener;

import java.util.ArrayList;

public class RvItemDetailRelatedNews implements RvItem {

    private final ArrayList<News> relatedNews;
    private final NewsDetailListener listener;


    public RvItemDetailRelatedNews(ArrayList<News> relatedNews, NewsDetailListener listener) {
        this.relatedNews = relatedNews;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemNewsDetailTagsAndNewsBinding binding = (RvItemNewsDetailTagsAndNewsBinding) holder.binding;

        if (relatedNews == null || relatedNews.size() == 0) {
            binding.textViewTitle.setVisibility(View.GONE);
            binding.view1.setVisibility(View.GONE);
            binding.view2.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.textViewTitle.setText(R.string.text_povezane_vesti);

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            NewsAdapter adapter = new NewsAdapter();
            binding.recyclerView.setAdapter(adapter);

            adapter.setData(relatedNews);
            adapter.setFinished(true);
            adapter.setNewsListener(news -> listener.onNewsClicked(news.id));
        }

    }
}
